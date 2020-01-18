package Control;

import Logic.Grid2D;
import Logic.Ship;
import Logic.ShipHarbor;
import Logic.ShotResult;
import Network.Client2;
import Network.Connector;
import Network.Server2;

import java.util.Scanner;

public class LogicMain {
    //args[0]: size;
    public static void main(String[] args) {
        if(args.length <= 0) {
            System.exit(1);
        }

        Grid2D a = new Grid2D(Integer.parseInt(args[0]));
        a.generateRandom();
        System.out.printf("Grid A:\n%s\n", a);
        Grid2D b = new Grid2D(Integer.parseInt(args[0]));
        b.generateRandom();
        System.out.printf("Grid B:\n%s\n", b);
        Scanner s = new Scanner(System.in);

        boolean is_a = true;
        System.out.printf("enter command for player %s.\n", is_a ? "A" : "B");
        System.out.println("e.g. shoot x y");

        boolean isServer = true;
        for(String type : args) {
            if(type.equals("client")) {
                isServer = false;
                break;
            }
        }

        Connector con = isServer ? new Server2() : new Client2("127.0.0.1");

        while(s.hasNextLine()) {
            try {
                String[] split = s.nextLine().split(" ");

                //simulate shit with x: split[1]&y: split[2]
                if (split[0].equals("shoot")) {
                    int x = Integer.parseInt(split[1]);
                    int y = Integer.parseInt(split[2]);

                    ShotResult res = null;
                    if(is_a) {
                        res = b.shoot(x, y);
                    } else {
                        res = a.shoot(x, y);
                    }

                    if (res != ShotResult.HIT && res != ShotResult.SUNK && res != ShotResult.ALREADY) {
                        is_a = !is_a;
                    }

                    System.out.format("boom at x:%d y:%d returns %s \n", x, y, res);

                    System.out.printf("Grid A:\n%s\n", a);
                    System.out.printf("Grid B:\n%s\n", b);

                    System.out.printf("enter command for player %s.\n", is_a ? "A" : "B");
                    System.out.println("e.g. shoot x y");
                }
            } catch(Exception e) {
                System.out.println("error!");
            }
        }
    }
}