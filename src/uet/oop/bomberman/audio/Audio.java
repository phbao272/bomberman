package uet.oop.bomberman.audio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Audio {
    public void playSound(String soundFile) {
        try {
            File f = new File("./" + soundFile);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {

        }

    }

    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
//        File f = new File("src/uet/oop/bomberman/audio/dead.wav");
//        AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
//        Clip clip = AudioSystem.getClip();
//        clip.open(audioIn);
//        clip.start();
    }
}
