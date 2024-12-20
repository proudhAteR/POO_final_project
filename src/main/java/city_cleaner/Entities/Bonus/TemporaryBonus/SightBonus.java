package city_cleaner.Entities.Bonus.TemporaryBonus;

import java.awt.Color;

import Doctrina.Entities.Properties.Sight;
import Doctrina.Physics.Position;
import city_cleaner.Entities.Player;

public final class SightBonus extends TemporaryBonus {
    private Sight prev;

    public SightBonus(Position pos, int value, int duration) {
        super(pos, value, duration);
        this.color = Color.cyan;
        this.name = "Eagle Vision";
    }

    @Override
    public void affect(Player player) {
        Sight s = player.getSight();
        prev = new Sight(player);
        prev.setSize(s.getSize());

        s.setSize(s.getSize().multiply(value));
        player.getSight().setSize(s.getSize());
        super.affect(player);
    }

    @Override
    public void disaffect(Player player) {
        if (prev != null) {
            player.setSight(prev);
        }
        super.disaffect(player);
    }

}
