package Doctrina.Physics;

import Doctrina.Entities.MovableEntity;
import Doctrina.Entities.StaticEntity;

public class Collision {
    private final MovableEntity entity;

    public Collision(MovableEntity entity) {
        this.entity = entity;
    }

    public int getAllowedSpeed() {
        return switch (entity.getDirection()) {
            case LEFT -> getAllowedLeftSpeed();
            case RIGHT -> getAllowedRightSpeed();
            case DOWN -> getAllowedDownSpeed();
            case UP -> getAllowedUpSpeed();
            default -> 0;
        };
    }

    private int getAllowedUpSpeed() {
        return distance((other -> entity.getY() - (other.getY() + other.getHeight()))); // the -> is a lambda
                                                                                        // declaration it's like having
                                                                                        // an anonymous function in JS
    }

    private int getAllowedDownSpeed() {
        return distance((other -> other.getY() - (entity.getY() + entity.getHeight())));
    }

    private int getAllowedLeftSpeed() {
        return distance((other -> entity.getX() - (other.getX() + other.getWidth())));
    }

    private int getAllowedRightSpeed() {
        return distance((other -> other.getX() - (entity.getX() + entity.getWidth())));
    }

    private int distance(DistanceCalculator calculator) {
        int allowedDistance = entity.getSpeed();
        for (StaticEntity other : CollidableRepository.getInstance()) {
            if (entity.intersectsWith(other)) {
                allowedDistance = Math.min(allowedDistance, calculator.calculateWidth(other));
            }
        }

        return allowedDistance;
    }

    public interface DistanceCalculator {
        int calculateWidth(StaticEntity other);
    }
}
