package Logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Load {

    public Load(){
    }

    public static Grid2D[] load(String adress){
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
            while ((i < 5) && ((help = in.readLine()) != null)){
                owngrid[i] = help;
                i++;
            }
            in.readLine();

            help = null;
            i = 0;
            while ((i < 5) && ((help = in.readLine()) != null)){
                foegrid[i] = help;
                i++;
            }

            Grid2D[] grids = new Grid2D[1];
            grids[0] = create_owngrid(owngrid, bound);
            grids[1] =  create_foegrid(foegrid, bound);
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


    public boolean test_load(String adress){
        BufferedReader in = null;
        File file = new File("src/main/java/logic/SaveGames/" + adress + ".txt");
        if (!file.exists() || !file.canRead()) {
            return false;
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
            while ((i < 5) && ((help = in.readLine()) != null)){
                owngrid[i] = help;
                i++;
            }
            in.readLine();

            help = null;
            i = 0;
            while ((i < 5) && ((help = in.readLine()) != null)){
                foegrid[i] = help;
                i++;
            }

            if(create_owngrid_test(owngrid, bound) && create_foegrid_test(foegrid, bound)) {
                return true;
            }
        } catch (IOException e) {
            return false;
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                    return false;
                }
        }
        return false;
    }


    public static Grid2D create_owngrid(String[] owngrid, int bound){
        Grid2D own_grid = new Grid2D(bound);
        try {
            new Thread(new Runnable() {
                public void run() {
                    boolean[] ids = new boolean[64];

                    //Grid2D own_grid = new Grid2D(bound);
                    Ship.id = 0;

                    int i = 0;
                    while (i < owngrid.length) {                                   //Schleife über jede Zeile der Datei
                        String[] col = owngrid[i].split("\\|");

                        int j = 0;
                        while (j < col.length) {                                   //Schleife über jedes Element einer Zeile
                            String[] temp = col[j].split(",");
                            if (col[j].equals("null") || col[j].equals("")) {
                                j++;
                                continue;
                            } else if (col[j].equals("-1")) {                             //Wassertreffer realisieren
                                own_grid.shoot(i, (j % bound) - 1);
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
                                own_grid.put(i,(j % bound) - 1, c);
                                //call ship status
                                for (int hit = 3; hit < temp.length; hit++) {
                                    if (temp[hit].equals("0")) c.shoot(hit - 3);
                                }
                            }
                            j++;
                        }
                        i++;
                    }
                }
            }).start();
        }catch(Exception e){
            return null;
        }
        return own_grid;
    }

    public static Grid2D create_foegrid(String[] foegrid, int bound){
        Grid2D foe_grid = new Grid2D(bound);
        try {
            new Thread(new Runnable() {
                public void run() {
                    //Grid2D foe_grid = new Grid2D(bound);

                    int i = 0;
                    while (i < foegrid.length) {                                   //Schleife über jede Zeile der Datei
                        String[] col = foegrid[i].split("\\|");

                        int j = 0;
                        while (j < col.length) {
                            if (col[j].equals("-1")) {
                                foe_grid.shoot(i, (j % bound) - 1);
                            }
                            else if (col[j].equals(1)) {
                                //foe_grid.shoot((i*5)+(j%bound)-1, true);          //*************Anzeigen ob gegnerisches virtuelles Schiff getroffen
                            }
                            else if(col[j].equals("null") || col[j].equals("")){
                                j++;
                                continue;
                            }
                            j++;
                        }
                        i++;
                    }
                }
            }).start();
        }catch(Exception e){
            return null;
        }
        return foe_grid;
    }


    private static boolean create_owngrid_test(String[] owngrid, int bound) {
        boolean[] ids = new boolean[64];

        int i = 0;
        while(i < owngrid.length){                                   //Schleife über jede Zeile der Datei
            String[] col = owngrid[i].split("\\|");
            int j = 0;
            while(j < col.length){                                   //Schleife über jedes Element einer Zeile
                String[] temp = col[j].split(",");
                if(col[j].equals("null") || col[j].equals("")) {
                    j++;
                    continue;
                }
                else if(col[j].equals("-1")){                             //Wassertreffer realisieren
                }
                else if(ids[Integer.parseInt(temp[0])] == false){    //Schiffe erstellen

                    switch(temp[2]){
                        case "VERTICAL":
                        case "HORIZONTAL":
                            break;
                    }
                    for(int hit=3;hit<temp.length;hit++){
                        if (!(temp[hit].equals("1") || temp[hit].equals("0"))){
                            return false;
                        }
                    }
                }
                else{return false;}
                j++;
            }
            i++;
        }
        return true;
    }

    private static boolean create_foegrid_test(String[] foegrid, int bound) {
        int i = 0;
        while(i < foegrid.length){                                   //Schleife über jede Zeile der Datei
            String[] col = foegrid[i].split("\\|");

            int j = 0;
            while(j<col.length) {
                if (col[j].equals("-1")) {}
                else if(col[j].equals("1")){}
                else if(col[j].equals("null") || col[j].equals("")){
                    j++;
                    continue;
                }
                else{return false;}
                j++;
            }
            i++;
        }
        return true;
    }
}
