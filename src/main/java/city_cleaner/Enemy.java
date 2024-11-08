package city_cleaner;
import Doctrina.Core.Camera;
import Doctrina.Core.Trace;
import Doctrina.Entities.*;
import Doctrina.Physics.Position;
import Doctrina.Physics.Size;
import Doctrina.Rendering.Canvas;
import Doctrina.Rendering.SpriteProperties;

public class Enemy extends MovableEntity implements Hostile {
    protected String ENEMY_SPRITE_SPATH= "images/sprite_sheets/Z_Walk.png";
    private final SpriteProperties props;
    private final Sight sight;

    public Enemy(){
        position = new Position(0, 0);
        teleport(position);
        size = new Size(32, 32);
        setDimension(size);
        sight = new Sight(this.size.multiply(6), this.position, this);
        props = new SpriteProperties(10, 32 ,0);
        setSpeed(1);
        load();
    }

    @Override
    public void attack() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'attack'");
    }

    @Override
    public void follow(MovableEntity entity) {
        for(Trace trace : entity.getTraces()){
            if(sight.intersects(trace.getBounds())){
                moveTo(trace.getPosition());
            }
        }
        if(sight.intersects(entity.getBounds())){
            moveTo(entity.getPosition());
        }
    }


    @Override
    public void update() {
        super.update();
        move();
        checkMovement();
    }
    @Override
    public void load() {
        loadSpriteSheet(ENEMY_SPRITE_SPATH);
        loadAnimationFrames(props);
    }

    @Override
    public void draw(Canvas canvas) {
        sight.draw(canvas);
        super.draw(canvas);
    }

}