package Doctrina.Core;

import Doctrina.Rendering.Canvas;
import Doctrina.Rendering.RenderingEngine;
import Doctrina.Physics.Size;;

public abstract class Game {
    private boolean playing = true;
    private final RenderingEngine renderingEngine;
    protected Camera camera;
    protected Size windowSize;
   

    public Game() {
        renderingEngine = RenderingEngine.getInstance();
        windowSize = renderingEngine.getScreen().getSize();
        camera = new Camera(windowSize);
    }

    public void stop() {
        playing = false;
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

    public Camera getCamera() {
        return camera;
    }

    protected abstract void initialize();

    protected abstract void update();

    protected abstract void draw(Canvas canvas);

}
