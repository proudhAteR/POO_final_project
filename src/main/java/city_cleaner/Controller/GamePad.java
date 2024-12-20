package city_cleaner.Controller;

import java.util.List;
import java.awt.event.KeyEvent;
import Doctrina.Controllers.MovementController;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GamePad extends MovementController {
    private int quitKey = KeyEvent.VK_ESCAPE;
    private int fireKey = MouseEvent.BUTTON1;
    private int stabKey = MouseEvent.BUTTON2;
    private int debug = KeyEvent.VK_0;
    private List<Integer> weaponKeys = new ArrayList<Integer>();

    public GamePad() {
        bindKeys(quitKey);
        bindKeys(debug);
        for (int i = KeyEvent.VK_1; i <= KeyEvent.VK_9; i++) {
            weaponKeys.add(i);
        }
        bindKeys(weaponKeys);
        bindKeys(debug);
        useWASDKeys();
    }

    public List<Integer> getWeaponKeys() {
        return weaponKeys;
    }

    public boolean isDebugPressed() {
        return isKeyPressed(debug);
    }

    public boolean isQuitPressed() {
        return isKeyPressed(quitKey);
    }

    public boolean isFirePressed() {
        return isMousePressed(fireKey);
    }

    public boolean isStabPressed() {
        return isMousePressed(stabKey);
    }

    public boolean isWeaponKeyPressed() {
        for (Integer key : weaponKeys) {
            if (isKeyPressed(key)) {
                return true;
            }
        }
        return false;
    }

    public int getWeaponKeyCode() {
        for (Integer key : weaponKeys) {
            if (isKeyPressed(key)) {
                return key - KeyEvent.VK_1 + 1;
            }
        }
        return 1;
    }
}