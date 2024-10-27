package Doctrina.Controllers;

import java.awt.event.KeyEvent;

public class MovementController extends Controller{
    private int upKey = KeyEvent.VK_UP;
    private int downKey = KeyEvent.VK_DOWN;
    private int leftKey = KeyEvent.VK_LEFT;
    private int rightKey = KeyEvent.VK_RIGHT;

    public MovementController(){
        bindKeys(upKey);
        bindKeys(downKey);
        bindKeys(leftKey);
        bindKeys(rightKey);
    }
    public void useWASDKeys(){
        setUpKey(KeyEvent.VK_W);
        setDownKey(KeyEvent.VK_S);
        setLeftKey(KeyEvent.VK_A);
        setRightKey(KeyEvent.VK_D);

    }
    public Direction getDirection(){
        if(isUpPressed()){
            return Direction.UP;
        }
        if(isDownPressed()){
            return Direction.DOWN;
        }
        if(isLeftPressed()){
            return Direction.LEFT;
        }
        if(isRightPressed()){
            return Direction.RIGHT;
        }
        return null;
    }

    public boolean isLeftPressed(){
        return isKeyPressed(leftKey);
    }
    public boolean isRightPressed(){
        return isKeyPressed(rightKey);
    }
    public boolean isUpPressed(){
        return isKeyPressed(upKey);
    }
    public boolean isDownPressed(){
        return isKeyPressed(downKey);
    }

    public boolean isMoving(){
        return isDownPressed() || isLeftPressed() || isRightPressed() ||isUpPressed();
    }

    public void setDownKey(int keyCode){
        unBindKeys(downKey);
        bindKeys(keyCode);
        this.downKey = keyCode;
    }
    public void setUpKey(int keyCode){
        unBindKeys(upKey);
        bindKeys(keyCode);
        this.upKey = keyCode;
    }
    public void setLeftKey(int keyCode){
        unBindKeys(leftKey);
        bindKeys(keyCode);
        this.leftKey = keyCode;
    }
    public void setRightKey(int keyCode){
        unBindKeys(rightKey);
        bindKeys(keyCode);
        this.rightKey = keyCode;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}