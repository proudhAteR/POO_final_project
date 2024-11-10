package Doctrina.Entities.Properties;


import Doctrina.Entities.StaticEntity;
import Doctrina.Physics.Position;
import Doctrina.Physics.Size;
import Doctrina.Rendering.Canvas;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Sight {
    Size size;
    Position position;
    StaticEntity entity;

    public Sight(Size size,StaticEntity entity) {
        this.size = size;
        this.entity = entity;
        this.position = entity.getPosition();
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle(getBounds(), entity);
    }

    public boolean intersects(Rectangle bounds) {
        return this.getBounds().intersects(bounds);
    }

    public Ellipse2D getBounds() {
        return new Ellipse2D.Double(
            position.getX() - size.getWidth() / 2 ,
            position.getY() - size.getHeight() / 2 ,
            size.getWidth() + entity.getWidth(),
            size.getHeight() + entity.getHeight()
        );    
    }
}
