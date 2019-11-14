package Network;

import java.io.*;
import java.net.*;


public class Server{

    private ServerSocket Server_Socket;

    public Server (int port) {
        new Thread(new Runnable(){
            public void run() {
                try{
                    Server_Socket = new ServerSocket(port);
                    System.out.println("Server startet...");
                    System.out.println("Warte auf Verbidnung...");
                    Server_Socket.setSoTimeout(100000);
                } catch(SocketException e){
                } catch(IOException e){}
            }
        }).start();
    }


    public void sendmsg(String msg) throws IOException {
        new Thread(new Runnable(){
            public void run(){
                    try{
                        Socket Client_Socket = Server_Socket.accept();
                        System.out.println("Client verbunden.");

                         DataOutputStream stream_out = new DataOutputStream((Client_Socket.getOutputStream()));
                         stream_out.writeUTF(msg);

                         Client_Socket.close();
                    }catch(Exception e) {
                        e.printStackTrace();
                    }
                }
        }).start();
    }


    public void listenToNetwork(){
        new Thread(new Runnable(){
            public void run() {
                while(true){
                    try {
                        Socket Client_Socket = Server_Socket.accept();
                        System.out.println("Client verbunden.");

                        DataInputStream stream_in = new DataInputStream(Client_Socket.getInputStream());
                        if(analyze(stream_in.readUTF()) == true) break;

                        Client_Socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

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
