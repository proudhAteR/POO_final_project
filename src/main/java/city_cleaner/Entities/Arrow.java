package city_cleaner.Entities;

import Doctrina.Entities.MovableEntity;
import Doctrina.Entities.Properties.Action;
import Doctrina.Entities.Properties.AttackProperties;
import Doctrina.Entities.Properties.Projectile;
import Doctrina.Rendering.Canvas;
import Doctrina.Rendering.SpriteProperties;

public class Arrow extends Projectile {
    private final String SPRITE_PATH = "images/sprite_sheets/player/Arrow.png";
    private final SpriteProperties SPRITE_PROPS = new SpriteProperties(9, 0, 0);

    public Arrow(MovableEntity e) {
        setSpeed(10);
        action = Action.IDLE;
        attackProperties = new AttackProperties(50, 15);
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
