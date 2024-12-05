package city_cleaner.Entities;

import Doctrina.Controllers.MovementController;
import Doctrina.Rendering.Canvas;
import Doctrina.Rendering.SpriteProperties;
import Doctrina.Entities.ControllableEntity;
import Doctrina.Entities.MovableEntity;
import Doctrina.Entities.Properties.Action;
import Doctrina.Entities.Properties.Collidable;
import Doctrina.Entities.Properties.Projectile;
import Doctrina.Entities.Properties.Sight;
import Doctrina.Physics.Position;
import Doctrina.Physics.Size;
import java.awt.Color;

public class Player extends ControllableEntity implements Collidable {
    private final String[] ATTACKS_PATHS = {
            "images/sprite_sheets/player/stab.png",
            "images/sprite_sheets/player/shoot.png",
            "images/sprite_sheets/player/Crossbow.png"
    };
    private final SpriteProperties[] ATTACKS_PROPS = {
            new SpriteProperties(4, 32, 0),
            new SpriteProperties(4, 32, 0),
            new SpriteProperties(6, 32, 0)
    };
    protected final String[] SPRITE_PATHS = {
            "images/sprite_sheets/player/walk.png",
            "images/sprite_sheets/player/idle.png",
            "images/sprite_sheets/player/death.png"
    };
    protected final SpriteProperties[] SPRITE_PROPS = {
            new SpriteProperties(4, 32, 0),
            new SpriteProperties(2, 32, 0),
            new SpriteProperties(4, 32, 0)
    };
    private int cooldown = 0;
    private final int GUN_COOLDOWN;
    private final int BOW_COOLDOWN;
    private int weaponIndex = 1;

    public Player(MovementController controller) {
        super(controller);
        GUN_COOLDOWN = (int) (MovableEntity.ANIMATION_SPEED * 6);
        BOW_COOLDOWN = (int) (MovableEntity.ANIMATION_SPEED * 8);
        canCollide(this);
        setHealth(100);
        position = new Position(0, 0);
        teleport(position);
        size = new Size(32, 32);
        sight = new Sight(this);
        sight.setSize(this.size.multiply(6));
        setDimension(size);
        setSpeed(4);
        load();
    }

    public void load() {
        for (Action action : Action.values()) {
            if (action == Action.ATTACKING) {
                loadSpriteSheet(ATTACKS_PATHS[0]);
                loadActions(ATTACKS_PROPS[0], action);
                continue;
            }
            loadSpriteSheet(SPRITE_PATHS[action.ordinal()]);
            loadActions(SPRITE_PROPS[action.ordinal()], action);
        }

        for (int i = 0; i < ATTACKS_PATHS.length; i++) {
            loadSpriteSheet(ATTACKS_PATHS[i]);
            loadAttacks(ATTACKS_PROPS[i]);
        }
    }

    public Player(MovementController controller, Color color) {
        this(controller);
        this.color = color;
    }

    public Projectile fire() {
        if (usesGun()) {
            cooldown = GUN_COOLDOWN;
            return new Bullet(this);
        }
        cooldown = BOW_COOLDOWN;
        return new Arrow(this);

    }

    private boolean usesGun() {
        return currentWeapon() == 1;
    }

    public boolean canFire() {
        return cooldown == 0;
    }

    public int currentWeapon() {
        return weaponIndex;
    }

    public void changeWeapon(int i) {
        if (i >= ATTACKS_PATHS.length) {
            return;
        }
        weaponIndex = i;
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
        int cooldownWidth = (cooldown * getWidth()) / GUN_COOLDOWN;
        if (!canFire()) {
            drawShootCoolDown(canvas, cooldownWidth);
        }
    }

    private void drawShootCoolDown(Canvas canvas, int cooldownWidth) {
        canvas.drawRectangle(this.getX(), getY() - 5, cooldownWidth, 2, Color.pink);
    }

}
