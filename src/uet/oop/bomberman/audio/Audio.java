package uet.oop.bomberman.audio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Audio {
    public static void playSound(String soundFile, int loop) {
        try {
            File f = new File("./" + soundFile);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(f.getAbsolutePath()));
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
            clip.loop(loop);
        } catch (Exception e) {
            System.out.println("Error");
        }
    }
}

