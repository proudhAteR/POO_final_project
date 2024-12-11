package Doctrina.Entities.Properties.StateMachine;

import java.awt.Image;

import Doctrina.Entities.MovableEntity;

public abstract class State {
    Image[][] frames;
    public State(Image[][] frames) {
        this.frames = frames;
    }
    public abstract void enter(MovableEntity entity);

    public abstract void update(MovableEntity entity);

    public Image[][] getFrames() {
        return frames;
    }
}
