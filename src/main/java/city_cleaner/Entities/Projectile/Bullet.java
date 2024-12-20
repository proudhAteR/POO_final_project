package city_cleaner.Entities.Projectile;

import Doctrina.Entities.MovableEntity;
import Doctrina.Entities.Projectile;
import Doctrina.Entities.Properties.Action;
import Doctrina.Entities.Properties.AttackProperties;
import Doctrina.Rendering.Canvas;
import Doctrina.Rendering.SpriteProperties;

public class Bullet extends Projectile {
    private final String SPRITE_PATH = "images/sprite_sheets/player/Bullet.png";
    private final SpriteProperties SPRITE_PROPS = new SpriteProperties(4, 0, 0);

    public Bullet(MovableEntity e) {
        weapon = "Gun";
        cooldown = 50;
        setSpeed(12);
        action = Action.IDLE;
        attackProperties = new AttackProperties(50, 25);
        this.direction = e.getDirection();
        initialize(e);
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
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

}
