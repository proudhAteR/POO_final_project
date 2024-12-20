package city_cleaner.Entities.Bonus;

import java.awt.Color;

import Doctrina.Entities.Properties.Sight;
import Doctrina.Physics.Position;
import city_cleaner.Entities.Player;

public class SightBonus extends Bonus {
    private Sight prev;

    public SightBonus(Position pos, int value) {
        super(pos, value);
        this.color = Color.green;
    }

    @Override
    public void affect(Player player) {
        Sight s = player.getSight();
        prev = s;
        s.setSize(s.getSize().multiply(2));
        player.getSight().setSize(s.getSize());
    }

    @Override
    public void disaffect(Player player) {
        player.getSight().setSize(prev.getSize());
    }

}
