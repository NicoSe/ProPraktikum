package Logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Load {

    public static Grid2D[] load(String adress) {
        BufferedReader in = null;
        File file = new File("src/main/java/logic/SaveGames/" + adress + ".txt");
        if (!file.exists() || !file.canRead()) {
            return null;
        }

        try {
            in = new BufferedReader(new FileReader(file));
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

    // Testet ob der Dateipfad zu finden und zu öffnen ist
    public boolean test_load(String adress) {
        BufferedReader in = null;
        File file = new File("src/main/java/logic/SaveGames/" + adress + ".txt");
        if (!file.exists() || !file.canRead()) {
            return false;
        }
        return true;
    }


    // erstellt das Grid für den Spieler
    public static Grid2D create_owngrid(String[] owngrid, int bound) {
        Grid2D own_grid = new Grid2D(bound);
        try {
            new Thread(new Runnable() {
                public void run() {
                    boolean[] ids = new boolean[64];

                    //Grid2D own_grid = new Grid2D(bound);
                    Ship.id_counter = 0;

                    //erst Schiffe platzieren
                    int i = 0;
                    while (i < owngrid.length) {                                   //Schleife über jede Zeile der Datei
                        String[] col = owngrid[i].split("\\|");             // col alle von | getrennten Inhalte
                        int j = 1;
                        while (j < col.length) {                                   //Schleife über jedes Element einer Zeile
                            String[] temp = col[j].split(",");              //temp beinhaltet jedes Element in |...|

                            //Wasser einlesen
                            if (temp[0].equals("w")) {
                                j++;
                                continue;

                                //Schiffe erstellen
                            } else if (ids[Integer.parseInt(temp[1])] == false) {
                                ids[Integer.parseInt(temp[0])] = true;

                                //call ship status
                                boolean[] hitbox = new boolean[Integer.parseInt(temp[1])];
                                for (int hit = 0; hit < hitbox.length; hit++) {
                                    hitbox[hit] = true;
                                }
                                for (int hit = 3; hit < temp.length; hit++) {
                                    if (temp[hit] == "0") {
                                        hitbox[hit - 3] = false;
                                    }
                                }

                                //create ship
                                Character c = new Ship(Integer.parseInt(temp[1]), hitbox);

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
                                own_grid.put(j-1, i, c);
                            }
                            j++;
                        }
                        i++;
                    }

                    // jetzt Wasser platzieren
                    i = 0;
                    while (i < owngrid.length) {                                   //Schleife über jede Zeile der Datei
                        String[] col = owngrid[i].split("\\|");             // col alle von | getrennten Inhalte
                        int j = 1;
                        while (j < col.length) {                                   //Schleife über jedes Element einer Zeile
                            String[] temp = col[j].split(",");              //temp beinhaltet jedes Element in |...|

                            //Wasser einlesen
                            if (temp[0].equals("w")) {
                                if (Integer.parseInt(temp[1]) == 1) own_grid.put(j-1, i, new Water(true));
                                else if (Integer.parseInt(temp[1]) == 0) own_grid.put(j-1, i, new Water(false));
                                j++;
                                continue;

                                //Schiffe erstellen
                            } else if (ids[Integer.parseInt(temp[1])] == false) {
                                j++;
                                continue;
                            }
                            j++;
                        }
                        i++;
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return own_grid;
    }

    //erstellt das Grid für den Gegner
    public static Grid2D create_foegrid(String[] foegrid, int bound) {
        Grid2D foe_grid = new Grid2D(bound);
        try {
            new Thread(new Runnable() {
                public void run() {
                    //Grid2D foe_grid = new Grid2D(bound);

                    int i = 0;
                    while (i < foegrid.length) {                                   //Schleife über jede Zeile der Datei
                        String[] col = foegrid[i].split("\\|");

                        int j = 1;
                        while (j < col.length) {
                            if (col[j].equals("0")) {
                                foe_grid.put(j-1,i,new FoeGridShootObject(0));
                            } else if (col[j].equals("1")) {
                                foe_grid.put(j-1,i,new FoeGridShootObject(1));
                            } else if (col[j].equals("2")) {
                                foe_grid.put(j-1,i,new FoeGridShootObject(2));
                            }
                            j++;
                        }
                        i++;
                    }
                }
            }).start();
        } catch (Exception e) {
            return null;
        }
        System.out.println(foe_grid);
        return foe_grid;
    }
}
