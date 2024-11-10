package Doctrina.Entities;

import java.awt.Rectangle;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

import Doctrina.Controllers.Direction;
import Doctrina.Core.Step;
import Doctrina.Physics.Collision;
import Doctrina.Physics.Position;
import Doctrina.Rendering.Canvas;
import Doctrina.Rendering.ResourcesManager;
import Doctrina.Rendering.SpriteProperties;

public abstract class MovableEntity extends StaticEntity {

    protected int speed = 0;
    private static final int ANIMATION_SPEED = 10;
    protected int currentAnimationFrame = 1;
    protected int nextFrame = ANIMATION_SPEED;
    protected Direction direction = Direction.DOWN;
    private int lastX = Integer.MIN_VALUE;
    private int lastY = Integer.MIN_VALUE;
    private boolean moved;
    private final Collision collision;
    protected ArrayList<Step> traces;

    private final Map<Action, Image[][]> actionFrames = new EnumMap<>(Action.class);
    private final ResourcesManager resourcesManager;

    protected BufferedImage image;

    public MovableEntity() {
        traces = new ArrayList<>();
        collision = new Collision(this);
        resourcesManager = new ResourcesManager();
        action = Action.IDLE;
    }

    public ArrayList<Step> getSteps() {
        return traces;
    }

    public void update() {
        moved = false;
    };

    public void move() {
        if (!isAttacking()) {
            int allowedSpeed = collision.getAllowedSpeed();
            position.addX(direction.calculateVelocityX(allowedSpeed));
            position.addY(direction.calculateVelocityY(allowedSpeed));
            moved = (position.getX() != lastX || position.getY() != lastY);
            action = hasMoved() ? Action.MOVE : Action.IDLE;

            lastX = position.getX();
            lastY = position.getY();
        }

    }
    protected void checkMovement() {
        if (isAttacking() || hasMoved()) {
            updateAnimation();
        } else {
            resetAnimationFrame();
        }

    }

    private void updateAnimation() {
        nextFrame--;
        if (isNextFrameZero()) {
            updateAnimationFrame();
        }
    }

    protected void resetAnimationFrame() {
        action = Action.IDLE;
        this.currentAnimationFrame = 1;
    }

    protected boolean isNextFrameZero() {
        return nextFrame == 0;
    }

    protected void updateAnimationFrame() {
        currentAnimationFrame++;
        if (currentAnimationFrame >= actionFrames.get(this.action)[0].length) {
            currentAnimationFrame = 0;
            if (isAttacking()) {
                setAction(Action.IDLE);
            }
        }
        nextFrame = ANIMATION_SPEED;
    }

    protected boolean isAttacking() {
        return action == Action.ATTACK;
    }

    protected boolean isIdle() {
        return action == Action.ATTACK;
    }

    protected boolean isMoving() {
        return action == Action.ATTACK;
    }

    protected void loadAnimationFrames(SpriteProperties properties, Action action) {
        Image[][] frames = new Image[4][];
        for (int i = 0; i < Direction.values().length; i++) {
            frames[i] = resourcesManager.extractFrames(properties.getFrameCount(), properties.getXOff(),
                    properties.getYOff(), this, image);
            properties.setYOff(this.size.getHeight());
        }
        actionFrames.put(action, frames);
        resetImage();
    }

    protected void loadSpriteSheet(String spritePath) {
        image = (BufferedImage) resourcesManager.getImage(spritePath);
    }

    private void resetImage() {
        image = null;
    }

    public void move(Direction direction) {
        this.direction = direction;
        move();
    }

    public void setAction(Action action) {
        this.action = action;
    }

    protected void moveTo(Position position) {
        int dx = this.position.getX() - position.getX();
        int dy = this.position.getY() - position.getY();

        this.direction = (Math.abs(dx) > Math.abs(dy)) ? (dx > 0 ? Direction.LEFT : Direction.RIGHT)
                : (dy > 0 ? Direction.UP : Direction.DOWN);

        move(direction);
    }

    public void moveUp() {
        move(Direction.UP);
    }

    public void moveLeft() {
        move(Direction.LEFT);
    }

    public void moveDown() {
        move(Direction.DOWN);
    }

    public void moveRight() {
        move(Direction.RIGHT);
    }

    public Rectangle getHitBox() {
        if (isGoingUp()) {
            return getUpperHitBox();
        }
        if (isGoingDown()) {
            return getLowerHitBox();
        }
        if (isGoingLeft()) {
            return getLeftHitBox();
        }
        if (isGoingRight()) {
            return getRightHitBox();
        }
        return getBounds();
    }

    @Override
    public void draw(Canvas canvas) {
        // drawHitBox(canvas, Color.PINK);
        drawFrames(canvas, actionFrames.get(this.action));
    }

    private void drawFrames(Canvas canvas, Image[][] frames) {
        switch (direction) {
            case DOWN -> canvas.drawImage(frames[0][currentAnimationFrame], position);
            case UP -> canvas.drawImage(frames[1][currentAnimationFrame], position);
            case RIGHT -> canvas.drawImage(frames[2][currentAnimationFrame], position);
            case LEFT -> canvas.drawImage(frames[3][currentAnimationFrame], position);

        }
    }

    private Rectangle getUpperHitBox() {
        return new Rectangle(position.getX(), position.getY() - speed, size.getWidth(), speed);
    }

    private Rectangle getLowerHitBox() {
        return new Rectangle(position.getX(), position.getY() + size.getHeight(), size.getWidth(), speed);
    }

    private Rectangle getLeftHitBox() {
        return new Rectangle(position.getX() - speed, position.getY(), speed, size.getHeight());
    }

    private Rectangle getRightHitBox() {
        return new Rectangle(position.getX() + size.getWidth(), position.getY(), speed, size.getHeight());
    }

    public boolean intersectsWith(StaticEntity other) {
        if (other == null) {
            return false;
        }
        return getHitBox().intersects(other.getBounds());
    }

    private boolean isGoingUp() {
        return direction == Direction.UP;
    }

    private boolean isGoingDown() {
        return direction == Direction.DOWN;
    }

    private boolean isGoingLeft() {
        return direction == Direction.LEFT;
    }

    private boolean isGoingRight() {
        return direction == Direction.RIGHT;
    }

    public boolean hasMoved() {
        return moved;
    }

    public int getSpeed() {
        return speed;
    }

    public int setSpeed(int speed) {
        return this.speed = speed;
    }

    public Direction getDirection() {
        return direction;
    }

    public Direction setDirection(Direction direction) {
        return this.direction = direction;
    }
}
