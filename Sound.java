
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class Sound {
    private Clip clip;
    private final URL[] soundURL = new URL[30];

    public Sound() {
        soundURL[0] = getClass().getResource("SoundEffects/boomEffect.wav");
    }

    public void setFile(int i) {
        try {
            if (clip != null && clip.isOpen()) {
                clip.close(); // Close previous clip to avoid resource leak
            }
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void play() {
        if (clip != null) {
            clip.setFramePosition(0); // Rewind to the beginning
            clip.start();
        }
    }

    public synchronized void loop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public synchronized void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public void close() {
        if (clip != null && clip.isOpen()) {
            clip.close();
        }
    }
}

