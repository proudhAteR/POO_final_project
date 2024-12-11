package Doctrina.Rendering;

import Doctrina.Physics.Position;

public abstract class UIComponent {
    protected Position position;

    abstract void draw(Canvas canvas);

    abstract void update();
}
