package city_cleaner.Entities.Bonus;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

import Doctrina.Physics.Position;
import Doctrina.Rendering.Canvas;
import city_cleaner.Entities.Player;

public abstract class Bonus {
    protected Position position;
    protected String name;
    protected boolean isFound;
    protected int value;
    protected Color color;
    private Timer timer;

    protected Bonus(Position position, int value) {
        this.isFound = false;
        this.position = position;
        this.value = value;
        this.color = Color.gray;
        timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!isFound) {
                    removeBonus(Bonus.this);
                }
            }
        }, 10000);
    }

    public Position getPosition() {
        return position;
    }

    public Color getColor() {
        return color;
    }

    public void place(Canvas canvas) {
        if (!isFound) {
            canvas.drawCircle(getBounds(), color);
        }
    }

    public Ellipse2D getBounds() {
        return new Ellipse2D.Double(
                position.getX(), position.getY(),
                6, 6);
    }

    public boolean isFound() {
        return isFound;
    }

    public abstract void affect(Player player);

    public abstract void disaffect(Player player);

    public void setFound(boolean isFound) {
        this.isFound = isFound;
        if (isFound) {
            timer.cancel();
        }
    }

    public int getValue() {
        return value;
    }

    public static void removeBonus(Bonus bonus) {
        BonusesRepository.getInstance().unregisterBonus(bonus);
    }

    public static void removeBonus(Collection<Bonus> bonuses) {
        BonusesRepository.getInstance().unregisterBonuses(bonuses);
    }
}
