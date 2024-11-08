package Doctrina.Entities;

import Doctrina.Controllers.MovementController;
import Doctrina.Core.Trace;

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

}
