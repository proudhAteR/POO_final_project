package Doctrina.Physics;

public class Size {
    private int width;
    private int height;

    public Size(int width, int height) {
        this.height = height;
        this.width = width;
    }

    public Size multiply(int num) {
        return new Size(this.height * num, this.width * num);
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean isSmaller(Size s) {
        int result = height * width;
        return result < (s.height * s.width);
    }
}
