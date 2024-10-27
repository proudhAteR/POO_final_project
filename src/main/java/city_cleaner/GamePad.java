package city_cleaner;

import java.awt.event.KeyEvent;
import Doctrina.Controllers.MovementController;

public class GamePad extends MovementController{
    private int quitKey = KeyEvent.VK_Q;
    private int fireKey = KeyEvent.VK_SPACE;
    public GamePad(){
        bindKeys(fireKey);
        bindKeys(quitKey);
    }

    public boolean isQuitPressed(){
        return isKeyPressed(quitKey);
    }
    public boolean isFirePressed(){
        return isKeyPressed(fireKey);
    }
}