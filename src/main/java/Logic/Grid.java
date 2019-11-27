package Logic;

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

    private Character remove(int pos) {
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
            for(int x = 0; x < width; ++x) {
                grid[tmpPos+x] = inst;
            }
        }

        inst.setPosition(pos);
        return true;
    }

    public ShotResult shoot(int pos) {
        Character c = grid[pos];
        if(c == null) {
            return ShotResult.NONE;
        }

        int basePos = c.getPosition();

        if(!c.isVertical()) {
            return c.shoot(pos-basePos);
        }
        return c.shoot((pos-basePos)/bound);
    }

    public void rotate(int pos) {
        Character c = grid[pos];
        if(c == null) {
            return;
        }

        int basePos = c.getPosition();
        remove(basePos);

        if(!isEmptyAt(pos, c.getSize(), !c.isVertical()) || !isValidPosition(pos, c.getSize(), !c.isVertical())) {
            System.out.println("invalid pos on rotate!");
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

    private boolean isValidPosition(int pos, int size, boolean isVertical) {
        if(!isVertical) {
            boolean hitBreakpoint = false;

            int alteredSize = size;
            for(int i = 0; i < size; ++i) {
                alteredSize--;
                if((pos+1+i) % bound == 0) {
                    hitBreakpoint = true;
                    break;
                }
            }

            return hitBreakpoint && alteredSize == 0;
        }
        return true;
    }

    private boolean isEmptyAt(int pos, int size, boolean isVertical) {
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
            for(int x = 0; x < width; ++x) {
                if(grid[tmpPos+x] != null) {
                    return false;
                }
            }
        }
        return true;
    }

    public int getBound() {
        return bound;
    }

    public void setBound(int bound) {
        this.bound = bound;
    }


    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        for(int y = 0; y < bound; ++y) {
            for(int x = 0; x < bound; ++x) {
                buf.append(String.format("|%s", grid[y*bound+x]));      //Nico:"ich hab mal die x Koordinate aus dem String entfernt,
                                                                                // da ich denke zum speichern brauchen wir die nicht
            }
            buf.append('\n');
        }
        return buf.toString();
    }
}
