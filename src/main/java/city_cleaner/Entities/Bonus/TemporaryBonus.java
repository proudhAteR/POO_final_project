package city_cleaner.Entities.Bonus;

import Doctrina.Physics.Position;

public abstract class TemporaryBonus extends Bonus {
    protected long duration;

    public TemporaryBonus(Position pos, int value) {
        super(pos, value);
    }

    public long getDuration() {
        return duration;
    }

}
