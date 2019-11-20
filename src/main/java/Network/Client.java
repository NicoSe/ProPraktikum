package Network;

import java.io.*;
import java.net.*;

public class Client {

    private int port = 50000;
    Socket client;
    BufferedReader usr;

    //Speichert die Addresse des Servers so ist ein eventuelles Wiederverbinden leichter.
    private InetSocketAddress address;

    //Abbruchvariable für die listenToNetwork()-Funktion
    public boolean Close_Socket = false;

//______________________________________________________________________________________________________________________

    public static void main(String[] args) {
        Client client = new Client("localhost");
        client.listenToNetwork();
        return;
    }


//______________________________________________________________________________________________________________________
    //Erstellt zuerst eine Addresse, die sich aus der IP-Addresse des Servers
    // und dem Port zusammensetzt. Danach wird ein Socket erstellt der sich
    // auf diese Addresse einwaehlt. Sollte kein Server gefunden werden,
    //wird abgebrochen.
    public Client(String host) {
        try {
            address = new InetSocketAddress(host, port);
            System.out.println("<C>Searching for Server");

            client = new Socket();
            client.connect(address, 10000);
            usr = new BufferedReader(new InputStreamReader(System.in));
        } catch (SocketException e) {
            System.out.println("<C>Can´t create Socket!");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("<C>Can´t find server at " + address);
            e.printStackTrace();
        }
        System.out.println("<C>Connect to server at " + address + " via " + client.getLocalPort());
    }


//______________________________________________________________________________________________________________________
    //Sendet eine Nachicht zum Server, diese muss dem Protokoll entsprechen.
    //Es muss ausschlieslich eine Nachicht in die Funktion uebergeben werden.
    public void sendmsg() {
        try {
            DataOutputStream stream_out = new DataOutputStream(client.getOutputStream());
            System.out.print("<C>>>> ");
            String msg = usr.readLine();
            stream_out.writeUTF(msg);
        } catch (IOException e) {
            System.out.println("<C>Can´t send message!");
            e.printStackTrace();
        }
        listenToNetwork();
    }


//______________________________________________________________________________________________________________________
    //ist die Funktion die den Client anweist das Netwerk zu ueberwachen, bei
    //lesen einer Nachicht vom Server wird analyze() ausgefuehrt um die
    //Nachicht zu deuten. Die Funktion kann ueber die Variable
    //Closed_Socket=true abgebrochen werden, dies geschiet auch beim Beenden(Close())
    //des Clients.
    public void listenToNetwork(){
        while (true) {
            if (Close_Socket == true){
                Close_Socket = false;
                break;
            }
            try {
                DataInputStream stream_in = new DataInputStream(client.getInputStream());
                String stream = stream_in.readUTF();
                System.out.println("<C><<< " + stream);
                if (analyze(stream)) break;
            } catch (SocketException e) {
                System.out.println("<C>Can´t find Server!");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("<C>Can´t read message from client or donÂ´t get one!");
                e.printStackTrace();
            }
        }
        sendmsg();
    }


//______________________________________________________________________________________________________________________
    //Analysiert Nachichten vom Server und fuehrt je nach dem Spielbefehle aus:
    //SIZE: Erstellen des Spielfeldes mit Groeßenangabe
    //CONFIRM: Bestaetigung des Spielbeginns
    //ANSWER: Info ueber Trefferstatus eines Schusses
    //SHOOT: Schuss vom Server mit x und y Koordinate
    //SAVE: Befehl zum speichern des aktuellen Spiels, uebergibt Name der Datei
    //LOAD: Befehl zum Laden eines bestimmten Spielstandes, uebergibt Name der Datei
    //PASS: passend es Zuges
    private boolean analyze(String msg){
        String[] words = msg.split("\\s+");
        words[0] = words[0].toUpperCase();
        switch(words[0]) {
            case "SHOOT":
                return true;
            case "CONFIRM":
                return true;
            case "ANSWER":
                switch (words[1].toUpperCase()) {
                    case "0":
                        listenToNetwork();
                        return true;
                    case "1":
                        return true;
                    case "2":
                        return true;
                }
            case "SAVE":
                Close_Socket = true;
                return true;
            case "SIZE":
                System.out.println(words[1]);
                return true;
        }
        return false;
    }


//______________________________________________________________________________________________________________________
    // Beenden des Clientsockets, bei Erfolg true, ansonsten false. Beendet zudem
    //auch die listenToNetwork() Funktion durch Closed_Socket=true.
    public boolean Close(){
        try {
            Close_Socket = true;
            client.close();
        } catch (IOException e) {
            System.out.println("<C>Client couldn´t be closed!");
            return false;
        }
        return true;
    }
}
