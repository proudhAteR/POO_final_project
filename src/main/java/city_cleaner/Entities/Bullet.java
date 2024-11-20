package city_cleaner.Entities;

import Doctrina.Controllers.Direction;
import Doctrina.Entities.MovableEntity;
import Doctrina.Entities.Properties.Action;
import Doctrina.Entities.Properties.AttackProperties;
import Doctrina.Entities.Properties.Projetable;
import Doctrina.Entities.Properties.Sight;
import Doctrina.Physics.Position;
import Doctrina.Physics.Size;
import Doctrina.Rendering.Canvas;
import Doctrina.Rendering.SpriteProperties;

public class Bullet extends MovableEntity implements Projetable {
    private final String SPRITE_PATH = "images/sprite_sheets/Bullet.png";
    private final SpriteProperties SPRITE_PROPS = new SpriteProperties(4, 0, 0);

    public Bullet(MovableEntity e) {
        setSpeed(10);
        action = Action.MOVING;
        attackProperties = new AttackProperties(50, 25);
        this.direction = e.getDirection();
        initialize(e);
    }

    private void initialize(MovableEntity e) {
        size = new Size(32, 32);
        position = new Position(0, 0);
        sight = new Sight(e);
        sight.setSize(new Size(0, 0));
        setDimension(size);
        teleport(position);
        load();
        positionProjectileAtEntity(e);
    }

    @Override
    public void positionProjectileAtEntity(MovableEntity entity) {
        placeAtCenter(entity);
    }

    private void placeAtCenter(MovableEntity entity) {
        int[] values = new int[2];
        values = invertValues(getWidth(), getHeight());

        switch (entity.getDirection()) {

            case RIGHT, LEFT -> {
                this.setDimension(new Size(values[0], values[1]));
                this.getPosition()
                        .setX(entity.getDirection() == Direction.RIGHT ? entity.getX() + (entity.getWidth() + 1)
                                : entity.getX() - 1);
                this.getPosition().setY(entity.getY() + entity.getHeight() / 2 - getHeight() / 2);
            }
            case DOWN, UP -> {
                this.getPosition().setX(entity.getX() + entity.getWidth() / 2 - getWidth() / 2);
                this.getPosition()
                        .setY(entity.getDirection() == Direction.DOWN ? entity.getY() + (entity.getHeight() + 1)
                                : entity.getY() - (entity.getHeight() + 1));
            }
        }
    }

    private int[] invertValues(int arg1, int arg2) {
        return new int[] { arg2, arg1 };
    }

    @Override
    public void load() {
        loadSpriteSheet(SPRITE_PATH);
        loadAnimationFrames(SPRITE_PROPS, Action.MOVING);
    }

    @Override
    public void update() {
        move(direction);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

}
