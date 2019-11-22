package Steuerung;

import Network.*;
import logic.*;
import logic.Character;

import java.io.IOException;

public class Main {

    public static Server s;
    public static Grid grid;

    public static void main(String[] args) throws IOException {
        grid = new Grid(5);
        Character c = new Ship(2);
        grid.put(0, c);
        Character c2 = new Ship(4);
        grid.put(2, c2);
        // System.out.println(grid);
        //grid.rotate(0);
        System.out.println(grid);


        new Save();

        //Server s = new Server();


        //GUI
        /* Create and display the form */
        /*java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MainFrame mf = new MainFrame();

            }
        });*/
    }
}
