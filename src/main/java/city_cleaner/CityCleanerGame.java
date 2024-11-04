package city_cleaner;

import java.awt.Color;

import Doctrina.Rendering.Canvas;
import Doctrina.Rendering.RenderingEngine;
import Doctrina.Core.Camera;
import Doctrina.Core.Game;
import Doctrina.Physics.Position;

public class CityCleanerGame extends Game {
    private GamePad gamePad;
    private Player player;

    // private World world;
    @Override
    protected void initialize() {
        gamePad = new GamePad();
        player = new Player(gamePad, Color.MAGENTA);
        camera = new Camera(new Position(0, 0));
        RenderingEngine.getInstance().getScreen().fullscreen();
        RenderingEngine.getInstance().getScreen().hideCursor();
    }

    @Override
    protected void update() {
        if (gamePad.isQuitPressed()) {
            stop();
        }
        player.update();
        camera.update(player);

    }

    @Override
    protected void draw(Canvas canvas) {
        canvas.drawBlueScreen();
        player.draw(canvas);
    }

}
