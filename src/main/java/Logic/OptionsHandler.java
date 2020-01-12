package Logic;

import java.io.*;

public class OptionsHandler {
    private static int musicVolume;
    private static int SFXVolume;
    private static boolean fullscreenMode;

    public OptionsHandler(){
        BufferedReader in = null;
        File file = new File("src/main/resources/settings/settings.txt");
        if (!file.exists() || !file.canRead()) {
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
            pWriter = new PrintWriter((new BufferedWriter((new FileWriter("src/main/resources/settings/settings.txt")))));
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
