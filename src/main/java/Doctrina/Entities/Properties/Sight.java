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
        this.size = new Size(0, 0);
    }

    public Sight(Sight s) {
        this.entity = s.entity;
        this.size = s.size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle(getBounds());
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

    @Override
    public String toString() {
        Ellipse2D bounds = this.getBounds();
        return "X = " + bounds.getX() + "," + "Y = " + bounds.getY() + "," + "W = " + bounds.getWidth() + "," + "H = "
                + bounds.getHeight();
    }

    public Size getSize() {
        return size;
    }

}
