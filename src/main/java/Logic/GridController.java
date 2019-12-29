package Logic;

import GUI.Grid.BasicGrid;
import GUI.Grid.PlacementMEventHandler;
import GUI.Grid.ShootMEventHandler;
import GUI.ScaleHelper;
import Misc.GridState;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.io.IOException;

public class GridController {
    private boolean isInitialized = false;
    private Grid2D model;
    private BasicGrid view;
    private MouseAdapter mouseAdapter;

    public GridController(Grid2D model, BasicGrid view) {
        this.model = model;
        this.view = view;
    }

    public void init(GridState state) {
        if(isInitialized) {
            return;
        }
        isInitialized = true;

        model.forEachCharacter((Integer x, Integer y, Character c) -> {
            try {
                view.addPiece(c.getImage(), x, y, c.getSize(), c.isVertical());
            } catch(IOException e) {
                System.out.printf("couldn't load character image. %s\n", e.getMessage());
            }
            return null;
        });

        mouseAdapter = state == GridState.SHOOT ? new ShootMEventHandler(this, view) : new PlacementMEventHandler(this, view);
        view.addMouseListener(mouseAdapter);
        view.addMouseMotionListener(mouseAdapter);
    }

    public void changePiecePos(Point od, Point nw, Dimension dim) {
        // TODO: write method that gets the component from grid
    }

    public void changePiecePos(Component comp, Point oldPos, Point newPos) {
        double sf = ScaleHelper.CalculateScalingFactor(view);
        Dimension objDim = BasicGrid.getRelativeSize(comp.getSize(), sf);
        if(model.move(oldPos.x, oldPos.y, newPos.x, newPos.y, objDim.width > objDim.height ? Rotation.HORIZONTAL : Rotation.VERTICAL)) {
            view.setPiecePos(comp, newPos);
        } else {
            view.setPiecePos(comp, oldPos);
        }
    }

    public void shoot(Point comp) {
        Point pos = view.getRelativePoint(comp.getLocation(), ScaleHelper.CalculateScalingFactor(view));
        System.out.printf("shoot at x: %d y: %d with sf: %f\n", pos.x, pos.y, ScaleHelper.CalculateScalingFactor(view));
        ShotResult res = model.shoot(pos.x, pos.y);
    }

    public void setInteractionState(GridState state) {
        view.setGridInteractionState(state);

        view.removeMouseListener(mouseAdapter);
        view.removeMouseMotionListener(mouseAdapter);

        if(state == GridState.FORBID) {
            return;
        }

        mouseAdapter = state == GridState.SHOOT ? new ShootMEventHandler(this, view) : new PlacementMEventHandler(this, view);
        view.addMouseListener(mouseAdapter);
        view.addMouseMotionListener(mouseAdapter);
    }

}
