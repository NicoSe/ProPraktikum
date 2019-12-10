package Network;

import Control.Konsolenanwendung;
import Logic.Grid2D;
import Logic.Load;
import Logic.Save;
import Logic.ShotResult;

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

    private String host;

//______________________________________________________________________________________________________________________
    //Erstellt zuerst eine Addresse, die sich aus der IP-Addresse des Servers
    // und dem Port zusammensetzt. Danach wird ein Socket erstellt der sich
    // auf diese Addresse einwaehlt. Sollte kein Server gefunden werden,
    //wird abgebrochen.
    public Client() {
        this.host = "localhost";
        Create_Client();
    }


//______________________________________________________________________________________________________________________
    public void Create_Client(){
        try {
            Close_Socket = false;
            address = new InetSocketAddress(host, port);
            System.out.println("<C>Searching for Server");

            client = new Socket();
            client.connect(address, 10000);
            usr = new BufferedReader(new InputStreamReader(System.in));
        } catch (SocketException e) {
            System.out.println("<C>Can´t create Socket!");
            e.printStackTrace();
            Close();
        } catch (IOException e) {
            System.out.println("<C>Can´t find server at " + address);
            e.printStackTrace();
            Close();
        }
        System.out.println("<C>Connect to server at " + address + " via " + client.getLocalPort());
        listenToNetwork();
    }


//______________________________________________________________________________________________________________________
    //Sendet eine Nachicht zum Server, diese muss dem Protokoll entsprechen.
    //Es muss ausschlieslich eine Nachicht in die Funktion uebergeben werden.
    public void sendmsg(String msg) {
        try {
            DataOutputStream stream_out = new DataOutputStream(client.getOutputStream());
            stream_out.writeUTF(msg);
        } catch (IOException e) {
            System.out.println("<C>Can´t send message!");
            e.printStackTrace();
            Close();
            Create_Client();
            sendmsg(msg);
        }
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
                Close();
                Create_Client();
            } catch (IOException e) {
                System.out.println("<C>Can´t read message from client or donÂ´t get one!");
                e.printStackTrace();
                Close();
                Create_Client();
            }
        }
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
            case "size":
                Konsolenanwendung.a = new Grid2D(Integer.parseInt(words[1]));
                Konsolenanwendung.a.generateRandom();
                Konsolenanwendung.b = new Grid2D(Integer.parseInt(words[1]));
                System.out.printf("Grid A:\n%s\n", Konsolenanwendung.a);
                System.out.printf("Grid B:\n%s\n", Konsolenanwendung.b);
                return true;
            case "shoot":
                ShotResult result = Konsolenanwendung.a.shoot(Integer.parseInt(words[1]),Integer.parseInt(words[2]));
                if(result == ShotResult.HIT) {
                    sendmsg("answer 1");
                    return false;
                }
                else if(result == ShotResult.SUNK) {
                    sendmsg("answer 2");
                    return false;
                }
                else if(result == ShotResult.NONE) {
                    sendmsg("answer 0");
                    return true;
                }
                return false;
            case "confirm":
                return true;
            case "answer":
                switch (words[1].toUpperCase()) {
                    case "0":
                        sendmsg("pass");
                        return false;
                    case "1":
                        //Konsolenanwendung.b.shoot()
                        return true;
                    case "2":
                        //Konsolenanwendung.b.shoot()
                        return true;
                }
            case "pass":
                return true;
            case "save":
                new Save(words[1]);
                Close();
                return true;
            case "load" :
                Load.load(words[1], false);
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
