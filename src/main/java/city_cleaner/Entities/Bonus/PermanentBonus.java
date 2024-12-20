package city_cleaner.Entities.Bonus;

import Doctrina.Physics.Position;
import city_cleaner.Entities.Player;

public abstract class PermanentBonus extends Bonus {

    public PermanentBonus(Position pos, int value) {
        super(pos, value);
    }

    @Override
    public void disaffect(Player player) {
    }
}
