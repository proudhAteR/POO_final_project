package city_cleaner;

import Doctrina.Rendering.Canvas;
import Doctrina.Rendering.RenderingEngine;
import Doctrina.Core.Game;
import Doctrina.Core.Step;
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
        enemy.teleport(new Position(10,10));
        //RenderingEngine.getInstance().getScreen().fullscreen();
        RenderingEngine.getInstance().getScreen().hideCursor();
        camera.focusOn(player);
    }

    @Override
    protected void update() {
        if (gamePad.isQuitPressed()) {
            stop();
        }
        player.update();
        handlePlayerMovement();
        enemy.follow(player);
        if (enemy.getHitBox().intersects(player.getBounds())) {
            enemy.attack();

        }
        enemy.update();
        camera.update();

    }

    private void handlePlayerMovement() {
        if (player.hasMoved()) {
            lastMovementTime--;
        }
        if (lastMovementTime == 0) {
            player.getSteps().add(new Step(new Position(player.getX(), player.getY()), player));
            resetMovementTime();
            if (player.getSteps().size() > 4) {
                player.getSteps().removeFirst();
            }
        }

    }

    @Override
    protected void draw(Canvas canvas) {
        canvas.drawBlueScreen();
        enemy.draw(canvas);
        /*for (Trace trace : player.getTraces()) {
            trace.placeTrace(canvas);
        }*/
        //player.drawHitBox(canvas, Color.PINK);
        player.draw(canvas);
        // drawCamPosition(canvas, player);
    }

}
