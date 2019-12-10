package GUI.Grid;

import Logic.GridController;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseEventHandler extends MouseAdapter {
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

    public MouseEventHandler(GridController c, BasicGrid grid) {
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
        oldLocation = grid.getRelativePoint(c.getLocation());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(currentComponent == null) {
            return;
        }

        currentComponent.setLocation(e.getX() - offset.x, e.getY() - offset.y);

        // TODO: check what we like better..
        Point gridPos = grid.getRelativePoint(e.getPoint());
        //Point gridPos = grid.getRelativePoint(currentComponent.getLocation());
        Dimension objDim = BasicGrid.getRelativeSize(currentComponent.getSize());
        if(gridPos == null || objDim == null || !grid.isValidRect(new Rectangle(BasicGrid.getAbsolutePoint(gridPos), BasicGrid.getAbsoluteDimension(objDim)))) {
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

        Point relativePoint = grid.getRelativePoint(e.getPoint());
        Dimension objDim = BasicGrid.getRelativeSize(currentComponent.getSize());
        if(relativePoint == null || !grid.isValidRect(new Rectangle(BasicGrid.getAbsolutePoint(relativePoint), BasicGrid.getAbsoluteDimension(objDim)))) {
            relativePoint = oldLocation;
        }

        //grid.setPiecePos(currentComponent, relativePoint);
        controller.changePiecePos(currentComponent, oldLocation, relativePoint);
        currentComponent = null;
        grid.highlightCell(null);
    }
}
