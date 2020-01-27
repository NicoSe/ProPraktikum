package Control;

import GUI.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            try {
                MainFrame mf = new MainFrame();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }
}

