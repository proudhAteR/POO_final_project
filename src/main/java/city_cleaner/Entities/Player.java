package city_cleaner.Entities;

import Doctrina.Controllers.MovementController;
import Doctrina.Rendering.Canvas;
import Doctrina.Rendering.SpriteProperties;
import Doctrina.Rendering.UI.Bar;
import city_cleaner.Entities.Projectile.Arrow;
import city_cleaner.Entities.Projectile.Bullet;
import Doctrina.Entities.ControllableEntity;
import Doctrina.Entities.Projectile;
import Doctrina.Entities.Properties.Action;
import Doctrina.Entities.Properties.Collidable;
import Doctrina.Entities.Properties.Sight;
import Doctrina.Physics.Position;
import Doctrina.Physics.Size;
import java.awt.Color;
import java.util.Collection;

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
    private int weaponIndex = 1;
    private Bar healthBar;
    private Bar healthHolderBar;
    private Bar coolDownBar;
    private final int BASE_WIDTH = 200;
    private Projectile proj;
    private String weapon;
    private final int BASE_HEALTH;

    public Player(MovementController controller) {
        super(controller);
        BASE_HEALTH = 100;
        healthHolderBar = new Bar(new Position(20, 20), new Size(BASE_WIDTH, 20), Color.red);
        healthBar = new Bar(new Position(20, 20), new Size(BASE_WIDTH, 20), Color.green);
        coolDownBar = new Bar(new Position(20, 48), new Size(BASE_WIDTH, 20), Color.pink);
        canCollide(this);
        setHealth(BASE_HEALTH);
        position = new Position(300, 400);
        teleport(position);
        size = new Size(32, 32);
        sight = new Sight(this);
        sight.setSize(this.size.multiply(8));
        setDimension(size);
        setSpeed(4);
        proj = new Bullet(this);
        weapon = proj.getWeapon();
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

    public int getBASE_HEALTH() {
        return BASE_HEALTH;
    }

    public Projectile fire() {
        proj = getProj();
        cooldown = proj.getCooldown();
        return proj;
    }

    private Projectile getProj() {
        return usesGun() ? new Bullet(this) : new Arrow(this);
    }

    public boolean usesGun() {
        return currentWeapon() == 1;
    }

    public boolean canFire(Collection<Enemy> enemies) {
        return !isReloading() && !enemies.stream().anyMatch(this::intersectsWith);
    }

    public boolean isReloading() {
        return cooldown > 0;
    }

    public int currentWeapon() {
        return weaponIndex;
    }

    public void changeWeapon(int i) {
        if (i >= ATTACKS_PATHS.length) {
            return;
        }
        proj = getProj();
        weapon = proj.getWeapon();
        weaponIndex = i;
    }

    public void update() {
        super.update();
        moveWithController();
        cooldown = isReloading() ? --cooldown : cooldown;
        updateAnimation();
        handleMovement();
    }

    public String getWeapon() {
        return weapon;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    public void drawShootCoolDown(Canvas canvas) {
        int cooldownWidth = (cooldown * BASE_WIDTH) / proj.getCooldown();
        coolDownBar.getSize().setWidth(cooldownWidth);
        coolDownBar.draw(canvas);
    }

    public void drawHealthBar(Canvas canvas) {
        int boundedHealth = Math.max(0, Math.min(health, BASE_HEALTH));
        int healthWidth = (boundedHealth * BASE_WIDTH) / BASE_HEALTH;

        healthBar.getSize().setWidth(healthWidth);

        healthHolderBar.draw(canvas);
        healthBar.draw(canvas);
    }

    public Bar getHealthBar() {
        return healthBar;
    }

    public Bar getHealthHolderBar() {
        return healthHolderBar;
    }

    public Bar getCoolDownBar() {
        return coolDownBar;
    }

}
