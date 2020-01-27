package Logic;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SaveManager {
    public static void save(String filename, Grid2D a, Grid2D b) {
        try {
            PrintWriter pWriter = new PrintWriter((new BufferedWriter((new FileWriter("./SaveGames/" + filename + ".txt")))));
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            pWriter.write(dtf.format(now) + "\n");
            pWriter.write("SIZE\n" + a.getBound() + "\n");
            pWriter.write("OWNGRID\n" + a.toString());
            pWriter.write("OTHERGRID\n" + b.toString());

            pWriter.flush();
            pWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Grid2D[] load(String adress) {
        BufferedReader in = null;
        File file = new File("./SaveGames/" + adress + ".txt");
        if (!file.exists() || !file.canRead()) {
            return null;
        }

        try {
            in = new BufferedReader(new FileReader(file));
            in.readLine();
            in.readLine();
            int bound = Integer.parseInt(in.readLine());

            String[] owngrid = new String[bound];
            String[] foegrid = new String[bound];
            in.readLine();

            String help = null;
            int i = 0;
            while ((i < bound) && ((help = in.readLine()) != null)) {
                owngrid[i] = help;
                i++;
            }
            in.readLine();

            help = null;
            i = 0;
            while ((i < bound) && ((help = in.readLine()) != null)) {
                foegrid[i] = help;
                i++;
            }

            Grid2D[] grids = new Grid2D[2];
            grids[0] = create_owngrid(owngrid, bound);
            grids[1] = create_foegrid(foegrid, bound);
            return grids;

        } catch (IOException e) {
            return null;
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                    return null;
                }
        }
    }

    public static Grid2D create_owngrid(String[] owngrid, int bound) {
        Grid2D own_grid = new Grid2D(bound);
        try {
            boolean[] ids = new boolean[bound*bound];

            //Grid2D own_grid = new Grid2D(bound);
            Ship.resetCounter();

            int currentRow = 0;
            while (currentRow < owngrid.length) {                                   ///Schleife über jede Zeile der Datei
                String[] col = owngrid[currentRow].split("\\|");

                int currentCol = 1;
                while (currentCol < col.length) {                                   ///Schleife über jedes Element einer Zeile
                    String[] temp = col[currentCol].split(",");
                    if (col[currentCol].equals("null") || col[currentCol].equals("")) {
                        break;

                    } else if (temp[0].equals("w")) {                             ///Wassertreffer realisieren
                        if (temp[1].equals("1")) own_grid.put(currentCol-1,currentRow, new Water(false));
                        else own_grid.put(currentCol-1,currentRow, new Water(true));

                    } else if (!ids[Integer.parseInt(temp[0])]) {    //Schiffe erstellen
                        ids[Integer.parseInt(temp[0])] = true;
                        ///create ship
                        Character c = new Ship(Integer.parseInt(temp[1]));
                        ///set rotation
                        switch (temp[2]) {
                            case "VERTICAL":
                                c.setRotation(Rotation.VERTICAL);
                                break;
                            case "HORIZONTAL":
                                c.setRotation(Rotation.HORIZONTAL);
                                break;
                        }
                        ///place ship in grid
                        own_grid.put(currentCol-1,currentRow, c);
                        ///call ship status
                        for (int hit = 3; hit < temp.length; hit++) {
                            if (temp[hit].equals("0")) c.shoot(hit - 3);
                        }
                    }
                    currentCol++;
                }
                currentRow++;
            }
            own_grid.recalculateAliveShips();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return own_grid;
    }

    public static Grid2D create_foegrid(String[] foegrid, int bound) {
        Grid2D foe_grid = new Grid2D(bound);
        boolean[] ids = new boolean[bound*bound];
        try {
            int currentRow = 0;
            while (currentRow < foegrid.length) {                                   ///Schleife über jede Zeile der Datei
                String[] col = foegrid[currentRow].split("\\|");

                int currentCol = 1;
                label:
                while (currentCol < col.length) {
                    switch (col[currentCol]) {
                        case "-1":
                            foe_grid.put(currentCol - 1, currentRow, new FoeGridShootObject(-1));
                            break;
                        case "0":
                            foe_grid.put(currentCol - 1, currentRow, new FoeGridShootObject(0));
                            break;
                        case "1":
                            foe_grid.put(currentCol - 1, currentRow, new FoeGridShootObject(1));          ///Anzeigen ob gegnerisches virtuelles Schiff getroffen

                            break;
                        case "2":
                            foe_grid.put(currentCol - 1, currentRow, new FoeGridShootObject(2));
                            break;
                        case "null":
                        case "":
                            break label;
                        default:
                            String[] temp = col[currentCol].split(",");
                            if (temp[0].equals("w")) {                             ///Wassertreffer realisieren
                                if (temp[1].equals("1")) foe_grid.put(currentCol - 1, currentRow, new Water(false));
                                else foe_grid.put(currentCol - 1, currentRow, new Water(true));

                            } else if (!ids[Integer.parseInt(temp[0])]) {    //Schiffe erstellen
                                ids[Integer.parseInt(temp[0])] = true;
                                ///create ship
                                Character c = new Ship(Integer.parseInt(temp[1]));
                                ///set rotation
                                switch (temp[2]) {
                                    case "VERTICAL":
                                        c.setRotation(Rotation.VERTICAL);
                                        break;
                                    case "HORIZONTAL":
                                        c.setRotation(Rotation.HORIZONTAL);
                                        break;
                                }
                                ///place ship in grid
                                foe_grid.put(currentCol - 1, currentRow, c, true);
                                ///call ship status
                                for (int hit = 3; hit < temp.length; hit++) {
                                    if (temp[hit].equals("0")) c.shoot(hit - 3);
                                }
                            }
                            break;
                    }
                    currentCol++;
                }
                currentRow++;
            }
            foe_grid.recalculateAliveShips();
        } catch (Exception e) {
            return null;
        }
        return foe_grid;
    }

}
