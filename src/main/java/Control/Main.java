package Control;

import GUI.MainFrame;
import Network.*;
import Logic.*;
import Logic.Character;

import java.awt.*;
import java.io.IOException;

public class Main {

    public static Server s;
    public static Grid2D own_grid;
    public static Grid2D foe_grid;

    public static void main(String[] args) throws IOException {
        //new Save();
        Load lo = new Load();
        Grid2D[] Gird_array = Load.load("1576750512796");
        own_grid = Gird_array[0];
        foe_grid = Gird_array[1];
        own_grid.toString();
        foe_grid.toString();

        OptionsHandler oh = new OptionsHandler();

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

