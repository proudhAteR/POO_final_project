package city_cleaner;

public enum SFX {
    STEPS("sounds/SFX/leaves02.wav");
    private String path;

    SFX(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}
