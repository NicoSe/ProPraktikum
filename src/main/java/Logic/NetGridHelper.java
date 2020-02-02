package Logic;

import java.util.LinkedList;

public class NetGridHelper {

    public static LinkedList<Integer[]> MarkSunkShipCorrectly(Grid2D g2d, int x, int y) {
        int xsave = x;
        int ysave = y;
        Character c = g2d.getCharacter(x, y);
        if(!(c instanceof FoeGridShootObject)) {
            return null;
        }

        LinkedList<Integer[]> markedPos = new LinkedList<>();
        markedPos.add(new Integer[]{x,y});

        Character ctmp;
        FoeGridShootObject tmp;

        while(g2d.getCharacter(--x, y) instanceof FoeGridShootObject && (tmp = (FoeGridShootObject)g2d.getCharacter(x, y)) != null && tmp.getHitStatus() == 1) {
            tmp.shoot(2);
            markedPos.addFirst(new Integer[]{x, y});
        }
        x = xsave;

        while(g2d.getCharacter(++x, y) instanceof FoeGridShootObject && (tmp = (FoeGridShootObject)g2d.getCharacter(x, y)) != null && tmp.getHitStatus() == 1) {
            tmp.shoot(2);
            markedPos.addLast(new Integer[]{x, y});
        }
        x = xsave;


        while(g2d.getCharacter(x, --y) instanceof FoeGridShootObject && (tmp = (FoeGridShootObject)g2d.getCharacter(x, y)) != null && tmp.getHitStatus() == 1) {
            tmp.shoot(2);
            markedPos.addFirst(new Integer[]{x, y});
        }
        y = ysave;

        while(g2d.getCharacter(x, ++y) instanceof FoeGridShootObject && (tmp = (FoeGridShootObject)g2d.getCharacter(x, y)) != null && tmp.getHitStatus() == 1) {
            tmp.shoot(2);
            markedPos.addLast(new Integer[]{x, y});
        }
        y = ysave;

        return markedPos;
    }
}
