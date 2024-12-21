package Doctrina.Core;

import Doctrina.Entities.StaticEntity;
import Doctrina.Physics.Position;
import Doctrina.Physics.Size;;

public class Camera {
    private static Camera instance;
    private Position position;
    private Size windowSize;
    private StaticEntity entity;

    private Camera() {
        this.position = new Position(0, 0);
        this.windowSize = RenderingEngine.getInstance().getScreen().getSize();
    }

    public static Camera getInstance() {
        if (instance == null) {
            instance = new Camera();
        }
        return instance;
    }

    public void update() {
        if (entity != null) {
            this.position.setX(entity.getX() - windowSize.getWidth() / 2);
            this.position.setY(entity.getY() - windowSize.getHeight() / 2);
        }
    }

    public Position getPosition() {
        return position;
    }

    public Size getWindowSize() {
        return windowSize;
    }

    public void focusOn(StaticEntity entity) {
        this.entity = entity;
    }

    public StaticEntity getEntityOnFocus() {
        if (this.entity != null) {
            return entity;
        }
        return null;
    }

}
