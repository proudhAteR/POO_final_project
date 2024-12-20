package city_cleaner.Entities.Bonus;

import java.awt.Color;

import Doctrina.Physics.Position;
import city_cleaner.Entities.Player;

public class HealthBonus extends PermanentBonus {
    
    public HealthBonus(Position pos, int value) {
        super(pos, value);
        this.color = Color.green;
    }

    @Override
    public void affect(Player player) {
        int v = Math.min(player.getHealth() + value, player.getBASE_HEALTH());
        player.setHealth(v);
    }

    
}
