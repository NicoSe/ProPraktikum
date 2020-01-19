package Network;

import Control.Konsolenanwendung;
import Logic.Load;
import Logic.Save;
import Logic.ShotResult;

import java.io.*;
import java.net.*;


public class Server {

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

//______________________________________________________________________________________________________________________
    //Erstellt zuerst den Server, dieser wartet bis ein Client sich verbindet sollte sich
    //keiner verbinden wird abgebrochen. Wird einer gefunden kann dieser aktzeptiert werden.
    public Server () {
        Create_Server();
    }

//______________________________________________________________________________________________________________________
    private void Create_Server(){
        new Thread(() -> {
            try {
                Close_Socket = false;
                System.out.println("<S>Starting Server...");
                Server_Socket = new ServerSocket(port);       //create Server

                System.out.println("<S>Wait for connection at Port:" + Server_Socket.getLocalPort());
                Server_Socket.setSoTimeout(10000);                    //set timeout

                usr = new BufferedReader(new InputStreamReader(System.in));

                Client_Socket = Server_Socket.accept();                    //accept client
                System.out.println("<S>Client connected.");
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }).start();
    }



//______________________________________________________________________________________________________________________
    //Gibt zurück ob Server verbunden
    public boolean isconnected(){
        return Client_Socket.isConnected();
    }



//______________________________________________________________________________________________________________________
    //Sendet eine Nachicht zum Server, diese muss dem Protokoll entsprechen.
    //Es muss ausschlieslich eine Nachricht in die Funktion uebergeben werden.
    public void sendmsg(String msg){
        try{
            DataOutputStream stream_out = new DataOutputStream((Client_Socket.getOutputStream()));
            stream_out.writeUTF(msg);
        }catch(SocketException e) {
            System.out.println("<S>Can´t find Server!");
            e.printStackTrace();
            Close();
            Create_Server();
            sendmsg(msg);
        }catch(IOException e){
            System.out.println("<S>Message can´t be send!");
            e.printStackTrace();
            Close();
            Create_Server();
            sendmsg(msg);
        }catch(NullPointerException e){
            System.out.println("<S>NullPointException");
            e.printStackTrace();
            Close();
            Create_Server();
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
                DataInputStream stream_in = new DataInputStream(Client_Socket.getInputStream());
                String stream = stream_in.readUTF();
                System.out.println("<S><<< " + stream);
                if (analyze(stream)) return stream;
            }catch(SocketException e){
                System.out.println("<S>Can´t find client!");
                e.printStackTrace();
                Close();
                Create_Server();
            } catch (IOException e) {
                System.out.println("<S>Can´t read message from client or don´t get one!");
                e.printStackTrace();
                Close();
                Create_Server();
            }
        }
        return "";
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
