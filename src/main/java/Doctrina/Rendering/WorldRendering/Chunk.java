package Doctrina.Rendering.WorldRendering;

import java.util.List;

public class Chunk {
    private List<Integer> data;
    private int height;
    private int width;
    private int x;
    private int y;

    public Chunk(List<Integer> data, int height, int width, int x, int y) {
        this.data = data;
        this.height = height;
        this.width = width;
        this.x = x;
        this.y = y;
    }

    public List<Integer> getData() {
        return data;
    }

    public void setData(List<Integer> value) {
        this.data = value;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int value) {
        this.height = value;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int value) {
        this.width = value;
    }

    public int getX() {
        return x;
    }

    public void setX(int value) {
        this.x = value;
    }

    public int getY() {
        return y;
    }

    public void setY(int value) {
        this.y = value;
    }
}