package Logic;

import java.io.*;

import Control.Konsolenanwendung;

public class Save {

    private static PrintWriter pWriter;

    public Save(String filename, Grid2D a, Grid2D b){
        pWriter = null;

        try {
            pWriter = new PrintWriter((new BufferedWriter((new FileWriter("src/main/java/logic/SaveGames/" + filename + ".txt")))));
            pWriter.write("SIZE\n" + a.getBound() + "\n");
            pWriter.write("OWNGRID\n" + a.toString());
            pWriter.write("OTHERGRID\n" + b.toString());

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
