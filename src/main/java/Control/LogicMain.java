package Control;

import Logic.Grid;
import Logic.Ship;
import Logic.ShipHarbor;

import java.util.Scanner;

public class LogicMain {
    //args[0]: size;
    public static void main(String[] args) {

        ShipHarbor harbor = new ShipHarbor();
        harbor.load();
        harbor.put(5);


        if(args.length <= 0) {
            System.exit(1);
        }

        Grid g = new Grid(Integer.parseInt(args[0]));
        Scanner s = new Scanner(System.in);

        System.out.println("enter command.");
        System.out.println("e.g. shoot x y");
        while(s.hasNextLine()) {
            try {
                String[] split = s.nextLine().split(" ");

                //simulate shit with x: split[1]&y: split[2]
                if (split[0].equals("shoot")) {
                    int x = Integer.parseInt(split[1]);
                    int y = Integer.parseInt(split[2]);

                    System.out.format("boom at x:%d y:%d (local: %d) returns %s \n", x, y, g.convertXYToLocal(x, y), g.shoot(g.convertXYToLocal(x, y)));
                }
            } catch(Exception e) {
                System.out.println("error! enter command.");
                System.out.println("e.g. shoot x y");
            }
        }
    }
}