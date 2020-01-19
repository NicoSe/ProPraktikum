package Network;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;


public class Server2 implements Connector{

    private int port = 50000;
    private ServerSocket Server_Socket;
    private Socket Client_Socket;
    BufferedReader usr;

    //mulithreading support.
    DataInputStream dis;
    DataOutputStream dos;

    //Speichert die Addresse des Servers so ist ein eventuelles Wiederverbinden leichter.
    private InetSocketAddress address;

    //Abbruchvariable für die listenToNetwork()-Funktion
    public boolean Close_Socket = false;
    private boolean turn = false;

//______________________________________________________________________________________________________________________
    //Erstellt zuerst den Server, dieser wartet bis ein Client sich verbindet sollte sich
    //keiner verbinden wird abgebrochen. Wird einer gefunden kann dieser aktzeptiert werden.
    public Server2() {
    }

//______________________________________________________________________________________________________________________
    @Override
    public void connect(){
        try {
            Close_Socket = false;
            System.out.println("<S>Starting Server...");
            Server_Socket = new ServerSocket(port);       //create Server

            System.out.println("<S>Wait for connection at Port:" + Server_Socket.getLocalPort());

            Client_Socket = Server_Socket.accept();                    //accept client

            dis = new DataInputStream(Client_Socket.getInputStream());
            dos = new DataOutputStream(Client_Socket.getOutputStream());
            turn = true;
            System.out.println("<S>Client connected.");
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }



//______________________________________________________________________________________________________________________
    //Gibt zurück ob Server verbunden
    public boolean isConnected(){
        return Client_Socket != null && Client_Socket.isConnected();
    }



//______________________________________________________________________________________________________________________
    //Sendet eine Nachicht zum Server, diese muss dem Protokoll entsprechen.
    //Es muss ausschlieslich eine Nachricht in die Funktion uebergeben werden.
    public void sendmsg(String msg){
        try{
            dos.writeUTF(msg);
            turn = false;
        }catch(SocketException e) {
            System.out.println("<S>Can´t find Server!");
            e.printStackTrace();
            Close();
            connect();
            sendmsg(msg);
        }catch(IOException e){
            System.out.println("<S>Message can´t be send!");
            e.printStackTrace();
            Close();
            connect();
            sendmsg(msg);
        }catch(NullPointerException e){
            System.out.println("<S>NullPointException");
            e.printStackTrace();
            Close();
            connect();
            sendmsg(msg);
        }
    }


//______________________________________________________________________________________________________________________
    //Ist die Funktion, die den Server anweist das Netwerk zu ueberwachen, bei
    //lesen einer Nachicht vom Client wird analyze() ausgefuehrt um die
    //Nachicht zu deuten. Die Funktion kann ueber die Variable
    //Closed_Socket=true abgebrochen werden, dies geschiet auch beim Beenden(Close())
    //des Servers.
    public String listenToNetwork(){
        while(true){
            if (Close_Socket == true){
                Close_Socket = false;
                break;
            }
            try {
                String stream = dis.readUTF();
                System.out.println("<S><<< " + stream);
                if (analyze(stream)) {
                    turn = true;
                    return stream;
                }
            }catch(SocketException e){
                System.out.println("<S>Can´t find client!");
                e.printStackTrace();
                Close();
                connect();
            } catch (IOException e) {
                System.out.println("<S>Can´t read message from client or don´t get one!");
                e.printStackTrace();
                Close();
                connect();
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
        return ConnectorType.SERVER;
    }


    //______________________________________________________________________________________________________________________
    //Analysiert Nachichten vom Client und fuehrt je nach dem Spielbefehle aus:
    //CONFIRM: Bestaetigung des Spielbeginns
    //ANSWER: Info ueber Trefferstatus eines Schusses
    //SHOOT: Schuss vom Server mit x und y Koordinate
    //SAVE: Befehl zum speichern des aktuellen Spiels, uebergibt Name der Datei
    //LOAD: Befehl zum Laden eines bestimmten Spielstandes, uebergibt Name der Datei
    //PASS: passen des Zuges
    private boolean analyze(String msg){
        String[] words = msg.split("\\s+");
        words[0] = words[0].toLowerCase();
        switch(words[0]) {
            case "shoot":
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
    // Beenden des Serverockets, bei Erfolg true, ansonsten false. Beendet zudem
    //auch die listenToNetwork() Funktion durch Closed_Socket=true.
    public boolean Close(){
        try {
            Close_Socket = true;
            Server_Socket.close();
            Client_Socket.close();
        } catch (IOException e) {
            System.out.println("<S>Sockets couldnßt be closed!");
            return false;
        }
        return true;
    }
}
