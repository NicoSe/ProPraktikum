package Logic;

import GUI.Grid.BasicGrid;
import GUI.Grid.PlacementMEventHandler;
import GUI.Grid.ShootMEventHandler;
import GUI.Helpers;
import Misc.GridState;
import Network.Connector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class GridController {
    HashMap<Component, Character> c2c = new HashMap<>();
    private Connector connector;
    private boolean isInitialized = false;
    private Connector con;
    private Grid2D model;
    private BasicGrid view;
    private MouseAdapter mouseAdapter;
    private int lastx = -1;
    private int lasty = -1;

    public GridController(Grid2D model, Connector connector, BasicGrid view) {
        this.model = model;
        this.view = view;
        this.connector = connector;
        view.setController(this);
    }

    public Character getCharacterOfComponent(Component c) {
        return c2c.get(c);
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

        if(state == GridState.FORBID) {
            return;
        }

        if(state == GridState.SHOOT) {
            onFinalizePlace();
        }

        mouseAdapter = state == GridState.SHOOT ? new ShootMEventHandler(this, view) : new PlacementMEventHandler(this, view);
        view.addMouseListener(mouseAdapter);
        view.addMouseMotionListener(mouseAdapter);
    }

    public void randomize() {
        c2c.forEach((k,v) -> {
            view.remove(k);
        });

        c2c.clear();
        model.clear();
        model.generateRandom();

        model.forEachCharacter((Integer x, Integer y, Character c) -> {
            try {
                c2c.put(view.addPiece(c.getImage(), x, y, c.getSize(), c.isVertical()), c);
            } catch(IOException e) {
                System.out.printf("couldn't load character image. %s\n", e.getMessage());
            }
            return null;
        });
        view.revalidate();
        view.repaint();
    }

    public void rotatePiece(Component comp) {
        Character c = c2c.get(comp);
        if(c == null) {
            return;
        }

        if(model.rotate(c.getX(), c.getY())) {
            view.remove(comp);
            c2c.remove(comp);

            try {
                c2c.put(view.addPiece(c.getImage(), c.getX(), c.getY(), c.getSize(), c.isVertical()), c);
                view.revalidate();
                view.repaint();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
        if(connector != null && !connector.turn()) {
            return;
        }

        Character c = c2c.get(comp);
        if(c == null) {
            return;
        }

        //dont shoot if the ship is dead already.
        if(!c.isAlive()) {
            return;
        }

        int currentTileSize = view.getScaledTileSize();

        ///check for invalid pos
        Rectangle validRect = new Rectangle(c.getX()*currentTileSize, c.getY()*currentTileSize, c.getSize()*currentTileSize, c.getSize()*currentTileSize);
        if(!validRect.contains(pos)) {
            return;
        }

        int shipPosOffsetX = (pos.x - c.getX() * currentTileSize) / currentTileSize;
        int shipPosOffsetY = (pos.y - c.getY() * currentTileSize) / currentTileSize;

        if(c instanceof FoeGridShootObject) {
            ///get network here and sendmsg("shoot x y") then in MainFrame loop on result 0/1/2. handle accordingly!
            lastx = c.getX() + shipPosOffsetX;
            lasty = c.getY() + shipPosOffsetY;
            connector.sendMessage(String.format("shot %d %d", lastx, lasty));
        }

        ShotResult res = model.shoot(c.getX() + shipPosOffsetX, c.getY() + shipPosOffsetY);
        if(res == ShotResult.SUNK) {
            model.markSurrounding(c.getX(), c.getY());
        }
        view.revalidate();
        view.repaint();
        // TODO: modify view/ship panel according to shot result.
    }

    public void shoot(int x, int y) {
        lastx = x;
        lasty = y;

        ShotResult res = model.shoot(x, y);
        if(res == ShotResult.SUNK) {
            model.markSurrounding(x, y);
        }
    }

    public void processShotResult(int shotResult) {
        ShotResult r = ShotResult.values()[shotResult];
        Character c = model.getCharacter(lastx, lasty);

        LinkedList<Integer[]> markedPos;
        Ship s = null;
        switch (r) {
            case NONE:
                c.shoot(0); // 0 -> wasser
                Helpers.playSFX("/SFX/firered_00E2.wav", 1);
                break;
            case HIT:
                c.shoot(1); // 1 -> ship
                Helpers.playSFX("/SFX/firered_00CF.wav", 1);
                break;
            case SUNK:
                c.shoot(2); // 2 -> sunk
                markedPos = NetGridHelper.MarkSunkShipCorrectly(model, lastx, lasty);
                if(markedPos != null) {
                    int shipSize = markedPos.size();
                    s = new Ship(shipSize, true);
                    Ship finalS = s;
                    markedPos.forEach((Integer[] xy) -> {
                        model.replaceThrough(xy[0], xy[1], finalS);
                    });
                    s.setPosition(markedPos.getFirst()[0], markedPos.getFirst()[1]);
                    s.setRotation((markedPos.getLast()[0] - markedPos.getFirst()[0]) == 0 ? Rotation.VERTICAL : Rotation.HORIZONTAL);
                    model.markSurrounding(s.getX(), s.getY());
                }
                Helpers.playSFX("/SFX/shipSunk2.wav", 1);
                break;
        }

        Ship finalS1 = s;
        SwingUtilities.invokeLater(() -> {
            if(finalS1 != null) {
                try {
                    c2c.put(view.addPiece(finalS1.getImage(), finalS1.getX(), finalS1.getY(), finalS1.getSize(), finalS1.isVertical()), finalS1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            view.revalidate();
            view.repaint();
        });
    }

    public void highlightCell(Component c) {
        Character ch = c2c.get(c);
        if(ch == null || !ch.isAlive()) {
            view.highlightCell(null);
            return;
        }

        Point gridPos = new Point(c.getLocation());
        gridPos.x /= view.getScaledTileSize();
        gridPos.y /= view.getScaledTileSize();

        Dimension objDim = new Dimension(c.getSize());
        objDim.width /= view.getScaledTileSize();
        objDim.height /= view.getScaledTileSize();

        view.highlightCell(new Rectangle(gridPos, objDim));
    }

    public void setInteractionState(GridState state) {
        view.removeMouseListener(mouseAdapter);
        view.removeMouseMotionListener(mouseAdapter);

        if(state == GridState.FORBID) {
            return;
        }

        if(state == GridState.SHOOT) {
            onFinalizePlace();
        }

        mouseAdapter = state == GridState.SHOOT ? new ShootMEventHandler(this, view) : new PlacementMEventHandler(this, view);
        view.addMouseListener(mouseAdapter);
        view.addMouseMotionListener(mouseAdapter);
    }

    public void onFinalizePlace() {
        ArrayList<Character> waterFields = model.placeWaterOnEmptyFields();
        for(Character c : waterFields) {
            try {
                c2c.put(view.addPiece(c.getImage(), c.getX(), c.getY(), c.getSize(), c.isVertical()), c);
            } catch(IOException e) {
            }
        }
    }
}
