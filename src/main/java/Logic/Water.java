package Logic;

//Wasserobject für eigenes Grid, wird erst *nach* den Schiffen platziert!
public class Water extends Character {

    private boolean isHit = false;             //false für noch nihct beschossen, true für bereits beschossen

    //Wasserobject hat Größe 1
    Water(boolean hit) {
        super(1);
        this.isHit = hit;
    }

    @Override
    //returne den Status des Waterobjects, zb für die Abfrage ob man noch mal hin schiesen darf
    public boolean isAlive() {
        return isHit;
    }

    @Override
    //Methode wird nicht gebraucht
    public ShotResult shoot(int hitpos) {
        isHit = true;
        return ShotResult.NONE;
    }

    @Override
    //für Konsolenanwendung
    public String toString() {
        if (isAlive()) return "w,1";
        else return "w,0";
    }
}
