package GUI.Grid;

import GUI.ImageHelper;
import GUI.ScaleHelper;
import Misc.GridState;
import sun.tools.jstat.Scale;

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
            for(Component comp : parent.getComponents()) {
                Rectangle rect = compGrid.get(comp);
                if(rect == null) {
                    //?
                    continue;
                }

                double sf = ScaleHelper.CalculateScalingFactor(parent) - 1.0;
                System.out.printf("scaling factor for layout: %f\n", sf);
                Point p = BasicGrid.getAbsolutePoint(rect.getLocation());
                p.x += p.x * sf;
                p.y += p.y * sf;
                Dimension d = BasicGrid.getAbsoluteDimension(rect.getSize());
                d.width += d.width * sf;
                d.height += d.height * sf;
                comp.setBounds(new Rectangle(p, d));
                System.out.printf("on layout container comp bounds: %s\n", comp.getBounds());
            }
        }
    }

    private BufferedImage bgImg;
    private Rectangle gridRect;
    private Rectangle highlightedCell;
    private GridState interactionState;
    public BasicGrid(int bound, GridState interactionState) {
        setLayout(new CustomLayoutManager(bound));

        int defaultSize = TILE_BASE_SIZE * bound;

        this.interactionState = interactionState;

        // TODO: maybe calculate offset?, resize this on window size change?
        gridRect = new Rectangle(getX(), getY(), defaultSize, defaultSize);

        bgImg = new BufferedImage(defaultSize, defaultSize, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bgImg.createGraphics();

        /*
        g2d.setColor(Color.BLUE);
        g2d.fill(new Rectangle(0, 0, defaultSize, defaultSize));
         */
        try {
            Image ocean = ImageIO.read(getClass().getResource("/Sprites/Waltertile2_64.png"));
            for(int i = 0; i < bound; ++i) {
                for(int j = 0; j < bound; ++j) {
                    g2d.drawImage(ocean, i * TILE_BASE_SIZE, j * TILE_BASE_SIZE, null);
                }
            }
        } catch(IOException e) {

        }

        g2d.setColor(Color.gray);
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
        g2d.setComposite(alphaComposite);
        for(int i = 0; i < bound; ++i) {
            for(int j = 0; j < bound; ++j) {
                g2d.draw(new Rectangle(i * TILE_BASE_SIZE, j * TILE_BASE_SIZE, TILE_BASE_SIZE, TILE_BASE_SIZE));
            }
        }
    }

    public void setGridInteractionState(GridState state) {
        this.interactionState = state;
    }

    public GridState getGridInteractionState() {
        return this.interactionState;
    }

    public void addPiece(BufferedImage texture, int x, int y, int size, boolean isVertical) {
        JLabel piece = new JLabel();
        try {
            piece = new JLabel() {
                public void paint(Graphics g) {
                    super.paint(g);

                    try {
                        BufferedImage ship = texture;
                        if(!isVertical) {
                            ship = ImageHelper.rotate(ship, Math.toRadians(-90));
                        }

                        ship = ImageHelper.scale(ship, this.getWidth(), this.getHeight());

                        g.drawImage(ship, 0, 0, this);
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
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //System.out.printf("%s on resize.\n", this.getSize());

        //System.out.printf("scaling factor: %f\n", (double)(getWidth()) / getPreferredSize().width);

        Graphics2D g2d = (Graphics2D)g.create();

        Image img = bgImg;
        if(getParent() != null) {
            img = bgImg.getScaledInstance(Math.min(getWidth(), getHeight()), -1, Image.SCALE_SMOOTH);
        }

        g2d.drawImage(img, 0, 0, this);

        if(highlightedCell != null) {
            g2d.setColor(Color.RED);
            double sf = ScaleHelper.CalculateScalingFactor(this);
            Rectangle rect = new Rectangle(getAbsolutePoint(highlightedCell.getLocation(), sf), getAbsoluteDimension(highlightedCell.getSize(), sf));
            g2d.draw(rect);
        }

        g2d.dispose();
    }

    public void setPiecePos(Component comp, Point pos) {
        CustomLayoutManager lmgr = (CustomLayoutManager)getLayout();
        lmgr.changePiecePos(comp, pos);
        invalidate();
        revalidate();
        repaint();
    }

    public Point getRelativePoint(Point p, double sf) {
        if (!getScaledGridRect().contains(p)) {
            return null;
        }

        Point grid = new Point(p);

        System.out.printf("Calculation of relative point scaled in 2 ways (sf: %f):\n" +
                "way1 (error with sf < 1):\n" +
                "\t grid.x = (int) (grid.x / sf) :: %d \n" +
                "\t grid.x = (int) (grid.x / sf) :: %d \n" +
                "way2 (new attempt...):\n" +
                "\t grid.x = (int) (grid.x / sf) :: %d \n" +
                "\t grid.x = (int) (grid.x / sf) :: %d \n" +
                "", sf, (int) Math.ceil(grid.x / sf), (int) Math.ceil(grid.y / sf), (int) (grid.x + grid.x * (sf-1.0)), (int) (grid.y + grid.y * (sf-1.0)));

        grid.x = (int) Math.ceil(grid.x / sf);
        grid.y = (int) Math.ceil(grid.y / sf);

        grid.x /= TILE_BASE_SIZE;
        grid.y /= TILE_BASE_SIZE;

        return grid;
    }

    public Point getRelativePoint(Point p) {
        if (!getScaledGridRect().contains(p)) {
            return null;
        }

        Point grid = new Point(p);
        grid.x /= TILE_BASE_SIZE;
        grid.y /= TILE_BASE_SIZE;

        return grid;
    }

    public static Dimension getRelativeSize(Dimension d) {
        Dimension dim = new Dimension(d);

        dim.width /= TILE_BASE_SIZE;
        dim.height /= TILE_BASE_SIZE;

        return dim;
    }

    public static Dimension getRelativeSize(Dimension d, double sf) {
        Dimension dim = new Dimension(d);

        dim.width = (int) Math.ceil(dim.width / sf);
        dim.height = (int) Math.ceil(dim.height / sf);

        dim.width /= TILE_BASE_SIZE;
        dim.height /= TILE_BASE_SIZE;

        return dim;
    }

    // TODO: calculate in offset & scaling...
    public static Point getAbsolutePoint(Point p) {

        Point grid = new Point(p);
        grid.x *= TILE_BASE_SIZE;
        grid.y *= TILE_BASE_SIZE;

        return grid;
    }

    public static Point getAbsolutePoint(Point p, double sf) {

        Point grid = new Point(p);

        grid.x *= TILE_BASE_SIZE;
        grid.y *= TILE_BASE_SIZE;

        grid.x = (int) (grid.x * sf);
        grid.y = (int) (grid.y * sf);

        return grid;
    }

    // TODO: calculate in offset & scaling...
    public static Dimension getAbsoluteDimension(Dimension d) {
        Dimension dim = new Dimension(d);
        dim.width *= TILE_BASE_SIZE;
        dim.height *= TILE_BASE_SIZE;

        return dim;
    }

    public static Dimension getAbsoluteDimension(Dimension d, double sf) {
        Dimension dim = new Dimension(d);

        dim.width *= TILE_BASE_SIZE;
        dim.height *= TILE_BASE_SIZE;

        dim.width = (int) (dim.width * sf);
        dim.height = (int) (dim.height * sf);

        return dim;
    }

    public boolean isValidRect(Rectangle rect) {
        return this.gridRect.contains(rect);
    }

    public Rectangle getScaledGridRect() {
        double sf = ScaleHelper.CalculateScalingFactor(this);
        return new Rectangle((int) (gridRect.x * sf),(int) (gridRect.y * sf), (int) (gridRect.width * sf), (int) (gridRect.height * sf));
    }

    public void highlightCell(Rectangle hc) {
        if(highlightedCell == hc) {
            return;
        }

        highlightedCell = hc;
        repaint();
    }
}
