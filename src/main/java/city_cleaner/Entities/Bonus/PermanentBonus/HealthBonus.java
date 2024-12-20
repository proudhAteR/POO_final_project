package city_cleaner.Entities.Bonus.PermanentBonus;

import java.awt.Color;

import Doctrina.Physics.Position;
import city_cleaner.Entities.Player;

public final class HealthBonus extends PermanentBonus {
    
    public HealthBonus(Position pos, int value) {
        super(pos, value);
        this.color = Color.green;
        this.name = "Iron Constitution";
    }

    @Override
    public void affect(Player player) {
        int v = Math.min(player.getHealth() + value, player.getBASE_HEALTH());
        player.setHealth(v);
    }

    
}
