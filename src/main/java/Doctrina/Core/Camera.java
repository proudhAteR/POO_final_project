package Doctrina.Core;

import Doctrina.Entities.StaticEntity;
import Doctrina.Physics.Position;

public class Camera {
    Position position;

    public Camera(Position position) {
        this.position = position;
    }

    public void update(StaticEntity entity) {
        
    }

    public Position getPosition() {
        return position;
    }
    public void setPosition(Position position) {
        this.position = position;
    }
}
