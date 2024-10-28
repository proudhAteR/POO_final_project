package Doctrina.Core;

public class GameConfig {
    private static boolean debugModeEnable;

    public static boolean isDebugModeEnable(){
        return debugModeEnable;
    }

    public static void setDebugMode(boolean value){
        debugModeEnable = value;
    }

}
