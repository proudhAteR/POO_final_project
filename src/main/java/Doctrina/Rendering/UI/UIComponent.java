package Doctrina.Rendering.UI;


import Doctrina.Physics.Position;
import Doctrina.Rendering.Camera;
import Doctrina.Rendering.Canvas;
import Doctrina.Rendering.RenderingEngine;

public abstract class UIComponent {
    Camera camera = RenderingEngine.getInstance().getCamera();
    protected Position pos;

    public UIComponent(Position position) {
        this.pos = position;
    }

    public abstract void draw(Canvas canvas);

    public abstract void update();
}
