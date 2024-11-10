package city_cleaner;

import java.awt.event.KeyEvent;
import Doctrina.Controllers.MovementController;

public class GamePad extends MovementController{
    private int quitKey = KeyEvent.VK_Q;
    private int fireKey = KeyEvent.VK_SPACE;
    private int debug = KeyEvent.VK_1;
    public GamePad(){
        bindKeys(fireKey);
        bindKeys(quitKey);
        bindKeys(debug);
    }
    public boolean isDebugPressed(){
        return isKeyPressed(debug);
    }
    public boolean isQuitPressed(){
        return isKeyPressed(quitKey);
    }
    public boolean isFirePressed(){
        return isKeyPressed(fireKey);
    }
}