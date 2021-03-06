package Logic;

import java.awt.image.BufferedImage;

///GridObject welches den Status des zugedeckten gegnerischen Feldes zeigt
public class FoeGridShootObject extends Character {
    private int hitStatus = -1;          ///0 noch nicht beschossen, 1 wasser, 2 Schiff

    ///erstelle GridObject mit Größe 1
    public FoeGridShootObject(int hitstatus) {
        super(1);
        this.hitStatus = hitstatus;
    }

    @Override
    ///Methode wird nicht gebraucht
    public boolean isAlive() {
        return hitStatus == -1;
    }

    /// überschriebene shoot-Methode, welche den Status statt der Koordinaten schreibt
    public ShotResult shoot(int status) {
        if (status < 0 || status > 2) {
            System.out.println("FoeGridShootObject: Invalid status!");
            return null;
        }
        hitStatus = status;
        return null;
    }

    @Override
    public BufferedImage getImage() {
        return null;
    }

    public int getHitStatus(){
        return hitStatus;
    }

    @Override
    public String toString() {
        return Integer.toString(hitStatus);
    }
}
