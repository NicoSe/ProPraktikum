package Logic;

import java.io.*;

import Control.Konsolenanwendung;

public class Save {

    private static PrintWriter pWriter;

    public Save(String filename){
        pWriter = null;

        try {
            pWriter = new PrintWriter((new BufferedWriter((new FileWriter("src/main/java/logic/SaveGames/" + filename + ".txt")))));
            pWriter.write("SIZE\n" + Konsolenanwendung.a.getBound() + "\n");
            pWriter.write("OWNGRID\n" + Konsolenanwendung.a.toString());
            pWriter.write("OTHERGRID\n" + Konsolenanwendung.a.toString());

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
