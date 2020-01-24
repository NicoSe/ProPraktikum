package Control;

import GUI.Helpers;
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

