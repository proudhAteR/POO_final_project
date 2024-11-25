package Doctrina.Rendering;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;

import Doctrina.Entities.StaticEntity;
import Doctrina.Physics.Position;

public class Canvas {
    private final Graphics2D graphics;
    private final Camera camera;

    public Canvas(Graphics2D graphics, Camera camera) {
        this.graphics = graphics;
        this.camera = camera;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setFont(new Font("Kenney Future Narrow", Font.PLAIN, 25));
    }

    public void drawString(String text, int x, int y, Paint paint) {
        graphics.setPaint(paint);
        graphics.drawString(text, x, y);
    }

    public void drawCircle(int x, int y, int height, int width) {
        graphics.setPaint(new Color(0, 255, 255, 128));
        graphics.fillOval(x - camera.getPosition().getX(), y - camera.getPosition().getY(), width, height);
    }

    public void drawCircle(int x, int y, int height, int width, Color color) {
        graphics.setPaint(color);
        graphics.fillOval(x - camera.getPosition().getX(), y - camera.getPosition().getY(), width, height);
    }

    public void drawCircle(int x, int y, int height, int width, RadialGradientPaint gradientPaint) {
        graphics.setPaint(gradientPaint);
        graphics.fillOval(x - camera.getPosition().getX(), y - camera.getPosition().getY(), width, height);
    }

    public void drawRectangle(int x, int y, int width, int height, Paint paint) {
        graphics.setPaint(paint);
        graphics.fillRect(x - camera.getPosition().getX(), y - camera.getPosition().getY(), width, height);
    }

    public void clip(Ellipse2D bounds) {
        GeneralPath clipPath = new GeneralPath();
        clipPath.append(new Ellipse2D.Double(bounds.getX() - camera.getPosition().getX(),
                bounds.getY() - camera.getPosition().getY(), bounds.getWidth(), bounds.getHeight()), true);
        graphics.clip(clipPath);
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

    public void drawScreen(Color color) {

        drawRectangle(camera.getPosition().getX(), camera.getPosition().getY(),
                RenderingEngine.getInstance().getScreen().getWidth(),
                RenderingEngine.getInstance().getScreen().getHeight(), color);
    }

    public void applyShaders(Ellipse2D bounds) {
        int screenWidth = RenderingEngine.getInstance().getScreen().getWidth();
        int screenHeight = RenderingEngine.getInstance().getScreen().getHeight();
        int diagonal = (int) Math.sqrt(Math.pow(screenWidth, 2) + Math.pow(screenHeight, 2));
        int diameter = (int) diagonal / 2;

        int sightCenterX = (int) bounds.getCenterX();
        int sightCenterY = (int) bounds.getCenterY();
        int sightWidth = (int) bounds.getWidth();

        RadialGradientPaint radialGradient = new RadialGradientPaint(
                new Point(sightCenterX - camera.getPosition().getX(), sightCenterY - camera.getPosition().getY()),
                (sightWidth / 2),
                new float[] { 0f, 1f },
                new Color[] { new Color(0, 0, 0, 0), new Color(0, 0, 0, 1f) });

        drawCircle(
                sightCenterX - (diameter / 2),
                sightCenterY - (diameter / 2),
                diameter,
                diameter,
                radialGradient);

    }

    public void drawImage(Image image, int x, int y) {
        graphics.drawImage(image, x - camera.getPosition().getX(), y - camera.getPosition().getY(), null);
    }

    public void drawImage(Image image, Position position) {
        drawImage(image, position.getX(), position.getY());
    }

    public void drawCircle(Ellipse2D bounds) {
        drawCircle((int) bounds.getX(), (int) bounds.getY(), (int) bounds.getHeight(), (int) bounds.getWidth());
    }
}
