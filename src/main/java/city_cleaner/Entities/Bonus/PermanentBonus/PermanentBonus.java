package city_cleaner.Entities.Bonus.PermanentBonus;

import Doctrina.Physics.Position;
import city_cleaner.Entities.Player;
import city_cleaner.Entities.Bonus.Bonus;

public abstract class PermanentBonus extends Bonus {

    public PermanentBonus(Position pos, int value) {
        super(pos, value);
    }

    @Override
    public void disaffect(Player player) {
    }
}
