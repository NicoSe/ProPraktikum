package Control;

import Logic.Grid2D;
import Logic.Ship;
import Logic.ShipHarbor;

import java.util.Scanner;

public class LogicMain {
    //args[0]: size;
    public static void main(String[] args) {
        if(args.length <= 0) {
            System.exit(1);
        }

        Grid2D g = new Grid2D(Integer.parseInt(args[0]));
        g.generateRandom();
        System.out.printf("Grid:\n%s\n", g);
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

                    System.out.format("boom at x:%d y:%d returns %s \n", x, y, g.shoot(x, y));
                }
            } catch(Exception e) {
                System.out.println("error! enter command.");
                System.out.println("e.g. shoot x y");
            }
        }
    }
}