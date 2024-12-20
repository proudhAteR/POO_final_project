package city_cleaner.Entities.Bonus;

import Doctrina.Physics.Position;
import city_cleaner.Entities.Player;

public abstract class TemporaryBonus extends Bonus {
    protected long duration;

    public TemporaryBonus(Position pos, int value) {
        super(pos, value);
    }

    public long getDuration() {
        return duration;
    }
    
    @Override
    public void disaffect(Player player) {
    }
}
