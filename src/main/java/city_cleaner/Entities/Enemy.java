package city_cleaner.Entities;

import java.util.Set;

import Doctrina.Entities.*;
import Doctrina.Entities.Properties.Action;
import Doctrina.Entities.Properties.Collidable;
import Doctrina.Entities.Properties.Hostile;
import Doctrina.Entities.Properties.Sight;
import Doctrina.Entities.Properties.Step;
import Doctrina.Physics.Position;
import Doctrina.Physics.Size;
import Doctrina.Rendering.Canvas;
import Doctrina.Rendering.SpriteProperties;
import city_cleaner.Entities.Bonus.BonusesRepository;
import city_cleaner.Factories.BonusFactory;

public class Enemy extends MovableEntity implements Hostile, Collidable {

    private final String ATTACKS_PATHS = "images/sprite_sheets/zombies/Attack.png";
    private final SpriteProperties ATTACKS_PROPS = new SpriteProperties(8, 32, 0);
    private boolean touched;
    protected final String[] SPRITE_PATHS = {
            "images/sprite_sheets/zombies/z_walk.png",
            "images/sprite_sheets/zombies/z_idle.png",
            "images/sprite_sheets/zombies/z_death.png"
    };
    protected final SpriteProperties[] SPRITE_PROPS = {
            new SpriteProperties(10, 32, 0),
            new SpriteProperties(5, 32, 0),
            new SpriteProperties(7, 32, 0)
    };

    public Enemy() {
        touched = false;
        canCollide(this);
        position = new Position(0, 0);
        size = new Size(32, 32);
        setDimension(size);
        setHealth(100);
        sight = new Sight(this);
        sight.setSize(size.multiply(4));
        setSpeed(1);
        load();
    }

    @Override
    public void follow(MovableEntity entity) {
        if (this.isDying() || isDead) {
            return;
        }
        if (isEntityInSight(entity) || isStepInSight(entity)) {
            moveToEntity(entity);
            removeStep(entity);
        }

    }

    public boolean isReachable(StaticEntity e) {
        return intersectsWith(e);
    }

 
    public boolean isStepInSight(MovableEntity entity) {
        Step closestStep = null;
        double closestDistance = Double.MAX_VALUE;

        for (Step step : entity.getSteps()) {
            if (sight.intersects(step.getBounds())) {
                double distance = calculateDistance(this.getPosition(), step.getPosition());
                if (distance < closestDistance) {
                    closestStep = step;
                    closestDistance = distance;
                }
            }
        }

        if (closestStep != null) {
            moveToStep(closestStep);
            return true;
        }

        return false;
    }

    private void moveToStep(Step step) {
        moveTo(step.getPosition());
    }

    private double calculateDistance(Position p1, Position p2) {
        double dx = p2.getX() - p1.getX();
        double dy = p2.getY() - p1.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    private void removeStep(MovableEntity entity) {
        for (Step step : entity.getSteps()) {
            if (this.intersectsWith(step)) {
                entity.getSteps().remove(step);
                break;
            }
        }
    }

    private void moveToEntity(MovableEntity entity) {
        moveTo(entity.getPosition());
    }

    @Override
    public void update() {
        if (!isDead) {
            super.update();
            move();
            updateAnimation();
            handleMovement();
        }
    }

    public void load() {
        for (Action action : Action.values()) {
            if (action == Action.ATTACKING) {
                loadSpriteSheet(ATTACKS_PATHS);
                loadAttacks(ATTACKS_PROPS);
                loadActions(ATTACKS_PROPS, action);
                continue;
            }
            loadSpriteSheet(SPRITE_PATHS[action.ordinal()]);
            loadActions(SPRITE_PROPS[action.ordinal()], action);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    public void getHurt(int number) {
        super.getHurt(number);

        if (!touched) {
            touched = true;
        }
    }

    public void dropBonus(Set<Position> droppedBonusPositions) {
        if (!bonusExistsAtPosition(getPosition(), droppedBonusPositions)) {
            int targetX = getX() + getWidth() / 2;
            int targetY = getY() + getHeight() / 2;

            Position pos = new Position(targetX, targetY);
            BonusesRepository.getInstance().registerBonus(BonusFactory.dropBonus(pos));
        }
    }

    private boolean bonusExistsAtPosition(Position position, Set<Position> droppedBonusPositions) {
        return droppedBonusPositions.contains(position);
    }

    public boolean isTouched() {
        return touched;
    }
}