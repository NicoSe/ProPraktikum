package logic;

public class Ship extends Character {
    public Ship(int size) {
        super(size);
    }

    @Override
    public ShotResult shoot() {
        System.out.println("i go kaboom.");
        return ShotResult.HIT;
    }
}
