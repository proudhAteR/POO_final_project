package city_cleaner.Entities;

import Doctrina.Entities.MovableEntity;
import Doctrina.Entities.Properties.Action;
import Doctrina.Entities.Properties.AttackProperties;
import Doctrina.Entities.Properties.Projectile;
import Doctrina.Entities.Properties.Sight;
import Doctrina.Physics.Position;
import Doctrina.Physics.Size;
import Doctrina.Rendering.Canvas;
import Doctrina.Rendering.SpriteProperties;

public class Bullet extends Projectile {
    private final String SPRITE_PATH = "images/sprite_sheets/Bullet.png";
    private final SpriteProperties SPRITE_PROPS = new SpriteProperties(4, 0, 0);

    public Bullet(MovableEntity e) {
        setSpeed(10);
        action = Action.IDLE;
        attackProperties = new AttackProperties(50, 25);
        this.direction = e.getDirection();
        initialize(e);
    }

    private void initialize(MovableEntity e) {
        size = new Size(32, 32);
        position = new Position(0, 0);
        sight = new Sight(e);
        sight.setSize(new Size(0, 0));
        setDimension(size);
        teleport(position);
        load();
        positionProjectileAtEntity(e);
    }

    @Override
    public void positionProjectileAtEntity(MovableEntity entity) {
        placeAtCenter(entity);
    }

    @Override
    public void load() {
        loadSpriteSheet(SPRITE_PATH);
        loadActions(SPRITE_PROPS, Action.IDLE);
    }

    @Override
    public void update() {
        moveTowards(direction);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

}
