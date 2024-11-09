package city_cleaner;

import java.awt.Image;

import Doctrina.Physics.Position;
import Doctrina.Rendering.Canvas;
import Doctrina.Rendering.ResourcesManager;

public class World {
    private static final String MAP_PATH = "images/map/test.png";
    private Image background;
    private ResourcesManager loader;
    Position position ;

    public World(){
        
        position = new Position(0, 0);
        loader = new ResourcesManager();
    }

    public void draw(Canvas canvas) {
        canvas.drawImage(background, position);
    }
    public Position getPosition() {
        return position;
    }
    public void load() {
        background = loader.getImage(MAP_PATH);
    }

}