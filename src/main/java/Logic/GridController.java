package Logic;

import GUI.Grid.BasicGrid;
import GUI.Grid.PlacementMEventHandler;
import GUI.Grid.ShootMEventHandler;
import GUI.ScaleHelper;
import Misc.GridState;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.HashMap;

public class GridController {
    HashMap<Component, Character> c2c = new HashMap<>();
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
                c2c.put(view.addPiece(c.getImage(), x, y, c.getSize(), c.isVertical()), c);
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

    public void changePiecePos(Component comp, Point newPos) {
        Character c = c2c.get(comp);
        if(c == null) {
            return;
        }

        int currentTileSize = view.getScaledTileSize();

        int oldPosX = c.getX();
        int oldPosY = c.getY();

        int newPosX = newPos.x / currentTileSize;
        int newPosY = newPos.y / currentTileSize;

        if(model.move(oldPosX, oldPosY, newPosX, newPosY, c.isVertical() ? Rotation.VERTICAL : Rotation.HORIZONTAL)) {
            view.setPiecePos(comp, new Point(newPosX, newPosY));
        } else {
            view.setPiecePos(comp, new Point(oldPosX, oldPosY));
        }
    }

    public void shoot(Component comp, Point pos) {
        Character c = c2c.get(comp);
        if(c == null) {
            return;
        }

        int currentTileSize = view.getScaledTileSize();

        //check for invalid pos
        Rectangle validRect = new Rectangle(c.getX()*currentTileSize, c.getY()*currentTileSize, c.getSize()*currentTileSize, c.getSize()*currentTileSize);
        if(!validRect.contains(pos)) {
            return;
        }

        int shipPosOffsetX = (pos.x - c.getX() * currentTileSize) / currentTileSize;
        int shipPosOffsetY = (pos.y - c.getY() * currentTileSize) / currentTileSize;

        ShotResult res = model.shoot(c.getX() + shipPosOffsetX, c.getY() + shipPosOffsetY);

        // TODO: modify view/ship panel according to shot result.
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
