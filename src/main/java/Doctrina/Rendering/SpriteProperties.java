package Doctrina.Rendering;

public class SpriteProperties {
    private int frameCount;
    private int xOffset;
    private int yOffset;

    public SpriteProperties(int frameCount, int xOffset, int yOffset) {
        this.frameCount = frameCount;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public int getFrameCount() {
        return frameCount;
    }

    public int getXOff() {
        return xOffset;
    }

    public int setXOff(int incr) {
        return xOffset += incr;
    }

    public int getYOff() {
        return yOffset;
    }

    public int setYOff(int incr) {
        return yOffset += incr;
    }
}
