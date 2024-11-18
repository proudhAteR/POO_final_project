package city_cleaner;

import Doctrina.Rendering.Canvas;
import Doctrina.Rendering.RenderingEngine;
import city_cleaner.Contoller.GamePad;
import city_cleaner.Entities.Bullet;
import city_cleaner.Entities.Enemy;
import city_cleaner.Entities.Player;
import Doctrina.Entities.MovableEntity;
import Doctrina.Entities.StaticEntity;
import Doctrina.Entities.Properties.AttackProperties;
import Doctrina.Entities.Properties.DestroyableManager;
import Doctrina.Entities.Properties.Step;
import Doctrina.Physics.Position;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import Doctrina.Core.*;

public class CityCleanerGame extends Game {
    private World world;
    private GamePad gamePad;
    private Player player;
    private ArrayList<Enemy> enemies;
    private ArrayList<MovableEntity> entities;
    private ArrayList<StaticEntity> destroyed;

    @Override
    protected void initialize() {
        world = new World();
        initializeEntities();
        initializePlayer();
        initializeEnemies();
        configureRenderingEngine();
        configureCamera();
    }

    @Override
    protected void update() {
        actionsHandler();
        for (MovableEntity e : entities) {
            e.update();
            if (e instanceof Enemy) {
                ((Enemy) e).follow(player);
            }
            if (e instanceof Bullet) {
                checkShotCollisions((Bullet) e);
                e.getAttackProperties().decreaseProps();
                if (isAttackOutOfRange(e)) {
                    destroyed.add(e);
                }
            }
        }
        if (!destroyed.isEmpty()) {
            DestroyableManager.destroyAll(destroyed);
            entities.removeAll(destroyed);
            destroyed.clear();
        }
        camera.update();

    }

    private boolean isAttackOutOfRange(MovableEntity e) {
        return e.getAttackProperties().getRange() <= 0;
    }

    @Override
    protected void draw(Canvas canvas) {
        canvas.drawScreen(Color.black);
        if (GameConfig.debugMode()) {
            renderDebugInfos(canvas);
        }
        renderWorld(canvas);
        renderEntities(canvas);
    }

    private void renderEntities(Canvas canvas) {
        for (MovableEntity e : entities) {
            e.draw(canvas);
        }
    }

    private void renderWorld(Canvas canvas) {
        canvas.clip(player.getSight().getBounds());
        world.draw(canvas);
    }

    private void renderDebugInfos(Canvas canvas) {
        for (StaticEntity e : entities) {
            if (e instanceof Player) {
                for (Step trace : ((MovableEntity) e).getSteps()) {
                    trace.placeStep(canvas);
                }
            } else {
                if (!(e instanceof Bullet)) {
                    e.getSight().draw(canvas);
                }
            }
        }
        GameConfig.drawCamPosition(RenderingEngine.getInstance(), canvas);
        GameConfig.drawCamFocusPosition(RenderingEngine.getInstance(), canvas);
        GameConfig.drawCount(enemies, canvas);
    }

    private void checkShotCollisions(Bullet e) {
        for (StaticEntity other : entities) {
            if (e.intersectsWith(other)) {
                if (!other.died()) {
                    ((MovableEntity) other).move(e.getDirection().getOppositeDirection());
                }
                destroyed.add(e);
                break;
            }
        }
    }

    private void actionsHandler() {
        handleUserAction();
        handlePlayerAction();
        handleEnemyAction();
    }

    private void handleEnemyAction() {
        for (Enemy enemy : enemies) {
            if (enemy.isReachable(player) && !enemy.isDying()) {
                enemy.attack();
                if (enemy.getHitBox().intersects(player.getBounds())) {
                    player.getHurt(enemy.getAttackProperties().getDamage());
                }
            }
        }
    }

    private void handleUserAction() {
        if (gamePad.isDebugPressed()) {
            GameConfig.toggleDebug();
        }
        if (gamePad.isQuitPressed()) {
            stop();
        }
    }

    private void handlePlayerAction() {
        if (gamePad.isFirePressed() && player.canFire()) {
            player.attack();
            entities.add(player.fire());
        }
        if (gamePad.isStabPressed()) {
            player.closeAttack();
            for (MovableEntity e : entities) {
                if (e instanceof Enemy) {
                    if (player.getHitBox().intersects(e.getBounds())) {
                        handlePlayerStab(e);
                    }
                }

            }
        }
    }

    private void handlePlayerStab(MovableEntity enemy) {
        int damage = player.getAttackProperties().getDamage();
        enemy.getHurt(damage);
        if (enemy.getHealth() <= 0) {
            enemy.die();
        }
    }

    private void initializeEntities() {
        entities = new ArrayList<>();
        destroyed = new ArrayList<>();
    }

    private void initializePlayer() {
        gamePad = new GamePad();
        player = new Player(gamePad);
        player.setAttackProperties(new AttackProperties(50, 0));
        entities.add(player);
    }

    private void initializeEnemies() {
        enemies = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Enemy enemy = new Enemy();
            enemy.setAttackProperties(new AttackProperties(100, 0));
            entities.add(enemy);
            enemies.add(enemy);
            enemy.canCollide(enemy);
            enemy.teleport(randomPosition());
        }
    }

    private Position randomPosition() {
        Random random = new Random();
        return new Position(random.nextInt(400), random.nextInt(300));
    }

    private void configureRenderingEngine() {
        RenderingEngine.getInstance().getScreen().fullscreen();
        RenderingEngine.getInstance().getScreen().hideCursor();
    }

    private void configureCamera() {
        camera.focusOn(player);
    }

}
