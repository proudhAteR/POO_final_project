package Doctrina.Entities;

import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import Doctrina.Controllers.Direction;
import Doctrina.Core.GameConfig;
import Doctrina.Entities.Properties.Action;
import Doctrina.Entities.Properties.AttackProperties;
import Doctrina.Entities.Properties.Sight;
import Doctrina.Entities.Properties.Step;
import Doctrina.Physics.Collision;
import Doctrina.Physics.Position;
import Doctrina.Rendering.Canvas;
import Doctrina.Rendering.ResourcesManager;
import Doctrina.Rendering.SpriteProperties;
import city_cleaner.Entities.Player;
import city_cleaner.Entities.Bonus.Bonus;

public abstract class MovableEntity extends StaticEntity {
    protected int speed = 0;
    private long lastActionTime = 0;
    private final int BASE_MOVEMENT_TIME = 20;
    protected int lastMovementTime = BASE_MOVEMENT_TIME;
    private static final long COOLDOWN_TIME = 1000;
    protected static final int ANIMATION_SPEED = 10;
    protected int currentAnimationFrame = 1;
    protected int nextFrame = ANIMATION_SPEED;
    protected Direction direction = Direction.DOWN;
    private int lastX = Integer.MIN_VALUE;
    private int lastY = Integer.MIN_VALUE;
    private boolean moved;
    private final Collision collision;
    protected ArrayList<Step> traces;

    private final Map<Action, Image[][]> actionFrames = new EnumMap<>(Action.class);
    private final List<Image[][]> attackFrames = new ArrayList<>();
    protected BufferedImage image;

    public MovableEntity() {
        sight = new Sight(this);
        sight.setSize(size);
        traces = new ArrayList<>();
        collision = new Collision(this);
        action = Action.IDLE;
    }

    public Image[][] getAttackAt(int i) {
        return attackFrames.get(i);
    }

    public ArrayList<Step> getSteps() {
        return traces;
    }

    public void update() {
        moved = false;
    };

    public boolean touched(StaticEntity e) {
        if (intersectsWith(e)) {
            return true;
        }
        return false;
    }

    public void receiveAttack(AttackProperties props) {
        getHurt(props.getDamage());
        checkDeath();

        if (!isDying() && props.getRange() > 1) {
            jumpBack(250, props.getForce());
        }
    }

    public void jumpBack(int duration, int force) {
        int startX = position.getX();
        int startY = position.getY();
        int targetX = position.getX() + getOppositeDirection().calculateVelocityX(speed) * force;
        int targetY = position.getY() + getOppositeDirection().calculateVelocityY(speed) * force;

        long startTime = System.currentTimeMillis();
        Position start = new Position(startX, startY);
        Position target = new Position(targetX, targetY);

        smoothTeleport(duration, startTime, start, target);
    }

    // !ChatGPT
    private void smoothTeleport(int duration, long startTime, Position start, Position target) {
        new Thread(() -> {
            long elapsed;
            Position position;
            do {
                elapsed = System.currentTimeMillis() - startTime;
                float t = Math.min(1, elapsed / (float) duration);

                position = lerp(t, start, target);
                teleport(position.getX(), position.getY());

                try {
                    Thread.sleep(16); // ~60 updates per second
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            } while (elapsed < duration);

            teleport(target.getX(), target.getY());
        }).start();
    }

    private Position lerp(float t, Position startPosition, Position targetPosition) {

        int interpolatedX = (int) (startPosition.getX() + (targetPosition.getX() - startPosition.getX()) * t);
        int interpolatedY = (int) (startPosition.getY() + (targetPosition.getY() - startPosition.getY()) * t);

        return new Position(interpolatedX, interpolatedY);
    }

    public void closeAttack() {
        long currentTime = System.currentTimeMillis();

        if (!canApplyDamage()) {
            return;
        }
        attack(0);
        lastActionTime = currentTime;
    }

    public void attack(int i) {
        if (isDying()) {
            return;
        }
        setAction(Action.ATTACKING);
        actionFrames.put(action, getAttackAt(i));
    }

    public boolean canApplyDamage() {
        return (System.currentTimeMillis() - lastActionTime) >= COOLDOWN_TIME;
    }

    public void die() {
        setAction(Action.DYING);
    }

    public void move() {
        if (!isAttacking() && !isDying()) {
            int allowedSpeed = collision.getAllowedSpeed();
            position.addX(direction.calculateVelocityX(allowedSpeed));
            position.addY(direction.calculateVelocityY(allowedSpeed));
            moved = (position.getX() != lastX || position.getY() != lastY);
            lastX = position.getX();
            lastY = position.getY();
        }

    }

    private void resetMovementTime() {
        lastMovementTime = BASE_MOVEMENT_TIME;
    }

    private void reduceMovementTime() {
        lastMovementTime--;
    }

    private int getLastMovementTime() {
        return lastMovementTime;
    }

    public void handleMovement() {
        if (hasMoved()) {
            reduceMovementTime();
        }
        if (getLastMovementTime() == 0) {
            getSteps().add(new Step(new Position(getX(), getY()), this, isHuman() ? Color.pink : Color.yellow));
            resetMovementTime();
            if (getSteps().size() > getSpeed() + 2) {
                getSteps().removeFirst();
            }
        }

    }

    public boolean isHuman() {
        return this instanceof Player;
    }

    protected void moveTo(Position position) {
        int dx = position.getX() - this.position.getX();
        int dy = position.getY() - this.position.getY();

        this.direction = (Math.abs(dx) > Math.abs(dy)) ? (dx > 0 ? Direction.RIGHT : Direction.LEFT)
                : (dy > 0 ? Direction.DOWN : Direction.UP);

        moveTowards(direction);
    }

    protected void updateAnimation() {
        nextFrame--;
        if (!isAttacking() && !isDying()) {
            setAction(hasMoved() ? Action.MOVING : Action.IDLE);
        }
        if (isNextFrameZero()) {
            updateAnimationFrame();
        }

    }

    @Override
    public void setAction(Action action) {
        if (action != this.action) {
            super.setAction(action);
            nextFrame = ANIMATION_SPEED;
            currentAnimationFrame = 0;
        }
    }

    protected void resetAnimationFrame() {
        setAction(Action.IDLE);
        nextFrame = ANIMATION_SPEED;
        currentAnimationFrame = 0;
    }

    protected boolean isNextFrameZero() {
        return nextFrame <= 0;
    }

    private void updateAnimationFrame() {
        currentAnimationFrame++;
        if (isLastAnimationFrame()) {
            if (isDying()) {
                currentAnimationFrame--;
                isDead = true;
            } else {
                resetAnimationFrame();
            }
        }
        nextFrame = ANIMATION_SPEED;
    }

    @Override
    public void draw(Canvas canvas) {
        if (GameConfig.debugMode()) {
            drawHitBox(canvas);
        }
        drawFrames(canvas, actionFrames.get(this.action));
    }

    private void drawFrames(Canvas canvas, Image[][] frames) {
        int upIndex = isProjetable() ? 0 : 1;
        int downIndex = isProjetable() ? 1 : 0;

        switch (direction) {
            case UP -> canvas.drawImage(frames[upIndex][currentAnimationFrame], position);
            case DOWN -> canvas.drawImage(frames[downIndex][currentAnimationFrame], position);
            case RIGHT -> canvas.drawImage(frames[2][currentAnimationFrame], position);
            case LEFT -> canvas.drawImage(frames[3][currentAnimationFrame], position);
            default -> canvas.drawImage(frames[upIndex][currentAnimationFrame], position);
        }
    }

    private boolean isProjetable() {
        return this instanceof Projectile;
    }

    public boolean isLastAnimationFrame() {
        return currentAnimationFrame >= actionFrames.get(this.action)[0].length;
    }

    protected void loadActions(SpriteProperties properties, Action action) {
        Image[][] frames = loadFrames(properties);
        actionFrames.put(action, frames);
    }

    private Image[][] loadFrames(SpriteProperties properties) {
        Image[][] frames = new Image[4][];
        int yOffset = properties.getYOff();
        for (int i = 0; i < 4; i++) {
            frames[i] = ResourcesManager.getInstance().extractFrames(properties.getFrameCount(), properties.getXOff(),
                    yOffset, this, image);
            yOffset += this.size.getHeight();

        }
        return frames;
    }

    protected void loadAttacks(SpriteProperties properties) {
        Image[][] frames = loadFrames(properties);
        attackFrames.add(frames);
    }

    protected void loadSpriteSheet(String spritePath) {
        image = (BufferedImage) ResourcesManager.getInstance().getImage(spritePath);
    }

    public void moveTowards(Direction direction) {
        this.direction = direction;
        move();
    }

    public void moveUp() {
        moveTowards(Direction.UP);
    }

    public void moveLeft() {
        moveTowards(Direction.LEFT);
    }

    public void moveDown() {
        moveTowards(Direction.DOWN);
    }

    public void moveRight() {
        moveTowards(Direction.RIGHT);
    }

    public Rectangle getHitBox() {

        return switch (direction) {
            case UP -> getUpperHitBox();
            case DOWN -> getLowerHitBox();
            case LEFT -> getLeftHitBox();
            case RIGHT -> getRightHitBox();
            default -> getBounds();
        };
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

    public boolean intersectsWith(Bonus b) {
        if (b == null) {
            return false;
        }

        Ellipse2D ellipse = b.getBounds();
        if (ellipse == null) {
            return false;
        }

        Rectangle2D ellipseBounds = ellipse.getBounds2D();
        return getBounds().intersects(ellipseBounds);
    }

    public Direction getOppositeDirection() {
        return switch (direction) {
            case UP -> Direction.DOWN;
            case DOWN -> Direction.UP;
            case LEFT -> Direction.RIGHT;
            case RIGHT -> Direction.LEFT;
            default -> Direction.NONE;
        };
    }

    public boolean intersectsWith(Step step) {
        if (step == null) {
            return false;
        }
        return getHitBox().intersects(step.getBounds());
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
