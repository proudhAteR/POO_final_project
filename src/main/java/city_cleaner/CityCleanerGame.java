package city_cleaner;

import Doctrina.Rendering.Canvas;
import Doctrina.Rendering.RenderingEngine;
import Doctrina.Core.Game;
import Doctrina.Core.Trace;
import Doctrina.Physics.Position;

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
        enemy.update();
        camera.update();
        handlePlayerMovement();
    }

    private void handlePlayerMovement() {
        if (player.hasMoved()) {
            lastMovementTime--;
        }
        if (lastMovementTime == 0) {
            traces.add(new Trace(new Position(player.getX(), player.getY()), player));
            lastMovementTime = 30;

            if (traces.size() > 4) {
                traces.removeFirst();
            }
        }
    }

    @Override
    protected void draw(Canvas canvas) {
        canvas.drawBlueScreen();
        enemy.draw(canvas);
        for (Trace trace : traces) {
            trace.placeTrace(canvas);
        }
        player.draw(canvas);
        // drawCamPosition(canvas, player);
    }

}
