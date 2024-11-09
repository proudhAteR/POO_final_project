package city_cleaner;

import Doctrina.Core.Step;
import Doctrina.Entities.*;
import Doctrina.Physics.Position;
import Doctrina.Physics.Size;
import Doctrina.Rendering.Canvas;
import Doctrina.Rendering.SpriteProperties;

public class Enemy extends MovableEntity implements Hostile, Collidable {
    protected final String[] SPRITE_PATHS = {
            "images/sprite_sheets/z_walk.png",
            "images/sprite_sheets/Attack.png",
            "images/sprite_sheets/z_idle.png"
    };
    protected final SpriteProperties[] SPRITE_PROPS = {
            new SpriteProperties(10, 32, 0),
            new SpriteProperties(8, 32, 0),
            new SpriteProperties(5, 32, 0)
    };

    public Enemy() {
        canCollide(this);
        position = new Position(0, 0);
        teleport(position);
        size = new Size(32, 32);
        setDimension(size);
        sight = new Sight(this.size.multiply(6), this.position, this);
        setSpeed(1);
        load();
    }

    @Override
    public void follow(MovableEntity entity) {
        if (isEntityInSight(entity)) {
            if (hasReachedEntity(entity)) {
                return;
            }
            if (hasReachedStep(entity)) {
                removeStep(entity);
            } else {
                moveToEntity(entity);
            }
        }
    }

    private boolean isEntityInSight(MovableEntity entity) {
        return sight.intersects(entity.getBounds());
    }

    private boolean hasReachedEntity(MovableEntity entity) {
        return this.getBounds().intersects(entity.getBounds());
    }

    private boolean hasReachedStep(MovableEntity entity) {
        for (Step step : entity.getSteps()) {
            if (sight.intersects(step.getBounds())) {
                moveTo(step.getPosition());
                return true;
            }
        }
        return false;
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
        super.update();
        move();
        checkMovement();
    }

    public void load() {
        for (Action action : Action.values()) {
            loadSpriteSheet(SPRITE_PATHS[action.ordinal()]);
            loadAnimationFrames(SPRITE_PROPS[action.ordinal()], action);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        // sight.draw(canvas);
        super.draw(canvas);
    }

}