package Doctrina.Entities;

import Doctrina.Controllers.MovementController;
import Doctrina.Core.Trace;
import Doctrina.Physics.Position;

import java.util.ArrayList;

public abstract class ControllableEntity extends MovableEntity{
   private final MovementController controller;
   public ControllableEntity(MovementController controller){
        this.controller = controller ;
    }

    public void moveWithController(){
        if (controller.isMoving()) {
            move(controller.getDirection());
        }
    }

    public long handleMovement(long lastMovementTime) {
        if (this.hasMoved()) {
            lastMovementTime--;
        }
        if (lastMovementTime == 0) {
            this.getTraces().add(new Trace(new Position(this.getX(), this.getY()), this));
            lastMovementTime = 30;

            if (this.getTraces().size() > 4) {
                this.getTraces().removeFirst();
            }
        }
        return lastMovementTime;
    }

}
