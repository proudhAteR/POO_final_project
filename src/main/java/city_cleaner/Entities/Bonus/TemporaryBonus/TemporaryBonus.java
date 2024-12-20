package city_cleaner.Entities.Bonus.TemporaryBonus;

import java.util.Timer;
import java.util.TimerTask;

import Doctrina.Physics.Position;
import city_cleaner.Entities.Player;
import city_cleaner.Entities.Bonus.Bonus;

public abstract class TemporaryBonus<P> extends Bonus {
    protected int duration; // value in seconds
    protected P prev;
    public TemporaryBonus(Position pos, int value, int duration) {
        super(pos, value);
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public void affect(Player player) {
        startTimer(player);
    }

    @Override
    public void disaffect(Player player) {
    }

    private void startTimer(Player player) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                disaffect(player);
                timer.cancel();
            }
        }, duration * 1000);
    }
    public void setPrev(P prev) {
        this.prev = prev;
    }
    public P getPrev() {
        return prev;
    }
}
