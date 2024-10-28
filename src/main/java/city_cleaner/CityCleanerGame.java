package city_cleaner;

import java.awt.Color;

import Doctrina.Rendering.Canvas;
import Doctrina.Rendering.RenderingEngine;
import Doctrina.Core.Game;

public class CityCleanerGame extends Game{
    private GamePad gamePad;
    private Player player;
    //private World world;
    @Override
    protected void initialize() {
        gamePad = new GamePad();
        player = new Player(gamePad, Color.MAGENTA);
        RenderingEngine.getInstance().getScreen().fullscreen();
        RenderingEngine.getInstance().getScreen().hideCursor();
    }

    @Override
    protected void update() {
        if (gamePad.isQuitPressed()) {
            stop();
        }
        player.update(); 

    }

    @Override
    protected void draw(Canvas canvas) {
        canvas.drawBlueScreen();
        player.draw(canvas);
    }

}
