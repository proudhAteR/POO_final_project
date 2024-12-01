package Doctrina.Rendering.WorldRendering;

import java.util.List;

public class Layer {
    private List<Chunk> chunks;
    private int id;
    private int height;
    private String name;
    private int opacity;
    private int startx;
    private int starty;
    private String type;
    private boolean visible;
    private int width;
    private int x;
    private int y;

    public Layer(List<Chunk> chunks,int height, int id, String name, int opacity, int startx, int starty, String type,
            boolean visible, int width, int x, int y) {
        this.chunks = chunks;
        this.id = id;
        this.name = name;
        this.opacity = opacity;
        this.startx = startx;
        this.starty = starty;
        this.type = type;
        this.visible = visible;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    public List<Chunk> getChunks() {
        return chunks;
    }

    public void setChunks(List<Chunk> value) {
        this.chunks = value;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public int getID() {
        return id;
    }

    public void setID(int value) {
        this.id = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public int getOpacity() {
        return opacity;
    }

    public void setOpacity(int value) {
        this.opacity = value;
    }

    public int getStartx() {
        return startx;
    }

    public void setStartx(int value) {
        this.startx = value;
    }

    public int getStarty() {
        return starty;
    }

    public void setStarty(int value) {
        this.starty = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String value) {
        this.type = value;
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean value) {
        this.visible = value;
    }
    public int getWidth() { return width; }
    public void setWidth(int value) { this.width = value; }

    public int getX() { return x; }
    public void setX(int value) { this.x = value; }

    public int getY() { return y; }
    public void setY(int value) { this.y = value; }
}