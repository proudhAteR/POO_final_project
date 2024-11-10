package city_cleaner;

import Doctrina.Rendering.Canvas;
import Doctrina.Rendering.RenderingEngine;
import city_cleaner.Contoller.GamePad;
import city_cleaner.Entities.Bullet;
import city_cleaner.Entities.Enemy;
import city_cleaner.Entities.Player;
import Doctrina.Entities.MovableEntity;
import Doctrina.Entities.StaticEntity;
import Doctrina.Entities.Properties.DestroyableManager;
import Doctrina.Physics.Position;

import java.awt.Color;
import java.util.ArrayList;

import Doctrina.Core.*;

public class CityCleanerGame extends Game {
    private GamePad gamePad;
    private Player player;
    private Enemy enemy;
    private ArrayList<MovableEntity> entities;
    private ArrayList<StaticEntity> destroyed;

    @Override
    protected void initialize() {
        entities = new ArrayList<>();
        destroyed = new ArrayList<>();
        gamePad = new GamePad();
        player = new Player(gamePad);
        entities.add(player);
        enemy = new Enemy();
        entities.add(enemy);
        enemy.teleport(new Position(10, 10));
        RenderingEngine.getInstance().getScreen().fullscreen();
        RenderingEngine.getInstance().getScreen().hideCursor();
        camera.focusOn(player);
    }

    @Override
    protected void update() {
        handleAction();
        enemy.follow(player);
        for (MovableEntity e : entities) {
            if (e instanceof Bullet) {
                checkCollisions(e);
            }
            e.update();
        }
        if (!destroyed.isEmpty()) {
            DestroyableManager.destroyAll(destroyed);
            entities.removeAll(destroyed);
            destroyed.clear();
        }
        camera.update();

    }

    private void checkCollisions(MovableEntity e) {
        for (StaticEntity other : entities) {
            if (e.intersectsWith(other)) {
                ((MovableEntity) other).move(e.getDirection().getOppositeDirection());
                destroyed.add(e);
                break;
            }
        }
    }

    private void handleAction() {
        if (gamePad.isDebugPressed()) {
            if (!GameConfig.debugMode()) {
                GameConfig.setDebugMode(true);
            } else {
                GameConfig.setDebugMode(false);
            }
        }
        if (gamePad.isQuitPressed()) {
            stop();
        }
        if (gamePad.isFirePressed() && player.canFire()) {
            player.attack();
            entities.add(player.fire());
        }
        if (gamePad.isStabPressed()) {
            player.closeAttack();
        }

        if (enemy.isReachable(player)) {
            enemy.attack();
        }
    }

    @Override
    protected void draw(Canvas canvas) { 
        canvas.drawScreen(Color.black);
        if (GameConfig.debugMode()) {
            enemy.getSight().draw(canvas);
            player.getSight().draw(canvas);
            for (Step trace : player.getSteps()) {
                trace.placeStep(canvas);
            }
            GameConfig.drawCamPosition(RenderingEngine.getInstance(), canvas, player);
        }
        canvas.clip(player.getSight().getBounds());
        canvas.drawScreen(Color.blue);    
        for (MovableEntity e : entities) {
            e.draw(canvas);
        }
        
       
    }

}
