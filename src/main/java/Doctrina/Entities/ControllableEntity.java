package Doctrina.Entities;

import Doctrina.Controllers.MovementController;

public abstract class ControllableEntity extends MovableEntity {
    private final MovementController controller;

    public ControllableEntity(MovementController controller) {
        this.controller = controller;
    }

    public void moveWithController() {
        if (controller.isMoving() && !isDead) {
            moveTowards(controller.getDirection());
        }
    }

}
