import GUI.MainFrame;
import Network.Client;
import logic.Grid;
import logic.Ship;
import logic.Character;
import Network.Server;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws IOException {
        //Grid grid = new Grid(5);
        //Character c = new Ship(2);
        //grid.put(0, c);
        //Character c2 = new Ship(2);
        //grid.put(2, c2);
        // System.out.println(grid);
        //grid.rotate(0);
        //System.out.println(grid);

        int port = 50000;
        Server s = new Server();
        Client c = new Client("localhost");
        c.listenToNetwork();
        s.sendmsg();
        return;

        //GUI
        /* Create and display the form */
        /*java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MainFrame mf = new MainFrame();

            }
        });*/
    }
}
