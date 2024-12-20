package city_cleaner.Render;

import java.util.List;

import Doctrina.Rendering.WorldRendering.Layer;
import Doctrina.Rendering.WorldRendering.Tileset;

public class World {
    private int compressionlevel;
    private int height;
    private boolean infinite;
    private List<Layer> layers;
    private int nextlayerid;
    private int nextobjectid;
    private String orientation;
    private String renderorder;
    private String tiledversion;
    private int tileheight;
    private List<Tileset> tilesets;
    private int tilewidth;
    private String type;
    private String version;
    private int width;

    public World(
            int compressionlevel,
            int height,
            boolean infinite,
            List<Layer> layers,
            int nextlayerid,
            int nextobjectid,
            String orientation,
            String renderorder,
            String tiledversion,
            int tileheight,
            List<Tileset> tilesets,
            int tilewidth,
            String type,
            String version,
            int width) {
        this.compressionlevel = compressionlevel;
        this.height = height;
        this.infinite = infinite;
        this.layers = layers;
        this.nextlayerid = nextlayerid;
        this.nextobjectid = nextobjectid;
        this.orientation = orientation;
        this.renderorder = renderorder;
        this.tiledversion = tiledversion;
        this.tileheight = tileheight;
        this.tilesets = tilesets;
        this.tilewidth = tilewidth;
        this.type = type;
        this.version = version;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public int getCompressionlevel() {
        return compressionlevel;
    }

    public void setCompressionlevel(int compressionlevel) {
        this.compressionlevel = compressionlevel;
    }

    public void setHeight(int value) {
        this.height = value;
    }

    public boolean getInfinite() {
        return infinite;
    }

    public void setInfinite(boolean value) {
        this.infinite = value;
    }

    public List<Layer> getLayers() {
        return layers;
    }

    public void setLayers(List<Layer> value) {
        this.layers = value;
    }

    public int getNextlayerid() {
        return nextlayerid;
    }

    public void setNextlayerid(int value) {
        this.nextlayerid = value;
    }

    public int getNextobjectid() {
        return nextobjectid;
    }

    public void setNextobjectid(int value) {
        this.nextobjectid = value;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String value) {
        this.orientation = value;
    }

    public String getRenderorder() {
        return renderorder;
    }

    public void setRenderorder(String value) {
        this.renderorder = value;
    }

    public String getTiledversion() {
        return tiledversion;
    }

    public void setTiledversion(String value) {
        this.tiledversion = value;
    }

    public int getTileheight() {
        return tileheight;
    }

    public void setTileheight(int value) {
        this.tileheight = value;
    }

    public List<Tileset> getTilesets() {
        return tilesets;
    }

    public void setTilesets(List<Tileset> value) {
        this.tilesets = value;
    }

    public int getTilewidth() {
        return tilewidth;
    }

    public void setTilewidth(int value) {
        this.tilewidth = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String value) {
        this.type = value;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String value) {
        this.version = value;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int value) {
        this.width = value;
    }

}