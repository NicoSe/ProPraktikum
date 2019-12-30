package GUI.Grid;

import GUI.ScaleHelper;
import Logic.GridController;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlacementMEventHandler extends MouseAdapter {
    /*
    private Grid grid;
    public MouseEventHandler(Grid g) {
        this.grid = g;
    }

     */
    private Component currentComponent;
    private GridController controller;
    private BasicGrid grid;
    private Point oldLocation;
    private Point offset;

    public PlacementMEventHandler(GridController c, BasicGrid grid) {
        this.grid = grid;
        this.controller = c;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point p = e.getPoint();
        Component c = grid.getComponentAt(p);
        if(c == null || !((BasicGrid.CustomLayoutManager)grid.getLayout()).isPlacedPiece(c)) {
            return;
        }

        offset = new Point();
        offset.x = e.getX() - c.getX();
        offset.y = e.getY() - c.getY();

        System.out.printf("%s at %s\n", c, p);
        currentComponent = c;
        oldLocation = grid.getRelativePoint(c.getLocation(), ScaleHelper.CalculateScalingFactor(c.getParent()));
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(currentComponent == null) {
            return;
        }

        currentComponent.setLocation(e.getX() - offset.x, e.getY() - offset.y);

        // TODO: check what we like better..
        Point alteredLocation = currentComponent.getLocation();
        alteredLocation.x += BasicGrid.TILE_BASE_SIZE / 2;
        alteredLocation.y += BasicGrid.TILE_BASE_SIZE / 2;

        Point gridPos = grid.getRelativePoint(alteredLocation, ScaleHelper.CalculateScalingFactor(currentComponent.getParent()));
        //Point gridPos = grid.getRelativePoint(currentComponent.getLocation());
        Dimension objDim = BasicGrid.getRelativeSize(currentComponent.getSize(), ScaleHelper.CalculateScalingFactor(currentComponent.getParent()));
        if(gridPos == null || !grid.getScaledGridRect().contains(currentComponent.getBounds())) {
            grid.highlightCell(null);
            return;
        }

        grid.highlightCell(new Rectangle(gridPos, objDim));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(currentComponent == null) {
            return;
        }

        Point alteredLocation = currentComponent.getLocation();
        alteredLocation.x += BasicGrid.TILE_BASE_SIZE / 2;
        alteredLocation.y += BasicGrid.TILE_BASE_SIZE / 2;
        Point relativePoint = grid.getRelativePoint(alteredLocation, ScaleHelper.CalculateScalingFactor(currentComponent.getParent()));
        if(relativePoint == null || !grid.getScaledGridRect().contains(currentComponent.getBounds())) {
            relativePoint = oldLocation;
        }

        //grid.setPiecePos(currentComponent, relativePoint);
        controller.changePiecePos(currentComponent, oldLocation, relativePoint);
        currentComponent = null;
        grid.highlightCell(null);
    }
}
