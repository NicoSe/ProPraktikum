package Network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

public class Client {

    private int port = 50000;
    private InetSocketAddress address;
    Socket client;

    public Client(String host) {
        new Thread(new Runnable() {
            public void run() {
                address = new InetSocketAddress(host, port);                //save current server address for reconnection
                System.out.println("Searching for Server");
                try {
                    client = new Socket();                           //create socket
                    client.connect(address, 10000);                 //connect to server
                } catch (SocketException e) {
                    System.out.println("Can´t create Socket!");
                    e.printStackTrace();
                } catch (IOException e) {
                    System.out.println("Can´t find server at " + address);
                    e.printStackTrace();
                }
                System.out.println("Connect to server at " + address + " via " + client.getLocalPort());
            }
        }).start();
    }

    public void sendmsg(String msg) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Socket socket = new Socket();                           //create socket
                    socket.connect(address, 10000);                 //connect to server
                } catch (SocketException e) {
                    System.out.println("Can´t create socket!");
                    e.printStackTrace();
                } catch (IOException e) {
                    System.out.println("Can´t find server at " + address + " via " + client.getLocalPort());
                    e.printStackTrace();
                }

                try {
                    DataOutputStream stream_out = new DataOutputStream(client.getOutputStream());
                    stream_out.writeUTF(msg);

                    client.close();
                } catch (IOException e) {
                    System.out.println("Can´t send message!");
                    e.printStackTrace();
                }
                listenToNetwork();
            }
        }).start();
    }

    public void listenToNetwork(){
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        Socket socket = new Socket();                           //create socket
                        socket.connect(address, 10000);                 //connect to server

                        DataInputStream stream_in = new DataInputStream(client.getInputStream());              //get message from inputstream
                        if (analyze(stream_in.readUTF()) == true) break;                 //analyze message from client

                        client.close();
                    } catch (SocketException e) {
                        System.out.println("Can´t find Server!");
                        e.printStackTrace();
                    } catch (IOException e) {
                        System.out.println("Can´t read message from client or don´t get one!");
                        e.printStackTrace();
                    }
                }
            }

            //analyze messages from server and call system function
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
                                return true;
                            case "1":
                                return true;
                            case "2":
                                return true;
                        }
                    case "SAVE":
                        return true;
                }
                return false;
            }
        }).start();
    }
}
