package Logic;

import java.util.List;
import java.util.Random;

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
        if(x-1 > 0 && !isEmptyAt(x-1, y, 1, height)) {
            return false;
        }

        //top
        if(y-1 > 0 && !isEmptyAt(x, y-1, width, 1)) {
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
        int width = inst.isVertical() ? size : 1;
        int height = inst.isVertical() ? 1 : size;

        for(int local_x = 0; local_x < width; ++local_x) {
            for(int local_y = 0; local_y < height; ++local_y) {
                characters[x+local_x][y+local_y] = inst;
            }
        }

        inst.setPosition(-1, -1);
        return inst;
    }

    public ShotResult shoot(int x, int y) {
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

    public boolean rotate(int x, int y) {
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
