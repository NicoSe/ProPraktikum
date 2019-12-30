package Logic;

import java.util.*;
import java.util.function.Function;

public class Grid2D {
    private int bound;
    private Character[][] characters;
    private ShipHarbor harbor;

    public Grid2D(int bound) {
        if(bound < 0) {
            throw new Error("invalid paramter for Grid2D constructor. has to be signed int.");
        }

        this.bound = bound;
        this.characters = new Character[bound][bound];
        this.harbor = new ShipHarbor();
        this.harbor.load();
    }

    @FunctionalInterface
    public interface CharFunc<T1, T2, T3, R1> {
        public R1 apply(T1 t1, T2 t2, T3 t3);
    }

    public void forEachCharacter(CharFunc<Integer, Integer, Character, Void> f) {
        HashSet<Character> already = new HashSet<Character>();
        for(int x = 0; x < bound; ++x) {
            for(int y = 0; y < bound; ++y) {
                Character c = characters[x][y];
                if(c == null || already.contains(c)) {
                    continue;
                }
                f.apply(x, y, c);
                already.add(c);
            }
        }
    }

    public void generateRandom() {
        List<HarborShipData> ships = harbor.getCopyOfHarborData(bound);
        int placeCount = harbor.getTotalShipCount(bound);
        while(placeCount > 0) {
            // 0 -> 5 ship; 1 -> 4 ship; 2 -> 3 ship; 3 -> 2 ship
            int randomShip = new Random().nextInt(ships.size());
            HarborShipData data = ships.get(randomShip);

            int randomX = Util.GetRandomNumberInRange(0, bound-1);
            int randomY = Util.GetRandomNumberInRange(0, bound-1);

            //generate all ships vertical, everything else won't happen for now.
            Ship s = new Ship(data.size);
            s.setRotation(Rotation.values()[new Random().nextInt(Rotation.MAX_NUM.ordinal())]);

            while(put(randomX, randomY, s) == null) {
                randomX = Util.GetRandomNumberInRange(0, bound-1);
                randomY = Util.GetRandomNumberInRange(0, bound-1);
            }

            if(--data.amount <= 0) {
                ships.remove(randomShip);
            }
            placeCount--;
        }
    }

    private boolean isEmptyAt(int x, int y, int width, int height) {
        //check if pos is valid or not.
        if(x+width < 0 || x+width > this.bound) {
            return false;
        }
        if(y+height < 0 || y+height > this.bound) {
            return false;
        }


        //check if position is empty or not.
        for(int local_x = 0; local_x < width; ++local_x) {
            for(int local_y = 0; local_y < height; ++local_y) {
                if(characters[x+local_x][y+local_y] != null) {
                    return false;
                }
            }
        }
        return true;

    }

    private boolean isValidAt(int x, int y, int width, int height) {
        //check left, right, top, bot for another ship
        // make sure to keep edge cases like placing it at field bounds

        //left
        if(x-1 >= 0 && !isEmptyAt(x-1, y, 1, height)) {
            return false;
        }

        //top
        if(y-1 >= 0 && !isEmptyAt(x, y-1, width, 1)) {
            return false;
        }

        //right
        if(x+width < bound && !isEmptyAt(x+width, y, 1, height)) {
            return false;
        }

        //bot
        if(y+height < bound && !isEmptyAt(x, y+height, width, 1)) {
            return false;
        }

        //things to do for corner hinting...
        //check if we are in bounds...
        //true: if we are, check if the position is empty.
        //otherwise: continue with the next corner
        //...
        //if we arent in bounds at every pos then we cant colide with anything and thus we have a valid pos!

        //top-left
        if(x-1 >= 0 && y-1 >= 0 && !isEmptyAt(x-1, y-1, 1, 1)) {
            return false;
        }

        //bottom-left
        if(x-1 >= 0 && y+height < bound && !isEmptyAt(x-1, y+height, 1, 1)) {
            return false;
        }

        //top-right
        if(x+width < bound && y-1 >= 0 && !isEmptyAt(x+width, y-1, 1, 1)) {
            return false;
        }


        if(x+width < bound && y+height < bound && !isEmptyAt(x+width, y+height, 1, 1)) {
            return false;
        }

        return true;
    }

    public Character put(int x, int y, Character inst) {
        int size = inst.getSize();
        int height = inst.isVertical() ? size : 1;
        int width = inst.isVertical() ? 1 : size;

        // this also validates the position.
        if(!isEmptyAt(x, y, width, height)) {
            return null;
        }

        // make sure there is no colision between ships
        if(!isValidAt(x, y, width, height)) {
            return null;
        }

        for(int local_x = 0; local_x < width; ++local_x) {
            for(int local_y = 0; local_y < height; ++local_y) {
                characters[x+local_x][y+local_y] = inst;
            }
        }

        inst.setPosition(x, y);
        return inst;
    }

    private Character remove(int x, int y) {
        if(x < 0 || y < 0 || x > bound || y > bound) {
            return null;
        }

        Character inst = characters[x][y];
        if(inst == null) {
            return null;
        }

        x = inst.getPosition()[0];
        y = inst.getPosition()[1];

        int size = inst.getSize();
        int width = inst.isVertical() ? 1 : size;
        int height = inst.isVertical() ? size : 1;

        for(int local_x = 0; local_x < width; ++local_x) {
            for(int local_y = 0; local_y < height; ++local_y) {
                characters[x+local_x][y+local_y] = null;
            }
        }

        inst.setPosition(-1, -1);
        return inst;
    }

    public ShotResult shoot(int x, int y) {
        if(x < 0 || y < 0 || x > bound || y > bound) {
            return null;
        }

        Character c = characters[x][y];
        if(c == null) {
            return ShotResult.NONE;
        }

        int[] basePos = c.getPosition();

        if(!c.isVertical()) {
            return c.shoot(x - basePos[0]);
        }
        return c.shoot(y - basePos[1]);
    }

    public boolean move(int old_x, int old_y, int x, int y, Rotation rot) {
        if(old_x < 0 || old_y < 0 || old_x > bound || old_y > bound) {
            return false;
        }
        if(x < 0 || y < 0 || x > bound || y > bound) {
            return false;
        }

        Character c = characters[old_x][old_y];
        if(c == null) {
            return false;
        }

        int[] basePos = c.getPosition();
        Rotation oldRot = c.getRotation();

        remove(basePos[0], basePos[1]);
        c.setRotation(rot);

        if(put(x, y, c) == null) {
            System.out.println("couldnt move ship.");
            c.setRotation(oldRot);
            put(basePos[0], basePos[1], c);
            return false;
        }
        return true;
    }

    public boolean rotate(int x, int y) {
        Character c = characters[x][y];
        if(c == null) {
            return false;
        }

        int[] basePos = c.getPosition();
        Rotation oldRot = c.getRotation();

        remove(basePos[0], basePos[1]);
        c.setRotation(c.getNextRotation());

        if(put(basePos[0], basePos[1], c) == null) {
            System.out.println("couldnt rotate ship.");
            c.setRotation(oldRot);
            put(basePos[0], basePos[1], c);
            return false;
        }
        return true;
    }

    public void clear() {
        this.characters = new Character[bound][bound];
    }

    public int getBound() {
        return bound;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        for(int y = 0; y < bound; ++y) {
            for(int x = 0; x < bound; ++x) {
                buf.append(String.format("|%s", characters[x][y]));      //Nico:"ich hab mal die x Koordinate aus dem String entfernt,
                // da ich denke zum speichern brauchen wir die nicht
            }
            buf.append('\n');
        }
        return buf.toString();
    }
}
