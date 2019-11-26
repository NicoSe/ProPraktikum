package Logic;

import java.io.*;
import Control.Main;

public class Save {

    private static PrintWriter pWriter;

    public Save(){
        pWriter = null;
        String filename = String.valueOf(System.currentTimeMillis());

        try {
            pWriter = new PrintWriter((new BufferedWriter((new FileWriter("src/main/java/logic/SaveGames/" + filename + ".txt")))));
            pWriter.write("SIZE\n" + Main.own_grid.getBound() + "\n");
            pWriter.write("OWNGRID\n" + Main.own_grid.toString());
            pWriter.write("OTHERGRID\n" + Main.foe_grid.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if (pWriter != null){
                pWriter.flush();
                pWriter.close();
            }
        }

    }


}
