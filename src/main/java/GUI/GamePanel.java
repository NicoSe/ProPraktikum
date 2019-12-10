package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Tried to make animated background didnt work unlucky
public class GamePanel extends BackgroundPanel implements ActionListener {
    Timer timer =  new Timer(100,this);
    public GamePanel(Image image) {
        super(image);
        timer.start();
    }

    public GamePanel(Image image, int style){
        super(image,style);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == timer){
            repaint();
        }
    }
}
