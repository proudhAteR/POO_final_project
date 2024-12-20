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
        player.setHealth(player.getHealth() + value);
    }

    
}
