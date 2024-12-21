package city_cleaner;

import Doctrina.Rendering.*;
import Doctrina.Rendering.UI.Bar;
import city_cleaner.Controller.GamePad;
import city_cleaner.Entities.*;
import city_cleaner.Entities.Bonus.Bonus;
import city_cleaner.Entities.Bonus.BonusesRepository;
import city_cleaner.Entities.Bonus.TemporaryBonus.TemporaryBonus;
import city_cleaner.Factories.BonusFactory;
import city_cleaner.Factories.EnemySpawner;
import Doctrina.Entities.*;
import Doctrina.Entities.Properties.*;
import Doctrina.Physics.*;
import java.util.List;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.tritonus.share.ArraySet;

import Doctrina.Core.*;

public class CityCleanerGame extends Game {
    private GamePad gamePad;
    private Player player;
    private ArrayList<Enemy> enemies;
    private ArrayList<MovableEntity> entities;
    private ArrayList<StaticEntity> destroyed;
    private BonusesRepository bonuses;
    private List<Bonus> found;
    private Set<TemporaryBonus<?>> bonusProgress;
    private Set<Position> droppedBonusPositions;
    private boolean wasDebugPressed = false;
    private boolean wasQuitPressed = false;
    // private World world;

    @Override
    protected void initialize() {
        bonuses = BonusesRepository.getInstance();
        droppedBonusPositions = new HashSet<>();
        found = new ArrayList<>();
        bonusProgress = new ArraySet<>();
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
        camera.update();
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

        player.drawHealthBar(canvas);
        canvas.drawString(player.getWeapon(), 20, 90, Color.white);
        if (player.isReloading()) {
            player.drawShootCoolDown(canvas);
        }
        renderBonusesBar(canvas);
    }

    private void renderBonusesBar(Canvas canvas) {
        final int BASE_X = 800;
        final int BASE_WIDTH = 200;
        int y = 40;

        for (TemporaryBonus<?> p : bonusProgress) {
            int duration = Math.max(1, p.getDuration() / 1000);

            Bar progressBar = new Bar(
                    new Position(BASE_X - BASE_WIDTH, y),
                    new Size(BASE_WIDTH, 20),
                    p.getColor());

            int barWidth = (p.getRemainingDur() * BASE_WIDTH) / duration;
            progressBar.getSize().setWidth(barWidth);

            progressBar.draw(canvas);

            y += 40;
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
        EnemySpawner.getInstance().startSpawning(enemies, entities);
        for (Bonus bonus : bonuses) {
            if (bonus.isFound()) {
                found.add(bonus);
                bonus.affect(player);
                if (bonus instanceof TemporaryBonus) {
                    bonusProgress.add((TemporaryBonus<?>) bonus);
                }
            }
        }
        registerBonuses();

        if (!enemies.isEmpty()) {
            decomposeTheDead();
        }
        emptyPlaceHolderArrays();
        removeFinishedBonus();
    }

    private void registerBonuses() {
        if (Math.random() < 0.0070) {
            Bonus bonus = BonusFactory.createRandomBonus();
            bonuses.registerBonus(bonus);
        }
    }

    private void emptyPlaceHolderArrays() {
        if (!destroyed.isEmpty()) {
            DestroyableManager.destroyAll(destroyed);
            entities.removeAll(destroyed);
            destroyed.clear();
        }
        if (!found.isEmpty()) {
            Bonus.removeBonus(found);
            found.clear();
        }
    }

    private void removeFinishedBonus() {
        Iterator<TemporaryBonus<?>> iterator = bonusProgress.iterator();
        while (iterator.hasNext()) {
            TemporaryBonus<?> bonus = iterator.next();
            if (bonus.getRemainingDur() <= 0) {
                iterator.remove();
            }
        }
    }

    private void decomposeTheDead() {
        Iterator<Enemy> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            Enemy e = iterator.next();
            if (e.died()) {
                Position dropPosition = e.getPosition();
                if (!droppedBonusPositions.contains(dropPosition)) {
                    e.dropBonus(droppedBonusPositions);
                    droppedBonusPositions.add(dropPosition);
                }
                DestroyableManager.destroy(e);
                if (!player.getSight().intersects(e.getBounds())) {
                    iterator.remove();
                    destroyed.add(e);
                }
            }
        }
    }

    private void renderEntities(Canvas canvas) {
        for (MovableEntity e : entities) {
            for (Step trace : ((MovableEntity) e).getSteps()) {
                if ((e instanceof Enemy) && ((Enemy) e).isTouched()) {
                    trace.placeStep(canvas);
                }

            }
            e.draw(canvas);
        }
    }

    private void renderDebugInfos(Canvas canvas) {
        for (StaticEntity e : entities) {
            for (Step trace : ((MovableEntity) e).getSteps()) {
                if (e instanceof Player) {
                    trace.placeStep(canvas);
                }
            }
            e.getSight().draw(canvas);
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

                if (enemy.touched(player)) {
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
        if (gamePad.isFirePressed() && player.canFire(enemies)) {
            player.attack(player.currentWeapon());
            entities.add(player.fire());
        }
        if (gamePad.isStabPressed()) {
            player.closeAttack();
            for (MovableEntity e : entities) {
                if (e instanceof Enemy) {
                    if (player.touched(e)) {
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
    }

    private void configureRenderingEngine() {
        RenderingEngine.getInstance().getScreen().fullscreen();
        RenderingEngine.getInstance().getScreen().hideCursor();
    }

    private void configureCamera() {
        camera.focusOn(player);
    }

}
