package city_cleaner.Contoller;

import Doctrina.Rendering.*;
import city_cleaner.Entities.*;
import city_cleaner.Entities.Bonus.Bonus;
import city_cleaner.Entities.Bonus.BonusFactory;
import city_cleaner.Entities.Bonus.BonusesRepository;
import Doctrina.Entities.*;
import Doctrina.Entities.Properties.*;
import Doctrina.Physics.*;
import java.util.List;
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
    private BonusesRepository bonuses;
    private List<Bonus> found;
    private boolean wasDebugPressed = false;
    private boolean wasQuitPressed = false;
    // private World world;

    @Override
    protected void initialize() {
        bonuses = BonusesRepository.getInstance();
        found = new ArrayList<>();
        // world = JSONParser.getInstance().getWorld("images/map/base_map.tmj");
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
        renderBonuses(canvas);
        renderEntities(canvas);

        if (!GameConfig.debugMode()) {
            canvas.applyShaders(player.getSight().getBounds());
        }

        renderHUD(canvas);
    }

    private void renderHUD(Canvas canvas) {
        player.getHealthBar().draw(canvas);
        canvas.drawString(player.getWeapon(), 20, 92, Color.white);
        if (player.isReloading()) {
            player.drawShootCoolDown(canvas);
        }
    }

    private void renderBonuses(Canvas canvas) {
        for (Bonus bonus : bonuses) {
            bonus.place(canvas);
        }
    }

    public boolean handleBonus(Bonus bonus) {
        if (player.intersectsWith(bonus)) {
            bonus.setFound(true);

        }
        return bonus.isFound();
    }

    private void entitiesUpdate() {
        for (MovableEntity e : entities) {
            e.update();
            if (e instanceof Projectile) {
                handleProjectile((Projectile) e);
            }
        }
        for (Bonus b : bonuses) {
            handleBonus(b);
        }
    }

    private void handleProjectile(Projectile projectile) {
        AttackProperties props = projectile.getAttackProperties();
        checkShotCollisions(projectile);

        if (!hasBeenDestroyed(projectile)) {
            props.decreaseProps();
            if (props.isAttackOutOfRange()) {
                destroyed.add(projectile);
            }
        }
    }

    private boolean hasBeenDestroyed(StaticEntity e) {
        return destroyed.contains(e);
    }

    private void arraysUpdate() {
        for (Bonus bonus : bonuses) {
            if (bonus.isFound()) {
                found.add(bonus);
                bonus.affect(player);
            }
        }
        if (Math.random() < 0.005) {
            bonuses.registerBonus(BonusFactory.createRandomBonus());
        }
        if (!enemies.isEmpty()) {
            decomposeTheDead();
        }
        if (!destroyed.isEmpty()) {
            DestroyableManager.destroyAll(destroyed);
            entities.removeAll(destroyed);
            destroyed.clear();
        }
        if (!found.isEmpty()) {
            Bonus.removeBonus(found);
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
                if (!(e instanceof Projectile)) {
                    e.getSight().draw(canvas);
                }
            }
        }
        GameConfig.drawCamPosition(RenderingEngine.getInstance(), canvas);
        GameConfig.drawCamFocusPosition(RenderingEngine.getInstance(), canvas);
        GameConfig.drawCount(enemies, canvas);
    }

    private void checkShotCollisions(Projectile projectile) {
        for (MovableEntity entity : entities) {
            if (projectile.touched(entity) && !entity.isHuman()) {
                if (!entity.died()) {
                    (entity).moveTowards(projectile.getOppositeDirection());
                }
                entity.receiveAttack(projectile.getAttackProperties());
                destroyed.add(projectile);
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
                enemy.closeAttack();

                if (enemy.attackWorked(player)) {
                    player.receiveAttack(enemy.getAttackProperties());
                }
            }
        }
    }

    private void handleUserAction() {
        boolean isDebugPressed = gamePad.isDebugPressed();
        if (isDebugPressed && !wasDebugPressed) {
            GameConfig.toggleDebug();
        }
        wasDebugPressed = isDebugPressed;

        boolean isQuitPressed = gamePad.isQuitPressed();
        if (isQuitPressed && !wasQuitPressed) {
            stop();
        }
        wasQuitPressed = isQuitPressed;
    }

    private void handlePlayerAction() {
        // The player can't shoot when too close to an enemy
        if (gamePad.isFirePressed() && player.canFire(enemies)) {
            player.attack(player.currentWeapon());
            entities.add(player.fire());
        }
        if (gamePad.isStabPressed()) {
            player.closeAttack();
            for (MovableEntity e : entities) {
                if (e instanceof Enemy) {
                    if (player.attackWorked(e)) {
                        e.receiveAttack(player.getAttackProperties());
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
        player.setAttackProperties(new AttackProperties(20, 0));
        entities.add(player);
    }

    private void initializeEnemies() {
        enemies = new ArrayList<>();
        for (int i = 0; i < 0; i++) {
            Enemy enemy = new Enemy();
            enemy.setAttackProperties(new AttackProperties(20, 0));
            entities.add(enemy);
            enemies.add(enemy);
            enemy.canCollide(enemy);
            enemy.teleport(randomPosition());
        }
    }

    private Position randomPosition() {
        Random random = new Random();
        final int x = RenderingEngine.getInstance().getScreen().getHeight();
        final int y = RenderingEngine.getInstance().getScreen().getWidth();

        return new Position(random.nextInt(x), random.nextInt(y));
    }

    private void configureRenderingEngine() {
        RenderingEngine.getInstance().getScreen().fullscreen();
        RenderingEngine.getInstance().getScreen().hideCursor();
    }

    private void configureCamera() {
        camera.focusOn(player);
    }

}
