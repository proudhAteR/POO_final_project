package city_cleaner.Entities;

import java.awt.Color;

import Doctrina.Controllers.Direction;
import Doctrina.Entities.MovableEntity;
import Doctrina.Entities.Properties.Projetable;
import Doctrina.Entities.Properties.Sight;
import Doctrina.Physics.Position;
import Doctrina.Physics.Size;
import Doctrina.Rendering.Canvas;

public class Bullet extends MovableEntity implements Projetable {

    public Bullet(MovableEntity e) {
        setSpeed(10);
        this.direction = e.getDirection();
        initialize(e);
        color = Color.PINK;
    }

    private void initialize(MovableEntity e) {
        size = new Size(2, 4);
        position = new Position(0, 0);
        sight = new Sight(e);
        sight.setSize(new Size(0, 0));
        setDimension(size);
        teleport(position);
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
                this.getPosition().setX(entity.getDirection() == Direction.RIGHT ? entity.getX() + (entity.getWidth() + 1)
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

    }

    @Override
    public void update() {
        move(direction);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRectangle(this);
    }

}
