package Control;

import GUI.MainFrame;

import java.io.IOException;

public class Main {
    ///Startet das MainFrame, welches die GUI steuert
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

