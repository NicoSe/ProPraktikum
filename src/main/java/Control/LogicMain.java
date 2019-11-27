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

        if(args.length <= 0) {
            System.exit(1);
        }

        Grid g = new Grid(Integer.parseInt(args[0]));
        Scanner s = new Scanner(System.in);

        while(s.hasNextLine()) {

        }

    }
}
