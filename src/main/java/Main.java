import logic.Grid;
import logic.Ship;
import logic.Character;

public class Main {
    public static void main(String[] args) {
        Grid grid = new Grid(5);
        Character c = new Ship(2);
        grid.put(0, c);
        Character c2 = new Ship(2);
        grid.put(2, c2);
        System.out.println(grid);
        grid.rotate(0);
        System.out.println(grid);
    }
}
