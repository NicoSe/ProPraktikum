package Network;

import java.io.*;
import java.net.*;


public class Server{

    private ServerSocket Server_Socket;
    private Socket Client_Socket;
    private int port = 50000;
    public boolean Close_Socket = false;
    BufferedReader usr;

    public static void main(String[] args) {
        Server s = new Server();
        s.sendmsg();
        return;
    }

    public Server () {
        try{
            System.out.println("<S>Starting Server...");
            Server_Socket = new ServerSocket(port);       //create Server

            System.out.println("<S>Wait for connection at Port:"+ Server_Socket.getLocalPort());
            Server_Socket.setSoTimeout(1000000);                    //set timeout

            usr = new BufferedReader(new InputStreamReader(System.in));

            Client_Socket = Server_Socket.accept();                    //accept client
            System.out.println("<S>Client connected.");
        } catch(SocketException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }catch(NullPointerException e){
            e.printStackTrace();
        }
    }

    //method should be called from outside, it automatically listen to the client after sending the message
    public void sendmsg(){
        try{
            DataOutputStream stream_out = new DataOutputStream((Client_Socket.getOutputStream()));
            String msg = usr.readLine();
            System.out.println("<S>>>> " + msg);
            stream_out.writeUTF(msg);                                        //send message
        }catch(SocketException e) {
            System.out.println("<S>Can´t find Server!");
            e.printStackTrace();
        }catch(IOException e){
            System.out.println("<S>Message can´t be send!");
        }catch(NullPointerException e){
            e.printStackTrace();
        }
        listenToNetwork();
    }


    public void listenToNetwork(){
        System.out.println("<S> Server is listening:");
        while(true){
            if (Close_Socket == true){
                Close_Socket = false;
                try {
                    Server_Socket.close();
                    Client_Socket.close();
                } catch (IOException e) {
                    System.out.println("Sockets can´t be closed!");
                    //e.printStackTrace();
                }
                break;
            }

            try {
                DataInputStream stream_in = new DataInputStream(Client_Socket.getInputStream());              //get message from inputstream
                System.out.println("<S><<< " + stream_in.readUTF());
                if (analyze(stream_in.readUTF()) == true) break;                 //analyze message from client
            }catch(SocketException e){
                System.out.println("<S>Can´t find client!");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("<S>Can´t read message from client or don´t get one!");
                e.printStackTrace();
            }
        }
        System.out.println("<S> Server is sending");
        sendmsg();
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
                    case "SIZE" :
                        System.out.println(words[1]);
                        return true;
                }
                return false;
            }
}
