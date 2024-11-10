package Doctrina.Controllers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import Doctrina.Physics.Position;
import Doctrina.Rendering.RenderingEngine;

public abstract class Controller implements KeyListener, MouseListener {
    private final HashMap<Integer, Boolean> pressedKeys;
    private final HashMap<Position, Integer> mouseClicks;

    public Controller() {
        pressedKeys = new HashMap<>();
        mouseClicks = new HashMap<>();
        RenderingEngine.getInstance().addKeyListener(this);
        RenderingEngine.getInstance().addMouseListener(this);
    }

    protected void bindKeys(int keyCode) {
        pressedKeys.put(keyCode, false);
    }

    protected void clearKeys() {
        pressedKeys.clear();
    }

    protected void unBindKeys(int keyCode) {
        pressedKeys.remove(keyCode);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (pressedKeys.containsKey(keyCode)) {
            pressedKeys.put(keyCode, true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (pressedKeys.containsKey(keyCode)) {
            pressedKeys.put(keyCode, false);
        }
    }

    public boolean isKeyPressed(int keyCode) {
        return pressedKeys.containsKey(keyCode) && pressedKeys.get(keyCode);
    }

    public boolean isMousePressed(int button) {
        return !mouseClicks.isEmpty() && mouseClicks.containsValue(button);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Position pos = new Position(e.getX(), e.getY());
        if (e.getButton() == MouseEvent.BUTTON1) {
            mouseClicks.put(pos, MouseEvent.BUTTON1);
        } else {
            mouseClicks.put(pos, MouseEvent.BUTTON2);
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseClicks.clear();
    }
}