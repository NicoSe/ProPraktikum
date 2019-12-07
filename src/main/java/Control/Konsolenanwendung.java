package Control;

import Logic.*;
import Network.Client;
import Network.Server;

import java.util.Scanner;

public class Konsolenanwendung {

    public static Grid2D a;
    public static Grid2D b;

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Choose between Server(s) and Client(c)!");
        String out = s.nextLine();
        if (out.equals("s")) {
            Konsolenanwendung_Server();
        } else if (out.equals("c")) {
            Konsolenanwendung_Client();
        } else {
            return;
        }
    }

    private static void Konsolenanwendung_Client() {
        Client socket = new Client("localhost");

        Scanner s = new Scanner(System.in);

        System.out.printf("Type your action!");
        while(s.hasNextLine()) {
            try {
                String[] split = s.nextLine().split(" ");

                //simulate shit with x: split[1]&y: split[2]
                if (split[0].equals("shoot")) {
                    int x = Integer.parseInt(split[1]);
                    int y = Integer.parseInt(split[2]);

                    socket.sendmsg(split[0] + " " + x + " " + y);
                }
                else if(split[0].equals("continue")){
                    socket.sendmsg(split[0]);
                }
                else if(split[0].equals("save")){
                    String savename = String.valueOf(System.currentTimeMillis());
                    new Save(savename);
                    socket.sendmsg(split[0] + " " + savename);
                }
                else if(split[0].equals("load")){
                    socket.sendmsg(split[0] + " " + split[1]);
                }
                else{
                    System.out.println("Try again.");
                }
            } catch(Exception e) {
                System.out.println("error!");
            }
        }
    }

    public static void Konsolenanwendung_Server(){
        Server socket = new Server();

        Scanner s = new Scanner(System.in);

        System.out.printf("Type your action!");
        while(s.hasNextLine()) {
            try {
                String[] split = s.nextLine().split(" ");

                //simulate shit with x: split[1]&y: split[2]
                if (split[0].equals("size")){
                    a = new Grid2D(Integer.parseInt(split[1]));
                    a.generateRandom();
                    b = new Grid2D(Integer.parseInt(split[1]));
                }
                else if (split[0].equals("shoot")) {
                    int x = Integer.parseInt(split[1]);
                    int y = Integer.parseInt(split[2]);

                    socket.sendmsg(split[0] + " " + x + " " + y);
                }
                else if(split[0].equals("continue")){
                    socket.sendmsg(split[0]);
                }
                else if(split[0].equals("save")){
                    String savename = String.valueOf(System.currentTimeMillis());
                    new Save(savename);
                    socket.sendmsg(split[0] + " " + savename);
                }
                else if(split[0].equals("load")){
                    socket.sendmsg(split[0] + " " + split[1]);
                }
                else{
                    System.out.println("Try again.");
                }
            } catch(Exception e) {
                System.out.println("error!");
            }
        }
    }
}