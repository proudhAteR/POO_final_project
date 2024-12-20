package city_cleaner.Entities.Bonus.TemporaryBonus;

import java.util.Timer;
import java.util.TimerTask;

import Doctrina.Physics.Position;
import city_cleaner.Entities.Player;
import city_cleaner.Entities.Bonus.Bonus;

public abstract class TemporaryBonus extends Bonus {
    protected int duration; // value in seconds

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
}
