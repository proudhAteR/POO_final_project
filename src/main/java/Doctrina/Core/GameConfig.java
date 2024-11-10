package Doctrina.Core;

import Doctrina.Entities.StaticEntity;
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

    public static void drawCamPosition(RenderingEngine renderingEngine, Canvas canvas, StaticEntity e) {
        canvas.drawString(String.valueOf(renderingEngine.getCamera().getPosition().getY()), 20, 40, Color.WHITE);
        canvas.drawString(String.valueOf(renderingEngine.getCamera().getPosition().getX()), 120, 40, Color.WHITE);
        canvas.drawString(String.valueOf(e.getPosition().getY()), 20, 80, Color.WHITE);
        canvas.drawString(String.valueOf(e.getPosition().getX()), 120, 80, Color.WHITE);
    }

    public static void drawCount(Collection<?> list, Canvas canvas) {
        canvas.drawString(String.valueOf(list.size()), 20, 120, Color.WHITE);
    }

}
