package Control;

import GUI.MainFrame;
import Network.*;
import Logic.*;
import Logic.Character;

import java.io.IOException;

public class Main {

    public static Server s;
    public static Grid2D own_grid;
    public static Grid2D foe_grid;

    public static void main(String[] args) throws IOException {
        own_grid = new Grid2D(5);
        Character c = new Ship(2);
        own_grid.put(0, 0, c);
        Character c2 = new Ship(4);
        own_grid.put(2, 0, c2);
        // System.out.println(grid);
        //grid.rotate(0);
        Character c3 = new Ship(4);
        own_grid.put(4, 0, c3);
        //System.out.println(own_grid);

        foe_grid = new Grid2D(5);
        foe_grid.put(1, 0,c3);
        //System.out.println(foe_grid);

        //new Save();
        Load lo = new Load();
        //Server s = new Server();

        //GUI
        // Create and display the form
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainFrame mf = new MainFrame();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}

