package city_cleaner.Entities.Bonus;

import java.awt.Color;

import Doctrina.Physics.Position;
import city_cleaner.Entities.Player;

public final class SpeedBonus extends TemporaryBonus {
    private int prev;

    public SpeedBonus(Position pos, int value) {
        super(pos, value);
        this.color = Color.yellow;
    }

    @Override
    public void affect(Player player) {
        int s = player.getSpeed();
        prev = s;
        player.setSpeed(s + value);
        super.affect(player);
    }

    @Override
    public void disaffect(Player player) {
        super.disaffect(player);
        player.setSpeed(prev);
    }

}
