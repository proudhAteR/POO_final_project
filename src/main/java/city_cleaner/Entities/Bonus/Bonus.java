package city_cleaner.Entities.Bonus;

import java.awt.Color;
import java.awt.Rectangle;

import Doctrina.Physics.Position;
import Doctrina.Rendering.Canvas;
import city_cleaner.Entities.Player;

public abstract class Bonus {
    protected Position position;
    protected boolean isFound;
    protected int value;
    protected Color color;

    protected Bonus(Position position, int value) {
        this.isFound = false;
        this.position = position;
        this.value = value;
        this.color = Color.gray;
    }

    public Position getPosition() {
        return position;
    }

    public void place(Canvas canvas) {
        canvas.drawRectangle(getBounds(), color);
    }

    public Rectangle getBounds() {
        return new Rectangle(position.getX(), position.getY(), 6, 6);
    }

    public boolean isFound() {
        return isFound;
    }

    public abstract void affect(Player player);

    public abstract void disaffect(Player player);

    public void setFound(boolean isFound) {
        this.isFound = isFound;
    }

    public int getValue() {
        return value;
    }
}
