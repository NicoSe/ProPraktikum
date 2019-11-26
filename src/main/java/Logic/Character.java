package Logic;

enum Rotation {
    NORTH,
    EAST,
    SOUTH,
    WEST,
    MAX_NUM,
}

enum ShotResult {
    NONE,
    HIT,
    SUNK,
}

public abstract class Character {
    private int size;
    private int pos;
    private Rotation rot;

    Character(int size) {
        this.size = size;
        this.rot = Rotation.NORTH;
    }

    void setRotation(Rotation rot) {
        this.rot = rot;
    }

    void setPosition(int pos) {
        this.pos = pos;
    }

    int getPosition() {
        return this.pos;
    }

    Rotation getNextRotation() {
        return Rotation.values()[rot.ordinal()+1 % Rotation.MAX_NUM.ordinal()];
    }

    public Rotation getRotation() {
        return this.rot;
    }

    public int getSize() {
        return size;
    }

    public boolean isVertical() {
        switch(rot) {
            case NORTH:
            case SOUTH:
                return true;
            default:
                return false;
        }
    }

    public abstract ShotResult shoot();
}
