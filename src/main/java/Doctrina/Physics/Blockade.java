package Doctrina.Physics;

import java.awt.Color;

import Doctrina.Entities.StaticEntity;
import Doctrina.Entities.Properties.Collidable;
import Doctrina.Rendering.Canvas;

public class Blockade extends StaticEntity implements Collidable{
    public Blockade(){
        super();
        color = new Color(225, 0, 0, 125);
        canCollide(this);
    }
    
    @Override
    public void draw(Canvas canvas) {
        canvas.drawRectangle(this);;
    }

    @Override
    public void load() {
        
    }

}
