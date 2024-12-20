package city_cleaner.Entities.Bonus.TemporaryBonus;

import java.awt.Color;

import Doctrina.Entities.Properties.Sight;
import Doctrina.Physics.Position;
import city_cleaner.Entities.Player;

public final class SightBonus extends TemporaryBonus<Sight> {

    public SightBonus(Position pos, int value, int duration) {
        super(pos, value, duration);
        this.color = Color.cyan;
        this.name = "Eagle Vision";
    }

    @Override
    public void affect(Player player) {
        Sight currentSight = player.getSight();
        if (prev == null) {
            prev = new Sight(currentSight);
        }

        currentSight.setSize(currentSight.getSize().multiply(value));
        player.setSight(currentSight);
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
