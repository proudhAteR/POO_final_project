package Doctrina.Entities;

import Doctrina.Controllers.MovementController;
import Doctrina.Core.Step;
import Doctrina.Physics.Position;

public abstract class ControllableEntity extends MovableEntity {
    private final MovementController controller;
    private final int BASE_MOVEMENT_TIME = 20;
    protected int lastMovementTime = BASE_MOVEMENT_TIME;

    public ControllableEntity(MovementController controller) {
        this.controller = controller;
    }

    public void moveWithController() {
        if (controller.isMoving()) {
            move(controller.getDirection());
        }
    }

    private void resetMovementTime() {
        lastMovementTime = BASE_MOVEMENT_TIME;
    }

    private void reduceMovementTime() {
        lastMovementTime--;
    }

    private int getLastMovementTime() {
        return lastMovementTime;
    }

    public void handleMovement() {
        if (hasMoved()) {
            reduceMovementTime();
        }
        if (getLastMovementTime() == 0) {
            getSteps().add(new Step(new Position(getX(), getY()), this));
            resetMovementTime();
            if (getSteps().size() > 3) {
                getSteps().removeFirst();
            }
        }

    }

}
