package city_cleaner;

import Doctrina.Controllers.MovementController;
import Doctrina.Entities.Action;
import Doctrina.Entities.Collidable;
import Doctrina.Rendering.SpriteProperties;
import Doctrina.Entities.ControllableEntity;
import Doctrina.Entities.Sight;
import Doctrina.Physics.Position;
import Doctrina.Physics.Size;
import java.awt.Color;

public class Player extends ControllableEntity implements Collidable {
    protected final String[] SPRITE_PATHS = {
            "images/sprite_sheets/walk.png",
            "images/sprite_sheets/shoot.png",
            "images/sprite_sheets/stab.png",
            "images/sprite_sheets/idle.png"
    };
    protected final SpriteProperties[] SPRITE_PROPS = {
            new SpriteProperties(4, 32, 0),
            new SpriteProperties(4, 32, 0),
            new SpriteProperties(4, 32, 0),
            new SpriteProperties(2, 32, 0)
    };

    public Player(MovementController controller) {
        super(controller);
        canCollide(this);
        position = new Position(0, 0);
        teleport(position);
        size = new Size(32, 32);
        sight = new Sight(this.size, this);
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

    public void update() {
        super.update();
        moveWithController();
        checkMovement();
        handleMovement();
    }

}
