import Network.*;
import logic.*;

import java.io.IOException;

public class Main {

    public static Server s;

    public static void main(String[] args) throws IOException {
        //Grid grid = new Grid(5);
        //Character c = new Ship(2);
        //grid.put(0, c);
        //Character c2 = new Ship(2);
        //grid.put(2, c2);
        // System.out.println(grid);
        //grid.rotate(0);
        //System.out.println(grid);


        new Save();

        s = new Server();
        //GUI
        /* Create and display the form */
        /*java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MainFrame mf = new MainFrame();

            }
        });*/
    }
}
