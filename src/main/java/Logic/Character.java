package Logic;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

enum Rotation {
    VERTICAL,
    HORIZONTAL,
    MAX_NUM,
}

public abstract class Character {
    private int size;
    private int[] pos;
    private Rotation rot;

    Character(int size) {
        this.size = size;
        this.rot = Rotation.VERTICAL;
        this.pos = new int[]{-1, -1};
    }

    void setRotation(Rotation rot) {
        this.rot = rot;
    }

    void setPosition(int x, int y) {
        this.pos[0] = x;
        this.pos[1] = y;
    }

    int[] getPosition() {
        return Arrays.copyOf(this.pos, this.pos.length);
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
            case VERTICAL:
                return true;
            default:
                return false;
        }
    }

    public abstract boolean isAlive();
    public abstract ShotResult shoot(int hitpos);
    public abstract BufferedImage getImage() throws IOException;
}
