package Doctrina.Rendering;

import Doctrina.Physics.Position;
import Doctrina.Physics.Size;

public class Minimap{
    private static Minimap instance;
    private Position position;
    private Size size;

    private Minimap(){
        size = new Size(32, 32);
        position = new Position(RenderingEngine.getInstance().getScreen().getWidth() / 2 + size.getWidth(), RenderingEngine.getInstance().getScreen().getHeight() / 2 - size.getHeight());
    }

    public static Minimap getInstance(){
        if (instance == null) {
            instance = new Minimap();
        }
        return instance;
    }
}
