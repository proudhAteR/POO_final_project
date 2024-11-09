package Doctrina.Core;

import Doctrina.Entities.StaticEntity;
import Doctrina.Rendering.Canvas;
import Doctrina.Rendering.RenderingEngine;
import java.awt.Color;

public class GameConfig {
    private static boolean debugModeEnable = false;

    public static boolean debugMode(){
        return debugModeEnable;
    }

    public static void setDebugMode(boolean value){
        debugModeEnable = value;
    }

    public static void drawCamPosition(RenderingEngine renderingEngine,Canvas canvas, StaticEntity e) {
        canvas.drawString(String.valueOf(e.getPosition().getY()), 0, 96, Color.WHITE);
        canvas.drawString(String.valueOf(e.getPosition().getX()), 136, 96, Color.WHITE);
        canvas.drawString(String.valueOf(renderingEngine.getCamera().getPosition().getY()), 0, 48, Color.WHITE);
        canvas.drawString(String.valueOf(renderingEngine.getCamera().getPosition().getX()), 136, 48, Color.WHITE);
    }


}
