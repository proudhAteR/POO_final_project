package Doctrina.Entities.Properties;

import Doctrina.Entities.StaticEntity;
import Doctrina.Physics.Position;
import Doctrina.Rendering.Canvas;
import java.awt.*;

public class Step extends StaticEntity{
    Position position;
    StaticEntity entity;

    public Step(Position position, StaticEntity entity) {
        this.position = position;
        this.entity = entity;
    }
    public Position getPosition() {
        return position;
    }
    public void placeStep(Canvas canvas) {
        canvas.drawRectangle(getBounds(), Color.PINK);
    }

    public Rectangle getBounds() {
        return new Rectangle(position.getX() + entity.getWidth() /2, position.getY() + entity.getHeight() /2, 6, 6);
    }
    @Override
    public void draw(Canvas canvas) {
        
    }
    @Override
    public void load() {
        
    }
}
