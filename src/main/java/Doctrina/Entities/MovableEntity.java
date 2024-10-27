package Doctrina.Entities;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Image;
import java.awt.image.BufferedImage;

import Doctrina.Controllers.Direction;
import Doctrina.Physics.Collision;
import Doctrina.Rendering.Canvas;
import Doctrina.Rendering.ResourcesManager;
import Doctrina.Rendering.SpriteProperties;

public abstract class MovableEntity extends StaticEntity {

    protected int speed = 5;
    private static final int ANIMATION_SPEED = 8;

    protected int currentAnimationFrame = 1;
    protected int nextFrame = ANIMATION_SPEED;
    protected Direction direction = Direction.RIGHT;
    private int lastX = Integer.MIN_VALUE;
    private int lastY = Integer.MIN_VALUE;
    private boolean moved;
    private Collision collision;

    protected Image[][] frames = new Image[4][];
    private ResourcesManager resourcesManager;

    private BufferedImage image;

    public MovableEntity() {
        collision = new Collision(this);
        resourcesManager = new ResourcesManager();
    }

    public void update() {
        moved = false;
    };

    public void move() {
        int allowedSpeed = collision.getAllowedSpeed();
        x += direction.calculateVelocityX(allowedSpeed);
        y += direction.calculateVelocityY(allowedSpeed);

        moved = (x != lastX || y != lastY);

        lastX = x;
        lastY = y;
    }

    protected void resetAnimationFrame() {
        this.currentAnimationFrame = 1;
    }

    protected boolean isNextFrameZero() {
        return nextFrame == 0;
    }

    protected void updateAnimationFrame() {
        currentAnimationFrame++;
        if (currentAnimationFrame >= frames[0].length) {
            currentAnimationFrame = 0;
        }
        nextFrame = ANIMATION_SPEED;
    }

    protected void loadAnimationFrames(SpriteProperties properties) {
        for (int i = 0; i < Direction.values().length; i++) {
            frames[i] = resourcesManager.extractFrames(properties.getFrameCount(), properties.getXOff(),
                    properties.getYOff(), this, image);
            properties.setYOff(this.height);
        }
    }

    protected void loadSpriteSheet(String spritePath) {
        image = (BufferedImage) resourcesManager.getImage(spritePath);
    }

    public void move(Direction direction) {
        this.direction = direction;
        move();
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

    protected void checkMovement() {
        if (hasMoved()) {
            this.nextFrame--;
            if (isNextFrameZero()) {
                updateAnimationFrame();
            }
        } else {
            resetAnimationFrame();
        }
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
    public void drawHitBox(Canvas canvas, Color color) {
        Rectangle rect = getHitBox();

        canvas.drawRectangle(rect, color);
    }

    @Override
    public void draw(Canvas canvas) {

        switch (direction) {
            case DOWN -> canvas.drawImage(frames[0][currentAnimationFrame], x, y);
            case LEFT -> canvas.drawImage(frames[1][currentAnimationFrame], x, y);
            case RIGHT -> canvas.drawImage(frames[2][currentAnimationFrame], x, y);
            case UP -> canvas.drawImage(frames[3][currentAnimationFrame], x, y);

        }
    }

    private Rectangle getUpperHitBox() {
        return new Rectangle(x, y - speed, width, speed);
    }

    private Rectangle getLowerHitBox() {
        return new Rectangle(x, y + height, width, speed);
    }

    private Rectangle getLeftHitBox() {
        return new Rectangle(x - speed, y, speed, height);
    }

    private Rectangle getRightHitBox() {
        return new Rectangle(x + width, y, speed, height);
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
