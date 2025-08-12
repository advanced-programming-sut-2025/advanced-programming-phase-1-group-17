package io.github.StardewValley.shared.dto;

// هیچ import jakarta.persistence ای وجود ندارد!
public class LightningStateDTO {
    private boolean flashing;
    private float lightningAlpha;
    private boolean boltActive;
    private int currentFrame;
    private float animationTime;

    public LightningStateDTO() {}
    public LightningStateDTO(boolean flashing, float lightningAlpha, boolean boltActive, int currentFrame, float animationTime) {
        this.flashing = flashing;
        this.lightningAlpha = lightningAlpha;
        this.boltActive = boltActive;
        this.currentFrame = currentFrame;
        this.animationTime = animationTime;
    }

    public boolean isFlashing() {
        return flashing;
    }

    public void setFlashing(boolean flashing) {
        this.flashing = flashing;
    }

    public float getLightningAlpha() {
        return lightningAlpha;
    }

    public void setLightningAlpha(float lightningAlpha) {
        this.lightningAlpha = lightningAlpha;
    }

    public boolean isBoltActive() {
        return boltActive;
    }

    public void setBoltActive(boolean boltActive) {
        this.boltActive = boltActive;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public float getAnimationTime() {
        return animationTime;
    }

    public void setAnimationTime(float animationTime) {
        this.animationTime = animationTime;
    }

    // کانستراکتور کامل و تمام گتر/سترها را اینجا قرار دهید...
}
