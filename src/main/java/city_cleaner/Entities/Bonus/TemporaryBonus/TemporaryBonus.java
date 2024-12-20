package city_cleaner.Entities.Bonus.TemporaryBonus;

import java.util.Timer;
import java.util.TimerTask;

import Doctrina.Physics.Position;
import city_cleaner.Entities.Player;
import city_cleaner.Entities.Bonus.Bonus;

public abstract class TemporaryBonus<P> extends Bonus {
    protected int duration; // value in seconds
    protected int remainingDur;
    protected P prev;
    private long lastUpdateTime;


    public TemporaryBonus(Position pos, int value, int duration) {
        super(pos, value);
        this.duration = duration * 1000;
        this.remainingDur = this.duration;
        this.lastUpdateTime = System.currentTimeMillis();
    }

    public int getRemainingDur() {
        return remainingDur / 1000;
    }

    public void update() {
        long currentTime = System.currentTimeMillis();
        int elapsed = (int) (currentTime - lastUpdateTime);
        remainingDur = Math.max(0, remainingDur - elapsed);
        lastUpdateTime = currentTime;
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
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (remainingDur > 0) {
                    update();
                } else {
                    disaffect(player);
                    timer.cancel();
                }
            }
        }, 0, 1000);
    }

    public void setPrev(P prev) {
        this.prev = prev;
    }

    public P getPrev() {
        return prev;
    }

}
