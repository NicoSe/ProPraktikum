package logic;

public class Grid {
    private int bound;
    private Character[] grid;

    public Grid(int bound) {
        if(bound <= 0) {
            throw new Error("Wrong bound for character field.");
        }

        this.bound = bound;
        this.grid = new Character[bound*bound];
    }

    public Character remove(int pos) {
        if(pos < 0 || pos > bound*bound) {
            return null;
        }
        Character c = grid[pos];
        if(c == null) {
            return null;
        }

        int height = c.isVertical() ? c.getSize() : 1;
        int width = c.isVertical() ? 1 : c.getSize();
        for(int y = 0; y < height; ++y) {
            int tmpPos = c.getPosition() + (y*bound);
            grid[tmpPos] = null;

            for(int x = 0; x < width; ++x) {
                grid[tmpPos+x] = null;
            }
        }
        return c;
    }

    public boolean put(int pos, Character inst) {
        if(!isEmptyAt(pos, inst.getSize(), inst.isVertical())) {
            return false;
        }

        int height = inst.isVertical() ? inst.getSize() : 1;
        int width = inst.isVertical() ? 1 : inst.getSize();
        for(int y = 0; y < height; ++y) {
            int tmpPos = pos + (y*bound);
            grid[tmpPos] = inst;

            for(int x = 1; x < width; ++x) {
                grid[tmpPos+x] = inst;
            }
        }

        inst.setPosition(pos);
        return true;
    }

    public void rotate(int pos) {
        Character c = grid[pos];
        if(c == null) {
            return;
        }

        int basePos = c.getPosition();
        remove(basePos);

        if(!isEmptyAt(pos, c.getSize(), !c.isVertical())) {
            put(basePos, c);
            return;
        }

        remove(pos);
        c.setRotation(c.getNextRotation());
        put(pos, c);
    }

    public void clear() {
        grid = new Character[bound*bound];
    }

    public boolean isEmptyAt(int pos, int size, boolean isVertical) {
        //invalid position
        if(pos < 0 || pos+size > bound*bound) {
            return false;
        }
        int row = pos/bound;
        //out of bounds
        if(row+size > bound) {
            return false;
        }

        int height = isVertical ? size : 1;
        int width = isVertical ? 1 : size;
        for(int y = 0; y < height; ++y) {
            int tmpPos = pos + (y*bound);
            if(grid[tmpPos] != null) {
                return false;
            }

            for(int x = 1; x < width; ++x) {
                if(grid[tmpPos+x] != null) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("Grid:\n");
        for(int y = 0; y < bound; ++y) {
            for(int x = 0; x < bound; ++x) {
                buf.append(String.format("|%d(%s)", x, grid[y*bound+x]));
            }
            buf.append('\n');
        }
        return buf.toString();
    }
}
