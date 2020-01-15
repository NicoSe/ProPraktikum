package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicBorders;
import javax.swing.text.html.Option;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;

import GUI.Grid.*;
import Logic.Grid2D;
import Logic.GridController;
import Logic.Load;
import Logic.OptionsHandler;
import Misc.GridState;
import Network.Client;
import Network.Server;

public class MainFrame {

    //Variablen
    private JFrame jf;
    private BackgroundPanel backgroundPanel;
    private JPanel pnlButton;
    private JPanel pnlHostX;
    private JPanel pnlHostY;
    private BasicGrid pnlGrid1;
    private BasicGrid pnlGrid2;
    private JPanel pnlPlay;
    private JPanel pnlDummyThicc;
        private JPanel pnlDummy;


    private JLabel lblTitle;
    private JLabel lblPlay;
    private JLabel lblSingle;
    private JLabel lblSize;
    private JSlider sldSize;
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




    public MainFrame() throws IOException {
        Components();
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
        pnlHostX.setLayout(new BoxLayout(pnlHostX, BoxLayout.X_AXIS));
        pnlHostX.setMinimumSize(new Dimension(1024, 850));
        pnlHostX.setMaximumSize((new Dimension(1980, 1080)));

        pnlHostY = new JPanel();
        pnlHostY.setOpaque(false);
        pnlHostY.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlHostY.setAlignmentY(Component.CENTER_ALIGNMENT);
        pnlHostY.setLayout(new BoxLayout(pnlHostY, BoxLayout.Y_AXIS));
        pnlHostY.setMinimumSize(new Dimension(1024, 850));
        pnlHostY.setMaximumSize((new Dimension(1980, 1080)));

        //panelPlay
        pnlPlay = new JPanel();
        pnlPlay.setLayout(new BoxLayout(pnlPlay,BoxLayout.Y_AXIS));
        pnlPlay.setMinimumSize(new Dimension(1024,850));
        pnlPlay.setMaximumSize(new Dimension(1920,1080));

        pnlReady = new JPanel(new FlowLayout());
        pnlReady.setPreferredSize(new Dimension(100,100));
        pnlReady.setMaximumSize(new Dimension(jf.getWidth(),jf.getHeight()/7));
        pnlReady.setOpaque(false);
        JLabel lblReady = new JLabel("Ready");
        JLabel lblRandomise = new JLabel("Randomise");
        pnlReady.add(lblReady);
        pnlReady.add(lblRandomise);

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
                pnlButton.add(sldSize);
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
                    pnlButton.add(pnlHostX);

                    pnlHostY.add(lblSize);
                    pnlHostY.add(sldSize);
                    pnlHostX.add(pnlHostY);
                    pnlHostX.add(scrollpane);
                    pnlHostX.add(lblDummyObj);

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
        sldSize = new JSlider(5,30);
        sldSize.setOpaque(false);
        sldSize.setMaximumSize(new Dimension(512,50));
        sldSize.setMajorTickSpacing(5);
        sldSize.setMinorTickSpacing(1);
        sldSize.setPaintLabels(true);
        sldSize.setPaintTicks(true);
        sldSize.setFont(new Font("Sprites/PrStart.ttf", Font.BOLD, 20));
        sldSize.setForeground(Color.BLACK);
        sldSize.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
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

                //pnlPlay.add(pnlReady);

                //backgroundPanel.add(pnlPlay);
                pnlGrid1 = new BasicGrid(sldSize.getValue(), GridState.PLACE);
                Grid2D g2d = new Grid2D(sldSize.getValue());
                g2d.generateRandom();
                GridController controller = new GridController(g2d, pnlGrid1);
                controller.init(GridState.PLACE);
                pnlGrid1.setOpaque(false);
                pnlGrid1.setAlignmentX(Component.CENTER_ALIGNMENT);
                //pnlPlay.add(pnlGrid1);

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

                Client c = new Client(txfIPAdress.getText());
                if (c.isconnected()){
                    pnlButton.setVisible(false);
                    pnlButton.removeAll();
                    System.out.println("Starte Spiel");
                    //play game
                    pnlButton.setVisible(true);
                }
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
        lblShowIP.setText("IP ADDRESS: "+ inetAddress);
        lblShowIP.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Load List
        lstLoad = new JList();
        lstLoad.setFont(new Font("Sprites/PrStart.ttf", Font.BOLD, 20));
        lstLoad.setOpaque(false);
        lstLoad.setBorder(new BasicBorders.FieldBorder(Color.BLACK, Color.gray, Color.BLACK, Color.WHITE));
        lstLoad.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstLoad.setAlignmentX(Component.CENTER_ALIGNMENT);


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
        scrollpane.setMaximumSize(new Dimension(300,200));
        scrollpane.getViewport().setOpaque(false);
        //scrollpane.setLocation(jf.getWidth()-200, lblSize.getY());
        lstLoad.setSelectedIndex(-1);


        lblDummyObj = new JLabel();
        lblDummyObj.setMaximumSize(new Dimension(400, 100));
    }

}