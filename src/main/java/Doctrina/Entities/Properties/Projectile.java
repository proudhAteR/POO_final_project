package Doctrina.Entities.Properties;

import Doctrina.Controllers.Direction;
import Doctrina.Entities.MovableEntity;
import Doctrina.Physics.Size;

public abstract class Projectile extends MovableEntity {
    public abstract void positionProjectileAtEntity(MovableEntity entity);

    protected void placeAtCenter(MovableEntity entity) {
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
            case DOWN, UP, NONE -> {
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
}
