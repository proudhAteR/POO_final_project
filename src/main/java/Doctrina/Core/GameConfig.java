package Doctrina.Core;

import Doctrina.Entities.StaticEntity;
import Doctrina.Rendering.Camera;
import Doctrina.Rendering.Canvas;
import Doctrina.Rendering.RenderingEngine;
import java.awt.Color;
import java.util.Collection;

public class GameConfig {
    private static boolean debugModeEnable = false;

    public static boolean debugMode() {
        return debugModeEnable;
    }

    public static void setDebugMode(boolean value) {
        debugModeEnable = value;
    }

    public static void drawCamPosition(RenderingEngine renderingEngine, Canvas canvas) {
        canvas.drawString(String.valueOf(renderingEngine.getCamera().getPosition().getY()), 10, 540, Color.WHITE);
        canvas.drawString(String.valueOf(renderingEngine.getCamera().getPosition().getX()), 110, 540, Color.WHITE);
    }

    public static void drawCamFocusPosition(RenderingEngine renderingEngine, Canvas canvas){
        Camera camera = renderingEngine.getCamera();
        StaticEntity e = camera.getEntityOnFocus();
        canvas.drawString(String.valueOf(e.getPosition().getY()), 10, 580, Color.WHITE);
        canvas.drawString(String.valueOf(e.getPosition().getX()), 110, 580, Color.WHITE);
    }

    public static void drawCount(Collection<?> list, Canvas canvas) {
        canvas.drawString(String.valueOf(list.size()), 10, 500, Color.WHITE);
    }

    public static void toggleDebug() {
        if (!debugMode()) {
            setDebugMode(true);
        } else {
            setDebugMode(false);
        }
    }
}
