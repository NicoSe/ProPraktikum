package Control;


import GUI.Grid.BasicGrid;
import Logic.Character;
import Logic.Grid2D;
import Logic.GridController;
import Misc.GridState;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GUIMain {
    public static final int size = 30;

    public static void main(String[] args) {
        new GUIMain();
    }

    public GUIMain() {
        Grid2D g2d = new Grid2D(size);
        g2d.generateRandom();
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                JFrame frame = new JFrame("Test");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());

                BasicGrid guiGrid = new BasicGrid(size, GridState.PLACE);

                GridController controller = new GridController(g2d, null, guiGrid);
                controller.init(GridState.PLACE);

                frame.add(guiGrid);

                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }

        });
    }
}