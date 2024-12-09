package Doctrina.Rendering.UI;

import java.awt.Color;

import Doctrina.Physics.Position;
import Doctrina.Physics.Size;
import Doctrina.Rendering.Canvas;

public class Bar extends UIComponent {
    protected Size size;
    protected Color color;

    public Bar(Position pos, Size size, Color color) {
        super(pos);
        this.size = size;
        this.color = color;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRectangle(pos.getX() + camera.getPosition().getX(), pos.getY() + camera.getPosition().getY(),
                size.getWidth(),
                size.getHeight(), color);
    }

    @Override
    public void update() {
        
    }
    public Size getSize() {
        return size;
    }

}
