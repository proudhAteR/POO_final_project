package Doctrina.Core;

import java.awt.Color;

import Doctrina.Entities.StaticEntity;
import Doctrina.Rendering.Canvas;
import Doctrina.Rendering.RenderingEngine;

public abstract class Game {
    private boolean playing = true;
    private final RenderingEngine renderingEngine;
    protected Camera camera;

    public Game() {
        renderingEngine = RenderingEngine.getInstance();
        camera = renderingEngine.getCamera();
    }

    public void stop() {
        playing = false;
    }

    protected void drawCamPosition(Canvas canvas, StaticEntity e) {
        canvas.drawString(String.valueOf(e.getPosition().getY() - renderingEngine.getCamera().getPosition().getY()), 0, 96, Color.WHITE);
        canvas.drawString(String.valueOf(e.getPosition().getX() - renderingEngine.getCamera().getPosition().getX()), 136, 96, Color.WHITE);
        canvas.drawString(String.valueOf(renderingEngine.getCamera().getPosition().getY()), 0, 48, Color.WHITE);
        canvas.drawString(String.valueOf(renderingEngine.getCamera().getPosition().getX()), 136, 48, Color.WHITE);
    }

    public final void start() {
        initialize();
        run();
    }

    private void run() {
        renderingEngine.start();
        GameTime gameTime = new GameTime();
        while (playing) {
            update();
            draw(renderingEngine.buildCanvas());
            renderingEngine.drawOnScreen();
            gameTime.synchronize();
        }
        renderingEngine.stop();
    }

    protected abstract void initialize();

    protected abstract void update();

    protected abstract void draw(Canvas canvas);

}
