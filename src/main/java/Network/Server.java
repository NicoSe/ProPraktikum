package Network;

import java.net.*;


public class Server{

    private Bufferedreader stream_in;
    private Writer stream_out;

    private prot;

    public static void Server(port) {
        this.port = port;
    }

    public static void listenToNetwork(){

        new Thread(new Runnable(){
            public void run(){
                try {
                    // erstelle einen Server und bestaetige Verbindung zu einem Client
                    ServerSocket ServerSocket = new ServerSocket(port);
                    System.out.println("Server stratet...");
                    System.out.println("Warte auf Verbidnung...");
                    Socket ClientSocket = ServerSocket.accept();
                    System.out.println("Client gefunden.");

                    BufferedReader stream_in = new Bufferedreader(new InputStreamreader(socket.getInputStream()));
                    Writer stream_out = new OutputStreamWriter(socket.getOutputStream());

                    BufferedReader stream_in_std = new BufferedReader(new InputStreamReader(System.in));

                    String line = in readLine();
                    if (line == null) break;

                    line = stream_in_std.readLine();
                    if (line == null || line.equals("")) break;
                    stream_out.write(String.format("%s%n", line));
                    stream_out.flush();

                    ClientSocket.close();
                    ServerSocket.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
        }
    }
}