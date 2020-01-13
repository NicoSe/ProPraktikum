package Control;

import Logic.*;
import Network.Client;
import Network.Server;

import java.util.Scanner;

public class Konsolenanwendung {

    static Server S_socket;
    static Client C_socket;
    public static Grid2D a;
    public static Grid2D b;
    private static int x;
    private static int y;

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
        Client C_socket = new Client("localhost");

        Scanner s = new Scanner(System.in);

        C_analyse(C_socket.listenToNetwork());
        while (true) {
            System.out.printf("Grid A:\n%s\n", a);
            System.out.printf("Grid B:\n%s\n", b);
            System.out.printf("Type your action:");
            try {
                String[] split = s.nextLine().split(" ");

                //simulate shit with x: split[1]&y: split[2]
                if (split[0].equals("shoot")) {
                    x = Integer.parseInt(split[1]);
                    y = Integer.parseInt(split[2]);

                    C_socket.sendmsg(split[0] + " " + x + " " + y);
                    C_analyse(C_socket.listenToNetwork());
                } else if (split[0].equals("confirmed")) {
                    C_socket.sendmsg(split[0]);
                    C_analyse(C_socket.listenToNetwork());
                } else if (split[0].equals("answer")) {
                    C_socket.sendmsg(split[0] + " " + split[1]);
                    C_analyse(C_socket.listenToNetwork());
                } else if (split[0].equals("save")) {
                    String savename = String.valueOf(System.currentTimeMillis());
                    new Save(savename, a, b);
                    C_socket.sendmsg(split[0] + " " + savename);
                    C_analyse(C_socket.listenToNetwork());
                } else if (split[0].equals("load")) {
                    C_socket.sendmsg(split[0] + " " + split[1]);
                    C_analyse(C_socket.listenToNetwork());
                } else if (split[0].equals("pass")) {
                    C_socket.sendmsg("pass");
                    C_analyse(C_socket.listenToNetwork());
                } else if (split[0].equals("end")) {
                    break;
                } else {
                    System.out.println("Try again.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error!");
            }
        }
    }


    public static void Konsolenanwendung_Server() {
        Server S_socket = new Server();

        Scanner s = new Scanner(System.in);

        while (true) {
            System.out.printf("Grid A:\n%s\n", a);
            System.out.printf("Grid B:\n%s\n", b);
            System.out.printf("Type your action:");
            try {
                String[] split = s.nextLine().split(" ");

                //simulate shit with x: split[1]&y: split[2]
                if (split[0].equals("size")) {
                    if (Integer.parseInt(split[1]) < 5 || Integer.parseInt(split[1]) > 30) {
                        System.out.println("Field must be between 3 and 30!");
                        continue;
                    }
                    a = new Grid2D(Integer.parseInt(split[1]));
                    a.generateRandom();
                    b = new Grid2D(Integer.parseInt(split[1]));
                    FoeGridShootObject temp = new FoeGridShootObject(0);
                    //b.setFoeGridObjects();
                    System.out.printf("Grid A:\n%s\n", a);
                    System.out.printf("Grid B:\n%s\n", b);
                    S_socket.sendmsg(split[0] + " " + split[1]);
                    S_analyse(S_socket.listenToNetwork());
                } else if (split[0].equals("shoot")) {
                    x = Integer.parseInt(split[1]);
                    y = Integer.parseInt(split[2]);

                    S_socket.sendmsg(split[0] + " " + x + " " + y);
                    S_analyse(S_socket.listenToNetwork());
                } else if (split[0].equals("confirmed")) {
                    S_socket.sendmsg(split[0]);
                    S_analyse(S_socket.listenToNetwork());
                } else if (split[0].equals("answer")) {
                    S_socket.sendmsg(split[0] + " " + split[1]);
                    S_analyse(S_socket.listenToNetwork());
                } else if (split[0].equals("save")) {
                    String savename = String.valueOf(System.currentTimeMillis());
                    new Save(savename, a, b);
                    S_socket.sendmsg(split[0] + " " + savename);
                    S_analyse(S_socket.listenToNetwork());
                } else if (split[0].equals("load")) {
                    S_socket.sendmsg(split[0] + " " + split[1]);
                    S_analyse(S_socket.listenToNetwork());
                } else if (split[0].equals("pass")) {
                    S_socket.sendmsg("pass");
                } else if (split[0].equals("end")) {
                    break;
                } else {
                    System.out.println("Try again.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error!");
            }
        }
    }

    private static void S_analyse(String msg) {
        String[] words = msg.split("\\s+");
        words[0] = words[0].toLowerCase();
        switch (words[0]) {
            case "shoot":
                ShotResult result = a.shoot(Integer.parseInt(words[1]), Integer.parseInt(words[2]));
                a.shoot(Integer.parseInt(words[1]), Integer.parseInt(words[2]));
                if(result == ShotResult.HIT) {
                    S_socket.sendmsg("answer 1");
                    S_analyse(S_socket.listenToNetwork());
                }
                else if(result == ShotResult.SUNK) {
                    S_socket.sendmsg("answer 2");
                    S_analyse(S_socket.listenToNetwork());
                }
                else if(result == ShotResult.NONE) {
                    S_socket.sendmsg("answer 0");
                    S_analyse(S_socket.listenToNetwork());
                }
                break;
            case "confirmed":
                break;
            case "answer":
                switch (words[1].toUpperCase()) {
                    case "0":
                        //b.shoot(0);
                        //S_socket.sendmsg("pass");
                    case "1":
                        //b.shoot(1);
                    case "2":
                        //b.shoot(1);
                }
                break;
            case "pass":
                break;
            case "save":
                new Save(words[1], a, b);
                S_socket.Close();
                break;
            case "load":
                Load.load(words[1]);
                break;
        }
    }

    private static void C_analyse(String msg) {
        String[] words = msg.split("\\s+");
        words[0] = words[0].toLowerCase();
        switch (words[0]) {
            case "size":
                a = new Grid2D(Integer.parseInt(words[1]));
                a.generateRandom();
                b = new Grid2D(Integer.parseInt(words[1]));
                //b.setFoeGridObjects();
                System.out.printf("Grid A:\n%s\n", a);
                System.out.printf("Grid B:\n%s\n", b);
                break;
            case "shoot":
                ShotResult result = a.shoot(Integer.parseInt(words[1]), Integer.parseInt(words[2]));
                if(result == ShotResult.HIT) {
                    C_socket.sendmsg("answer 1");
                    C_analyse(C_socket.listenToNetwork());
                }
                else if(result == ShotResult.SUNK) {
                    C_socket.sendmsg("answer 2");
                    C_analyse(C_socket.listenToNetwork());
                }
                else if(result == ShotResult.NONE) {
                    C_socket.sendmsg("answer 0");
                    C_analyse(C_socket.listenToNetwork());
                }
                break;
            case "confirmed":
                break;
            case "answer":
                switch (words[1].toUpperCase()) {
                    case "0":
                        //b.shoot(0);
                        C_socket.sendmsg("pass");
                    case "1":
                        //b.shoot(1);
                    case "2":
                        //b.shoot(2);
                }
                break;
            case "pass":
                break;
            case "save":
                new Save(words[1], a, b);
                C_socket.Close();
                break;
            case "load":
                Load.load(words[1]);
                break;
        }
    }
}