package logic;

import java.io.*;
import Steuerung.Main;

public class Save {

    private static PrintWriter pWriter;

    public Save(){
        pWriter = null;
        String filename = String.valueOf(System.currentTimeMillis());


        try {
            pWriter = new PrintWriter((new BufferedWriter((new FileWriter("src/main/java/logic/SaveGames/" + filename +"-" + Main.grid.getBound() + ".txt")))));
            pWriter.write(Main.grid.toString());

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
