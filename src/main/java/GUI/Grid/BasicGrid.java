package GUI.Grid;

import GUI.ImageHelper;
import GUI.ScaleHelper;
import Logic.*;
import Logic.Character;
import Misc.GridState;
//import org.w3c.dom.css.Rect;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BasicGrid extends JPanel {
    public static final int TILE_BASE_SIZE = 64;

    //docs: John Zukowski: The Definitive Guide to Java Swing (page 345)
    static class CustomLayoutManager implements LayoutManager2 {
        private Map<Component, Rectangle> compGrid;
        private int gridBounds;
        public CustomLayoutManager(int bounds) {
            compGrid = new HashMap<>();
            this.gridBounds = bounds;
        }

        public boolean isPlacedPiece(Component comp) {
            return compGrid.containsKey(comp);
        }

        public void changePiecePos(Component comp, Point pos) {
            Rectangle rect = compGrid.get(comp);
            if (rect == null) {
                return;
            }

            rect.setLocation(pos);
        }

        @Override
        public void addLayoutComponent(Component comp, Object constraints) {
            if (!(constraints instanceof Rectangle)) {
                throw new IllegalArgumentException("expected Point array as constraints.");
            }

            compGrid.put(comp, (Rectangle)constraints);
        }

        @Override
        public Dimension maximumLayoutSize(Container target) {
            // TODO: make this scalable.
            return new Dimension(TILE_BASE_SIZE * gridBounds, TILE_BASE_SIZE * gridBounds);
        }

        //alignment around x axis, 0.5 means center.
        @Override
        public float getLayoutAlignmentX(Container target) {
            //
            return 0.5f;
        }

        //alignment around y axis, 0.5 means center.
        @Override
        public float getLayoutAlignmentY(Container target) {
            return 0.5f;
        }


        @Override
        public void invalidateLayout(Container target) {
        }

        @Override
        public void addLayoutComponent(String name, Component comp) {
//            throw new NotImplementedException();
        }

        @Override
        public void removeLayoutComponent(Component comp) {
            compGrid.remove(comp);
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            return new Dimension(TILE_BASE_SIZE * gridBounds, TILE_BASE_SIZE * gridBounds);
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            return new Dimension(TILE_BASE_SIZE * gridBounds, TILE_BASE_SIZE * gridBounds);
        }

        @Override
        public void layoutContainer(Container parent) {
            Component[] comps = parent.getComponents();
            for(int i = 0; i < comps.length; ++i) {
                Component comp = comps[i];
                Rectangle rect = compGrid.get(comp);
                if(rect == null) {
                    //?
                    continue;
                }
                Point p = rect.getLocation();
                Dimension d = rect.getSize();

                int scaledTile = GetScaledTileSize(parent.getParent() != null ? parent.getParent() : parent, gridBounds);
                comp.setBounds(new Rectangle(p.x * scaledTile, p.y * scaledTile, d.width * scaledTile, d.height * scaledTile));
                //System.out.printf("on layout container comp bounds: %s\n", comp.getBounds());
            }
        }
    }

    private BufferedImage bgImg;
    private BufferedImage baseImg;
    private int scaledSize;
    private int bound;
    private Rectangle gridRect;
    private Rectangle highlightedCell;
    private GridController controller = null;
    BufferedImage rocketNone;
    BufferedImage normalRocket;
    BufferedImage rocketNoneScaled;
    BufferedImage normalRocketScaled;

    public BasicGrid(int bound, GridState state) {
        setLayout(new CustomLayoutManager(bound));
        this.bound = bound;
        int defaultSize = TILE_BASE_SIZE * bound;
        scaledSize = 0;

        // TODO: maybe calculate offset?, resize this on window size change?
        gridRect = new Rectangle(getX(), getY(), defaultSize, defaultSize);

        baseImg = new BufferedImage(defaultSize, defaultSize, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = baseImg.createGraphics();

        /*
        g2d.setColor(Color.BLUE);
        g2d.fill(new Rectangle(0, 0, defaultSize, defaultSize));
         */
        try {
            rocketNone = ImageIO.read(getClass().getResource("/Sprites/whitepin2.png"));
            normalRocket = ImageIO.read(getClass().getResource("/Sprites/rackete2.png"));
            rocketNoneScaled = rocketNone;
            normalRocketScaled = normalRocket;

            Image ocean = ImageIO.read(getClass().getResource("/Sprites/Waltertile2_64.png"));
            for(int i = 0; i < bound; ++i) {
                for(int j = 0; j < bound; ++j) {
                    g2d.drawImage(ocean, i * TILE_BASE_SIZE, j * TILE_BASE_SIZE, null);
                }
            }
        } catch(IOException e) {

        }

        g2d.setColor(Color.BLACK);
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f);
        g2d.setComposite(alphaComposite);
        g2d.setStroke(new BasicStroke(3));
        for(int i = 0; i < bound; ++i) {
            for(int j = 0; j < bound; ++j) {
                g2d.draw(new Rectangle(i * TILE_BASE_SIZE, j * TILE_BASE_SIZE, TILE_BASE_SIZE, TILE_BASE_SIZE));
            }
        }
    }

    public void setController(GridController controller) {
        this.controller = controller;
    }

    public Component addPiece(BufferedImage texture, int x, int y, int size, boolean isVertical) {
        JLabel piece = new JLabel();
        try {
            piece = new JLabel() {

                public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    try {
                        BufferedImage ship = texture;

                        if(!isVertical) {
                            ship = ImageHelper.rotate(ship, Math.toRadians(-90));
                        }

                        ship = ImageHelper.scale(ship, this.getWidth(), this.getHeight());

                        g.drawImage(ship, 0, 0, this);


                        Character ch = controller.getCharacterOfComponent(this);
                        if(ch != null) {
                            int correctSize = Math.min(this.getWidth(), this.getHeight());
                            if(ch instanceof Water) {
                                if(!ch.isAlive()) {
                                    g.drawImage(rocketNoneScaled, 0, 0, this);
                                }
                            } else if(ch instanceof FoeGridShootObject) {
                                //not shot on, dont paint anything.
                                if(ch.isAlive()) {
                                    return;
                                }

                                FoeGridShootObject fgso = (FoeGridShootObject)ch;
                                if(fgso.getHitStatus() == 0) {
                                    g.drawImage(rocketNoneScaled, 0, 0, this);
                                } else if(fgso.getHitStatus() == 1 || fgso.getHitStatus() == 2) {
                                    g.drawImage(normalRocketScaled, 0, 0, this);
                                }
                            }
                            else if(ch instanceof Ship) {
                                Ship s = (Ship)ch;
                                boolean[] hitbox = s.getHitbox();
                                for(int i = 0; i < hitbox.length; ++i) {
                                    if(hitbox[i]) {
                                        continue;
                                    }
                                    g.drawImage(normalRocketScaled, !s.isVertical() ? i*getScaledTileSize() : 0, s.isVertical() ? i*getScaledTileSize() : 0, this);
                                }
                            }
                        }

                    } catch(NullPointerException e) {
                        System.out.printf("ERR: PIECE TEXTURE IS NULL!\nerr: %s\n", e.getMessage());
                    }
                    //g.setColor(Color.red);
                    //g.fillRect(0, 0, this.getWidth(), this.getHeight());
                }
            };
            piece.setSize(100, 100);
            //piece.setIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/Luke.png"))));
        } catch (Exception ex) {
            piece.setBackground(new Color(255, 0, 0, 64));
            piece.setOpaque(true);
        }
        add(piece, new Rectangle(x, y, isVertical ? 1 : size, isVertical ? size : 1));
        return piece;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        boolean isValidPaintjob = true;
        int tileSize = getScaledTileSize();
        int currentSize = tileSize*bound;
        if(currentSize != scaledSize) {
            if (getParent() != null) {
                bgImg = ImageHelper.scale(baseImg, currentSize, currentSize);
                rocketNoneScaled = ImageHelper.scale(rocketNone, tileSize, tileSize);
                normalRocketScaled = ImageHelper.scale(normalRocket, tileSize, tileSize);
            }
            this.setPreferredSize(new Dimension(currentSize, currentSize));
            scaledSize = currentSize;

            isValidPaintjob = false;
        }

        Graphics2D g2d = (Graphics2D)g.create();

        g2d.drawImage(bgImg, 0, 0, this);

        if(highlightedCell != null) {
            g2d.setColor(Color.RED);
            g2d.drawRect(highlightedCell.x*getScaledTileSize(), highlightedCell.y*getScaledTileSize(),
                    highlightedCell.width*getScaledTileSize(), highlightedCell.height*getScaledTileSize());
        }
        g2d.dispose();

        if(!isValidPaintjob) {
            revalidate();
            repaint();
            return;
        }
    }

    public void setPiecePos(Component comp, Point pos) {
        CustomLayoutManager lmgr = (CustomLayoutManager)getLayout();
        lmgr.changePiecePos(comp, pos);
        //invalidate();
        revalidate();
        repaint();
    }

    public boolean isValidRect(Rectangle rect) {
        return this.gridRect.contains(rect);
    }

    public static int GetScaledTileSize(Component c, int bound) {
        double defaultSize = TILE_BASE_SIZE * bound;
        double size = Math.min(c.getWidth(), c.getHeight());
        // TODO: differenciate between width and height.
        double prefSize = size == c.getWidth() ? c.getPreferredSize().width : c.getPreferredSize().height;
        double sf = size / defaultSize;
        return (int) (Math.floor(TILE_BASE_SIZE * sf));
    }

    public int getScaledTileSize() {
        return GetScaledTileSize(getParent() != null ? getParent() : this, bound);
    }

    public Rectangle getScaledGridRect() {
        return new Rectangle(gridRect.x, gridRect.y, bound*getScaledTileSize(), bound*getScaledTileSize());
    }

    public void highlightCell(Rectangle hc) {
        if(highlightedCell == hc) {
            return;
        }

        highlightedCell = hc;
        repaint();
    }
}
