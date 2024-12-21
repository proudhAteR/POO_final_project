package Doctrina.Rendering;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import Doctrina.Entities.StaticEntity;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class ResourcesManager {

    private static ResourcesManager instance;

    public static ResourcesManager getInstance() {
        if (instance == null) {
            instance = new ResourcesManager();
        }
        return instance;
    }

    public Image getImage(String imagePath) {
        Image image;
        try {
            image = ImageIO.read(
                    Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(imagePath)));
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

    public AudioInputStream getAudio(String soundPath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                    Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(soundPath)));

            return audioInputStream;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String readFile(String filePath) throws FileNotFoundException, IOException, URISyntaxException {
        URL stream = ClassLoader.getSystemResource(filePath);
        Path path = Path.of(stream.toURI());
        String json = Files.readString(path);

        return json;
    }
}
