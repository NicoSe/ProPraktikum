package Logic;

public class Ship extends Character {
    private int id;
    private static int i = 0;

    public Ship(int size) {
        super(size);
        this.id = i++;
    }

    @Override
    public ShotResult shoot() {
        System.out.println("i go kaboom.");
        return ShotResult.HIT;
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
