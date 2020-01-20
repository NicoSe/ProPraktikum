package GUI;
/*
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
*/
import Logic.OptionsHandler;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;


public class Helpers {
    /**
    *play Sound effects
    *musictype can be 0(music) and 1(SFX)
    */
    public static void playSFX(String filepath, int musicType){
        new Thread(() -> {
            try{
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(MainFrame.class.getResourceAsStream(filepath));
                clip.open(inputStream);

                FloatControl volControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                if(musicType == 0){
                    float vol = (float) OptionsHandler.getMusicVolume()/100;
                    vol = 80 * vol;
                    vol = -80 + vol;
                    volControl.setValue((float) vol);
                }
                else if(musicType == 1){

                    float vol = (float) OptionsHandler.getSFXVolume()/100;
                    vol = 80 * vol;
                    vol = -80 + vol;
                    volControl.setValue(vol);
                }
                else return;
                clip.start();
            }catch(Exception e){
                System.err.println(e.getMessage());
            }
        }).start();
    }






}
