package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.*;
import java.io.IOException;
import GUI.Helpers;
//import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;

public class MainFrame {

    //Variablen
    private JFrame jf;
    private BackgroundPanel backgroundPanel;
    private JPanel pnlButton;
    private JPanel pnlCredits;
    private JLabel lblYeet;
    private JLabel lblYeet2;
    private JLabel lblTitle;
    private JLabel lblPlay;
    private JLabel lblOptions;
    private JLabel lblCredits;
    private JLabel lblExit;


    public MainFrame() throws IOException {
        Components();
    }

    //Komponenten
    private void Components() throws IOException {
        //Frame
        jf = new JFrame();
        jf.setMinimumSize(new Dimension(1024, 768));
        jf.setMaximumSize(new Dimension(1920, 1080));
        jf.setTitle("Battleships");
        jf.setVisible(true);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //JLabel contentPane = new JLabel();
        //contentPane.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ee7d4460451792a.gif"))));
        //contentPane.setLayout(new BorderLayout());
        //jf.setContentPane(contentPane);
        //Background
        backgroundPanel = new BackgroundPanel(ImageIO.read(getClass().getResource("/Sprites/Waltertile2_64.png")), BackgroundPanel.TILED);
        backgroundPanel.setMinimumSize(new Dimension(1024, 768));
        backgroundPanel.setMaximumSize(new Dimension(1920, 1080));
        jf.setContentPane(backgroundPanel);

        //panel
        pnlButton = new JPanel();
        pnlButton.setOpaque(true);
        pnlButton.setLayout(new BoxLayout(pnlButton,BoxLayout.Y_AXIS));
        pnlButton.setMinimumSize(new Dimension(1024,768));
        pnlButton.setMaximumSize(new Dimension(1920,1080));
        backgroundPanel.add(pnlButton);

        //Credits Panel
        pnlCredits = new JPanel();
        pnlCredits.setOpaque(true);
        pnlCredits.setLayout(new BoxLayout(pnlCredits,BoxLayout.Y_AXIS));
        pnlCredits.setMinimumSize(new Dimension(1024,768));
        pnlCredits.setMaximumSize(new Dimension(1920,1080));



        //Titel
        lblTitle = new JLabel();
        lblTitle.setIcon(new ImageIcon(getClass().getResource("/Sprites/Title_v10.gif")));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlButton.add(lblTitle);


        //Buttons for Button Panel
        //Play Button
        lblPlay = new JLabel();
        lblPlay.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/PlayBW.png"))));
        lblPlay.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlButton.add(lblPlay);
        lblPlay.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                try {
                    lblPlayMouseClicked(e);
                } catch(IOException el){
                    el.printStackTrace();
                }
            }
            public void mouseEntered(MouseEvent e) {
                try {
                    lblPlayMouseEntered(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseExited(MouseEvent e){
                try {
                    lblPlayMouseExited(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mousePressed(MouseEvent e){
                try {
                    lblPlayMousePressed(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseReleased(MouseEvent e){
                try {
                    lblPlayMouseReleased(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        //Options Button
        lblOptions = new JLabel();
        lblOptions.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/OptionsBW.png"))));
        lblOptions.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlButton.add(lblOptions);
        lblOptions.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                try {
                    lblOptionsMouseClicked(e);
                } catch(IOException el){
                    el.printStackTrace();
                }
            }
            public void mouseEntered(MouseEvent e) {
                try {
                    lblOptionsMouseEntered(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseExited(MouseEvent e){
                try {
                    lblOptionsMouseExited(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mousePressed(MouseEvent e){
                try {
                    lblOptionsMousePressed(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseReleased(MouseEvent e){
                try {
                    lblOptionsMouseReleased(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        //Credits Button
        lblCredits = new JLabel();
        lblCredits.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/CreditsBW.png"))));
        lblCredits.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlButton.add(lblCredits);
        lblCredits.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                try {
                    lblCreditsMouseClicked(e);
                } catch(IOException el){
                    el.printStackTrace();
                }
            }
            public void mouseEntered(MouseEvent e) {
                try {
                    lblCreditsMouseEntered(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseExited(MouseEvent e){
                try {
                    lblCreditsMouseExited(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mousePressed(MouseEvent e){
                try {
                    lblCreditsMousePressed(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseReleased(MouseEvent e){
                try {
                    lblCreditsMouseReleased(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        //Exit Button
        lblExit = new JLabel();
        lblExit.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ExitBW.png"))));
        lblExit.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlButton.add(lblExit);
        lblExit.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                try {
                    lblExitMouseClicked(e);
                } catch(IOException el){
                    el.printStackTrace();
                }
            }
            public void mouseEntered(MouseEvent e) {
                try {
                    lblExitMouseEntered(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseExited(MouseEvent e){
                try {
                    lblExitMouseExited(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mousePressed(MouseEvent e){
                try {
                    lblExitMousePressed(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseReleased(MouseEvent e){
                try {
                    lblExitMouseReleased(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });



    }

    //Action Events
    //Play
    private void lblPlayMouseClicked(MouseEvent e) throws IOException {
        Helpers.playSFX("/SFX/SA2_142.wav");
        pnlButton.setVisible(false);
    }

    private void lblPlayMouseReleased(MouseEvent e) throws IOException {
        lblPlay.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/PlayWB.png"))));
    }

    private void lblPlayMousePressed(MouseEvent e) throws IOException {
        lblPlay.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/PlayOnPress.png"))));
    }

    private void lblPlayMouseExited(MouseEvent e) throws IOException {
        lblPlay.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/PlayBW.png"))));
    }

    private void lblPlayMouseEntered(MouseEvent e) throws IOException {
        lblPlay.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/PlayWB.png"))));
        Helpers.playSFX("/SFX/Menu_Tick.wav");
    }


    //Options
    private void lblOptionsMouseClicked(MouseEvent e) throws IOException {
        Helpers.playSFX("/SFX/SA2_142.wav");
    }

    private void lblOptionsMouseReleased(MouseEvent e) throws IOException {
        lblOptions.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/OptionsWB.png"))));
    }

    private void lblOptionsMousePressed(MouseEvent e) throws IOException {
        lblOptions.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/OptionsOnPress.png"))));
    }

    private void lblOptionsMouseExited(MouseEvent e) throws IOException {
        lblOptions.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/OptionsBW.png"))));
    }

    private void lblOptionsMouseEntered(MouseEvent e) throws IOException {
        lblOptions.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/OptionsWB.png"))));
        Helpers.playSFX("/SFX/Menu_Tick.wav");
    }

    //Credits
    private void lblCreditsMouseClicked(MouseEvent e) throws IOException {
        Helpers.playSFX("/SFX/SA2_142.wav");
        pnlButton.setVisible(false);
        backgroundPanel.add(pnlCredits);
        lblYeet = new JLabel("yeet made this game");
        lblYeet2 = new JLabel("yeet2 made this game");
        pnlCredits.add(lblTitle);
        pnlCredits.add(lblYeet);
        pnlCredits.add(lblYeet2);
        pnlCredits.setVisible(true);



    }

    private void lblCreditsMouseReleased(MouseEvent e) throws IOException {
        lblCredits.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/CreditsWB.png"))));
    }

    private void lblCreditsMousePressed(MouseEvent e) throws IOException {
        lblCredits.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/CreditsOnPress.png"))));
    }

    private void lblCreditsMouseExited(MouseEvent e) throws IOException {
        lblCredits.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/CreditsBW.png"))));
    }

    private void lblCreditsMouseEntered(MouseEvent e) throws IOException {
        lblCredits.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/CreditsWB.png"))));
        Helpers.playSFX("/SFX/Menu_Tick.wav");
    }

    //Exit
    private void lblExitMouseClicked(MouseEvent e) throws IOException {
        Helpers.playSFX("/SFX/SA2_142.wav");
        System.exit(0);
    }

    private void lblExitMouseReleased(MouseEvent e) throws IOException {
        lblExit.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ExitWB.png"))));
    }

    private void lblExitMousePressed(MouseEvent e) throws IOException {
        lblExit.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ExitOnPress.png"))));
    }

    private void lblExitMouseExited(MouseEvent e) throws IOException {
        lblExit.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ExitBW.png"))));
    }

    private void lblExitMouseEntered(MouseEvent e) throws IOException {
        lblExit.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ExitWB.png"))));
        Helpers.playSFX("/SFX/Menu_Tick.wav");
    }
}