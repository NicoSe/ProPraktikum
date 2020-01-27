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
    HashMap<Component, Character> co2ch = new HashMap<>();
    HashMap<Character, Component> ch2co = new HashMap<>();
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
        return co2ch.get(c);
    }

    public void init(GridState state) {
        if(isInitialized) {
            return;
        }
        isInitialized = true;

        model.forEachCharacter((Integer x, Integer y, Character c) -> {
            try {
                Component comp = view.addPiece(c.getImage(), x, y, c.getSize(), c.isVertical());
                co2ch.put(comp, c);
                ch2co.put(c, comp);
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
        co2ch.forEach((k, v) -> {
            view.remove(k);
        });

        co2ch.clear();
        ch2co.clear();
        model.clear();
        model.generateRandom();

        model.forEachCharacter((Integer x, Integer y, Character c) -> {
            try {
                Component comp = view.addPiece(c.getImage(), x, y, c.getSize(), c.isVertical());
                co2ch.put(comp, c);
                ch2co.put(c, comp);
            } catch(IOException e) {
                System.out.printf("couldn't load character image. %s\n", e.getMessage());
            }
            return null;
        });
        view.revalidate();
        view.repaint();
    }

    public void rotatePiece(Component comp) {
        Character c = co2ch.get(comp);
        if(c == null) {
            return;
        }

        if(model.rotate(c.getX(), c.getY())) {
            view.remove(comp);
            co2ch.remove(comp);
            ch2co.remove(c);

            try {
                Component compNew = view.addPiece(c.getImage(), c.getX(), c.getY(), c.getSize(), c.isVertical());
                co2ch.put(compNew, c);
                ch2co.put(c, compNew);
                view.revalidate();
                view.repaint();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void changePiecePos(Component comp, Point newPos) {
        Character c = co2ch.get(comp);
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

        Character c = co2ch.get(comp);
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
                        Character tmpCh = model.getCharacter(xy[0], xy[1]);
                        SwingUtilities.invokeLater(() -> {
                            Component comp = ch2co.remove(tmpCh);
                            if(comp != null) {
                                co2ch.remove(comp);
                                view.remove(comp);
                            }
                        });
                        model.replaceThrough(xy[0], xy[1], finalS);
                    });
                    s.setPosition(markedPos.getFirst()[0], markedPos.getFirst()[1]);
                    s.setRotation((markedPos.getLast()[0] - markedPos.getFirst()[0]) == 0 ? Rotation.VERTICAL : Rotation.HORIZONTAL);
                    model.markSurrounding(s.getX(), s.getY());
                    model.recalculateAliveShips();
                }
                Helpers.playSFX("/SFX/shipSunk2.wav", 1);
                break;
        }

        Ship finalS1 = s;
        SwingUtilities.invokeLater(() -> {
            if(finalS1 != null) {
                try {
                    Component comp = view.addPiece(finalS1.getImage(), finalS1.getX(), finalS1.getY(), finalS1.getSize(), finalS1.isVertical());
                    co2ch.put(comp, finalS1);
                    ch2co.put(finalS1, comp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            view.revalidate();
            view.repaint();
        });
    }

    public void highlightCell(Component c) {
        Character ch = co2ch.get(c);
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
                Component comp = view.addPiece(c.getImage(), c.getX(), c.getY(), c.getSize(), c.isVertical());
                ch2co.put(c, comp);
                co2ch.put(comp, c);
            } catch(IOException e) {
            }
        }
    }
}
