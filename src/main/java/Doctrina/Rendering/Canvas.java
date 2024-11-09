package Doctrina.Rendering;

import java.awt.*;
import java.awt.geom.Ellipse2D;

import Doctrina.Entities.StaticEntity;
import Doctrina.Physics.Position;
import Doctrina.Core.Camera;

public class Canvas {
    private final Graphics2D graphics;
    private final Camera camera;

    public Canvas(Graphics2D graphics, Camera camera) {
        this.graphics = graphics;
        this.camera = camera;
        graphics.setFont(new Font("Roboto", Font.PLAIN, 50));
    }

    public void drawString(String text, int x, int y, Paint paint) {
        graphics.setPaint(paint);
        graphics.drawString(text, x, y);
    }

    public void drawCircle(int x, int y, int height, int width) {
        graphics.setPaint(new Color(0, 255, 255, 128));
        graphics.fillOval(x - camera.getPosition().getX(), y  - camera.getPosition().getY(), width, height);
    }

    public void drawRectangle(int x, int y, int width, int height, Paint paint) {
        graphics.setPaint(paint);
        graphics.fillRect(x - camera.getPosition().getX(), y - camera.getPosition().getY(), width, height);
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
        drawRectangle(camera.getPosition().getX(), camera.getPosition().getY(), width, height, Color.BLUE);
    }

    public void drawImage(Image image, int x, int y) {
        graphics.drawImage(image, x - camera.getPosition().getX(), y - camera.getPosition().getY(), null);
    }

    public void drawImage(Image image, Position position) {
        drawImage(image, position.getX(), position.getY());
    }

    public void drawCircle(Ellipse2D bounds, StaticEntity entity) {
        drawCircle((int) bounds.getX() , (int) bounds.getY(), (int) bounds.getHeight(), (int) bounds.getWidth());
    }
}
