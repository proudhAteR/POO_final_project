package city_cleaner;

import java.awt.Image;

import Doctrina.Rendering.Canvas;

public class World {
    private Image background;

    public void draw(Canvas canvas) {
        canvas.drawImage(background, 0, -64);
    }

    public void load() {
        //background = manager.getImage();
    }
}
