package city_cleaner;

import Doctrina.Rendering.Canvas;
import Doctrina.Rendering.RenderingEngine;
import Doctrina.Core.Game;
import Doctrina.Physics.Position;
import Doctrina.Core.*;;;

public class CityCleanerGame extends Game {
    private GamePad gamePad;
    private Player player;
    private Enemy enemy;

    @Override
    protected void initialize() {
        gamePad = new GamePad();
        gamePad.useWASDKeys();
        player = new Player(gamePad);
        enemy = new Enemy();
        enemy.teleport(new Position(10, 10));
        RenderingEngine.getInstance().getScreen().fullscreen();
        RenderingEngine.getInstance().getScreen().hideCursor();
        camera.focusOn(player);
    }

    @Override
    protected void update() {
        if (gamePad.isDebugPressed()) {
            if (!GameConfig.debugMode()) {
                GameConfig.setDebugMode(true);
            }else{
                GameConfig.setDebugMode(false);
            }
        }
        if (gamePad.isQuitPressed()) {
            stop();
        }
        player.update();
        enemy.follow(player);
        if (gamePad.isFirePressed()) {
            player.attack();
        }

        if (enemy.getHitBox().intersects(player.getBounds())) {
            enemy.attack();
        }
        enemy.update();
        camera.update();

    }

    @Override
    protected void draw(Canvas canvas) {
        canvas.drawBlueScreen();
        if (GameConfig.debugMode()) {
            enemy.getSight().draw(canvas);
            player.getSight().draw(canvas);
            for (Step trace : player.getSteps()) {
                trace.placeStep(canvas);
            }
            GameConfig.drawCamPosition(RenderingEngine.getInstance(), canvas, player);
        }
        enemy.draw(canvas);
        player.draw(canvas);

    }

}
