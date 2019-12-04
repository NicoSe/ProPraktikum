package GUI;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class Helpers {

    //play Sound effects
    public static void playSFX(String filepath){
        try{
           Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(MainFrame.class.getResourceAsStream(filepath));
            clip.open(inputStream);
            clip.start();
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }





}
