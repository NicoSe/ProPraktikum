package Logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Load {

    public Load() {
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
            while ((i < 5) && ((help = in.readLine()) != null)) {
                owngrid[i] = help;
                i++;
            }
            in.readLine();

            help = null;
            i = 0;
            while ((i < 5) && ((help = in.readLine()) != null)) {
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
            boolean[] ids = new boolean[64];

            //Grid2D own_grid = new Grid2D(bound);
            Ship.id = 0;

            int i = 0;
            while (i < owngrid.length) {                                   //Schleife über jede Zeile der Datei
                String[] col = owngrid[i].split("\\|");

                int j = 1;
                while (j < col.length) {                                   //Schleife über jedes Element einer Zeile
                    String[] temp = col[j].split(",");
                    if (col[j].equals("null") || col[j].equals("")) {
                        break;

                    } else if (temp[0].equals("w")) {                             //Wassertreffer realisieren
                        if (temp[1].equals("1")) own_grid.put(j-1,i, new Water(false));
                        else own_grid.put(j-1,i, new Water(true));

                    } else if (ids[Integer.parseInt(temp[0])] == false) {    //Schiffe erstellen
                        ids[Integer.parseInt(temp[0])] = true;
                        //create ship
                        Character c = new Ship(Integer.parseInt(temp[1]));
                        //set rotation
                        switch (temp[2]) {
                            case "VERTICAL":
                                c.setRotation(Rotation.VERTICAL);
                                break;
                            case "HORIZONTAL":
                                c.setRotation(Rotation.HORIZONTAL);
                                break;
                        }
                        //place ship in grid
                        own_grid.put(j-1,i, c);
                        //call ship status
                        for (int hit = 3; hit < temp.length; hit++) {
                            if (temp[hit].equals("0")) c.shoot(hit - 3);
                        }
                    }
                    j++;
                }
                i++;
            }
        } catch (Exception e) {
            return null;
        }
        return own_grid;
    }

    public static Grid2D create_foegrid(String[] foegrid, int bound) {
        Grid2D foe_grid = new Grid2D(bound);
        try {
            int i = 0;
            while (i < foegrid.length) {                                   //Schleife über jede Zeile der Datei
                String[] col = foegrid[i].split("\\|");

                int j = 1;
                while (j < col.length) {
                    if (col[j].equals("0")) {
                        foe_grid.put(j-1, i, new FoeGridShootObject(0));
                    } else if (col[j].equals("1")) {
                        foe_grid.put(j-1, i, new FoeGridShootObject(1));          //*************Anzeigen ob gegnerisches virtuelles Schiff getroffen
                    } else if (col[j].equals("2")) {
                        foe_grid.put(j-1, i, new FoeGridShootObject(2));
                    } else if (col[j].equals("null") || col[j].equals("")) {
                        break;
                    }
                    j++;
                }
                i++;
            }
        } catch (Exception e) {
            return null;
        }
        return foe_grid;
    }
}