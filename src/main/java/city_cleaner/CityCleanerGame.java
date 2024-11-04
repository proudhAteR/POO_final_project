package city_cleaner;

import java.awt.Color;

import Doctrina.Rendering.Canvas;
import Doctrina.Rendering.RenderingEngine;
import Doctrina.Core.Game;

public class CityCleanerGame extends Game {
    private GamePad gamePad;
    private Player player;

    // private World world;
    @Override
    protected void initialize() {
        gamePad = new GamePad();
        player = new Player(gamePad, Color.MAGENTA);
        RenderingEngine.getInstance().getScreen().fullscreen();
        RenderingEngine.getInstance().getScreen().hideCursor();
        camera.focusOn(player);
    }

    @Override
    protected void update() {
        if (gamePad.isQuitPressed()) {
            stop();
        }
        player.update();
        camera.update();

    }

    @Override
    protected void draw(Canvas canvas) {
        canvas.drawBlueScreen();
        player.draw(canvas);
        canvas.drawString(String.valueOf(camera.getPosition().getY()), 0, 48, Color.WHITE);
        canvas.drawString(String.valueOf(camera.getPosition().getX()), 120, 48, Color.WHITE);
    }

}
