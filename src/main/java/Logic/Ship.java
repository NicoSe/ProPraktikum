package Logic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

public class Ship extends Character {
    public int id;                  //SchiffID zum Laden wichtig
    private boolean[] hitbox;
    private int health;
    public static int id_counter = 0;       //hilft bei der Erstellung von ID

    public Ship(int size) {
        super(size);
        health = size;
        hitbox = new boolean[size];
        for(int i = 0; i < size; ++i) {
            hitbox[i] = true;
        }
        this.id = id_counter;
        id_counter++;
    }

    public Ship(int size, boolean[] hitbox){
        super(size);
        health = size;
        this.hitbox = hitbox;
        this.id = id_counter;
        id_counter++;
    }

    @Override
    //beschießt das Schiff an einer bestimmten Stelle(hitpos)
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

    public void setId(int id) {
        this.id = id;
    }

    public String toString(){
        String output = getId() + "," + getSize() + "," + getRotation();
        for(int i=0; i<hitbox.length; i++){
            if(hitbox[i] == true){output = output + ",1";}
            else{output = output + ",0";}
        }
        return output;
    }
}
