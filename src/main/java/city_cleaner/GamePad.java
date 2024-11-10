package city_cleaner;

import java.awt.event.KeyEvent;
import Doctrina.Controllers.MovementController;
import java.awt.event.MouseEvent;

public class GamePad extends MovementController{
    private int quitKey = KeyEvent.VK_Q;
    private int fireKey = MouseEvent.BUTTON1;
    private int debug = KeyEvent.VK_1;
    public GamePad(){
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
        return isMousePressed(fireKey);
    }
}