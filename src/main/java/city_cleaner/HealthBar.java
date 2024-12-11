package city_cleaner;

import java.awt.Color;

import Doctrina.Physics.Position;
import Doctrina.Physics.Size;
import Doctrina.Rendering.UI.Bar;

public class HealthBar extends Bar {

    public HealthBar(Position pos, Size size) {
        super(pos, size, Color.green);
    }

    @Override
    public void update() {

    }
}