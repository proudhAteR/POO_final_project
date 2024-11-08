package city_cleaner;

import Doctrina.Rendering.Canvas;
import Doctrina.Rendering.RenderingEngine;
import Doctrina.Core.Game;
import Doctrina.Core.Trace;
import Doctrina.Physics.Position;

import java.awt.*;

public class CityCleanerGame extends Game {
    private GamePad gamePad;
    private Player player;
    private Enemy enemy;

    @Override
    protected void initialize() {
        gamePad = new GamePad();
        player = new Player(gamePad);
        enemy = new Enemy();
        enemy.teleport(player.getPosition());
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
        lastMovementTime = player.handleMovement(lastMovementTime);
        enemy.follow(player);
        enemy.update();
        camera.update();

    }



    @Override
    protected void draw(Canvas canvas) {
        canvas.drawBlueScreen();
        enemy.draw(canvas);
        for (Trace trace : player.getTraces()) {
            trace.placeTrace(canvas);
        }
        player.drawHitBox(canvas, Color.PINK);
        player.draw(canvas);
        // drawCamPosition(canvas, player);
    }

}
