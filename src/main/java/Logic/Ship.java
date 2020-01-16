package Logic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

public class Ship extends Character {
    public int id;
    private boolean[] hitbox;
    private int health;
    private static int counter = 0;

    public Ship(int size) {
        super(size);
        health = size;
        hitbox = new boolean[size];
        for(int i = 0; i < size; ++i) {
            hitbox[i] = true;
        }
        this.id = counter;
        counter++;
    }

    @Override
    public ShotResult shoot(int hitpos) {
        System.out.printf("shot %s at pos: %d (hitbox: %s)\n", this, hitpos, Arrays.toString(hitbox));

        if(health <= 0) {
            return ShotResult.ALREADY;
        }

        if(!hitbox[hitpos]) {
            return ShotResult.ALREADY;
        }

        hitbox[hitpos] = false;
        if(--health == 0) {
            System.out.println("i go glug glug glug.");
            return ShotResult.SUNK;
        }


        System.out.println("i go kaboom.");
        return ShotResult.HIT;
    }

    @Override
    public BufferedImage getImage() throws IOException {
        switch (getSize()) {
            case 2:
                return ImageIO.read(getClass().getResource("/Sprites/takethismydude.png"));
            case 3:
                return ImageIO.read(getClass().getResource("/Sprites/takethismydude.png"));
            case 4:
                return ImageIO.read(getClass().getResource("/Sprites/takethismydude.png"));
            case 5:
                return ImageIO.read(getClass().getResource("/Sprites/takethismydude.png"));

        }
        //TODO: return some kind of dummy image.
        return null;
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }

    public int getId() {
        return id;
    }

    public static void resetCounter(){counter = 0;}

    public void setId(int id) {
        this.id = id;
    }

    public boolean[] getHitbox() {
        return hitbox;
    }

    public String toString(){
        String output = getId() + "," + getSize() + "," + getRotation(); //Status über Schiffsteil fehlt
        for(int i=0; i<hitbox.length; i++){
            if(hitbox[i] == true){output = output + ",1";}
            else{output = output + ",0";}
        }
        return output;
    }
}
