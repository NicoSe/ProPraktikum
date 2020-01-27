package GUI;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
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
import java.awt.GridBagLayout;
import java.util.ArrayList;

import GUI.Grid.*;
import Logic.*;
import Misc.GridState;
import Network.*;

public class MainFrame {
    private JLabel lblComrade;
    ///net
    Connector net;
    Connector foe;
    Thread netThread;

    Grid2D selfGrid;
    Grid2D foeGrid;

    GridController gcS;
    GridController gcF;

    //AI ai;
    NewKI ki;
    Thread kiThread;

    Clip mainTheme;

    ///Variablen
    private JFrame jf;
    private BackgroundPanel backgroundPanel;
    private JPanel pnlButton;
    private JPanel pnlSingleX;
    private JPanel pnlSingleY;
    private JPanel pnlSingleY2;
    private JPanel pnlHostX;
    private JPanel pnlHostY;
    private JPanel pnlHostY2;
    private JLabel lblLoad;
    private BasicGrid pnlGrid1;
    private BasicGrid pnlGrid2;
    private JPanel pnlField;
    private JPanel pnlGridWrapper;


    private JLabel lblTitle;
    private JLabel lblPlay;
    private JLabel lblSingle;
    private JLabel lblMulti;
    private JLabel lblSize;
    private JSlider sldSizeSingle;
    private JLabel lblDifficulty;
    private JLabel lblHost;
    private JList lstLoad;
    private JList lstSingleLoad;
    private JScrollPane scrollpane;

    private String[] filenames;
    private String[] filedesc;

    private JLabel lblStartHostNew;
    private JLabel lblStartHostLoad;
    private JLabel lblStartSingleNew;
    private JLabel lblStartSingleLoad;
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
    private JLabel lblReturnToGameMode;
    private JLabel lblRect;

    private JLabel lblReady;
    private JLabel lblRandomize;
    private JLabel lblPlaceReturn;

    private JLabel lblSave;
    private JLabel lblTurn;

    private JLabel pnlFoeGrid;
    private boolean foeBigState = false;
    private JLabel lblLoading;
    private MouseListener resizeFoeGridListener;
    private JComboBox<String> comboDifficulty;

    private JLabel lblKivsKi;
    private JLabel lblKiHost;
    private JLabel lblKiHostGame;
    private JLabel lblKiJoin;
    private JLabel lblKiConnect;


    private MainFrame mf;
    public MainFrame() throws IOException {
        Components();
        this.mf = this;
    }

    private void setReadyPanelStatus(boolean isOnStart) {
        pnlReady.removeAll();
        if(isOnStart) {
            pnlReady.add(lblPlaceReturn);
            pnlReady.add(lblRandomize);
            pnlReady.add(lblReady);
        } else {
            pnlReady.add(lblSave);
        }
        pnlReady.add(lblTurn);
        pnlReady.revalidate();
        pnlReady.repaint();
    }

    ///Komponenten
    private void Components() throws IOException {
        //Frame
        jf = new JFrame();
        jf.setMinimumSize(new Dimension(1024, 850));
        jf.setMaximumSize(new Dimension(1920, 1080));
        jf.setTitle("Battleships");
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        mainTheme = Helpers.playSFX("/Music/pirate.wav", 0);
        mainTheme.loop(Clip.LOOP_CONTINUOUSLY);

        if (OptionsHandler.getFullscreenMode()){
            jf.setExtendedState(JFrame.MAXIMIZED_BOTH);
            jf.dispose();
            jf.setUndecorated(true);
            jf.setVisible(true);
        }

        backgroundPanel = new BackgroundPanel(ImageIO.read(getClass().getResource("/Sprites/Waltertile2_64.png")), BackgroundPanel.TILED);
        backgroundPanel.setMinimumSize(new Dimension(1024, 850));
        backgroundPanel.setMaximumSize(new Dimension(1920, 1080));
        jf.setContentPane(backgroundPanel);

        ///panel
        pnlButton = new JPanel();
        pnlButton.setOpaque(true);
        pnlButton.setLayout(new BoxLayout(pnlButton,BoxLayout.Y_AXIS));
        pnlButton.setMinimumSize(new Dimension(1024,850));
        pnlButton.setMaximumSize(new Dimension(1920,1080));
        backgroundPanel.add(pnlButton);

        /// Panel that holds left and right side of Singleplayer selection
        pnlSingleX = new JPanel();
        pnlSingleX.setOpaque(false);
        pnlSingleX.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlSingleX.setMinimumSize(new Dimension(1024, 350));
        pnlSingleX.setMaximumSize((new Dimension(1980, 350)));
        pnlSingleX.setLayout(new GridBagLayout());

        ///Left Panel of Singleplayer selection
        pnlSingleY = new JPanel();
        pnlSingleY.setOpaque(false);
        pnlSingleY.setAlignmentX(Component.RIGHT_ALIGNMENT);
        pnlSingleY.setAlignmentY(Component.RIGHT_ALIGNMENT);
        pnlSingleY.setLayout(new BoxLayout(pnlSingleY, BoxLayout.Y_AXIS));
        pnlSingleY.setMinimumSize(new Dimension(300, 350));
        pnlSingleY.setMaximumSize(new Dimension(300,350));
        pnlSingleY.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        pnlSingleY.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                pnlButton.setVisible(false);
                pnlButton.remove(lblStartSingleLoad);
                pnlButton.remove(lblReturnToGameMode);
                lstLoad.clearSelection();
                lstSingleLoad.clearSelection();
                pnlButton.add(lblStartSingleNew);
                pnlButton.add(lblReturnToGameMode);
                pnlButton.setVisible(true);
            }

        });

        ///Right Panel of Singleplayer Selection
        pnlSingleY2 = new JPanel();
        pnlSingleY2.setOpaque(false);
        pnlSingleY2.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlSingleY2.setAlignmentY(Component.CENTER_ALIGNMENT);
        pnlSingleY2.setLayout(new BoxLayout(pnlSingleY2, BoxLayout.Y_AXIS));
        pnlSingleY2.setMinimumSize(new Dimension(300, 350));
        pnlSingleY2.setMaximumSize((new Dimension(300, 350)));
        pnlSingleY2.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));


        /// Panel that holds left and right side of Host selection
        pnlHostX = new JPanel();
        pnlHostX.setOpaque(false);
        pnlHostX.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlHostX.setMinimumSize(new Dimension(1024, 350));
        pnlHostX.setMaximumSize((new Dimension(1980, 350)));
        pnlHostX.setLayout(new GridBagLayout());

        ///Left Panel of Host selection
        pnlHostY = new JPanel();
        pnlHostY.setOpaque(false);
        pnlHostY.setAlignmentX(Component.RIGHT_ALIGNMENT);
        pnlHostY.setAlignmentY(Component.RIGHT_ALIGNMENT);
        pnlHostY.setLayout(new BoxLayout(pnlHostY, BoxLayout.Y_AXIS));
        pnlHostY.setMinimumSize(new Dimension(300, 350));
        pnlHostY.setMaximumSize(new Dimension(300,350));
        pnlHostY.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        pnlHostY.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                lstLoad.clearSelection();
                lstSingleLoad.clearSelection();
                pnlButton.setVisible(false);
                pnlButton.remove(lblStartHostLoad);
                pnlButton.remove(lblShowIP);
                pnlButton.remove(lblReturnToGameMode);
                pnlButton.add(lblStartHostNew);
                pnlButton.add(lblReturnToGameMode);
                pnlButton.add(lblShowIP);
                pnlButton.setVisible(true);

            }

        });

        ///Right Panel of Host Selection
        pnlHostY2 = new JPanel();
        pnlHostY2.setOpaque(false);
        pnlHostY2.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlHostY2.setAlignmentY(Component.CENTER_ALIGNMENT);
        pnlHostY2.setLayout(new BoxLayout(pnlHostY2, BoxLayout.Y_AXIS));
        pnlHostY2.setMinimumSize(new Dimension(300, 350));
        pnlHostY2.setMaximumSize((new Dimension(300, 350)));
        pnlHostY2.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));


        ///panel with Ready,Return and Randomize Button on grid
        pnlReady = new JPanel(new FlowLayout());
        pnlReady.setPreferredSize(new Dimension(64,64*5));
        pnlReady.setMaximumSize(new Dimension(jf.getWidth(),jf.getHeight()/7));
        pnlReady.setOpaque(false); //CHANGE THIS BACK FOR TRANNSPA
        pnlReady.setBackground(Color.GREEN);

        lblPlaceReturn = new JLabel();
        lblPlaceReturn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/PlaceReturnBW.png"))));
        lblReady = new JLabel();
        lblReady.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ReadyBW.png"))));
        lblRandomize = new JLabel();
        lblRandomize.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/RandomBW.png"))));

        lblSave = new JLabel();
        lblSave.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/SaveBW.png"))));
        lblTurn = new JLabel();
        lblTurn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/EnemyTurn.png"))));

        setReadyPanelStatus(true);

        ///Panel that Holds Enemy Grid
        pnlFoeGrid = new JLabel();
        pnlFoeGrid.setLayout(new FlowLayout(FlowLayout.RIGHT));
        pnlFoeGrid.setPreferredSize(new Dimension(225,225));
        pnlFoeGrid.setMaximumSize(new Dimension(jf.getWidth(),jf.getHeight()/6));
        pnlFoeGrid.setBackground(Color.BLUE);
        pnlFoeGrid.setOpaque(false); //CHANGE THIS BACK FOR TRANNSPA


        ///panel DUmmythicc contains the Main Gridpanel
        pnlField =  new JPanel(new BorderLayout());
        pnlField.setBackground(Color.RED);
        pnlField.setBorder(BorderFactory.createEmptyBorder(jf.getHeight()/15,jf.getWidth()/15,jf.getHeight()/15,jf.getWidth()/15));

        pnlGridWrapper =  new JPanel();
        pnlGridWrapper.setBackground(Color.GREEN);

        /// Combobox for KI difficulty selection
        String[] difficultyOptions = {"Easy", "Medium", "Hard"};
        comboDifficulty = new JComboBox<String>(difficultyOptions);
        comboDifficulty.setPreferredSize(new Dimension(10,30));
        comboDifficulty.setOpaque(true);
        comboDifficulty.setSelectedIndex(1);


//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
///Buttons for Button panel and Object Disign

        ///Titel
        lblTitle = new JLabel();
        lblTitle.setIcon(new ImageIcon(getClass().getResource("/Sprites/Title_v11.gif")));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlButton.add(lblTitle);

        //soviet
        lblComrade = new JLabel();
        lblComrade.setIcon(new ImageIcon(getClass().getResource("/Sprites/sovietflag.gif")));

        //Loading animation
        lblLoading =  new JLabel();
        lblLoading.setIcon(new ImageIcon(getClass().getResource("/Sprites/Loadinanimation.gif")));
        lblLoading.setAlignmentX(Component.CENTER_ALIGNMENT);

        ///Play Button  moves to Mode selection
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
                pnlButton.add(lblMulti);
                pnlButton.add(lblKivsKi);
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


        ///Options Button moves to option selection
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

        ///Credits Button shows creators of this game
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

        ///Exit Button quits the application
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

        ///Return Button Returns to the Main Menu
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

        ///Return Button Returns to the Gamemode Selection screen
        lblReturnToGameMode = new JLabel();
        lblReturnToGameMode.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ReturnBW.png"))));
        lblReturnToGameMode.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblReturnToGameMode.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                try {

                    Helpers.playSFX("/SFX/firered_0017.wav", 1);
                    lblSingle.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/SingleBW.png"))));
                    lblMulti.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/MultiplayerBW.png"))));
                    lblReturnToGameMode.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ReturnBW.png"))));
                    pnlButton.setVisible(false);
                    pnlButton.removeAll();
                    pnlButton.add(lblTitle);
                    pnlButton.add(lblSingle);
                    pnlButton.add(lblMulti);
                    pnlButton.add(lblKivsKi);
                    pnlButton.add(lblReturn);
                    pnlButton.setVisible(true);
                    resetNetwork();

                } catch(IOException el){
                    el.printStackTrace();
                }
            }
            public void mouseEntered(MouseEvent e) {
                try {
                    lblReturnToGameMode.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ReturnWB.png"))));
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
                    lblReturnToGameMode.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ReturnBW.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mousePressed(MouseEvent e){
                try {
                    lblReturnToGameMode.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ReturnOnPress.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseReleased(MouseEvent e){
                try {
                    lblReturnToGameMode.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ReturnWB.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        ///Singleplayer Button Opens the singleplayer Options
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

                GridBagConstraints c = new GridBagConstraints();
                c.gridheight = 3;
                c.anchor = GridBagConstraints.LINE_START;
                c.fill = GridBagConstraints.VERTICAL;
                c.ipadx = 5;
                c.ipady = 5;
                pnlSingleY.add(lblSize);
                pnlSingleY.add(sldSizeSingle);
                pnlSingleY.add(lblDifficulty);
                pnlSingleY.add(comboDifficulty);
                pnlSingleX.add(pnlSingleY,c);

                c.anchor = GridBagConstraints.CENTER;
                c.fill = GridBagConstraints.VERTICAL;
                pnlSingleX.add(lblRect,c);

                c.anchor = GridBagConstraints.LINE_END;
                c.fill = GridBagConstraints.VERTICAL;
                c.gridheight = 3;
                pnlSingleY2.add(lblLoad);
                scrollpane.setViewportView(lstSingleLoad);
                pnlSingleY2.add(scrollpane);
                pnlSingleX.add(pnlSingleY2, c);

                pnlButton.add(pnlSingleX);
                pnlButton.add(lblStartSingleNew);
                pnlButton.add(lblReturnToGameMode);

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

        ///Button that opens the Multiplayer options
        lblMulti = new JLabel();
        lblMulti.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/MultiplayerBW.png"))));
        lblMulti.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblMulti.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                pnlButton.setVisible(false);
                pnlButton.removeAll();
                pnlButton.add(lblTitle);
                pnlButton.add(lblHost);
                pnlButton.add(lblJoin);
                pnlButton.add(lblReturnToGameMode);
                pnlButton.setVisible(true);

            }
            public void mouseEntered(MouseEvent e) {
                try {
                    lblMulti.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/MultiplayerWB.png"))));
                    Helpers.playSFX("/SFX/Menu_Tick.wav", 1);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseExited(MouseEvent e){
                try {
                    lblMulti.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/MultiplayerBW.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mousePressed(MouseEvent e){
                try {
                    lblMulti.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/MultiplayerOnPress.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseReleased(MouseEvent e){
                try {
                    lblMulti.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/MultiplayerWB.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        ///Host Button Opens the host game options
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
                    scrollpane.setViewportView(lstLoad);
                    pnlHostY2.add(scrollpane);
                    pnlHostX.add(pnlHostY2, c);

                    pnlButton.add(pnlHostX);
                    pnlButton.add(lblStartHostNew);
                    pnlButton.add(lblReturnToGameMode);
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

        ///Join Button Opens the Join game options
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
                    pnlButton.add(lblReturnToGameMode);
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

        ///KivsKi Button Opens the KivsKi game menu
        lblKivsKi = new JLabel();
        lblKivsKi.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/KivskiBW.png"))));
        lblKivsKi.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblKivsKi.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                try {
                    Helpers.playSFX("/SFX/SA2_142.wav", 1);
                    pnlButton.setVisible(false);
                    pnlButton.removeAll();
                    pnlButton.add(lblTitle);
                    pnlButton.add(lblKiHost);
                    pnlButton.add(lblKiJoin);
                    pnlButton.add(lblReturnToGameMode);
                    pnlButton.setVisible(true);
                    lblKivsKi.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/KivskiBW.png"))));
                } catch(IOException el){
                    el.printStackTrace();
                }
            }
            public void mouseEntered(MouseEvent e) {
                try {
                    lblKivsKi.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/KivskiWB.png"))));
                    Helpers.playSFX("/SFX/Menu_Tick.wav", 1);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseExited(MouseEvent e){
                try {
                    lblKivsKi.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/KivskiBW.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mousePressed(MouseEvent e){
                try {
                    lblKivsKi.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/KivskiOnPress.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseReleased(MouseEvent e){
                try {
                    lblKivsKi.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/KivskiWB.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        ///Button to Host KI server
        lblKiHost = new JLabel();
        lblKiHost.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/KiHostBW.png"))));
        lblKiHost.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblKiHost.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){

                pnlButton.setVisible(false);
                pnlButton.removeAll();
                pnlButton.add(lblTitle);
                pnlButton.add(lblSize);
                pnlButton.add(sldSizeSingle);
                pnlButton.add(lblDifficulty);
                pnlButton.add(comboDifficulty);
                pnlButton.add(lblKiHostGame);
                pnlButton.add(lblReturnToGameMode);
                pnlButton.add(lblShowIP);
                pnlButton.setVisible(true);
                try {
                    lblKiHost.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/KiHostBW.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
            public void mouseEntered(MouseEvent e) {
                try {
                    lblKiHost.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/KiHostWB.png"))));
                    Helpers.playSFX("/SFX/Menu_Tick.wav", 1);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseExited(MouseEvent e){
                try {
                    lblKiHost.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/KiHostBW.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mousePressed(MouseEvent e){
                try {
                    lblKiHost.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/KiHostOnPress.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseReleased(MouseEvent e){
                try {
                    lblKiHost.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/KiHostWB.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        ///button to host a game as KI
        lblKiHostGame = new JLabel();
        lblKiHostGame.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/StartGameBW.png"))));
        lblKiHostGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblKiHostGame.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                Helpers.playSFX("/SFX/SA2_142.wav", 1);
                runKIServer(sldSizeSingle.getValue(), comboDifficulty.getSelectedIndex());
            }
            public void mouseEntered(MouseEvent e) {
                try {
                    lblKiHostGame.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/StartGameWB.png"))));
                    Helpers.playSFX("/SFX/Menu_Tick.wav", 1);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseExited(MouseEvent e){
                try {
                    lblKiHostGame.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/StartGameBW.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mousePressed(MouseEvent e){
                try {
                    lblKiHostGame.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/StartGameOnPress.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseReleased(MouseEvent e){
                try {
                    lblKiHostGame.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/StartGameWB.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        ///button to join as KI
        lblKiJoin = new JLabel();
        lblKiJoin.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/KiJoinBW.png"))));
        lblKiJoin.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblKiJoin.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                try {
                    Helpers.playSFX("/SFX/SA2_142.wav", 1);
                    pnlButton.setVisible(false);
                    pnlButton.removeAll();
                    pnlButton.add(lblTitle);
                    pnlButton.add(lblIPAdress);
                    pnlButton.add(txfIPAdress);
                    pnlButton.add(comboDifficulty);
                    pnlButton.add(lblKiConnect);
                    pnlButton.add(lblReturnToGameMode);
                    pnlButton.setVisible(true);
                    lblKiJoin.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/KiJoinBW.png"))));
                } catch(IOException el){
                    el.printStackTrace();
                }
            }
            public void mouseEntered(MouseEvent e) {
                try {
                    lblKiJoin.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/KiJoinWB.png"))));
                    Helpers.playSFX("/SFX/Menu_Tick.wav", 1);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseExited(MouseEvent e){
                try {
                    lblKiJoin.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/KiJoinBW.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mousePressed(MouseEvent e){
                try {
                    lblKiJoin.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/KiJoinOnPress.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseReleased(MouseEvent e){
                try {
                    lblKiJoin.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/KiJoinWB.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        ///button to connect to a Server as KI
        lblKiConnect = new JLabel();
        lblKiConnect.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ConnectBW.png"))));
        lblKiConnect.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblKiConnect.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                Helpers.playSFX("/SFX/SA2_142.wav", 1);
                runKIClient(txfIPAdress.getText(), comboDifficulty.getSelectedIndex());
            }
            public void mouseEntered(MouseEvent e) {
                try {
                    lblKiConnect.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ConnectWB.png"))));
                    Helpers.playSFX("/SFX/Menu_Tick.wav", 1);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseExited(MouseEvent e){
                try {
                    lblKiConnect.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ConnectBW.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mousePressed(MouseEvent e){
                try {
                    lblKiConnect.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ConnectOnPress.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseReleased(MouseEvent e){
                try {
                    lblKiConnect.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ConnectWB.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        ///Size Slider and label used to determine grid size on singleplayer
        lblSize =  new JLabel();
        lblSize.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/SizeBW.png")).getScaledInstance(400,100,Image.SCALE_SMOOTH)));
        lblSize.setAlignmentX(Component.CENTER_ALIGNMENT);
        sldSizeSingle = new JSlider(5,30);
        sldSizeSingle.setOpaque(false);
        sldSizeSingle.setMinimumSize(new Dimension(300,50));
        sldSizeSingle.setMaximumSize(new Dimension(450,50));
        sldSizeSingle.setMajorTickSpacing(5);
        sldSizeSingle.setMinorTickSpacing(1);
        sldSizeSingle.setValue(10);
        sldSizeSingle.setPaintLabels(true);
        sldSizeSingle.setPaintTicks(true);
        sldSizeSingle.setFont(new Font("Sprites/PrStart.ttf", Font.BOLD, 20));
        sldSizeSingle.setForeground(Color.BLACK);
        sldSizeSingle.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

            }
        });

        ///Difficulty sprite
        lblDifficulty = new JLabel();
        lblDifficulty.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/DifficultyBW.png"))));
        lblDifficulty.setAlignmentX(Component.CENTER_ALIGNMENT);

        /// button that returns from Grid back to menu
        lblPlaceReturn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    pnlReady.setVisible(false);
                    Helpers.playSFX("/SFX/firered_0017.wav", 1);
                    lblPlaceReturn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/PlaceReturnBW.png"))));
                    lblPlay.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/PlayBW.png"))));
                    lblStartSingleNew.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/NewGameBW.png"))));
                    pnlGridWrapper.setVisible(false);
                    pnlField.setBorder(BorderFactory.createEmptyBorder(jf.getHeight()/15,jf.getWidth()/15,jf.getHeight()/15,jf.getWidth()/15));
                    pnlGrid1.removeMouseListener(resizeFoeGridListener);
                    pnlButton.removeAll();
                    pnlButton.setVisible(false);
                    pnlButton.add(lblTitle);
                    pnlButton.add(lblPlay);
                    pnlButton.add(lblOptions);
                    pnlButton.add(lblCredits);
                    pnlButton.add(lblExit);
                    pnlButton.setVisible(true);
                    backgroundPanel.add(pnlButton);
                    resetNetwork();



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

        ///Button that places the ships randomly on the grid
        lblRandomize.addMouseListener(new MouseAdapter() {
            ;
            public void mouseClicked (MouseEvent e){
                Helpers.playSFX("/SFX/SA2_142.wav", 1);
                gcS.randomize();
            }

            public void mouseEntered (MouseEvent e){
                try {
                    lblRandomize.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/RandomWB.png"))));
                    Helpers.playSFX("/SFX/Menu_Tick.wav", 1);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseExited (MouseEvent e){
                try {
                    lblRandomize.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/RandomBW.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mousePressed (MouseEvent e){
                try {
                    lblRandomize.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/RandomOnPress.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseReleased (MouseEvent e){
                try {
                    lblRandomize.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/RandomWB.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        ///Button that places the ships randomly on the grid
        lblSave.addMouseListener(new MouseAdapter() {
            ;
            public void mouseClicked (MouseEvent e){
                Helpers.playSFX("/SFX/SA2_142.wav", 1);
                if(net.turn()) {
                    long savetime = System.currentTimeMillis();
                    net.sendMessage(String.format("save %d", savetime));
                    SaveManager.save(String.format("%d", savetime), selfGrid, foeGrid);
                    net.close();

                    try {
                        if(kiThread != null) {
                            kiThread.join();
                        }
                        if(netThread != null) {
                            netThread.join();
                        }
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    System.exit(0);
                }
            }

            public void mouseEntered (MouseEvent e){
                try {
                    lblSave.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/SaveWB.png"))));
                    Helpers.playSFX("/SFX/Menu_Tick.wav", 1);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseExited (MouseEvent e){
                try {
                    lblSave.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/SaveBW.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mousePressed (MouseEvent e){
                try {
                    lblSave.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/SaveOnPress.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseReleased (MouseEvent e){
                try {
                    lblSave.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/SaveWB.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        /// button that confirms that you´re done placing your ships and ready to play
        lblReady.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Helpers.playSFX("/SFX/SA2_142.wav", 1);
                if(net.turn()) {
                    gcS.onFinalizePlace();
                    setTurn(false);
                    net.sendMessage("confirmed");
                    onGameReady();
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

        ///Fullscreen Button
        lblFullscreen = new JLabel();
        lblFullscreen.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/FullscreenBW.png"))));
        lblFullscreen.setAlignmentX(Component.CENTER_ALIGNMENT);

        ///Fullscreen Checkbox Picture
        lblFullscreenPicture = new JLabel();
        if (OptionsHandler.getFullscreenMode()){
            lblFullscreenPicture.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/Checkbox_ticked.png")).getScaledInstance(40,40,Image.SCALE_SMOOTH)));
        }
        else{
            lblFullscreenPicture.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/Checkbox_clear.png")).getScaledInstance(40,40,Image.SCALE_SMOOTH)));
        }
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

        ///Music Label
        lblMusic = new JLabel();
        lblMusic.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/musicVolumeBW.png"))));
        lblMusic.setAlignmentX(Component.CENTER_ALIGNMENT);

        ///SFX Label
        lblSFX = new JLabel();
        lblSFX.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/sfxVolumeBW.png"))));
        lblSFX.setAlignmentX(Component.CENTER_ALIGNMENT);

        ///Music Slider
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
                if(mainTheme != null) {
                    Helpers.fixVolume(mainTheme, 0);
                }
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

        ///IPAdress Label and Text Field
        lblIPAdress = new JLabel();
        lblIPAdress.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/IpAddressBW.png"))));
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

        ///Connect Button
        lblConnect = new JLabel();
        lblConnect.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ConnectBW.png"))));
        lblConnect.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblConnect.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                Helpers.playSFX("/SFX/SA2_142.wav", 1);

                runMuliplayerClient(txfIPAdress.getText());
                pnlButton.setVisible(false);
                pnlButton.add(lblLoading);
                pnlButton.setVisible(true);
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
                    lblConnect.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ConnectWB.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseExited(MouseEvent e){
                try {
                    lblConnect.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ConnectBW.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mousePressed(MouseEvent e){
                try {
                    lblConnect.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ConnectOnPress.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseReleased(MouseEvent e){
                try {
                    lblConnect.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/ConnectWB.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        ///Start new Singleplayer game
        lblStartSingleNew = new JLabel();
        lblStartSingleNew.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/NewGameBW.png"))));
        lblStartSingleNew.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblStartSingleNew.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                Helpers.playSFX("/SFX/SA2_142.wav", 1);
                backgroundPanel.removeAll();

                runSingleplayer(sldSizeSingle.getValue());

                pnlGrid1 = new BasicGrid(sldSizeSingle.getValue(), GridState.PLACE);
                selfGrid = new Grid2D(sldSizeSingle.getValue());
                selfGrid.generateRandom();
                gcS = new GridController(selfGrid, null, pnlGrid1);
                gcS.init(GridState.PLACE);
                pnlGrid1.setOpaque(false);
                pnlGrid1.setAlignmentX(Component.CENTER_ALIGNMENT);

                pnlGrid2 = new BasicGrid(sldSizeSingle.getValue(), GridState.FORBID);
                foeGrid = new Grid2D(sldSizeSingle.getValue());
                foeGrid.placeFgoOnEmptyFields();
                gcF = new GridController(foeGrid, net, pnlGrid2);
                gcF.init(GridState.FORBID);

                backgroundPanel.add(pnlField);

                pnlField.removeAll();
                pnlGridWrapper.removeAll();
                pnlGridWrapper.setOpaque(false); //CHANGE THIS BACK FOR TRANNSPA
                pnlField.add(pnlGridWrapper, BorderLayout.CENTER);
                pnlGridWrapper.add(pnlGrid1);
                setReadyPanelStatus(true);
                pnlGridWrapper.add(pnlReady);
                pnlReady.setVisible(true);
                pnlGridWrapper.setVisible(true);
                try {
                    backgroundPanel.setImage(ImageIO.read(getClass().getResource("/Sprites/Waltertile2_64.png")));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                /*
                if(OptionsHandler.getFullscreenMode()){
                    jf.setSize(new Dimension(1981,1080));
                    jf.setSize(new Dimension(1980,1080));
                }else{
                    jf.setSize(new Dimension(1025,851));
                    jf.setSize(new Dimension(1024,850));
                }

                 */
                jf.revalidate();
                jf.repaint();
            }
            public void mouseEntered(MouseEvent e) {
                try {
                    lblStartSingleNew.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/NewGameWB.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseExited(MouseEvent e){
                try {
                    lblStartSingleNew.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/NewGameBW.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mousePressed(MouseEvent e){
                try {
                    lblStartSingleNew.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/NewGameOnPress.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseReleased(MouseEvent e){
                try {
                    lblPlay.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/NewGameWB.png"))));
                    Helpers.playSFX("/SFX/Menu_Tick.wav", 1);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        /// Start singleplayer game with load file
        lblStartSingleLoad = new JLabel();
        lblStartSingleLoad.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/LoadGameBW.png"))));
        lblStartSingleLoad.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblStartSingleLoad.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                int idx = lstSingleLoad.getSelectedIndex();
                runSingleplayer(filenames[idx]);
            }
            public void mouseEntered(MouseEvent e) {
                try {
                    lblStartSingleLoad.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/LoadGameWB.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseExited(MouseEvent e){
                try {
                    lblStartSingleLoad.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/LoadGameBW.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mousePressed(MouseEvent e){
                try {
                    lblStartSingleLoad.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/LoadGameOnPress.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseReleased(MouseEvent e){
                try {
                    lblPlay.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/LoadGameWB.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });


        ///Host Start Button
        lblStartHostNew = new JLabel();
        lblStartHostNew.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/NewGameBW.png"))));
        lblStartHostNew.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblStartHostNew.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                Helpers.playSFX("/SFX/SA2_142.wav", 1);
                runMultiplayerServer(sldSizeSingle.getValue());
                Helpers.playSFX("/SFX/SA2_142.wav", 1);
                backgroundPanel.removeAll();


                pnlGrid1 = new BasicGrid(sldSizeSingle.getValue(), GridState.PLACE);
                selfGrid = new Grid2D(sldSizeSingle.getValue());
                selfGrid.generateRandom();
                gcS = new GridController(selfGrid, null, pnlGrid1);
                gcS.init(GridState.PLACE);
                pnlGrid1.setOpaque(false);
                pnlGrid1.setAlignmentX(Component.CENTER_ALIGNMENT);

                pnlGrid2 = new BasicGrid(sldSizeSingle.getValue(), GridState.FORBID);
                foeGrid = new Grid2D(sldSizeSingle.getValue());
                foeGrid.placeFgoOnEmptyFields();
                gcF = new GridController(foeGrid, net, pnlGrid2);
                gcF.init(GridState.FORBID);

                backgroundPanel.add(pnlField);

                pnlField.removeAll();
                pnlGridWrapper.removeAll();
                pnlGridWrapper.setOpaque(false);
                pnlField.add(pnlGridWrapper, BorderLayout.CENTER);
                pnlGridWrapper.add(pnlGrid1);
                setReadyPanelStatus(true);
                pnlGridWrapper.add(pnlReady);
                pnlReady.setVisible(true);
                pnlGridWrapper.setVisible(true);
                try {
                    backgroundPanel.setImage(ImageIO.read(getClass().getResource("/Sprites/Waltertile2_64.png")));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                /*
                if(OptionsHandler.getFullscreenMode()){
                    jf.setSize(new Dimension(1981,1080));
                    jf.setSize(new Dimension(1980,1080));
                }else{
                    jf.setSize(new Dimension(1025,851));
                    jf.setSize(new Dimension(1024,850));
                }

                 */
                jf.revalidate();
                jf.repaint();
            }
            public void mouseEntered(MouseEvent e) {
                try {
                    lblStartHostNew.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/NewGameWB.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseExited(MouseEvent e){
                try {
                    lblStartHostNew.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/NewGameBW.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mousePressed(MouseEvent e){
                try {
                    lblStartHostNew.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/NewGameOnPress.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseReleased(MouseEvent e){
                try {
                    lblPlay.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/NewGameWB.png"))));
                    Helpers.playSFX("/SFX/Menu_Tick.wav", 1);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        ///Start Hosted Game with Loadfile
        lblStartHostLoad = new JLabel();
        lblStartHostLoad.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/LoadGameBW.png"))));
        lblStartHostLoad.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblStartHostLoad.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                int idx = lstLoad.getSelectedIndex();
                runMultiplayerServer(filenames[idx]);
            }
            public void mouseEntered(MouseEvent e) {
                try {
                    lblStartHostLoad.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/LoadGameWB.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseExited(MouseEvent e){
                try {
                    lblStartHostLoad.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/LoadGameBW.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mousePressed(MouseEvent e){
                try {
                    lblStartHostLoad.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/LoadGameOnPress.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseReleased(MouseEvent e){
                try {
                    lblPlay.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/LoadGameWB.png"))));
                    Helpers.playSFX("/SFX/Menu_Tick.wav", 1);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        ///Show own IP adress
        lblShowIP = new JLabel();
        InetAddress inetAddress = InetAddress.getLocalHost();
        lblShowIP.setFont(new Font("Sprites/PrStart.ttf", Font.BOLD, 20));
        lblShowIP.setText("IP ADDRESS: "+ inetAddress.getHostAddress());
        lblShowIP.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblShowIP.setBorder(BorderFactory.createEmptyBorder(0,0,5,jf.getWidth()/2));

        ///Load List Host
        lstLoad = new JList();
        lstLoad.setFont(new Font("Sprites/PrStart.ttf", Font.BOLD, 20));
        lstLoad.setOpaque(false);
        lstLoad.setBorder(new BasicBorders.FieldBorder(Color.BLACK, Color.gray, Color.BLACK, Color.WHITE));
        lstLoad.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstLoad.setAlignmentX(Component.CENTER_ALIGNMENT);
        lstLoad.setMinimumSize(new Dimension(300,50));
        lstLoad.setMaximumSize(new Dimension(300,150));

        ///Load List Single
        lstSingleLoad = new JList();
        lstSingleLoad.setFont(new Font("Sprites/PrStart.ttf", Font.BOLD, 20));
        lstSingleLoad.setOpaque(false);
        lstSingleLoad.setBorder(new BasicBorders.FieldBorder(Color.BLACK, Color.gray, Color.BLACK, Color.WHITE));
        lstSingleLoad.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstSingleLoad.setAlignmentX(Component.CENTER_ALIGNMENT);
        lstSingleLoad.setMinimumSize(new Dimension(300,50));
        lstSingleLoad.setMaximumSize(new Dimension(300,150));

        File dir = new File("./SaveGames");
        File[] data = dir.listFiles();
        if(data == null) {
            dir.mkdirs();
            data = dir.listFiles();
        }

        ArrayList<String> fn = new ArrayList<>();
        ArrayList<String> fd = new ArrayList<>();
        BufferedReader in = null;
        File file;

        for(int i=0;i<data.length;i++){
            if(data[i].isFile() && data[i].canRead()){
                file = data[i];

                String name = file.getName();
                int pos = name.lastIndexOf(".");
                if (pos > 0) {
                    name = name.substring(0, pos);
                }

                if(name.startsWith("ai")) {
                    continue;
                }

                fn.add(name);

                in = new BufferedReader(new FileReader(file));
                fd.add(in.readLine());
            }
        }
        filenames = fn.stream().toArray(String[]::new);
        filedesc = fd.stream().toArray(String[]::new);

        lstLoad.setListData(filedesc);
        lstLoad.setVisibleRowCount(filedesc.length);
        lstSingleLoad.setListData(filedesc);
        lstSingleLoad.setVisibleRowCount(filedesc.length);
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
                pnlButton.setVisible(false);
                pnlButton.remove(lblStartHostNew);
                pnlButton.remove(lblShowIP);
                pnlButton.remove(lblReturnToGameMode);
                pnlButton.add(lblStartHostLoad);
                pnlButton.add(lblReturnToGameMode);
                pnlButton.add(lblShowIP);
                pnlButton.setVisible(true);
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

        lstSingleLoad.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pnlButton.setVisible(false);
                pnlButton.remove(lblStartSingleNew);
                pnlButton.remove(lblReturnToGameMode);
                pnlButton.add(lblStartSingleLoad);
                pnlButton.add(lblReturnToGameMode);
                pnlButton.setVisible(true);
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

        ///Trennstrich für Host UI
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

    private void resetThiccPanel() {

    }

    private void runSingleplayer(String save) {
        resetNetwork();
        handleLoadEvent(save, false);

        netThread = new Thread(() -> {
            net = new Server();
            net.connect();

            net.sendMessage(String.format("load %s", save));
            handleData(net);
        });
        netThread.start();

        if(ki != null) {
            ki.close();
            ki = null;
        }

        kiThread = new Thread(() -> {
            ki = new NewKI(new Client("localhost"), null, comboDifficulty.getSelectedIndex());
        });
        kiThread.start();
    }

    private void runSingleplayer(int bound) {
        resetNetwork();
        netThread = new Thread(() -> {
            net = new Server();
            net.connect();

            net.sendMessage(String.format("size %d", bound));
            handleData(net);
        });
        netThread.start();

        if(ki != null) {
            ki.close();
            ki = null;
        }

        kiThread = new Thread(() -> {
            ki = new NewKI(new Client("localhost"), null, comboDifficulty.getSelectedIndex());
        });
        kiThread.start();
    }

    private void closeSinglePlayerConnection(Client client){
        client.close();
    }

    private void setTurn(boolean isSelfTurn) {
        try {
            lblTurn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource(String.format("/Sprites/%s", isSelfTurn ? "YourTurn.png" : "EnemyTurn.png")))));
            lblTurn.revalidate();
            lblTurn.repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setGridFromKI(Grid2D self, Grid2D foe) {
        selfGrid = self;
        foeGrid = foe;

        pnlGrid1 = new BasicGrid(selfGrid.getBound(), GridState.FORBID);
        pnlGrid2 = new BasicGrid(foeGrid.getBound(), GridState.FORBID);

        gcS = new GridController(selfGrid, null, pnlGrid1);
        gcS.init(GridState.FORBID);
        pnlGrid1.setOpaque(false);
        pnlGrid1.setAlignmentX(Component.CENTER_ALIGNMENT);

        gcF = new GridController(foeGrid, null, pnlGrid2);
        gcF.init(GridState.FORBID);

        SwingUtilities.invokeLater(() -> {
            runAfterGridInit(true);
            setTurn(false); // ???
            onGameReady();
            gcS.setInteractionState(GridState.FORBID);
            gcF.setInteractionState(GridState.FORBID);
        });
    }

    public void handleKIShoot(int x, int y) {
        gcF.shoot(x, y);
    }

    ///handle "shot" event that got forwarded from KI
    public boolean handleOnKIShot(int x, int y) {
        ShotResult result = selfGrid.shoot(x, y);
        //its the players turn, when the result says he hit nothing.
        SwingUtilities.invokeLater(() -> {
            setTurn(result.ordinal() == 0);
            refreshFoeGrid();
        });
        if (selfGrid.getShipsAliveCount() <= 0) {
            SwingUtilities.invokeLater(() -> {
                if (mainTheme != null) {
                    mainTheme.stop();
                }
                Helpers.playSFX("/SFX/youLooseDramatic.wav", 0);
                Object[] options = {"Exit"};
                JLabel lblInformation = new JLabel("YOU LOOSE!");
                lblInformation.setFont(new Font("Sprites/PrStart.ttf", Font.BOLD, 20));
                JOptionPane.showOptionDialog(null,lblInformation,"Information", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,null, options, options[0]);
                System.exit(0);
            });
            return false;
        }
        return true;
    }

    public boolean handleKIAnswer(int answer) {
        gcF.processShotResult(answer);
        if (foeGrid.getShipsAliveCount() <= 0) {
            SwingUtilities.invokeLater(() -> {
                if (mainTheme != null) {
                    mainTheme.stop();
                }
                Helpers.playSFX("/SFX/youwincomrad.wav", 0);
                pnlFoeGrid.setVisible(false);
                pnlFoeGrid.add(lblComrade);
                pnlFoeGrid.setVisible(true);
                Object[] options = {"Exit"};
                JLabel lblInformation = new JLabel("YOU WON!");
                lblInformation.setFont(new Font("Sprites/PrStart.ttf", Font.BOLD, 20));
                JOptionPane.showOptionDialog(null,lblInformation,"Information", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,null, options, options[0]);
                System.exit(0);
            });
            return false;
        }
        if (answer == 0) {
            SwingUtilities.invokeLater(() -> {
                setTurn(false);
                refreshFoeGrid();
            });
        }
        return true;
    }

    private void handleData(Connector c) {
        while (c.isConnected()) {
            String res = c.listenToNetwork();
            String[] cmd = res.split(" ");
            switch (cmd[0]) {
                case "load":
                    handleLoadEvent(cmd[1], true);
                    break;
                case "save":
                    SaveManager.save(String.format("%s", cmd[1]), selfGrid, foeGrid);
                    net.close();
                    System.exit(0);
                    return;
                case "confirmed":
                    SwingUtilities.invokeLater(() -> setTurn(true));
                    break;
                case "size":
                    handleSizeEvent(Integer.parseInt(cmd[1]));
                    SwingUtilities.invokeLater(() -> {
                        setTurn(true);
                        refreshFoeGrid();
                    });
                    break;
                case "pass":
                    SwingUtilities.invokeLater(this::refreshFoeGrid);
                    break;
                case "answer":
                    int answer = Integer.parseInt(cmd[1]);
                    gcF.processShotResult(answer);
                    if (foeGrid.getShipsAliveCount() <= 0) {
                        SwingUtilities.invokeLater(() -> {
                            if (mainTheme != null) {
                                mainTheme.stop();
                            }
                            Helpers.playSFX("/SFX/youwincomrad.wav", 0);
                            pnlFoeGrid.setVisible(false);
                            pnlFoeGrid.add(lblComrade);
                            pnlFoeGrid.setVisible(true);
                            Object[] options = {"Exit"};
                            JLabel lblInformation = new JLabel("YOU WON!");
                            lblInformation.setFont(new Font("Sprites/PrStart.ttf", Font.BOLD, 20));
                            JOptionPane.showOptionDialog(null,lblInformation,"Information", JOptionPane.DEFAULT_OPTION,
                                    JOptionPane.INFORMATION_MESSAGE,null, options, options[0]);
                            c.close();
                            System.exit(0);
                        });
                        return;
                    }
                    if (answer == 0) {
                        c.sendMessage("pass");
                        SwingUtilities.invokeLater(() -> {
                            setTurn(false);
                            refreshFoeGrid();
                        });
                    }
                    break;
                case "shot":
                    ShotResult result = selfGrid.shoot(Integer.parseInt(cmd[1]), Integer.parseInt(cmd[2])); //bp here.
                    c.sendMessage(String.format("answer %d", result.ordinal()));
                    //its the players turn, when the result says he hit nothing.
                    SwingUtilities.invokeLater(() -> {
                        setTurn(result.ordinal() == 0);
                        refreshFoeGrid();
                    });
                    if (selfGrid.getShipsAliveCount() <= 0) {
                        SwingUtilities.invokeLater(() -> {
                            if (mainTheme != null) {
                                mainTheme.stop();
                            }
                            Helpers.playSFX("/SFX/youLooseDramatic.wav", 0);
                            Object[] options = {"Exit"};
                            JLabel lblInformation = new JLabel("YOU LOOSE!");
                            lblInformation.setFont(new Font("Sprites/PrStart.ttf", Font.BOLD, 20));
                            JOptionPane.showOptionDialog(null,lblInformation,"Information", JOptionPane.DEFAULT_OPTION,
                                    JOptionPane.INFORMATION_MESSAGE,null, options, options[0]);
                            net.close();
                            System.exit(0);
                        });
                        return;
                    }
                    break;
                default:
                    System.out.println("Invalid command.");
                    System.out.println(res);
                    break;
            }
        }
    }

    private void refreshFoeGrid() {
        pnlFoeGrid.revalidate();
        pnlFoeGrid.repaint();
    }

    private void resetNetwork() {
        if(net != null) {
            net.close();
        }
        if(foe != null) {
            foe.close();
        }
        net = null;
        foe = null;
    }

    private void runMultiplayerServer(String save) {
        handleLoadEvent(save, false);
        resetNetwork();
        netThread = new Thread(() -> {
            net = new Server();
            net.connect();

            net.sendMessage(String.format("load %s", save));
            handleData(net);
        });
        netThread.start();
    }

    private void runKIServer(int bound, int difficulty) {
        setTurn(false);
        resetNetwork();

        if(ki != null) {
            ki.close();
            ki = null;
        }

        kiThread = new Thread(() -> {
            ki = new NewKI(new Server(), this, bound, difficulty);
        });
        kiThread.start();
    }

    private void runKIClient(String host, int difficulty) {
        setTurn(false);
        resetNetwork();

        if(ki != null) {
            ki.close();
            ki = null;
        }

        kiThread = new Thread(() -> {
            ki = new NewKI(new Client(host), this, difficulty);
        });
        kiThread.start();
    }

    private void runMultiplayerServer(int bound) {
        setTurn(false);
        resetNetwork();
        new Thread(() -> {
            net = new Server();
            net.connect();

            net.sendMessage(String.format("size %d", bound));
            handleData(net);
        }).start();
    }

    private void runMuliplayerClient(String host) {
        resetNetwork();
        new Thread(() -> {
            net = new Client(host);
            handleData(net);
        }).start();
    }

    private void handleSizeEvent(int size) {
        backgroundPanel.removeAll();

        pnlGrid1 = new BasicGrid(size, GridState.PLACE);
        selfGrid = new Grid2D(size);
        selfGrid.generateRandom();

        pnlGrid2 = new BasicGrid(size, GridState.FORBID);
        foeGrid = new Grid2D(size);
        foeGrid.placeFgoOnEmptyFields();

        SwingUtilities.invokeLater(() -> {
            runAfterGridInit(false);
        });
    }

    private void onGameReady() {
        gcS.setInteractionState(GridState.FORBID);
        pnlFoeGrid.add(pnlGrid1);

        resizeFoeGridListener = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                            /*
                            pnlFoeGrid.setPreferredSize(new Dimension(foeBigState ? 390 : 250, foeBigState ? 390 : 250));
                            pnlFoeGrid.revalidate();
                            pnlFoeGrid.repaint();
                            */
                pnlGrid1.setBorder(BorderFactory.createLineBorder(Color.RED));
                System.out.println("enter");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                            /*
                            pnlFoeGrid.setPreferredSize(new Dimension(foeBigState ? 375 : 225, foeBigState ? 400 : 225));
                            pnlFoeGrid.revalidate();
                            pnlFoeGrid.repaint();
                            */
                pnlGrid1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                System.out.println("ima head out");
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                foeBigState = !foeBigState;
                pnlFoeGrid.setPreferredSize(new Dimension(foeBigState ? 600 : 150, foeBigState ? 600 : 150));
                pnlFoeGrid.revalidate();
                pnlFoeGrid.repaint();
                System.out.println("clicked.");
            }
        };
        pnlGrid1.addMouseListener(resizeFoeGridListener);


        pnlField.setBorder(null);

        pnlGridWrapper.remove(pnlReady);
        pnlGridWrapper.remove(pnlGrid1);


        pnlGridWrapper.add(pnlGrid2);
        setReadyPanelStatus(false);
        pnlGridWrapper.add(pnlReady);
        backgroundPanel.add(pnlFoeGrid, BorderLayout.SOUTH);

        //pnlDummy.add(pnlFoeGrid);
        gcF.setInteractionState(GridState.SHOOT);
        pnlGridWrapper.revalidate();
        pnlGridWrapper.repaint();
    }

    private void handleLoadEvent(String save, boolean turnState) {
        backgroundPanel.removeAll();

        Grid2D[] grids = SaveManager.load(save);
        if(grids == null) {
            System.out.println("error on loading grid");
            return;
        }
        int bound = grids[0].getBound();

        pnlGrid1 = new BasicGrid(bound, GridState.PLACE);
        selfGrid = grids[0];

        pnlGrid2 = new BasicGrid(bound, GridState.FORBID);
        foeGrid = grids[1];

        SwingUtilities.invokeLater(() -> {
            runAfterGridInit(false);
            setTurn(turnState);
            onGameReady();
        });
    }

    private void runAfterGridInit(boolean isAlreadyInitalized) {
        backgroundPanel.removeAll();

        if(!isAlreadyInitalized) {
            gcS = new GridController(selfGrid, null, pnlGrid1);
            gcS.init(GridState.PLACE);
            pnlGrid1.setOpaque(false);
            pnlGrid1.setAlignmentX(Component.CENTER_ALIGNMENT);

            gcF = new GridController(foeGrid, net, pnlGrid2);
            gcF.init(GridState.FORBID);
        }

        backgroundPanel.add(pnlField);

        pnlGridWrapper.setOpaque(false);
        pnlField.add(pnlGridWrapper, BorderLayout.CENTER);
        pnlGridWrapper.add(pnlGrid1);
        setReadyPanelStatus(true);
        pnlGridWrapper.add(pnlReady);
        try {
            backgroundPanel.setImage(ImageIO.read(getClass().getResource("/Sprites/Waltertile2_64.png")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        /*
        if(OptionsHandler.getFullscreenMode()){
            jf.setSize(new Dimension(1981,1080));
            jf.setSize(new Dimension(1980,1080));
        }else{
            jf.setSize(new Dimension(1025,851));
            jf.setSize(new Dimension(1024,850));
        }

         */
        jf.revalidate();
        jf.repaint();
    }

    private void runKIvsKI() {

    }
}