package Doctrina.Entities;


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
        this.position = entity.position;
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle(getBounds(), entity);
    }

    public boolean intersects(Rectangle bounds) {
        return this.getBounds().intersects(bounds);
    }

    private Ellipse2D getBounds() {
        return new Ellipse2D.Double(
            position.getX() - size.getWidth() / 2 ,
            position.getY() - size.getHeight() / 2 ,
            size.getWidth() + entity.getWidth(),
            size.getHeight() + entity.getHeight()
        );    
    }
}
