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

    int getX() {
        return this.pos[0];
    }

    int getY() {
        return this.pos[1];
    }

    Rotation getNextRotation() {
        return this.rot == Rotation.HORIZONTAL ? Rotation.VERTICAL : Rotation.HORIZONTAL;
    }

    public Rotation getRotation() {
        return this.rot;
    }

    public int getSize() {
        return size;
    }

    public boolean isVertical() {
        return rot == Rotation.VERTICAL;
    }

    public abstract boolean isAlive();
    public abstract ShotResult shoot(int hitpos);
    public abstract BufferedImage getImage() throws IOException;
}
