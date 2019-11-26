package Logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Load {

    private BufferedReader in = null;

    public Load(){}

    public boolean load(String adress){
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
            System.out.println(in.readLine());

            if(create_owngrid(owngrid, bound) && create_foegrid(foegrid, bound)){
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


    public boolean create_owngrid(String[] owngrid, int bound){
        Grid own_grid = new Grid(bound);
        return true;
    }

    public boolean create_foegrid(String[] foegrid, int bound){
        Grid foe_grid = new Grid(bound);
        return true;
    }
}
