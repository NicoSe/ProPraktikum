package GUI.Grid;

import GUI.ScaleHelper;
import Logic.GridController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlacementMEventHandler extends MouseAdapter {

    ///private Grid grid;
    ///public MouseEventHandler(Grid g) {
    ///    this.grid = g;
    ///}

    private Component currentComponent;
    private GridController controller;
    private BasicGrid grid;
    private Point offset;

    public PlacementMEventHandler(GridController c, BasicGrid grid) {
        this.grid = grid;
        this.controller = c;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point p = e.getPoint();
        Component c = grid.getComponentAt(p);
        if (c == null || !((BasicGrid.CustomLayoutManager) grid.getLayout()).isPlacedPiece(c)) {
            return;
        }

        if(SwingUtilities.isLeftMouseButton(e)) {
            offset = new Point();
            offset.x = e.getX() - c.getX();
            offset.y = e.getY() - c.getY();

            System.out.printf("%s at %s\n", c, p);
            currentComponent = c;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(SwingUtilities.isLeftMouseButton(e)) {
            if (currentComponent == null) {
                return;
            }

            currentComponent.setLocation(e.getX() - offset.x, e.getY() - offset.y);

            /// TODO: check what we like better..
            Point alteredLocation = currentComponent.getLocation();
            alteredLocation.x += grid.getScaledTileSize() / 2;
            alteredLocation.y += grid.getScaledTileSize() / 2;

            Point gridPos = new Point(alteredLocation);
            gridPos.x /= grid.getScaledTileSize();
            gridPos.y /= grid.getScaledTileSize();

            Dimension objDim = new Dimension(currentComponent.getSize());
            objDim.width /= grid.getScaledTileSize();
            objDim.height /= grid.getScaledTileSize();

            if (gridPos == null || !grid.getScaledGridRect().contains(currentComponent.getBounds())) {
                grid.highlightCell(null);
                return;
            }

            grid.highlightCell(new Rectangle(gridPos, objDim));
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(SwingUtilities.isLeftMouseButton(e)) {
            if (currentComponent == null) {
                return;
            }

            Point alteredLocation = currentComponent.getLocation();
            alteredLocation.x += grid.getScaledTileSize() / 2;
            alteredLocation.y += grid.getScaledTileSize() / 2;

            controller.changePiecePos(currentComponent, alteredLocation);
            currentComponent = null;
            grid.highlightCell(null);
        }
        else if(SwingUtilities.isRightMouseButton(e)) {
            Point p = e.getPoint();
            Component c = grid.getComponentAt(p);
            if (c == null || !((BasicGrid.CustomLayoutManager) grid.getLayout()).isPlacedPiece(c)) {
                return;
            }

            controller.rotatePiece(c);
        }
    }
}
