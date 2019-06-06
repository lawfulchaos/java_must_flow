package kerbin;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Sound {
    public static void playSound(String name) {
        try {
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(name));
            Clip clip = AudioSystem.getClip();
            clip.open(inputStream);
            clip.loop(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
