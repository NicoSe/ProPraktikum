package Control;

import Logic.Grid;

import java.util.Scanner;

public class LogicMain {
    //args[0]: size;
    public static void main(String[] args) {
        if(args.length <= 0) {
            System.exit(1);
        }

        Grid g = new Grid(Integer.parseInt(args[0]));
        Scanner s = new Scanner(System.in);

        while(s.hasNextLine()) {
        }

    }
}
