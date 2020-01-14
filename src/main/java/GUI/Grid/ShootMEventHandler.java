package GUI.Grid;

import Logic.GridController;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ShootMEventHandler extends MouseAdapter {
    private GridController controller;
    private BasicGrid grid;

    public ShootMEventHandler(GridController c, BasicGrid grid) {
        this.grid = grid;
        this.controller = c;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Point p = e.getPoint();
        Component c = grid.getComponentAt(p);
        if(c == null || !((BasicGrid.CustomLayoutManager)grid.getLayout()).isPlacedPiece(c)) {
            controller.highlightCell(null);
            return;
        }

        controller.highlightCell(c);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Point p = e.getPoint();
        Component c = grid.getComponentAt(p);
        if(c == null || !((BasicGrid.CustomLayoutManager)grid.getLayout()).isPlacedPiece(c)) {
            return;
        }
        controller.shoot(c, p);
    }
}
