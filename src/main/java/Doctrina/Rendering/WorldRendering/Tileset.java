package Doctrina.Rendering.WorldRendering;

public class Tileset {
    private int columns;
    private int firstgid;
    private String image;
    private int imageheight;
    private int imagewidth;
    private int margin;
    private String name;
    private int spacing;
    private int tilecount;
    private int tileheight;
    private int tilewidth;

    public Tileset(int columns, int firstgid, String image, int imageheight, int imagewidth, int margin,
            String name, int spacing, int tilecount, int tileheight, int tilewidth) {
        this.columns = columns;
        this.firstgid = firstgid;
        this.image = image;
        this.imageheight = imageheight;
        this.imagewidth = imagewidth;
        this.margin = margin;
        this.name = name;
        this.spacing = spacing;
        this.tilecount = tilecount;
        this.tileheight = tileheight;
        this.tilewidth = tilewidth;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int value) {
        this.columns = value;
    }

    public int getFirstgid() {
        return firstgid;
    }

    public void setFirstgid(int value) {
        this.firstgid = value;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String value) {
        this.image = value;
    }

    public int getImageheight() {
        return imageheight;
    }

    public void setImageheight(int value) {
        this.imageheight = value;
    }

    public int getImagewidth() {
        return imagewidth;
    }

    public void setImagewidth(int value) {
        this.imagewidth = value;
    }

    public int getMargin() {
        return margin;
    }

    public void setMargin(int value) {
        this.margin = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public int getSpacing() {
        return spacing;
    }

    public void setSpacing(int value) {
        this.spacing = value;
    }

    public int getTilecount() {
        return tilecount;
    }

    public void setTilecount(int value) {
        this.tilecount = value;
    }

    public int getTileheight() {
        return tileheight;
    }

    public void setTileheight(int value) {
        this.tileheight = value;
    }

    public int getTilewidth() {
        return tilewidth;
    }

    public void setTilewidth(int value) {
        this.tilewidth = value;
    }
}
