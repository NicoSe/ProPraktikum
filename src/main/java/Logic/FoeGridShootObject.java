package Logic;

import java.awt.image.BufferedImage;
import java.io.IOException;

///GridObject welches den Status des zugedeckten gegnerischen Feldes zeigt
public class FoeGridShootObject extends Character {
    private int hitStatus = 0;          ///0 noch nicht beschossen, 1 wasser, 2 Schiff

    ///erstelle GridObject mit Größe 1
    public FoeGridShootObject(int hitstatus) {
        super(1);
        this.hitStatus = hitstatus;
    }

    @Override
    ///Methode wird nicht gebraucht
    public boolean isAlive() {
        return hitStatus != 2;
    }

    @Override
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
    public BufferedImage getImage() throws IOException {
        return null;
    }

    public int getHitStatus(){
        return hitStatus;
    }

    @Override
    /// für Konsolenanwendung
    public String toString() {
        String output;
        if (hitStatus == 0) return "0";
        else if (hitStatus == 1) return "1";
        else if (hitStatus == 2) return "2";
        return null;
    }
}
