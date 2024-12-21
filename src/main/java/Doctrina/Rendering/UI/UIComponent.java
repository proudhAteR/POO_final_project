package Doctrina.Rendering.UI;


import Doctrina.Core.Camera;
import Doctrina.Core.RenderingEngine;
import Doctrina.Physics.Position;
import Doctrina.Rendering.Canvas;

public abstract class UIComponent {
    Camera camera = RenderingEngine.getInstance().getCamera();
    protected Position pos;

    public UIComponent(Position position) {
        this.pos = position;
    }

    public abstract void draw(Canvas canvas);

    public abstract void update();
}
