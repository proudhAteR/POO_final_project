package Doctrina.Entities.Properties;

import Doctrina.Entities.StaticEntity;
import Doctrina.Physics.Size;
import Doctrina.Rendering.Canvas;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Sight {
    Size size;
    StaticEntity entity;

    public Sight(StaticEntity entity) {
        this.entity = entity;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle(getBounds(), entity);
    }

    public boolean intersects(Rectangle bounds) {
        return this.getBounds().intersects(bounds);
    }

    public Ellipse2D getBounds() {
        return new Ellipse2D.Double(
                entity.getX() - size.getWidth() / 2,
                entity.getY() - size.getHeight() / 2,
                size.getWidth() + entity.getWidth(),
                size.getHeight() + entity.getHeight());
    }

    // !BUG : Size returns null sometimes (happens a lot more in debug mode)
}
