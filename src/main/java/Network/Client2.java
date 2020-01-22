package Network;

import java.io.*;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class Client2 implements Connector {

    private int port = 50000;
    Socket client;
    BufferedReader usr;

    ///mulithreading support.
    DataInputStream dis;
    DataOutputStream dos;

    ///Speichert die Addresse des Servers so ist ein eventuelles Wiederverbinden leichter.
    private InetSocketAddress address;

    ///Abbruchvariable für die listenToNetwork()-Funktion
    public boolean Close_Socket = false;
    private boolean turn = false;

    private String host;

//______________________________________________________________________________________________________________________
    /**
     *     Erstellt zuerst eine Addresse, die sich aus der IP-Addresse des Servers
     *     und dem Port zusammensetzt. Danach wird ein Socket erstellt der sich
     *     auf diese Addresse einwaehlt. Sollte kein Server gefunden werden,
     *     wird abgebrochen.
     */
    public Client2(String host) {
        if(host.isEmpty() || host.equals("") || host == null || host.length()<10) return;
        if(host.contains(".")){
            this.host = host;
            connect();
        }

    }


//______________________________________________________________________________________________________________________
    @Override
    public void connect(){
        try {
            Close_Socket = false;
            address = new InetSocketAddress(host, port);
            System.out.println("<C>Searching for Server");

            client = new Socket();
            client.connect(address, 5000);
            dis = new DataInputStream(client.getInputStream());
            dos = new DataOutputStream(client.getOutputStream());
            System.out.println("<C>Connect to server at " + address + " via " + client.getLocalPort());
        } catch (ConnectException e) {
            System.out.println("Couldn't connect. retrying...");
            Close();
            connect();
        } catch (SocketException e) {
            System.out.println("<C>Can´t create Socket!");
            e.printStackTrace();
            Close();
        } catch (IOException e) {
            System.out.println("<C>Can´t find server at " + address);
            e.printStackTrace();
            Close();
        }
    }


//______________________________________________________________________________________________________________________
    /**Sendet eine Nachicht zum Server, diese muss dem Protokoll entsprechen.
     *  Es muss ausschlieslich eine Nachicht in die Funktion uebergeben werden.
     */
    public void sendmsg(String msg) {
        try {
            dos.writeUTF(msg);
            turn = false;
        } catch (IOException e) {
            System.out.println("<C>Can´t send message!");
            e.printStackTrace();
            Close();
            connect();
            sendmsg(msg);
        }
    }



//______________________________________________________________________________________________________________________
    ///Gibt zurück ob Client verbunden
    @Override
    public boolean isConnected(){
        return !Close_Socket;
    }


//______________________________________________________________________________________________________________________
    /**ist die Funktion die den Client anweist das Netwerk zu ueberwachen, bei
     *lesen einer Nachicht vom Server wird analyze() ausgefuehrt um die
     *Nachicht zu deuten. Die Funktion kann ueber die Variable
     *Closed_Socket=true abgebrochen werden, dies geschiet auch beim Beenden(Close())
     *des Clients.
     */
    public String listenToNetwork(){
        while (true) {
            if (Close_Socket){
                break;
            }
            try {
                String stream = dis.readUTF().toLowerCase();
                System.out.println("<C><<< " + stream);
                if (analyze(stream)) {
                    turn = true;
                    return stream;
                }
            } catch(EOFException e) {
                System.out.println("<C>Can´t read from socket.");
                Close();
            } catch (SocketException e) {
                System.out.println("<C>Can´t find Server!");
                e.printStackTrace();
                Close();
                //connect();
            } catch (IOException e) {
                System.out.println("<C>Can´t read message from client or donÂ´t get one!");
                e.printStackTrace();
                Close();
                //connect();
            } finally {
                if (Close_Socket){
                    break;
                }
            }
        }
        return "";
    }

    @Override
    public boolean turn() {
        return turn;
    }

    @Override
    public ConnectorType getConnectorType() {
        return ConnectorType.CLIENT;
    }

//______________________________________________________________________________________________________________________
    /**
     * Analysiert Nachichten vom Server und fuehrt je nach dem Spielbefehle aus:
     *     SIZE: Erstellen des Spielfeldes mit Groeßenangabe
     *     CONFIRM: Bestaetigung des Spielbeginns
     *     ANSWER: Info ueber Trefferstatus eines Schusses
     *     SHOOT: Schuss vom Server mit x und y Koordinate
     *     SAVE: Befehl zum speichern des aktuellen Spiels, uebergibt Name der Datei
     *     LOAD: Befehl zum Laden eines bestimmten Spielstandes, uebergibt Name der Datei
     *     PASS: passend es Zuges
     */
    private boolean analyze(String msg){
        String[] words = msg.split("\\s+");
        words[0] = words[0].toLowerCase();
        switch(words[0]) {
            case "size":
            case "shot":
            case "confirmed":
            case "save":
            case "load" :
            case "pass":
                return true;
            case "answer":
                switch (words[1].toUpperCase()) {
                    case "0":
                    case "1":
                    case "2":
                        return true;
                }
        }
        return false;
    }


//______________________________________________________________________________________________________________________
    /**
     *     Beenden des Clientsockets, bei Erfolg true, ansonsten false. Beendet zudem
     *     auch die listenToNetwork() Funktion durch Closed_Socket=true.
     */
    public boolean Close(){
        try {
            Close_Socket = true;
            if(dis != null) {
                dis.close();
            }
            if(dos != null) {
                dos.close();
            }
            if(client != null) {
                client.close();
            }
        } catch (IOException e) {
            System.out.println("<C>Client couldn´t be closed!");
            return false;
        }
        return true;
    }
}
