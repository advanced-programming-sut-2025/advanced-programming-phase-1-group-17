package org.example.models.cooking;

public class Buff {
    private BuffType buffType;
    private int duration;
    public Buff(BuffType buffType, int duration) {
        this.buffType = buffType;
        this.duration = duration;
    }

    public BuffType getBuffType() {
        return buffType;
    }

    public void setBuffType(BuffType buffType) {
        this.buffType = buffType;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
