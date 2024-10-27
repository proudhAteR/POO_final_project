package Doctrina.Core;

import java.util.concurrent.TimeUnit;

public class GameTime {
    private static final int FPS_TARGET = 60;

    private static int currentFps;
    private static int fpsCount;
    private static long fpsTimeDelta;
    private static long gameStartTime;
    private long syncTime;

    protected GameTime() {
        updateSyncTime();
        gameStartTime = System.currentTimeMillis();
        fpsTimeDelta = 0;
        currentFps = 0;
    }

    private void updateSyncTime() {
        syncTime = System.currentTimeMillis();
    }
    public void synchronize(){
        update();
        try {
            Thread.sleep(getSleepTime());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        updateSyncTime();
    }
    private long getSleepTime() {
        long targetTime = 1000L / FPS_TARGET;

        long sleep = targetTime - (System.currentTimeMillis() - syncTime);

        if (sleep < 0) {
            sleep = 4;
        }

        return sleep;
    }

    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public static int getCurrentFps() {
        return (currentFps > 0) ? currentFps : fpsCount;
    }
    public static long getElapsedTime() {
        return System.currentTimeMillis() - gameStartTime;
    }

    public static String getElapsedTimeAsString() {
        long elapsedTime = getElapsedTime();
        long hours = TimeUnit.MILLISECONDS.toHours(elapsedTime);
        elapsedTime -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTime);
        elapsedTime -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTime);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);

    }

    private void update(){
        fpsCount++;
        long now = TimeUnit.MILLISECONDS.toSeconds(getElapsedTime());
        if (now != fpsTimeDelta) {
            currentFps = fpsCount;
            fpsCount = 0;
        }
        fpsTimeDelta = now;
    }
}
