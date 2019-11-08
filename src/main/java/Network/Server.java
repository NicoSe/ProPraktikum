package Network;

import java.io.*;
import java.net.*;


public class Server{

    private BufferedReader stream_in;
    private BufferedReader stream_in_std;
    private Writer stream_out;

    private ServerSocket Server_Socket;
    private Socket Client_Socket;

    private int port;


    public Server (int port) {
        this.port = port;
    }


    public void connectClient() throws IOException {
        new Thread(new Runnable(){
            public void run(){
                //while(true){
                    try {
                        // erstelle einen Server und bestaetige Verbindung zu einem Client
                        Server_Socket = new ServerSocket(port);
                        System.out.println("Server startet...");
                        System.out.println("Warte auf Verbidnung...");
                        Client_Socket = Server_Socket.accept();
                        System.out.println("Client verbunden.");

                        stream_in = new BufferedReader(new InputStreamReader(Client_Socket.getInputStream()));
                        stream_out = new OutputStreamWriter(Client_Socket.getOutputStream());
                        stream_in_std = new BufferedReader(new InputStreamReader(System.in));
                        while(true) {
                            String line = stream_in.readLine();
                            if (line == null) break;
                            System.out.println("<<< " + line);
                            System.out.print(">>> ");
                            line = stream_in_std.readLine();
                            stream_out.write(String.format("%s%n", line));
                            stream_out.flush();

                            Client_Socket.close();
                            Server_Socket.close();
                        }
                        connectClient();
                    }catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            //}
        }).start();
    }


    public void sendShoot(int x, int y) throws IOException,NullPointerException{
        listenToNetwork();
    }


    public void closeServer(){
        try {
            Server_Socket.close();
            Client_Socket.close();
            System.out.println("Verbindung getrennt!");
        } catch(IOException e){
            System.out.println("Verbindung konnte nicht getrennt werden!");
        }
    }

    public void listenToNetwork(){
        new Thread(new Runnable(){
            public void run() {
                BufferedReader stream_in = null;
                try {
                    stream_in = new BufferedReader(new InputStreamReader(Client_Socket.getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                while(true){
                    try {
                        String line = stream_in.readLine();
                        if (line == null) break;
                        System.out.println("<<< " + line);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                //AnalyseInput();
            }
        }).start();
    }
}

//        BufferedReader stream_in = new BufferedReader(new InputStreamReader(Client_Socket.getInputStream()));
//        Writer stream_out = new OutputStreamWriter(Client_Socket.getOutputStream());
//
//        BufferedReader stream_in_std = new BufferedReader(new InputStreamReader(System.in));
//
//        String line = stream_in.readLine();
//        if (line == null) break;
//        System.out.println("<<< " + line);
//        System.out.print(">>> ");
//        line = stream_in_std.readLine();
//
//        stream_out.write(String.format("%s%n", line));
//        stream_out.flush();