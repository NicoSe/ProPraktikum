package Logic;

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
