package city_cleaner.Entities.Bonus;

import java.awt.Color;

import Doctrina.Entities.Properties.Sight;
import Doctrina.Physics.Position;
import city_cleaner.Entities.Player;

public class SightBonus extends TemporaryBonus {
    private Sight prev;

    public SightBonus(Position pos, int value) {
        super(pos, value);
        this.color = Color.cyan;
    }

    @Override
    public void affect(Player player) {
        Sight s = player.getSight();
        prev = s;
        s.setSize(s.getSize().multiply(value));
        player.getSight().setSize(s.getSize());
        super.affect(player);
    }

    @Override
    public void disaffect(Player player) {
        super.disaffect(player);
        player.getSight().setSize(prev.getSize());
    }

}
