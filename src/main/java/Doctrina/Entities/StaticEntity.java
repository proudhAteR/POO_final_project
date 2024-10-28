package Doctrina.Entities;

import java.awt.Color;
import java.awt.Rectangle;

import Doctrina.Physics.Position;
import Doctrina.Physics.Size;
import Doctrina.Rendering.Canvas;

public abstract class StaticEntity{
    protected Position position;
    protected Size size;
    protected Color color;

    public abstract void draw(Canvas canvas);

    public void teleport(int x, int y) {
        this.position.setX(x);
        this.position.setY(y);
    }

    public void teleport(Position position){
        teleport(position.getX(), position.getY());
    }
    public Position getPosition() {
        return position;
    }

    public abstract void load();

    public void setDimension(Size size) {
        this.size.setHeight(size.getHeight());
        this.size.setWidth(size.getWidth());
    }

    public boolean collided(StaticEntity entity) {
        return this.getBounds().intersects(entity.getBounds());
    }

    protected Rectangle getBounds() {
        return new Rectangle(position.getX(), position.getY(), size.getWidth(), size.getHeight());
    }

    public void drawHitBox(Canvas canvas, Color color) {
        Rectangle rect = getBounds();
        canvas.drawRectangle(rect, color);
    }
    public boolean canMove() {
        return this instanceof MovableEntity;
    }

    public Color getColor() {
        return this.color;
    }

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }

    public int getWidth() {
        return size.getWidth();
    }

    public int getHeight() {
        return size.getHeight();
    }

}
