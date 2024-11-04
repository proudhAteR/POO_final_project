package Doctrina.Rendering;

import java.awt.*;

import Doctrina.Entities.StaticEntity;
import Doctrina.Physics.Position;
import Doctrina.Core.Camera;

public class Canvas {
    private Graphics2D graphics;
    private Camera camera;

    public Canvas(Graphics2D graphics, Camera camera) {
        this.graphics = graphics;
        this.camera = camera;
        graphics.setFont(new Font("Roboto", Font.PLAIN, 50));
    }

    public void drawString(String text, int x, int y, Paint paint) {
        graphics.setPaint(paint);
        graphics.drawString(text, x, y);
    }

    public void drawCircle(int x, int y, int radius, Paint paint) {
        graphics.setPaint(paint);
        graphics.fillOval(x, y, radius * 2, radius * 2);
    }

    public void drawRectangle(int x, int y, int width, int height, Paint paint) {
        graphics.setPaint(paint);
        graphics.fillRect(x, y, width, height);
    }

    public void drawRectangle(StaticEntity entity, Paint paint) {
        drawRectangle(entity.getX(), entity.getY(), entity.getWidth(), entity.getHeight(), paint);
    }

    public void drawRectangle(StaticEntity entity) {
        drawRectangle(entity, entity.getColor());
    }

    public void drawRectangle(Rectangle rect, Paint paint) {
        drawRectangle(rect.x, rect.y, rect.width, rect.height, paint);
    }

    public void drawBlueScreen() {
        int width = 800;
        int height = 600;
        drawRectangle(0, 0, width, height, Color.BLUE);
    }

    public void drawImage(Image image, int x, int y) {
        graphics.drawImage(image, x - camera.getPosition().getX(), y -  camera.getPosition().getY(), null);
    }

    public void drawImage(Image image, Position position) {
        graphics.drawImage(image, position.getX() , position.getY(), null);
    }
}
