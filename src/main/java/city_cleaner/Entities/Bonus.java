package city_cleaner.Entities;

import java.awt.Color;
import java.awt.Rectangle;

import Doctrina.Physics.Position;
import Doctrina.Rendering.Canvas;

public class Bonus {
    private Position position;
    private boolean isFound;
    private int value;

    public Bonus(Position position, int value) {
        this.isFound = false;
        this.position = position;
        this.value = value;
    }

    public Position getPosition() {
        return position;
    }

    public void place(Canvas canvas) {
        canvas.drawRectangle(getBounds(), Color.GREEN);
    }

    public Rectangle getBounds() {
        return new Rectangle(position.getX(), position.getY(), 6, 6);
    }

    public boolean isFound() {
        return isFound;
    }

    public void setFound(boolean isFound) {
        this.isFound = isFound;
    }
    public int getValue() {
        return value;
    }
}
