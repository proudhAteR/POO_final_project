package Doctrina.Controllers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import Doctrina.Rendering.RenderingEngine;

public abstract class Controller implements KeyListener  {
    private final HashMap<Integer, Boolean> pressedKeys;


    public Controller() {
        pressedKeys = new HashMap<>();
        RenderingEngine.getInstance().addKeyListener(this);
    }
    protected void bindKeys(int keyCode){
        pressedKeys.put(keyCode, false);
    }
    protected void clearKeys(){
        pressedKeys.clear();
    }
    protected void unBindKeys(int keyCode){
        pressedKeys.remove(keyCode);
    }


    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if( pressedKeys.containsKey(keyCode) ){
            pressedKeys.put(keyCode, true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if( pressedKeys.containsKey(keyCode) ){
            pressedKeys.put(keyCode, false);
        }
    }

    public boolean isKeyPressed(int keyCode){
        return pressedKeys.containsKey(keyCode) && pressedKeys.get(keyCode);
    }
}