package Network;

import java.io.*;
import java.net.*;


public class Server{

    private ServerSocket Server_Socket;
    private int port = 50000;

    public Server () {
        new Thread(new Runnable(){
            public void run() {
                try{
                    System.out.println("Starting Server...");
                    Server_Socket = new ServerSocket(port);                //create Server
                    System.out.println("Wait for connection at Port:"+ Server_Socket.getLocalPort());
                    Server_Socket.setSoTimeout(100000);                    //set timeout
                } catch(SocketException e){
                } catch(IOException e){}
            }
        }).start();
    }

    //method should be called from outside, it automatically listen to the client after sending the message
    public void sendmsg(String msg) throws IOException {
        new Thread(new Runnable(){
            public void run(){
                    try{
                        Socket Client_Socket = Server_Socket.accept();                    //accept client
                        System.out.println("Client connected.");

                         DataOutputStream stream_out = new DataOutputStream((Client_Socket.getOutputStream()));
                         stream_out.writeUTF(msg);                                        //send message

                         Client_Socket.close();
                    }catch(SocketException e) {
                        System.out.println("Can´t find Server!");
                        e.printStackTrace();
                    }catch(IOException e){
                        System.out.println("Message can´t be send!");
                    }
                }
        }).start();
    }


    public void listenToNetwork(){
        new Thread(new Runnable(){
            public void run() {
                while(true){
                    try {
                        Socket Client_Socket = Server_Socket.accept();                 //accept client
                        System.out.println("Client connected.");

                        DataInputStream stream_in = new DataInputStream(Client_Socket.getInputStream());              //get message from inputstream
                        if (analyze(stream_in.readUTF()) == true) break;                 //analyze message from client

                        Client_Socket.close();
                    }catch(SocketException e){
                        System.out.println("Can´t find client!");
                        e.printStackTrace();
                    } catch (IOException e) {
                        System.out.println("Can´t read message from client or don´t get one!");
                        e.printStackTrace();
                    }
                }
            }

            //analyze messages from client and call system function
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
