package city_cleaner;

import Doctrina.Controllers.MovementController;
import Doctrina.Rendering.SpriteProperties;
import Doctrina.Entities.ControllableEntity;
import Doctrina.Physics.Position;
import Doctrina.Physics.Size;

import java.awt.Color;

public class Player extends ControllableEntity {
    protected static final String PLAYER_SPRITE_PATH = "images/sprite_sheets/walk.png";
    private final SpriteProperties properties;

    public Player(MovementController controller) {
        super(controller);
        position = new Position(0, 0);
        teleport(position);
        size = new Size(32, 32);
        setDimension(size);
        properties = new SpriteProperties(4, 32, 0);
        setSpeed(2);
        load();
    }

    public void load() {
        loadSpriteSheet(PLAYER_SPRITE_PATH);
        loadAnimationFrames(properties);
    }
    public Player(MovementController controller, Color color) {
        this(controller);
        this.color = color;
    }
    

    public void update() {
        super.update();
        moveWithController();
        checkMovement();
    }

}
