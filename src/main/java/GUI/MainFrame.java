package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import GUI.Helpers;
//import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;

public class MainFrame {

    //Variablen
    private JFrame jf;
    private BackgroundPanel backgroundPanel;
    private JPanel pnlButton;

    private JLabel lblYeet;
    private JLabel lblYeet2;
    private JLabel lblYeet3;
    private JLabel lblTitle;
    private JLabel lblPlay;
    private JLabel lblOptions;
    private JLabel lblCredits;
    private JLabel lblExit;
    private JLabel lblReturn;
    private JLabel lblSingle;
    private JLabel lblHost;
    private JLabel lblJoin;


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
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/Sprites/ee7d4460451792a.gif"));
        //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/Sprites/Waltertile2_1024.png"));
        backgroundPanel = new BackgroundPanel(ImageIO.read(getClass().getResource("/Sprites/Waltertile2_64.png")), BackgroundPanel.TILED);
        //backgroundPanel = new GamePanel(image);
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


        //Titel
        lblTitle = new JLabel();
        lblTitle.setIcon(new ImageIcon(getClass().getResource("/Sprites/Title_v11.gif")));
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

        //Return Button
        lblReturn = new JLabel();
        lblReturn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ReturnBW.png"))));
        lblReturn.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblReturn.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                try {
                    lblReturnMouseClicked(e);
                } catch(IOException el){
                    el.printStackTrace();
                }
            }
            public void mouseEntered(MouseEvent e) {
                try {
                    lblReturnMouseEntered(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseExited(MouseEvent e){
                try {
                    lblReturnMouseExited(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mousePressed(MouseEvent e){
                try {
                    lblReturnMousePressed(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseReleased(MouseEvent e){
                try {
                    lblReturnMouseReleased(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        //Singleplayer Button
        lblSingle = new JLabel();
        lblSingle.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/SingleBW.png"))));
        lblSingle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSingle.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                try {
                    lblSingleMouseClicked(e);
                } catch(IOException el){
                    el.printStackTrace();
                }
            }
            public void mouseEntered(MouseEvent e) {
                try {
                    lblSingleMouseEntered(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseExited(MouseEvent e){
                try {
                    lblSingleMouseExited(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mousePressed(MouseEvent e){
                try {
                    lblSingleMousePressed(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseReleased(MouseEvent e){
                try {
                    lblSingleMouseReleased(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });


        //Host Button
        lblHost = new JLabel();
        lblHost.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/HostBW.png"))));
        lblHost.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblHost.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                try {
                    lblHostMouseClicked(e);
                } catch(IOException el){
                    el.printStackTrace();
                }
            }
            public void mouseEntered(MouseEvent e) {
                try {
                    lblHostMouseEntered(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseExited(MouseEvent e){
                try {
                    lblHostMouseExited(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mousePressed(MouseEvent e){
                try {
                    lblHostMousePressed(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseReleased(MouseEvent e){
                try {
                    lblHostMouseReleased(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        //Join Button
        lblJoin = new JLabel();
        lblJoin.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/JoinBW.png"))));
        lblJoin.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblJoin.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                try {
                    lblJoinMouseClicked(e);
                } catch(IOException el){
                    el.printStackTrace();
                }
            }
            public void mouseEntered(MouseEvent e) {
                try {
                    lblJoinMouseEntered(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseExited(MouseEvent e){
                try {
                    lblJoinMouseExited(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mousePressed(MouseEvent e){
                try {
                    lblJoinMousePressed(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseReleased(MouseEvent e){
                try {
                    lblJoinMouseReleased(e);
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
        pnlButton.removeAll();
        pnlButton.add(lblTitle);
        pnlButton.add(lblSingle);
        pnlButton.add(lblHost);
        pnlButton.add(lblJoin);
        pnlButton.add(lblReturn);
        pnlButton.setVisible(true);

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
        lblYeet = new JLabel("PLACEHOLDER");
        lblYeet2 = new JLabel("PLACEHOLDER");
        lblYeet3 = new JLabel("PLACEHOLDER");

        pnlButton.setVisible(false);
        pnlButton.removeAll();
        pnlButton.add(lblTitle);
        pnlButton.add(lblYeet);
        pnlButton.add(lblYeet2);
        pnlButton.add(lblYeet3);
        pnlButton.add(lblReturn);
        pnlButton.setVisible(true);
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
        Helpers.playSFX("/SFX/firered_0011.wav");
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

    //Return
    private void lblReturnMouseClicked(MouseEvent e) throws IOException {
        Helpers.playSFX("/SFX/firered_0017.wav");
        lblReturn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ReturnBW.png"))));
        pnlButton.removeAll();
        pnlButton.setVisible(false);
        pnlButton.add(lblTitle);
        pnlButton.add(lblPlay);
        pnlButton.add(lblOptions);
        pnlButton.add(lblCredits);
        pnlButton.add(lblExit);
        pnlButton.setVisible(true);
    }

    private void lblReturnMouseReleased(MouseEvent e) throws IOException {
        lblReturn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ReturnWB.png"))));
    }

    private void lblReturnMousePressed(MouseEvent e) throws IOException {
        lblReturn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ReturnOnPress.png"))));
    }

    private void lblReturnMouseExited(MouseEvent e) throws IOException {
        lblReturn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ReturnBW.png"))));
    }

    private void lblReturnMouseEntered(MouseEvent e) throws IOException {
        lblReturn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ReturnWB.png"))));
        lblCredits.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/CreditsBW.png"))));
        lblPlay.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/PlayBW.png"))));
        lblOptions.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/OptionsBW.png"))));
        lblExit.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ExitBW.png"))));
        Helpers.playSFX("/SFX/Menu_Tick.wav");
    }

    //SinglePlayer
    private void lblSingleMouseClicked(MouseEvent e) throws IOException {
        Helpers.playSFX("/SFX/SA2_142.wav");
    }
    private void lblSingleMouseReleased(MouseEvent e) throws IOException {
        lblSingle.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/SingleWB.png"))));
    }

    private void lblSingleMousePressed(MouseEvent e) throws IOException {
        lblSingle.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/SingleOnPress.png"))));
    }

    private void lblSingleMouseExited(MouseEvent e) throws IOException {
        lblSingle.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/SingleBW.png"))));
    }

    private void lblSingleMouseEntered(MouseEvent e) throws IOException {
        lblSingle.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/SingleWB.png"))));
        Helpers.playSFX("/SFX/Menu_Tick.wav");
    }

    //Host
    private void lblHostMouseClicked(MouseEvent e) throws IOException {
        Helpers.playSFX("/SFX/SA2_142.wav");
    }

    private void lblHostMouseReleased(MouseEvent e) throws IOException {
        lblHost.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/HostWB.png"))));
    }

    private void lblHostMousePressed(MouseEvent e) throws IOException {
        lblHost.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/HostOnPress.png"))));
    }

    private void lblHostMouseExited(MouseEvent e) throws IOException {
        lblHost.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/HostBW.png"))));
    }

    private void lblHostMouseEntered(MouseEvent e) throws IOException {
        lblHost.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/HostWB.png"))));
        Helpers.playSFX("/SFX/Menu_Tick.wav");
    }

    //Join
    private void lblJoinMouseClicked(MouseEvent e) throws IOException {
        Helpers.playSFX("/SFX/SA2_142.wav");
    }

    private void lblJoinMouseReleased(MouseEvent e) throws IOException {
        lblJoin.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/JoinWB.png"))));
    }

    private void lblJoinMousePressed(MouseEvent e) throws IOException {
        lblJoin.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/JoinOnPress.png"))));
    }

    private void lblJoinMouseExited(MouseEvent e) throws IOException {
        lblJoin.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/JoinBW.png"))));
    }

    private void lblJoinMouseEntered(MouseEvent e) throws IOException {
        lblJoin.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/JoinWB.png"))));
        Helpers.playSFX("/SFX/Menu_Tick.wav");
    }
}