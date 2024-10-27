package Doctrina.Entities;

import java.awt.Color;
import java.awt.Rectangle;

import Doctrina.Rendering.Canvas;

public abstract class StaticEntity{
    protected int x;
    protected int y;
    protected int height;
    protected int width;
    protected Color color;

    public abstract void draw(Canvas canvas);

    public void teleport(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract void load();

    public void setDimension(int width, int height) {
        this.height = height;
        this.width = width;
    }

    public boolean collided(StaticEntity entity) {
        return this.getBounds().intersects(entity.getBounds());
    }

    protected Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
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
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
