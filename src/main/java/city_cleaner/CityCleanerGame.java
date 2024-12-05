package city_cleaner;

import Doctrina.Rendering.Canvas;
import Doctrina.Rendering.RenderingEngine;
import Doctrina.Rendering.WorldRendering.JSONParser;
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
    private GamePad gamePad;
    private Player player;
    private ArrayList<Enemy> enemies;
    private ArrayList<MovableEntity> entities;
    private ArrayList<StaticEntity> destroyed;
    World world;

    @Override
    protected void initialize() {
        world = JSONParser.getInstance().getWorld("images/map/base_map.tmj");
        initializeEntities();
        initializePlayer();
        initializeEnemies();
        configureRenderingEngine();
        configureCamera();
    }

    @Override
    protected void update() {
        actionsHandler();
        entitiesUpdate();
        arraysUpdate();
    }

    @Override
    protected void draw(Canvas canvas) {
        canvas.drawScreen(Color.blue);
        if (GameConfig.debugMode()) {
            renderDebugInfos(canvas);
        }
        renderEntities(canvas);

        if (!GameConfig.debugMode()) {
            canvas.applyShaders(player.getSight().getBounds());
        }
    }

    private void entitiesUpdate() {
        for (MovableEntity e : entities) {
            e.update();
            if (e instanceof Bullet) {
                handleBullet((Bullet) e);
            }
        }
    }

    private void handleBullet(Bullet bullet) {
        AttackProperties props = bullet.getAttackProperties();
        checkShotCollisions(bullet);

        if (!hasBeenDestroyed(bullet)) {
            props.decreaseProps();
            if (props.isAttackOutOfRange()) {
                destroyed.add(bullet);
            }
        }
    }

    private boolean hasBeenDestroyed(StaticEntity e) {
        return destroyed.contains(e);
    }

    private void arraysUpdate() {
        if (!enemies.isEmpty()) {
            decomposeTheDead();
        }
        if (!destroyed.isEmpty()) {
            DestroyableManager.destroyAll(destroyed);
            entities.removeAll(destroyed);
            destroyed.clear();
        }
        camera.update();
    }

    private void decomposeTheDead() {
        for (Enemy e : enemies) {
            if (e.died() && !player.getSight().intersects(e.getBounds())) {
                enemies.remove(e);
                destroyed.add(e);
                break;
            }
        }
    }

    private void renderEntities(Canvas canvas) {
        for (MovableEntity e : entities) {
            e.draw(canvas);
        }
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

    private void checkShotCollisions(Bullet bullet) {
        for (MovableEntity entity : entities) {
            if (bullet.touched(entity)) {
                if (!entity.died()) {
                    (entity).moveTowards(bullet.getOppositeDirection());
                }
                entity.receiveAttack(bullet.getAttackProperties().getDamage());
                destroyed.add(bullet);
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
            enemy.follow(player);

            if (enemy.isReachable(player) && !enemy.isDying()) {
                enemy.attack(0);
                if (enemy.touched(player)) {
                    player.receiveAttack(enemy.getAttackProperties().getDamage());
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
            player.attack(player.currentWeapon());
            entities.add(player.fire());
        }
        if (gamePad.isStabPressed()) {
            player.attack(0);
            for (MovableEntity e : entities) {
                if (e instanceof Enemy) {
                    if (player.touched(e)) {
                        e.receiveAttack(player.getAttackProperties().getDamage());
                    }
                }

            }
        }
        if (gamePad.isWeaponKeyPressed()) {
            player.changeWeapon(gamePad.getWeaponKeyCode());
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
        for (int i = 0; i < 6; i++) {
            Enemy enemy = new Enemy();
            enemy.setAttackProperties(new AttackProperties(0, 0));
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
