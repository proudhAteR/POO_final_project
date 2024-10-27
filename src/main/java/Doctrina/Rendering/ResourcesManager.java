package Doctrina.Rendering;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import Doctrina.Entities.StaticEntity;

import java.awt.Image;
import java.awt.image.BufferedImage;

public class ResourcesManager {

    public Image getImage(String imagePath) {
        Image image;
        try {
            image = ImageIO.read(
                    getClass().getClassLoader().getResourceAsStream(imagePath));
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
            return null;
        }
        return image;
    }

    public Image[] extractFrames(int frameCount, int xOffset, int yOffset, StaticEntity entity, BufferedImage image) {
        Image[] frames = new Image[frameCount];
        for (int i = 0; i < frameCount; i++) {
            frames[i] = image.getSubimage(i * xOffset, yOffset, entity.getWidth(), entity.getHeight());
        }
        return frames;
    }

    public void playContinuosTrack(String soundPath) {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                    this.getClass().getClassLoader().getResourceAsStream(soundPath));
            clip.open(audioInputStream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playSFX(String SFXPath) {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                    this.getClass().getClassLoader().getResourceAsStream(SFXPath));
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
