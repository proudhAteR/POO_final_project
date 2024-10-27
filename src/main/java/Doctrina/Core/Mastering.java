package Doctrina.Core;

public class Mastering {
    private int soundCoolDown;
    private int min;
    private int max;

    public Mastering(int minimum, int maximum) {
        this.max = maximum;
        this.min = minimum;
        this.soundCoolDown = minimum;
    }

    public void maximizeCoolDown() {
        soundCoolDown = max;
    }

    public boolean coolDownAtMin() {
        return soundCoolDown == min;
    }

    public void readjustCoolDown() {
        if (coolDownBellowMin()) {
            soundCoolDown = min;
        }

    }

    public void decrementCoolDown() {
        soundCoolDown--;
    }

    private boolean coolDownBellowMin() {
        return soundCoolDown < min;
    }

}
