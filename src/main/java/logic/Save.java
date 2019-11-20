package logic;

import java.io.*;

public class Save {

    private static PrintWriter pWriter;

    public Save(){
        pWriter = null;
        String filename = String.valueOf(System.currentTimeMillis());
        //Main.s.sendmsg(filename);

        try {
            pWriter = new PrintWriter((new BufferedWriter((new FileWriter("src/main/java/logic/SaveGames/" + filename + ".txt")))));
            pWriter.write("hi, du da.\n");

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
