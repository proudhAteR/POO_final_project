package city_cleaner;

import Doctrina.Controllers.MovementController;
import Doctrina.Rendering.SpriteProperties;
import Doctrina.Entities.ControllableEntity;
import Doctrina.Physics.Position;
import Doctrina.Physics.Size;

import java.awt.Color;

public class Player extends ControllableEntity {
    protected static final String SPRITE_PATH = "images/sprite_sheets/male.png";
    private SpriteProperties properties;

    public Player(MovementController controller) {
        super(controller);
        position = new Position(400, 300);
        teleport(position);
        size = new Size(16, 16);
        setDimension(size);
        properties = new SpriteProperties(3, 16, 0);
        setSpeed(3);
        load();
    }

    public void load() {
        loadSpriteSheet(SPRITE_PATH);
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
