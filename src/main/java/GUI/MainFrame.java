package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;

import GUI.Grid.*;
import Logic.*;
import Misc.GridState;
import Network.*;

public class MainFrame {

    //net
    Connector self;
    Connector foe;

    Grid2D selfGrid;
    Grid2D foeGrid;

    GridController gcS;
    GridController gcF;

    //Variablen
    private JFrame jf;
    private BackgroundPanel backgroundPanel;
    private JPanel pnlButton;
    private JPanel pnlHostX;
    private JPanel pnlHostY;
    private JPanel pnlHostY2;
        private JLabel lblLoad;
    private BasicGrid pnlGrid1;
    private BasicGrid pnlGrid2;
    private JPanel pnlPlay;
    private JPanel pnlDummyThicc;
        private JPanel pnlDummy;


    private JLabel lblTitle;
    private JLabel lblPlay;
    private JLabel lblSingle;
    private JLabel lblSize;
    private JSlider sldSizeSingle;
    private JSlider sldSizeHost;
    private JLabel lblDifficulty;
    private JLabel lblStartSingle;
    private JLabel lblHost;
    private JList lstLoad;
    private JScrollPane scrollpane;
    File[] data;
    private JLabel lblStartHost;
    private JLabel lblShowIP;
    private JLabel lblJoin;
    private JLabel lblIPAdress;
    private JTextField txfIPAdress;
    private JLabel lblConnect;
    private JPanel pnlReady;
    private JLabel lblOptions;
    private JLabel lblFullscreen;
    private JLabel lblFullscreenPicture;
    private JLabel lblMusic;
    private JSlider sldMusicSlider;
    private JLabel lblSFX;
    private JSlider sldSFXSlider;
    OptionsHandler optionsHandler = new OptionsHandler();
    private JLabel lblCredits;
    private JLabel lblYeet;
    private JLabel lblYeet2;
    private JLabel lblYeet3;
    private JLabel lblExit;
    private JLabel lblReturn;
    private JLabel lblDummyObj;
    private JLabel lblDummyObj2;
    private JLabel lblDummyObj3;
    private JLabel lblDummyObj4;
    private JLabel lblRect;

    private JLabel lblReady;
    private JLabel lblRandomize;
    private JLabel lblPlaceReturn;



    private MainFrame mf;
    public MainFrame() throws IOException {
        Components();
        this.mf = this;
    }

    //Komponenten
    private void Components() throws IOException {
        //Frame
        jf = new JFrame();
        jf.setMinimumSize(new Dimension(1024, 850));
        jf.setMaximumSize(new Dimension(1920, 1080));
        jf.setTitle("Battleships");
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        if (OptionsHandler.getFullscreenMode()){
            jf.setExtendedState(JFrame.MAXIMIZED_BOTH);
            jf.dispose();
            jf.setUndecorated(true);
            jf.setVisible(true);
        }


        //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/Sprites/ee7d4460451792a.gif"));
        //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/Sprites/Waltertile2_1024.png"));
        backgroundPanel = new BackgroundPanel(ImageIO.read(getClass().getResource("/Sprites/Waltertile2_64.png")), BackgroundPanel.TILED);
        //backgroundPanel = new GamePanel(image);
        backgroundPanel.setMinimumSize(new Dimension(1024, 850));
        backgroundPanel.setMaximumSize(new Dimension(1920, 1080));
        jf.setContentPane(backgroundPanel);

        /* unused...
        //grid Panel
        pnlGrid1 = new BasicGrid(5, GridState.PLACE);
        Grid2D g2d = new Grid2D(5);
        pnlGrid1.setSize(new Dimension(50,50));
        g2d.generateRandom();
        GridController controller = new GridController(g2d, pnlGrid1);
        controller.init(GridState.PLACE);

        //grid Panel enemy
        pnlGrid2 = new BasicGrid(2,GridState.FORBID);
        */

        //panel
        pnlButton = new JPanel();
        pnlButton.setOpaque(true);
        pnlButton.setLayout(new BoxLayout(pnlButton,BoxLayout.Y_AXIS));
        pnlButton.setMinimumSize(new Dimension(1024,850));
        pnlButton.setMaximumSize(new Dimension(1920,1080));
        backgroundPanel.add(pnlButton);

        pnlHostX = new JPanel();
        pnlHostX.setOpaque(false);
        pnlHostX.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlHostX.setMinimumSize(new Dimension(1024, 350));
        pnlHostX.setMaximumSize((new Dimension(1980, 350)));
        //pnlHostX.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        pnlHostX.setLayout(new GridBagLayout());

        pnlHostY = new JPanel();
        pnlHostY.setOpaque(false);
        pnlHostY.setAlignmentX(Component.RIGHT_ALIGNMENT);
        pnlHostY.setAlignmentY(Component.RIGHT_ALIGNMENT);
        pnlHostY.setLayout(new BoxLayout(pnlHostY, BoxLayout.Y_AXIS));
        pnlHostY.setMinimumSize(new Dimension(300, 350));
        pnlHostY.setMaximumSize(new Dimension(300,350));
        pnlHostY.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        pnlHostY2 = new JPanel();
        pnlHostY2.setOpaque(false);
        pnlHostY2.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlHostY2.setAlignmentY(Component.CENTER_ALIGNMENT);
        pnlHostY2.setLayout(new BoxLayout(pnlHostY2, BoxLayout.Y_AXIS));
        pnlHostY2.setMinimumSize(new Dimension(300, 350));
        pnlHostY2.setMaximumSize((new Dimension(300, 350)));
        pnlHostY2.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        //panelPlay
        pnlPlay = new JPanel();
        pnlPlay.setLayout(new BoxLayout(pnlPlay,BoxLayout.Y_AXIS));
        pnlPlay.setMinimumSize(new Dimension(1024,850));
        pnlPlay.setMaximumSize(new Dimension(1920,1080));

        pnlReady = new JPanel(new FlowLayout());
        pnlReady.setPreferredSize(new Dimension(100,220));
        pnlReady.setMaximumSize(new Dimension(jf.getWidth(),jf.getHeight()/7));
        pnlReady.setOpaque(false);
        lblPlaceReturn = new JLabel();
        lblPlaceReturn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/PlaceReturnBW.png"))));
        lblReady = new JLabel();
        lblReady.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ReadyBW.png"))));
        lblRandomize = new JLabel();
        lblRandomize.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/randomBW.png"))));

        pnlReady.add(lblPlaceReturn);
        pnlReady.add(lblRandomize);
        pnlReady.add(lblReady);


        //panel DUmmythicc
        pnlDummyThicc =  new JPanel(new BorderLayout());
        pnlDummyThicc.setBackground(Color.RED);
        pnlDummyThicc.setBorder(BorderFactory.createEmptyBorder(jf.getHeight()/10,jf.getWidth()/10,jf.getHeight()/10,jf.getWidth()/10));

        pnlDummy =  new JPanel();
        pnlDummy.setBackground(Color.GREEN);
        //pnlDummy.setPreferredSize(new Dimension(425,425));


//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
//Buttons for Button panel and Object Disign

        //Titel
        lblTitle = new JLabel();
        lblTitle.setIcon(new ImageIcon(getClass().getResource("/Sprites/Title_v11.gif")));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlButton.add(lblTitle);

        //Play Button
        lblPlay = new JLabel();
        lblPlay.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/PlayBW.png"))));
        lblPlay.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlButton.add(lblPlay);
        lblPlay.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                Helpers.playSFX("/SFX/SA2_142.wav", 1);
                pnlButton.setVisible(false);
                pnlButton.removeAll();
                pnlButton.add(lblTitle);
                pnlButton.add(lblSingle);
                pnlButton.add(lblHost);
                pnlButton.add(lblJoin);
                pnlButton.add(lblReturn);
                pnlButton.setVisible(true);
            }
            public void mouseEntered(MouseEvent e) {
                try {
                    lblPlay.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/PlayWB.png"))));
                    Helpers.playSFX("/SFX/Menu_Tick.wav", 1);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseExited(MouseEvent e){
                try {
                    lblPlay.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/PlayBW.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mousePressed(MouseEvent e){
                try {
                    lblPlay.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/PlayOnPress.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseReleased(MouseEvent e){
                try {
                    lblPlay.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/PlayWB.png"))));
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
                Helpers.playSFX("/SFX/SA2_142.wav", 1);
                pnlButton.setVisible(false);
                pnlButton.removeAll();
                pnlButton.add(lblTitle);
                pnlButton.add(lblFullscreen);
                pnlButton.add(lblFullscreenPicture);
                pnlButton.add(lblMusic);
                pnlButton.add(sldMusicSlider);
                pnlButton.add(lblSFX);
                pnlButton.add(sldSFXSlider);
                pnlButton.add(lblReturn);
                pnlButton.setVisible(true);
            }
            public void mouseEntered(MouseEvent e) {
                try {
                    lblOptions.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/OptionsWB.png"))));
                    Helpers.playSFX("/SFX/Menu_Tick.wav", 1);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseExited(MouseEvent e){
                try {
                    lblOptions.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/OptionsBW.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mousePressed(MouseEvent e){
                try {
                    lblOptions.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/OptionsOnPress.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseReleased(MouseEvent e){
                try {
                    lblOptions.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/OptionsWB.png"))));
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
                Helpers.playSFX("/SFX/SA2_142.wav", 1);
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
            public void mouseEntered(MouseEvent e) {
                try {
                    lblCredits.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/CreditsWB.png"))));
                    Helpers.playSFX("/SFX/Menu_Tick.wav", 1);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseExited(MouseEvent e){
                try {
                    lblCredits.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/CreditsBW.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mousePressed(MouseEvent e){
                try {
                    lblCredits.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/CreditsOnPress.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseReleased(MouseEvent e){
                try {
                    lblCredits.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/CreditsWB.png"))));
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
                Helpers.playSFX("/SFX/firered_0011.wav", 1);
                System.exit(0);
            }
            public void mouseEntered(MouseEvent e) {
                try {
                    lblExit.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ExitWB.png"))));
                    Helpers.playSFX("/SFX/Menu_Tick.wav", 1);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseExited(MouseEvent e){
                try {
                    lblExit.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ExitBW.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mousePressed(MouseEvent e){
                try {
                    lblExit.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ExitOnPress.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseReleased(MouseEvent e){
                try {
                    lblExit.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ExitWB.png"))));
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
                    Helpers.playSFX("/SFX/firered_0017.wav", 1);
                    lblReturn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ReturnBW.png"))));
                    pnlButton.removeAll();
                    pnlButton.setVisible(false);
                    pnlButton.add(lblTitle);
                    pnlButton.add(lblPlay);
                    pnlButton.add(lblOptions);
                    pnlButton.add(lblCredits);
                    pnlButton.add(lblExit);
                    pnlButton.setVisible(true);
                } catch(IOException el){
                    el.printStackTrace();
                }
            }
            public void mouseEntered(MouseEvent e) {
                try {
                    lblReturn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ReturnWB.png"))));
                    lblCredits.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/CreditsBW.png"))));
                    lblPlay.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/PlayBW.png"))));
                    lblOptions.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/OptionsBW.png"))));
                    lblExit.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ExitBW.png"))));
                    Helpers.playSFX("/SFX/Menu_Tick.wav", 1);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseExited(MouseEvent e){
                try {
                    lblReturn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ReturnBW.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mousePressed(MouseEvent e){
                try {
                    lblReturn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ReturnOnPress.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseReleased(MouseEvent e){
                try {
                    lblReturn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ReturnWB.png"))));
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
                Helpers.playSFX("/SFX/SA2_142.wav", 1);

                pnlButton.setVisible(false);
                pnlButton.removeAll();
                pnlButton.add(lblTitle);
                pnlButton.add(lblSize);
                pnlButton.add(sldSizeSingle);
                pnlButton.add(lblDifficulty);
                pnlButton.add(lblStartSingle);
                pnlButton.add(lblReturn);
                pnlButton.setVisible(true);
            }
            public void mouseEntered(MouseEvent e) {
                try {
                    lblSingle.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/SingleWB.png"))));
                    Helpers.playSFX("/SFX/Menu_Tick.wav", 1);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseExited(MouseEvent e){
                try {
                    lblSingle.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/SingleBW.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mousePressed(MouseEvent e){
                try {
                    lblSingle.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/SingleOnPress.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseReleased(MouseEvent e){
                try {
                    lblSingle.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/SingleWB.png"))));
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
                    Helpers.playSFX("/SFX/SA2_142.wav", 1);
                    pnlButton.setVisible(false);
                    pnlButton.removeAll();
                    pnlButton.add(lblTitle);

                    GridBagConstraints c = new GridBagConstraints();
                    c.gridheight = 3;
                    c.anchor = GridBagConstraints.LINE_START;
                    c.fill = GridBagConstraints.VERTICAL;
                    c.ipadx = 5;
                    c.ipady = 5;
                    pnlHostY.add(lblSize);
                    pnlHostY.add(sldSizeSingle);
                    pnlHostX.add(pnlHostY,c);

                    c.anchor = GridBagConstraints.CENTER;
                    c.fill = GridBagConstraints.VERTICAL;
                    pnlHostX.add(lblRect,c);

                    c.anchor = GridBagConstraints.LINE_END;
                    c.fill = GridBagConstraints.VERTICAL;
                    c.gridheight = 3;
                    pnlHostY2.add(lblLoad);
                    pnlHostY2.add(scrollpane);
                    pnlHostX.add(pnlHostY2, c);

                    pnlButton.add(pnlHostX);
                    pnlButton.add(lblStartHost);
                    pnlButton.add(lblReturn);
                    pnlButton.add(lblShowIP);
                    pnlButton.setVisible(true);
                    lblHost.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/HostBW.png"))));
                } catch(IOException el){
                    el.printStackTrace();
                }
            }
            public void mouseEntered(MouseEvent e) {
                try {
                    lblHost.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/HostWB.png"))));
                    Helpers.playSFX("/SFX/Menu_Tick.wav", 1);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseExited(MouseEvent e){
                try {
                    lblHost.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/HostBW.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mousePressed(MouseEvent e){
                try {
                    lblHost.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/HostOnPress.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseReleased(MouseEvent e){
                try {
                    lblHost.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/HostWB.png"))));
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
                    Helpers.playSFX("/SFX/SA2_142.wav", 1);
                    pnlButton.setVisible(false);
                    pnlButton.removeAll();
                    pnlButton.add(lblTitle);
                    pnlButton.add(lblIPAdress);
                    pnlButton.add(txfIPAdress);
                    pnlButton.add(lblConnect);
                    pnlButton.add(lblReturn);
                    pnlButton.setVisible(true);
                    lblJoin.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/JoinBW.png"))));
                } catch(IOException el){
                    el.printStackTrace();
                }
            }
            public void mouseEntered(MouseEvent e) {
                try {
                    lblJoin.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/JoinWB.png"))));
                    Helpers.playSFX("/SFX/Menu_Tick.wav", 1);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseExited(MouseEvent e){
                try {
                    lblJoin.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/JoinBW.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mousePressed(MouseEvent e){
                try {
                    lblJoin.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/JoinOnPress.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseReleased(MouseEvent e){
                try {
                    lblJoin.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/JoinWB.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        //Size Slider and label
        lblSize =  new JLabel();
        lblSize.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/SizeBW.png")).getScaledInstance(400,100,Image.SCALE_SMOOTH)));
        lblSize.setAlignmentX(Component.CENTER_ALIGNMENT);
        sldSizeSingle = new JSlider(5,30);
        sldSizeSingle.setOpaque(false);
        sldSizeSingle.setMinimumSize(new Dimension(300,50));
        sldSizeSingle.setMaximumSize(new Dimension(450,50));
        sldSizeSingle.setMajorTickSpacing(5);
        sldSizeSingle.setMinorTickSpacing(1);
        sldSizeSingle.setPaintLabels(true);
        sldSizeSingle.setPaintTicks(true);
        sldSizeSingle.setFont(new Font("Sprites/PrStart.ttf", Font.BOLD, 20));
        sldSizeSingle.setForeground(Color.BLACK);

        //Size Slider Host UI
        sldSizeHost = new JSlider(5,30);
        sldSizeHost.setOpaque(false);
        sldSizeHost.setMinimumSize(new Dimension(300,50));
        sldSizeHost.setMaximumSize(new Dimension(300,50));
        sldSizeHost.setMajorTickSpacing(5);
        sldSizeHost.setMinorTickSpacing(1);
        sldSizeHost.setPaintLabels(true);
        sldSizeHost.setPaintTicks(true);
        sldSizeHost.setFont(new Font("Sprites/PrStart.ttf", Font.BOLD, 20));
        sldSizeHost.setForeground(Color.BLACK);
        sldSizeHost.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                try {
                    lblStartHost.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/StartGameBW.png"))));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                lstLoad.clearSelection();
                lstLoad.setSelectedIndex(-1);
            }
        });

        //Difficulty
        lblDifficulty = new JLabel();
        lblDifficulty.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/DifficultyBW.png"))));
        lblDifficulty.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Start Game Button
        lblStartSingle = new JLabel();
        lblStartSingle.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/StartGameBW.png"))));
        lblStartSingle.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblStartSingle.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                Helpers.playSFX("/SFX/SA2_142.wav", 1);
                backgroundPanel.removeAll();

                runSingleplayer(sldSizeSingle.getValue());

                //backgroundPanel.add(pnlPlay);
                pnlGrid1 = new BasicGrid(sldSizeSingle.getValue(), GridState.PLACE);
                selfGrid = new Grid2D(sldSizeSingle.getValue());
                selfGrid.generateRandom();
                gcS = new GridController(selfGrid, pnlGrid1);
                gcS.init(GridState.PLACE);
                pnlGrid1.setOpaque(false);
                pnlGrid1.setAlignmentX(Component.CENTER_ALIGNMENT);
                //pnlPlay.add(pnlGrid1);

                lblReady.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        Helpers.playSFX("/SFX/SA2_142.wav", 1);
                        if(self.turn()) {
                            self.sendmsg("confirmed");
                            pnlDummy.remove(pnlReady);
                            pnlDummy.remove(pnlGrid1);
                            pnlDummy.add(pnlGrid2);
                            gcF.setInteractionState(GridState.SHOOT);
                            pnlDummy.revalidate();
                            pnlDummy.repaint();
                        }
                    }

                    public void mouseEntered(MouseEvent e) {
                        try {
                            lblReady.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ReadyWB.png"))));
                            Helpers.playSFX("/SFX/Menu_Tick.wav", 1);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    public void mouseExited(MouseEvent e){
                        try {
                            lblReady.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ReadyBW.png"))));
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    public void mousePressed(MouseEvent e){
                        try {
                            lblReady.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ReadyOnPress.png"))));
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    public void mouseReleased(MouseEvent e){
                        try {
                            lblReady.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ReadyWB.png"))));
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                });

                lblRandomize.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        Helpers.playSFX("/SFX/SA2_142.wav", 1);
                        gcS.randomize();
                    }

                    public void mouseEntered(MouseEvent e) {
                        try {
                            lblRandomize.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/RandomWB.png"))));
                            Helpers.playSFX("/SFX/Menu_Tick.wav", 1);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    public void mouseExited(MouseEvent e){
                        try {
                            lblRandomize.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/RandomBW.png"))));
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    public void mousePressed(MouseEvent e){
                        try {
                            lblRandomize.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/RandomOnPress.png"))));
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    public void mouseReleased(MouseEvent e){
                        try {
                            lblRandomize.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/RandomWB.png"))));
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                });

                lblPlaceReturn.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try {
                            pnlReady.setVisible(false);
                            Helpers.playSFX("/SFX/firered_0017.wav", 1);
                            lblPlaceReturn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/PlaceReturnBW.png"))));
                            lblPlay.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/PlayBW.png"))));
                            lblStartSingle.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/StartGameBW.png"))));
                            pnlDummy.setVisible(false);
                            pnlButton.removeAll();
                            pnlButton.setVisible(false);
                            pnlButton.add(lblTitle);
                            pnlButton.add(lblPlay);
                            pnlButton.add(lblOptions);
                            pnlButton.add(lblCredits);
                            pnlButton.add(lblExit);
                            pnlButton.setVisible(true);
                            backgroundPanel.add(pnlButton);
                        } catch(IOException el){
                            el.printStackTrace();
                        }
                    }

                    public void mouseEntered(MouseEvent e) {
                        try {
                            lblPlaceReturn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/PlaceReturnWB.png"))));
                            Helpers.playSFX("/SFX/Menu_Tick.wav", 1);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    public void mouseExited(MouseEvent e){
                        try {
                            lblPlaceReturn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/PlaceReturnBW.png"))));
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    public void mousePressed(MouseEvent e){
                        try {
                            lblPlaceReturn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/PlaceReturnOnPress.png"))));
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    public void mouseReleased(MouseEvent e){
                        try {
                            lblPlaceReturn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/PlaceReturnWB.png"))));
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                });

                pnlGrid2 = new BasicGrid(sldSizeSingle.getValue(), GridState.FORBID);
                foeGrid = new Grid2D(sldSizeSingle.getValue());
                foeGrid.placeFGOeverywhere();
                gcF = new GridController(foeGrid, pnlGrid2);
                gcF.init(GridState.FORBID);

                //pnlGrid2 = new BasicGrid(sldSize.getValue(),GridState.FORBID);
                //GridController controller2 = new GridController(g2d,pnlGrid2);
                //pnlGrid2.setOpaque(false);
                //pnlGrid2.setAlignmentX(Component.CENTER_ALIGNMENT);
                //controller2.init(GridState.SHOOT);

                //backgroundPanel.add(pnlPlay);

                backgroundPanel.add(pnlDummyThicc);

                pnlDummy.setOpaque(false);
                pnlDummyThicc.add(pnlDummy, BorderLayout.CENTER);
                pnlDummy.add(pnlGrid1);
                pnlDummy.add(pnlReady);
                try {
                    backgroundPanel.setImage(ImageIO.read(getClass().getResource("/Sprites/Waltertile2_64.png")));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if(OptionsHandler.getFullscreenMode()){
                    jf.setSize(new Dimension(1981,1080));
                    jf.setSize(new Dimension(1980,1080));
                }else{
                    jf.setSize(new Dimension(1025,851));
                    jf.setSize(new Dimension(1024,850));
                }


            }
            public void mouseEntered(MouseEvent e) {
                try {
                    lblStartSingle.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/StartGameWB.png"))));
                    Helpers.playSFX("/SFX/Menu_Tick.wav", 1);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseExited(MouseEvent e){
                try {
                    lblStartSingle.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/StartGameBW.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mousePressed(MouseEvent e){
                try {
                    lblStartSingle.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/StartGameOnPress.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseReleased(MouseEvent e){
                try {
                    lblStartSingle.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/StartGameWB.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        //Fullscreen Button
        lblFullscreen = new JLabel();
        lblFullscreen.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/FullscreenBW.png"))));
        lblFullscreen.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Fullscreen Checkbox Picture
        lblFullscreenPicture = new JLabel();
        if (OptionsHandler.getFullscreenMode()){
            lblFullscreenPicture.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/Checkbox_ticked.png")).getScaledInstance(40,40,Image.SCALE_SMOOTH)));
        }
        else{
            lblFullscreenPicture.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/Checkbox_clear.png")).getScaledInstance(40,40,Image.SCALE_SMOOTH)));
        }
        lblFullscreenPicture.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/Checkbox_clear.png")).getScaledInstance(40,40,Image.SCALE_SMOOTH)));
        lblFullscreenPicture.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblFullscreenPicture.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                try {
                    Helpers.playSFX("/SFX/SA2_142.wav", 1);
                    int prev_window_x = 0;
                    int prev_window_y = 0;
                    if (OptionsHandler.getFullscreenMode()){
                        OptionsHandler.changeFullscreenMode(false);
                        lblFullscreenPicture.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/Checkbox_clear.png")).getScaledInstance(40,40,Image.SCALE_SMOOTH)));

                        if(prev_window_x != 0){
                            jf.setSize(prev_window_x,prev_window_y);

                        }
                        else{
                            jf.setSize(1024, 850);
                            jf.setLocationRelativeTo(null);
                        }
                        jf.dispose();
                        jf.setUndecorated(false);
                        jf.setVisible(true);
                    }
                    else{
                        OptionsHandler.changeFullscreenMode(true);
                        prev_window_x = jf.getWidth();
                        prev_window_y = jf.getHeight();
                        lblFullscreenPicture.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/Checkbox_ticked.png")).getScaledInstance(40,40,Image.SCALE_SMOOTH)));
                        jf.setExtendedState(JFrame.MAXIMIZED_BOTH);
                        jf.dispose();
                        jf.setUndecorated(true);
                        jf.setVisible(true);
                    }
                } catch(IOException el){
                    el.printStackTrace();
                }
            }
            public void mouseEntered(MouseEvent e) {
            }
            public void mouseExited(MouseEvent e){
            }
            public void mousePressed(MouseEvent e){
            }
            public void mouseReleased(MouseEvent e){
            }
        });

        //Music Label
        lblMusic = new JLabel();
        lblMusic.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/musicVolumeBW.png"))));
        lblMusic.setAlignmentX(Component.CENTER_ALIGNMENT);

        //SFX Label
        lblSFX = new JLabel();
        lblSFX.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/sfxVolumeBW.png"))));
        lblSFX.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Music Slider
        sldMusicSlider = new JSlider(0,100);
        sldMusicSlider.setPreferredSize(new Dimension(200,50));
        sldMusicSlider.setFont(new Font("Sprites/PrStart.ttf", Font.BOLD, 20));
        sldMusicSlider.setOpaque(false);
        sldMusicSlider.setMaximumSize(new Dimension(512, 50));
        sldMusicSlider.setValue(OptionsHandler.getMusicVolume());
        sldMusicSlider.setMinorTickSpacing(0);
        sldMusicSlider.setMajorTickSpacing(20);
        sldMusicSlider.setPaintLabels(true);
        sldMusicSlider.setPaintTicks(true);
        sldMusicSlider.setPaintTrack(true);
        sldMusicSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                optionsHandler.changeMusicVolume(sldMusicSlider.getValue());
            }
        });

        //SFX Slider
        sldSFXSlider = new JSlider(0,100);
        sldSFXSlider.setFont(new Font("Sprites/PrStart.ttf", Font.BOLD, 20));
        sldSFXSlider.setOpaque(false);
        sldSFXSlider.setMaximumSize(new Dimension(512, 50));
        sldSFXSlider.setValue(OptionsHandler.getSFXVolume());
        sldSFXSlider.setMinorTickSpacing(0);
        sldSFXSlider.setMajorTickSpacing(20);
        sldSFXSlider.setPaintLabels(true);
        sldSFXSlider.setPaintTicks(true);
        sldSFXSlider.setPaintTrack(true);
        sldSFXSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                OptionsHandler.changeSFXVolume(sldSFXSlider.getValue());
            }
        });

        //IPAdress Label and Text Field
        lblIPAdress = new JLabel();
        lblIPAdress.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/SingleBW.png"))));
        lblIPAdress.setAlignmentX(Component.CENTER_ALIGNMENT);
        txfIPAdress = new JTextField();
        txfIPAdress.setAlignmentX(Component.CENTER_ALIGNMENT);
        txfIPAdress.setFont(new Font("Sprites/PrStart.ttf", Font.BOLD, 20));
        txfIPAdress.setMaximumSize(new Dimension(512,50));
        txfIPAdress.setEditable(true);
        txfIPAdress.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(txfIPAdress.getText().length() > 15){
                    if (!(e.getKeyCode() == 8 || e.getKeyCode() == 127)){
                        e.consume();
                    }
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(txfIPAdress.getText().length() > 15){
                    if (!(e.getKeyCode() == 8 || e.getKeyCode() == 127)){
                        e.consume();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        //Connect Button
        lblConnect = new JLabel();
        lblConnect.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/StartGameBW.png"))));
        lblConnect.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblConnect.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                Helpers.playSFX("/SFX/SA2_142.wav", 1);

                runMuliplayerClient(txfIPAdress.getText());

                backgroundPanel.removeAll();

                //backgroundPanel.add(pnlPlay);
                pnlGrid1 = new BasicGrid(sldSizeSingle.getValue(), GridState.PLACE);
                selfGrid = new Grid2D(sldSizeSingle.getValue());
                selfGrid.generateRandom();
                gcS = new GridController(selfGrid, pnlGrid1);
                gcS.init(GridState.PLACE);
                pnlGrid1.setOpaque(false);
                pnlGrid1.setAlignmentX(Component.CENTER_ALIGNMENT);
                //pnlPlay.add(pnlGrid1);

                lblReady.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        Helpers.playSFX("/SFX/SA2_142.wav", 1);

                        if(self.turn()) {
                            self.sendmsg("confirmed");
                            pnlDummy.remove(pnlReady);
                            pnlDummy.remove(pnlGrid1);
                            pnlDummy.add(pnlGrid2);
                            gcF.setInteractionState(GridState.SHOOT);
                            pnlDummy.revalidate();
                            pnlDummy.repaint();
                        }
                    }


                });


                pnlGrid2 = new BasicGrid(sldSizeSingle.getValue(), GridState.FORBID);
                foeGrid = new Grid2D(sldSizeSingle.getValue());
                foeGrid.placeFGOeverywhere();
                gcF = new GridController(foeGrid, pnlGrid2);
                gcF.init(GridState.FORBID);

                //pnlGrid2 = new BasicGrid(sldSize.getValue(),GridState.FORBID);
                //GridController controller2 = new GridController(g2d,pnlGrid2);
                //pnlGrid2.setOpaque(false);
                //pnlGrid2.setAlignmentX(Component.CENTER_ALIGNMENT);
                //controller2.init(GridState.SHOOT);

                //backgroundPanel.add(pnlPlay);

                backgroundPanel.add(pnlDummyThicc);

                pnlDummy.setOpaque(false);
                pnlDummyThicc.add(pnlDummy, BorderLayout.CENTER);
                pnlDummy.add(pnlGrid1);
                pnlDummy.add(pnlReady);
                try {
                    backgroundPanel.setImage(ImageIO.read(getClass().getResource("/Sprites/Waltertile2_64.png")));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if(OptionsHandler.getFullscreenMode()){
                    jf.setSize(new Dimension(1981,1080));
                    jf.setSize(new Dimension(1980,1080));
                }else{
                    jf.setSize(new Dimension(1025,851));
                    jf.setSize(new Dimension(1024,850));
                }

                /*
                Client c = new Client(txfIPAdress.getText());
                if (c.isconnected()){
                    pnlButton.setVisible(false);
                    pnlButton.removeAll();
                    System.out.println("Starte Spiel");
                    //play game
                    pnlButton.setVisible(true);
                }
                 */
            }
            public void mouseEntered(MouseEvent e) {
                try {
                    lblPlay.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/PlayWB.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseExited(MouseEvent e){
                try {
                    lblPlay.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/PlayBW.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mousePressed(MouseEvent e){
                try {
                    lblPlay.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/PlayOnPress.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseReleased(MouseEvent e){
                try {
                    lblPlay.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/PlayWB.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });


        //Host Start Button
        lblStartHost = new JLabel();
        lblStartHost.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/StartGameBW.png"))));
        lblStartHost.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblStartHost.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                Helpers.playSFX("/SFX/SA2_142.wav", 1);

                backgroundPanel.removeAll();

                runMultiplayerServer(sldSizeHost.getValue());

                //backgroundPanel.add(pnlPlay);
                pnlGrid1 = new BasicGrid(sldSizeSingle.getValue(), GridState.PLACE);
                selfGrid = new Grid2D(sldSizeSingle.getValue());
                selfGrid.generateRandom();
                gcS = new GridController(selfGrid, pnlGrid1);
                gcS.init(GridState.PLACE);
                pnlGrid1.setOpaque(false);
                pnlGrid1.setAlignmentX(Component.CENTER_ALIGNMENT);
                //pnlPlay.add(pnlGrid1);

                lblReady.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if(self.turn()) {
                            self.sendmsg("confirmed");
                            pnlDummy.remove(pnlReady);
                            pnlDummy.remove(pnlGrid1);
                            pnlDummy.add(pnlGrid2);
                            gcF.setInteractionState(GridState.SHOOT);
                            pnlDummy.revalidate();
                            pnlDummy.repaint();
                        }
                    }
                });

                lblRandomize.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        gcS.randomize();
                    }
                });

                pnlGrid2 = new BasicGrid(sldSizeSingle.getValue(), GridState.FORBID);
                foeGrid = new Grid2D(sldSizeSingle.getValue());
                foeGrid.placeFGOeverywhere();
                gcF = new GridController(foeGrid, pnlGrid2);
                gcF.init(GridState.FORBID);

                //pnlGrid2 = new BasicGrid(sldSize.getValue(),GridState.FORBID);
                //GridController controller2 = new GridController(g2d,pnlGrid2);
                //pnlGrid2.setOpaque(false);
                //pnlGrid2.setAlignmentX(Component.CENTER_ALIGNMENT);
                //controller2.init(GridState.SHOOT);

                //backgroundPanel.add(pnlPlay);

                backgroundPanel.add(pnlDummyThicc);

                pnlDummy.setOpaque(false);
                pnlDummyThicc.add(pnlDummy, BorderLayout.CENTER);
                pnlDummy.add(pnlGrid1);
                pnlDummy.add(pnlReady);
                try {
                    backgroundPanel.setImage(ImageIO.read(getClass().getResource("/Sprites/Waltertile2_64.png")));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if(OptionsHandler.getFullscreenMode()){
                    jf.setSize(new Dimension(1981,1080));
                    jf.setSize(new Dimension(1980,1080));
                }else{
                    jf.setSize(new Dimension(1025,851));
                    jf.setSize(new Dimension(1024,850));
                }

                /*
                Server s = new Server();
                if (s.isconnected()){
                    pnlButton.setVisible(false);
                    pnlButton.removeAll();
                    System.out.println("Play Game");
                    if(lstLoad.getSelectedIndex() == -1){
                        //play Game with selected Size
                    }
                    else{
                        String filename = data[lstLoad.getSelectedIndex()].toString();
                        s.sendmsg("load " + filename);
                        Load l = new Load();
                        Grid2D[] grids = l.load(filename);
                        //play Game with loaded grids
                    }
                    pnlButton.setVisible(true);
                }
                 */
            }
            public void mouseEntered(MouseEvent e) {
                try {
                    lblPlay.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/StartGameWB.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseExited(MouseEvent e){
                try {
                    lblPlay.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/StartGameBW.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mousePressed(MouseEvent e){
                try {
                    lblPlay.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/StartGameOnPress.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseReleased(MouseEvent e){
                try {
                    lblPlay.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/StartGameWB.png"))));
                    Helpers.playSFX("/SFX/Menu_Tick.wav", 1);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        //Show own IP adress
        lblShowIP = new JLabel();
        InetAddress inetAddress = InetAddress.getLocalHost();
        lblShowIP.setFont(new Font("Sprites/PrStart.ttf", Font.BOLD, 20));
        lblShowIP.setText("IP ADDRESS: "+ inetAddress.getHostAddress());
        lblShowIP.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblShowIP.setBorder(BorderFactory.createEmptyBorder(50,0,5,jf.getWidth()/2));

        //Load List
        lstLoad = new JList();
        lstLoad.setFont(new Font("Sprites/PrStart.ttf", Font.BOLD, 20));
        lstLoad.setOpaque(false);
        lstLoad.setBorder(new BasicBorders.FieldBorder(Color.BLACK, Color.gray, Color.BLACK, Color.WHITE));
        lstLoad.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstLoad.setAlignmentX(Component.CENTER_ALIGNMENT);
        lstLoad.setMinimumSize(new Dimension(300,50));
        lstLoad.setMaximumSize(new Dimension(300,150));

        File dir = new File("./SaveGames");
        data = dir.listFiles();
        if(data == null) {
            dir.mkdirs();
            data = dir.listFiles();
        }
        String[] filenames = new String[data.length];
        BufferedReader in = null;
        File file;

        for(int i=0;i<data.length;i++){
            if(data[i].isFile() && data[i].canRead()){
                file = data[i];
                in = new BufferedReader(new FileReader(file));
                filenames[i] = in.readLine();
            }
        }
        lstLoad.setListData(filenames);

        lstLoad.setVisibleRowCount(filenames.length);
        scrollpane = new JScrollPane();
        scrollpane.setViewportView(lstLoad);
        scrollpane.setOpaque(false);
        scrollpane.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollpane.setMinimumSize(new Dimension(300,50));
        scrollpane.setMaximumSize(new Dimension(300,100));
        scrollpane.getViewport().setOpaque(false);
        lstLoad.setSelectedIndex(-1);
        lstLoad.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    lblStartHost.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/LoadGameBW.png"))));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        //Trennstrich für Host UI
        lblRect = new JLabel();
        lblRect.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblRect.setMinimumSize(new Dimension(40, 50));
        lblRect.setMaximumSize(new Dimension(40, 50));
        lblRect.setBackground(Color.BLACK);
        lblRect.setOpaque(true);

        lblLoad =  new JLabel();
        lblLoad.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblLoad.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/LoadBW.png"))));

    }

    private void runSingleplayer(int bound) {
        runMultiplayerServer(bound);
        new Thread(() -> {
            foe = new Client2("127.0.0.1");
            handleData(foe);
        }).start();
    }

    private void handleData(Connector c) {
        while (true) {
            String res = c.listenToNetwork();
            String[] cmd = res.split(" ");
            switch(cmd[0]) {
                case "size":
                    break;
            }
        }
    }

    private void runMultiplayerServer(int bound) {
        new Thread(() -> {
            self = new Server2();
            self.connect();

            self.sendmsg(String.format("size %d", bound));
            handleData(self);
        }).start();
    }

    private void runMuliplayerClient(String host) {
        new Thread(() -> {
            self = new Client2(host);
            handleData(self);
        }).start();
    }

    private void runKIvsKI() {

    }
}