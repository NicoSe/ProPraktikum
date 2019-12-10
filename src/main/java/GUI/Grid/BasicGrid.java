package GUI.Grid;

import Control.GUIMain;
import Logic.Grid2D;
//import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.util.List;

public class BasicGrid extends JPanel {
    private static final int TILE_BASE_SIZE = 50;

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

                Point p = BasicGrid.getAbsolutePoint(rect.getLocation());
                Dimension d = BasicGrid.getAbsoluteDimension(rect.getSize());
                comp.setBounds(new Rectangle(p, d));
                System.out.println(comp.getBounds());
            }
        }
    }

    private BufferedImage bgImg;
    private Rectangle gridRect;
    private Rectangle highlightedCell;

    public BasicGrid(int bound) {
        setLayout(new CustomLayoutManager(bound));

        int defaultSize = TILE_BASE_SIZE * bound;

        // TODO: maybe calculate offset?, resize this on window size change?
        gridRect = new Rectangle(getX(), getY(), defaultSize, defaultSize);

        bgImg = new BufferedImage(defaultSize, defaultSize, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bgImg.createGraphics();

        g2d.setColor(Color.BLUE);
        g2d.fill(new Rectangle(0, 0, defaultSize, defaultSize));
        g2d.setColor(Color.GRAY);
        for(int i = 0; i < bound; ++i) {
            for(int j = 0; j < bound; ++j) {
                g2d.draw(new Rectangle(i * TILE_BASE_SIZE, j * TILE_BASE_SIZE, TILE_BASE_SIZE, TILE_BASE_SIZE));
            }
        }
    }

    public void addPiece(int x, int y, int size, boolean isVertical) {
        JLabel piece = new JLabel();
        try {
            piece = new JLabel("This is a ship.") {
                public void paint(Graphics g) {
                    super.paint(g);
                    g.setColor(Color.red);
                    g.fillRect(0, 0, this.getWidth(), this.getHeight());
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

        Graphics2D g2d = (Graphics2D)g.create();

        Image img = bgImg;
        if(getParent() != null) {
            img = bgImg.getScaledInstance(getParent().getWidth(), getParent().getHeight(), Image.SCALE_SMOOTH);
        }

        g2d.drawImage(img, 0, 0, this);

        if(highlightedCell != null) {
            g2d.setColor(Color.RED);
            Rectangle rect = new Rectangle(getAbsolutePoint(highlightedCell.getLocation()), getAbsoluteDimension(highlightedCell.getSize()));
            g2d.draw(rect);
        }

        /*
        if(getParent() != null) {
            System.out.println(getParent().getSize());
        }
        */

        g2d.dispose();
    }

    public void setPiecePos(Component comp, Point pos) {
        CustomLayoutManager lmgr = (CustomLayoutManager)getLayout();
        lmgr.changePiecePos(comp, pos);
        invalidate();
        revalidate();
        repaint();
    }

    public Point getRelativePoint(Point p) {
        if (!gridRect.contains(p)) {
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

    // TODO: calculate in offset & scaling...
    public static Point getAbsolutePoint(Point p) {

        Point grid = new Point(p);
        grid.x *= TILE_BASE_SIZE;
        grid.y *= TILE_BASE_SIZE;

        return grid;
    }

    // TODO: calculate in offset & scaling...
    public static Dimension getAbsoluteDimension(Dimension d) {
        Dimension dim = new Dimension(d);
        dim.width *= TILE_BASE_SIZE;
        dim.height *= TILE_BASE_SIZE;

        return dim;
    }

    public boolean isValidRect(Rectangle rect) {
        return this.gridRect.contains(rect);
    }

    public void highlightCell(Rectangle hc) {
        if(highlightedCell == hc) {
            return;
        }

        highlightedCell = hc;
        repaint();
    }
}
