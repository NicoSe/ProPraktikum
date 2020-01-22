package Logic;

import Network.Connector;

import javax.swing.*;
import java.util.Collections;
import java.util.LinkedList;


enum KIMode
{
    STUPID,
    NORMAL,
}

enum PossibleDirection {
    NORTH,
    EAST,
    SOUTH,
    WEST
}

public class NewKI
{
    public static class FoundShip {
        public int x;
        public int y;
        public int shotCount;
        public LinkedList<Integer> validXPos = new LinkedList<Integer>();
        public LinkedList<Integer> validYPos = new LinkedList<Integer>();
        public PossibleDirection lastDir = null;
        public LinkedList<PossibleDirection> possibleDirections = new LinkedList<PossibleDirection>() {{
            add(PossibleDirection.NORTH);
            add(PossibleDirection.EAST);
            add(PossibleDirection.SOUTH);
            add(PossibleDirection.WEST);
            Collections.shuffle(this);
        }};
    }

    private Grid2D grid;
    private Connector s;
    private Grid2D enemyGrid;
    private KIMode mode;
    static boolean firstAction = true;
    private static int initialX, initialY, direction = -1;
    private int[] usedCases = new int[4];
    private int enemyShipsAlive = 0;

    private FoundShip ship;

    /**
     * Easy Mode: Just fires in a random pattern at the enemy.
     * Normal Mode: Shoots in a random pattern at the enemy. If its manages to hit a ship, it will try to find out its direction and shoot in this direction.
     *              At the first "MISS" it will fire in the opposite direction, starting form the initial hit+1.
     * Hard Mode: Its first shot will be the middle of the grid. After that it will fire in a chess pattern at the enemy. It also tries to locate the direction of a damaged ship
     *            and knows his initial hit of the ship.
     */

    public NewKI(Connector s, int mode)
    {
        this.s = s;

        if (mode == 0) {
            this.mode = KIMode.STUPID;
        } else {
            this.mode = KIMode.NORMAL;
        }

        //check if connector is server, create grid and send size.

        handleData(this.s);
    }

    public void close() {
        s.Close();
    }

    private void init(int bound) {
        grid = new Grid2D(bound);
        grid.generateRandom();
        enemyShipsAlive = grid.getShipCount();
        enemyGrid = new Grid2D(bound);
        enemyGrid.placeFGOeverywhere();
    }

    private void handleData(Connector c) {
        while (true) {
            if(!c.isConnected()) {
                break;
            }
            String res = c.listenToNetwork();
            String[] cmd = res.split(" ");
            switch(cmd[0]) {
                case "confirmed":
                case "pass":
                    shoot();
                    break;
                case "size":
                    init(Integer.parseInt(cmd[1]));
                    c.sendmsg("confirmed");
                    break;
                case "answer":
                    int answer = Integer.parseInt(cmd[1]);
                    int x,y;
                    switch(answer) {
                        case 0:
                            enemyGrid.shoot(ship.validXPos.removeLast(), ship.validYPos.removeLast(), answer);
                            if(ship.lastDir != null) {
                                ship.lastDir = null;
                            } else {
                                ship = null;
                            }

                            c.sendmsg("pass");
                            break;
                        case 1:
                            enemyGrid.shoot(ship.validXPos.peekLast(), ship.validYPos.peekLast(), answer);

                            removeImpossibleDirections();
                            shoot();
                            break;
                        case 2:
                            enemyShipsAlive--;
                            if(checkWinCondition()) {
                                SwingUtilities.invokeLater(() -> {
                                    JOptionPane.showMessageDialog(null, "You lost, noob. Ok, exits the game.");
                                    System.exit(0);
                                });
                                return;
                            }

                            enemyGrid.shoot(ship.validXPos.peekLast(), ship.validYPos.peekLast(), answer);
                            markSunkenShip();
                            ship = null;
                            shoot();
                            break;
                    }
                    break;
                case "shot":
                    ShotResult sr = grid.shoot(Integer.parseInt(cmd[1]), Integer.parseInt(cmd[2]));
                    c.sendmsg(String.format("answer %d", sr.ordinal()));
                    break;
                default:
                    System.out.println("Invalid command.");
                    break;
            }
        }
    }

    public boolean checkWinCondition() {
        return enemyShipsAlive <= 0;
    }

    private void shoot() {
        if(ship != null && mode != KIMode.STUPID) {
            tryFinishShip();
        } else {
            ship = new FoundShip();
            ship.x = Util.GetRandomNumberInRange(0, enemyGrid.getBound()-1);
            ship.y = Util.GetRandomNumberInRange(0, enemyGrid.getBound()-1);

            Character c = enemyGrid.getCharacter(ship.x, ship.y);
            if(c == null || !c.isAlive()) {
                ship = null;
                shoot();
                return;
            }

            shootCommand(ship.x, ship.y);
        }
    }

    private void getNewDirection() {
        int key = Util.GetRandomNumberInRange(0, ship.possibleDirections.size()-1);
        ship.lastDir = ship.possibleDirections.remove(key);
        ship.shotCount = 0;
    }

    private void removeImpossibleDirections() {
        if(ship.lastDir == null) {
            return;
        }

        ship.possibleDirections.removeIf((pos) -> {
            switch(ship.lastDir) {
                case NORTH:
                case SOUTH:
                    return pos == PossibleDirection.EAST || pos == PossibleDirection.WEST;
                case EAST:
                case WEST:
                    return pos == PossibleDirection.NORTH || pos == PossibleDirection.SOUTH;
            }
            return false;
        });
    }

    private void markSunkenShip() {
        System.out.println(ship.validXPos);
        System.out.println(ship.validYPos);

        int size = ship.validXPos.size();
        for(int i = 0; i < size; ++i) {
            int tmpx = ship.validXPos.removeFirst();
            int tmpy = ship.validYPos.removeFirst();

            for(int x = tmpx-1; x < tmpx+2; ++x) {
                for(int y = tmpy-1; y < tmpy+2; ++y) {
                    if(x < 0 || y < 0 || x >= grid.getBound() || y >= grid.getBound()) {
                        continue;
                    }

                    if(enemyGrid.getCharacter(x, y).isAlive()) {
                        enemyGrid.shoot(x, y, 0);
                        System.out.printf("mark sunken ship at x %d y %d\n", x, y);
                    }
                }
            }
        }
    }

    private boolean shootCommand(int x, int y) {
        if(x < 0 || y < 0 || x >= grid.getBound() || y >= grid.getBound() /*|| enemyGrid[x][y]*/) {
            return false;
        }

        ship.validXPos.addLast(x);
        ship.validYPos.addLast(y);
        s.sendmsg(String.format("shot %d %d", x, y));
        //enemyGrid[x][y] = true;

        return true;
    }

    private void tryFinishShip() {
        if(ship.lastDir == null) {
            getNewDirection();
        }

        /**
         * 1.
         */
        switch(ship.lastDir) {
            case NORTH:
                //shot would be invalid, try other direction.
                if(ship.y - ship.shotCount - 1 < 0) {
                    ship.lastDir = null;
                    tryFinishShip();
                    return;
                }
                if(shootCommand(ship.x, ship.y - (ship.shotCount+1))) ++ship.shotCount;
                break;
            case EAST:
                //shot would be invalid, try other direction.
                if(ship.x + ship.shotCount + 1 >= grid.getBound()) {
                    ship.lastDir = null;
                    tryFinishShip();
                    return;
                }
                if(shootCommand(ship.x + (ship.shotCount + 1), ship.y)) ++ship.shotCount;
                break;
            case SOUTH:
                //shot would be invalid, try other direction.
                if(ship.y + ship.shotCount + 1 >= grid.getBound()) {
                    ship.lastDir = null;
                    tryFinishShip();
                    return;
                }
                if(shootCommand(ship.x, ship.y + (ship.shotCount+1))) ++ship.shotCount;
                break;

            case WEST:
                //shot would be invalid, try other direction.
                if(ship.x - ship.shotCount - 1 < 0) {
                    ship.lastDir = null;
                    tryFinishShip();
                    return;
                }
                if(shootCommand(ship.x - (ship.shotCount+1), ship.y)) ++ship.shotCount;
                break;
        }
    }
}
