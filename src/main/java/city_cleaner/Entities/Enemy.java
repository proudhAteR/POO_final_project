package city_cleaner.Entities;

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

public class Enemy extends MovableEntity implements Hostile, Collidable {
    protected final String[] SPRITE_PATHS = {
            "images/sprite_sheets/zombies/z_walk.png",
            "images/sprite_sheets/zombies/Attack.png",
            "images/sprite_sheets/zombies/Attack.png",
            "images/sprite_sheets/zombies/z_idle.png",
            "images/sprite_sheets/zombies/z_death.png"
    };
    protected final SpriteProperties[] SPRITE_PROPS = {
            new SpriteProperties(10, 32, 0),
            new SpriteProperties(8, 32, 0),
            new SpriteProperties(8, 32, 0),
            new SpriteProperties(5, 32, 0),
            new SpriteProperties(7, 32, 0)
    };

    public Enemy() {
        canCollide(this);
        position = new Position(0, 0);
        size = new Size(32, 32);
        setDimension(size);
        setHealth(100);
        sight = new Sight(this);
        sight.setSize(size.multiply(5));
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
        return getHitBox().intersects(e.getBounds());
    }

    public boolean isEntityInSight(MovableEntity entity) {
        return sight.intersects(entity.getBounds());
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
            moveTo(closestStep.getPosition());
            return true;
        }

        return false;
    }

    private double calculateDistance(Position p1, Position p2) {
        double dx = p2.getX() - p1.getX();
        double dy = p2.getY() - p1.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    private void removeStep(MovableEntity entity) {
        for (Step step : entity.getSteps()) {
            if (this.getHitBox().intersects(step.getBounds())) {
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
        }
    }

    public void load() {
        for (Action action : Action.values()) {
            loadSpriteSheet(SPRITE_PATHS[action.ordinal()]);
            loadAnimationFrames(SPRITE_PROPS[action.ordinal()], action);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

}