package Doctrina.Core;

import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import Doctrina.Rendering.ResourcesManager;
import city_cleaner.Sound.SFX;

public class MasteringEngine {
    private static MasteringEngine instance;
    private static HashMap<String, AudioInputStream> cache = new HashMap<>();

    public static MasteringEngine getInstance() {
        if (instance == null) {
            instance = new MasteringEngine();
        }
        return instance;
    }

    public void playContinuousTrack(String path, float volume) {
        try {
            Clip clip = prepareClip(path, volume);
            if (clip != null) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playSFX(SFX effect, float volume) {
        try {
            Clip clip = prepareClip(effect.getPath(), volume);
            if (clip != null) {
                clip.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Clip prepareClip(String path, float volume) throws Exception {
        Clip clip = AudioSystem.getClip();
        if (!cache.containsKey(path)) {
            cache.put(path, ResourcesManager.getInstance().getAudio(path));
        }
        clip.open(cache.get(path));
        configureVolume(clip, volume);
        return clip;
    }

    private void configureVolume(Clip clip, float volume) {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(volume);
    }

}
