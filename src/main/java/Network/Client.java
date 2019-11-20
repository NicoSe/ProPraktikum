package Network;

import java.io.*;
import java.net.*;

public class Client {

    private int port = 50000;
    private InetSocketAddress address;
    Socket client;
    public boolean Close_Socket = false;
    BufferedReader usr;


    public static void main(String[] args) {
        Client client = new Client("localhost");
        client.listenToNetwork();
        return;
    }


    public Client(String host) {
        try {
            address = new InetSocketAddress(host, port);                 //save current server address for reconnection
            System.out.println("<C>Searching for Server");

            client = new Socket();                                       //create socket
            client.connect(address, 10000);                      //connect to server
            usr = new BufferedReader(new InputStreamReader(System.in));
        } catch (SocketException e) {
            System.out.println("<C>CanÂ´t create Socket!");
            //e.printStackTrace();
        } catch (IOException e) {
            System.out.println("<C>CanÂ´t find server at " + address);
            //e.printStackTrace();
        }
        System.out.println("<C>Connect to server at " + address + " via " + client.getLocalPort());
    }


    public void sendmsg() {
        try {
            DataOutputStream stream_out = new DataOutputStream(client.getOutputStream());
            String msg = usr.readLine();
            System.out.println("<C>>>> " + msg);
            stream_out.writeUTF(msg);
        } catch (IOException e) {
            System.out.println("<C>CanÂ´t send message!");
            //e.printStackTrace();
        }
        listenToNetwork();
    }


    public void listenToNetwork(){
        System.out.println("<C> Client is listening.");
        while (true) {
            if (Close_Socket == true){
                Close_Socket = false;
                try {
                    client.close();
                } catch (IOException e) {
                    System.out.println("Sockets can´t be closed!");
                    //e.printStackTrace();
                }
                break;
            }

            try {
                DataInputStream stream_in = new DataInputStream(client.getInputStream());              //get message from inputstream
                System.out.println("<C><<< " + stream_in.readUTF());
                if (analyze(stream_in.readUTF()) == true) break;                 //analyze message from client
            } catch (SocketException e) {
                System.out.println("<C>CanÂ´t find Server!");
                //e.printStackTrace();
            } catch (IOException e) {
                System.out.println("<C>CanÂ´t read message from client or donÂ´t get one!");
                //e.printStackTrace();
            }
        }
        System.out.println("<C> Client is sending.");
        sendmsg();
    }


    //analyze messages from server and call system function
    private boolean analyze(String msg){
        String[] words = msg.split("\\s+");
        words[0] = words[0].toUpperCase();
        System.out.println("geht");
        switch(words[0]) {
            case "SHOOT":
                return true;
            case "CONFIRM":
                return true;
            case "ANSWER":
                switch (words[1].toUpperCase()) {
                    case "0":
                        return true;
                    case "1":
                        return true;
                    case "2":
                        return true;
                }
            case "SAVE":
                return true;
            case "SIZE":
                System.out.println(words[1]);
                return true;
        }
        return false;
    }
}
