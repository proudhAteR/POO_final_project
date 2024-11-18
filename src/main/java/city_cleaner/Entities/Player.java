package city_cleaner.Entities;

import Doctrina.Controllers.MovementController;
import Doctrina.Rendering.Canvas;
import Doctrina.Rendering.SpriteProperties;
import Doctrina.Entities.ControllableEntity;
import Doctrina.Entities.MovableEntity;
import Doctrina.Entities.Properties.Action;
import Doctrina.Entities.Properties.Collidable;
import Doctrina.Entities.Properties.Sight;
import Doctrina.Physics.Position;
import Doctrina.Physics.Size;
import java.awt.Color;

public class Player extends ControllableEntity implements Collidable {
    protected final String[] SPRITE_PATHS = {
            "images/sprite_sheets/player/walk.png",
            "images/sprite_sheets/player/shoot.png",
            "images/sprite_sheets/player/stab.png",
            "images/sprite_sheets/player/idle.png",
            "images/sprite_sheets/player/death.png"
    };
    protected final SpriteProperties[] SPRITE_PROPS = {
            new SpriteProperties(4, 32, 0),
            new SpriteProperties(4, 32, 0),
            new SpriteProperties(4, 32, 0),
            new SpriteProperties(2, 32, 0),
            new SpriteProperties(4, 32, 0)
    };
    private int cooldown = 0;
    private final int INITIAL_COOLDOWN;

    public Player(MovementController controller) {
        super(controller);
        health = 100;
        INITIAL_COOLDOWN = (int) (MovableEntity.ANIMATION_SPEED * 4);
        canCollide(this);
        position = new Position(0, 0);
        teleport(position);
        size = new Size(32, 32);
        sight = new Sight(this);
        sight.setSize(this.size.multiply(2));
        setDimension(size);
        setSpeed(4);
        load();
    }

    public void load() {
        for (Action action : Action.values()) {
            loadSpriteSheet(SPRITE_PATHS[action.ordinal()]);
            loadAnimationFrames(SPRITE_PROPS[action.ordinal()], action);
        }
    }

    public Player(MovementController controller, Color color) {
        this(controller);
        this.color = color;
    }

    public Bullet fire() {
        cooldown = INITIAL_COOLDOWN;
        return new Bullet(this);
    }

    public boolean canFire() {
        return cooldown == 0;
    }

    public void update() {
        super.update();
        moveWithController();
        cooldown = !canFire() ? --cooldown : cooldown;
        updateAnimation();
        handleMovement();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        int cooldownWidth = (cooldown * getWidth()) / INITIAL_COOLDOWN;
        if (!canFire()) {
            canvas.drawRectangle(this.getX(), getY() - 5, cooldownWidth, 2, Color.pink);
        }
    }

}
