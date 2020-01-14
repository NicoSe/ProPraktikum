package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicBorders;
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
import java.nio.file.Path;
import java.nio.file.Paths;

import GUI.Grid.*;
import Logic.Grid2D;
import Logic.GridController;
import Logic.Load;
import Logic.OptionsHandler;
import Misc.GridState;
import Network.Client;
import Network.Server;
//import sun.misc.JavaLangAccess;
//import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;

public class MainFrame {

    //Variablen
    private JFrame jf;
    private BackgroundPanel backgroundPanel;
    private JPanel pnlButton;
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


        //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/Sprites/ee7d4460451792a.gif"));
        //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/Sprites/Waltertile2_1024.png"));
        backgroundPanel = new BackgroundPanel(ImageIO.read(getClass().getResource("/Sprites/Waltertile2_64.png")), BackgroundPanel.TILED);
        //backgroundPanel = new GamePanel(image);
        backgroundPanel.setMinimumSize(new Dimension(1024, 850));
        backgroundPanel.setMaximumSize(new Dimension(1920, 1080));
        jf.setContentPane(backgroundPanel);

        //grid Panel
        pnlGrid1 = new BasicGrid(5, GridState.PLACE);
        Grid2D g2d = new Grid2D(5);
        pnlGrid1.setSize(new Dimension(50,50));
        g2d.generateRandom();
        GridController controller = new GridController(g2d, pnlGrid1);
        controller.init(GridState.PLACE);

        //grid Panel enemy
        pnlGrid2 = new BasicGrid(2,GridState.FORBID);

        //panel
        pnlButton = new JPanel();
        pnlButton.setOpaque(true);
        pnlButton.setLayout(new BoxLayout(pnlButton,BoxLayout.Y_AXIS));
        pnlButton.setMinimumSize(new Dimension(1024,850));
        pnlButton.setMaximumSize(new Dimension(1920,1080));
        backgroundPanel.add(pnlButton);

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
        pnlDummy.setPreferredSize(new Dimension(20,25));







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
                try {
                    lblStartGameMouseClicked(e);
                } catch(IOException el){
                    el.printStackTrace();
                }
            }
            public void mouseEntered(MouseEvent e) {
                try {
                    lblStartGameMouseEntered(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseExited(MouseEvent e){
                try {
                    lblStartGameMouseExited(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mousePressed(MouseEvent e){
                try {
                    lblStartGameMousePressed(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseReleased(MouseEvent e){
                try {
                    lblStartGameMouseReleased(e);
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
                    lblFullscreenPictureMouseClicked(e);
                } catch(IOException el){
                    el.printStackTrace();
                }
            }
            public void mouseEntered(MouseEvent e) {
                try {
                    lblFullscreenPictureMouseEntered(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseExited(MouseEvent e){
                try {
                    lblFullscreenPictureMouseExited(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mousePressed(MouseEvent e){
                try {
                    lblFullscreenPictureMousePressed(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseReleased(MouseEvent e){
                try {
                    lblFullscreenPictureMouseReleased(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
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
                if(txfIPAdress.getText().length() > 20){
                    if (!(e.getKeyCode() == 8 || e.getKeyCode() == 127)){
                        e.consume();
                    }
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(txfIPAdress.getText().length() > 20){
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
                try {
                    lblConnectMouseClicked(e);
                } catch(IOException el){
                    el.printStackTrace();
                }
            }
            public void mouseEntered(MouseEvent e) {
                try {
                    lblConnectMouseEntered(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseExited(MouseEvent e){
                try {
                    lblConnectMouseExited(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mousePressed(MouseEvent e){
                try {
                    lblConnectMousePressed(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseReleased(MouseEvent e){
                try {
                    lblConnectMouseReleased(e);
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
                try {
                    lblStartHostMouseClicked(e);
                } catch(IOException el){
                    el.printStackTrace();
                }
            }
            public void mouseEntered(MouseEvent e) {
                try {
                    lblStartHostMouseEntered(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseExited(MouseEvent e){
                try {
                    lblStartHostMouseExited(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mousePressed(MouseEvent e){
                try {
                    lblStartHostPressed(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseReleased(MouseEvent e){
                try {
                    lblStartHostReleased(e);
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

        File dir = new File("src/main/java/Logic/SaveGames");
        data = dir.listFiles();
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
        scrollpane.setAlignmentX(FlowLayout.LEFT);
        scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollpane.setMaximumSize(new Dimension(200,200));
        scrollpane.getViewport().setOpaque(false);
        scrollpane.setLocation(jf.getWidth()-200, lblSize.getY());
        lstLoad.setSelectedIndex(-1);
    }




//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


    //Action Events
    //Play
    private void lblPlayMouseClicked(MouseEvent e) throws IOException {
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
        Helpers.playSFX("/SFX/Menu_Tick.wav", 1);
    }


    //Options
    private void lblOptionsMouseClicked(MouseEvent e) throws IOException {
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
        Helpers.playSFX("/SFX/Menu_Tick.wav", 1);
    }

    //Credits
    private void lblCreditsMouseClicked(MouseEvent e) throws IOException {
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
        Helpers.playSFX("/SFX/Menu_Tick.wav", 1);
    }

    //Exit
    private void lblExitMouseClicked(MouseEvent e) throws IOException {
        Helpers.playSFX("/SFX/firered_0011.wav", 1);
        OptionsHandler.changeFullscreenMode(false);
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
        Helpers.playSFX("/SFX/Menu_Tick.wav", 1);
    }

    //Return
    private void lblReturnMouseClicked(MouseEvent e) throws IOException {
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
        Helpers.playSFX("/SFX/Menu_Tick.wav", 1);
    }

    //SinglePlayer
    private void lblSingleMouseClicked(MouseEvent e) throws IOException {
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
        Helpers.playSFX("/SFX/Menu_Tick.wav", 1);
    }

    //Host
    private void lblHostMouseClicked(MouseEvent e) throws IOException {
        Helpers.playSFX("/SFX/SA2_142.wav", 1);
        pnlButton.setVisible(false);
        pnlButton.removeAll();
        pnlButton.add(lblTitle);
        pnlButton.add(lblSize);
        pnlButton.add(sldSize);
        pnlButton.add(scrollpane);
        scrollpane.setLocation(jf.getWidth()-220,lblSize.getY());

        pnlButton.add(lblStartHost);
        pnlButton.add(lblReturn);
        pnlButton.add(lblShowIP);
        pnlButton.setVisible(true);
        lblHost.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/HostBW.png"))));
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
        Helpers.playSFX("/SFX/Menu_Tick.wav", 1);
    }

    //Join
    private void lblJoinMouseClicked(MouseEvent e) throws IOException {
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
        Helpers.playSFX("/SFX/Menu_Tick.wav", 1);
    }

    //Start Game
    private void lblStartGameMouseClicked(MouseEvent e) throws IOException {
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
        //pnlPlay.add(pnlGrid1);

        //pnlGrid2 = new BasicGrid(sldSize.getValue(),GridState.FORBID);
        //GridController controller2 = new GridController(g2d,pnlGrid2);
        //pnlGrid2.setOpaque(false);
        //pnlGrid2.setAlignmentX(Component.CENTER_ALIGNMENT);
        //controller2.init(GridState.SHOOT);


        //backgroundPanel.add(pnlPlay);

        backgroundPanel.add(pnlDummyThicc);
        pnlDummyThicc.setOpaque(true);
        pnlDummyThicc.add(pnlDummy,BorderLayout.CENTER);
        pnlDummy.add(pnlGrid1);
        jf.setSize(new Dimension(1025,851));
        jf.setSize(new Dimension(1024,850));

    }

    private void lblStartGameMouseReleased(MouseEvent e) throws IOException {
        lblStartSingle.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/StartGameWB.png"))));
    }

    private void lblStartGameMousePressed(MouseEvent e) throws IOException {
        lblStartSingle.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/StartGameOnPress.png"))));
    }

    private void lblStartGameMouseExited(MouseEvent e) throws IOException {
        lblStartSingle.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/StartGameBW.png"))));
    }

    private void lblStartGameMouseEntered(MouseEvent e) throws IOException {
        lblStartSingle.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/StartGameWB.png"))));
        Helpers.playSFX("/SFX/Menu_Tick.wav", 1);
    }

    //Fullscreen Button
    private void lblFullscreenPictureMouseClicked(MouseEvent e) throws IOException {
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
    }

    private void lblFullscreenPictureMouseEntered(MouseEvent e) throws IOException {
        //lblJoin.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/OptionsWB.png"))));
    }

    private void lblFullscreenPictureMouseExited(MouseEvent e) throws IOException {
        //lblJoin.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/OptionsBW.png"))));
    }

    private void lblFullscreenPictureMousePressed(MouseEvent e) throws IOException {
        //lblJoin.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/OptionsOnPress.png"))));
    }

    private void lblFullscreenPictureMouseReleased(MouseEvent e) throws IOException {
        //lblJoin.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/OptionsWB.png"))));
    }

    //Connect Button
    private void lblConnectMouseClicked(MouseEvent e) throws IOException {
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

    private void lblConnectMouseExited(MouseEvent e) throws IOException {
        lblPlay.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/PlayBW.png"))));
    }

    private void lblConnectMousePressed(MouseEvent e) throws IOException {
        lblPlay.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/PlayOnPress.png"))));
    }

    private void lblConnectMouseReleased(MouseEvent e) throws IOException {
        lblPlay.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/PlayWB.png"))));
    }

    private void lblConnectMouseEntered(MouseEvent e) throws IOException {
        lblPlay.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/PlayWB.png"))));
    }

    //Host Start Game Button
    private void lblStartHostMouseClicked(MouseEvent e) throws IOException{
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

    private void lblStartHostMouseEntered(MouseEvent e) throws IOException {
        lblPlay.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/StartGameWB.png"))));
    }

    private void lblStartHostMouseExited(MouseEvent e) throws IOException {
        lblPlay.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/StartGameBW.png"))));
    }

    private void lblStartHostPressed(MouseEvent e) throws IOException {
        lblPlay.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/StartGameOnPress.png"))));
    }

    private void lblStartHostReleased(MouseEvent e) throws IOException {
        lblPlay.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/Sprites/StartGameWB.png"))));
        Helpers.playSFX("/SFX/Menu_Tick.wav", 1);
    }
}