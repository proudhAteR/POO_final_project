package city_cleaner;

import java.awt.Image;

import Doctrina.Rendering.Canvas;
import Doctrina.Rendering.ResourcesManager;

public class World {
    private static final String MAP_PATH = "images/demo.png";
    private Image background;
    private ResourcesManager loader = new ResourcesManager();

    public void draw(Canvas canvas) {
        canvas.drawImage(background, 0, -64);
    }

    public void load() {
        background = loader.getImage(MAP_PATH);
    }

}