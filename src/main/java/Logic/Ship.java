package Logic;

public class Ship extends Character {
    private int id;
    private boolean[] hitbox;
    private int health;
    private static int i = 0;

    public Ship(int size) {
        super(size);
        health = size;
        hitbox = new boolean[size];
        for(int i = 0; i < size; ++i) {
            hitbox[i] = true;
        }
        this.id = i++;
    }

    @Override
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
        return getId() + "," + getSize() + "," + getRotation() ; //Status über Schiffsteil fehlt
    }
}
