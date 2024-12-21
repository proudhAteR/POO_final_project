package Doctrina.Entities.Properties;

import Doctrina.Entities.StaticEntity;
import Doctrina.Physics.Position;
import Doctrina.Rendering.Canvas;
import java.awt.*;

public class Step {
    private Position position;
    private StaticEntity entity;
    private Color color;

    public Step(Position position, StaticEntity entity, Color color) {
        this.position = position;
        this.entity = entity;
    }

    public Position getPosition() {
        return position;
    }

    public void placeStep(Canvas canvas) {
        if (!entity.died()) {
            canvas.drawRectangle(getBounds(), color);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(position.getX() + entity.getWidth() / 2, position.getY() + entity.getHeight() / 2, 6, 6);
    }

}
