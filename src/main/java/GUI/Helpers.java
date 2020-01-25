package GUI;
/*
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
*/
import Logic.OptionsHandler;

import javax.sound.sampled.*;


public class Helpers {
    /**
    *play Sound effects
    *musictype can be 0(music) and 1(SFX)
    */

    private static Clip clip;
    public static AudioRunnable run;

    public static void playSFX(String filepath, int musicType) {
        run = new AudioRunnable(){};
        new Thread(run) {
            @Override
            public void run() {
                try{
                    clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(MainFrame.class.getResource(filepath));
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
                    System.out.println("lol");
                    e.printStackTrace();
                }
            }

            public void stopMusic(){
                clip.stop();
            }
        }.start();
    }
}
