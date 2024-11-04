package Doctrina.Core;

import Doctrina.Entities.StaticEntity;
import Doctrina.Physics.Position;
import Doctrina.Physics.Size;;

public class Camera {
    Position position;
    Size windowSize;
    StaticEntity entity;

    public Camera(Size windowSize) {
        this.position = new Position(0, 0);
        this.windowSize = windowSize;
    }

    public Camera(Size windowSize, StaticEntity e) {
        focusOn(e);
        this.windowSize = windowSize;
    }

    public void update() {
        this.position.setX(entity.getX() - windowSize.getWidth() / 2);
        this.position.setY(entity.getY() - windowSize.getHeight() / 2);
    }

    public Position getPosition() {
        return position;
    }

    public void focusOn(StaticEntity entity) {
        this.entity = entity;
    }
}
