package Doctrina.Core;

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
