package Logic;

import java.awt.image.BufferedImage;

///Wasserobject für eigenes Grid, wird erst *nach* den Schiffen platziert!
public class Water extends Character {

    private boolean isHit = false;             //false für noch nicht beschossen, true für bereits beschossen

    ///Wasserobject hat Größe 1
    Water(boolean hit) {
        super(1);
        this.isHit = hit;
    }

    @Override
    ///return den Status des Waterobjects, zb für die Abfrage ob man noch mal hin schiesen darf
    public boolean isAlive() {
        return !isHit;
    }

    @Override
    public ShotResult shoot(int hitpos) {
        if(isHit) {
            System.out.println("water that was hit already.");
            return ShotResult.NONE;
        }
        isHit = true;
        System.out.println("Water hit!");
        return ShotResult.NONE;
    }

    @Override
    public BufferedImage getImage() {
        return null;
    }

    @Override
    public String toString() {
        if (isAlive()) return "w,1";
        else return "w,0";
    }
}
