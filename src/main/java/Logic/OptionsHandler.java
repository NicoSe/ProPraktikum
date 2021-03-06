package Logic;

import java.io.*;

/// behandelt das Optionsmenue, alle Werte sind veränderbar und abrufbar
public class OptionsHandler {
    private static int musicVolume;
    private static int SFXVolume;
    private static boolean fullscreenMode;

    public OptionsHandler(){
        BufferedReader in = null;
        File file = new File("./settings.txt");
        if (!file.exists() || !file.canRead()) {
            musicVolume = 100;
            SFXVolume = 100;
            fullscreenMode = false;
            saveSettings();
            return;
        }
        try{
            in = new BufferedReader(new FileReader(file));

            in.readLine();
            int loadedValue = Integer.parseInt(in.readLine());
            if(loadedValue >= 0 && loadedValue <=100){
                musicVolume = loadedValue;
            }
            else{
                musicVolume = 100;
            }

            in.readLine();
            loadedValue = Integer.parseInt(in.readLine());
            if(loadedValue >= 0 && loadedValue <=100){
                SFXVolume = loadedValue;
            }
            else {
                SFXVolume = 100;
            }

            in.readLine();
            String loadedValueB = in.readLine();
            if(loadedValueB.equals("true")){
                fullscreenMode = true;
            }
            else if(loadedValueB.equals("false")){
                fullscreenMode = false;
            }
            else{
                fullscreenMode = false;
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void changeMusicVolume(int newvalue){
        musicVolume = newvalue;
        saveSettings();
    }

    public static int getMusicVolume(){
        return musicVolume;
    }

    public static void changeSFXVolume(int newvalue){
        SFXVolume = newvalue;
        saveSettings();
    }

    public static int getSFXVolume(){
        return SFXVolume;
    }

    public static void changeFullscreenMode(boolean newvalue){
        fullscreenMode = newvalue;
        saveSettings();
    }

    public static boolean getFullscreenMode(){
        return fullscreenMode;
    }

    private static void saveSettings() {
        PrintWriter pWriter = null;

        try {
            pWriter = new PrintWriter((new BufferedWriter((new FileWriter("./settings.txt")))));
            pWriter.write("Music Value:\n" + musicVolume + "\n");
            pWriter.write("SFX Value:\n" + SFXVolume + "\n");
            pWriter.write("Fullscreen:\n" + fullscreenMode);

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
