package io.github.StardewValley.shared;

import io.github.StardewValley.shared.dto.LightningStateDTO;

public class LightningLogicController {
    private boolean flashing = false;
    private float lightningAlpha = 0;
    private boolean boltActive = false;
    private int currentFrame = 0;
    private float animationTime = 0f;
    private static final int TOTAL_FRAMES = 4;

    public void triggerLightning() {
        this.flashing = true;
        this.lightningAlpha = 1f;
        this.boltActive = true;
        this.currentFrame = 0;
    }

    //TODo: use inside game Loop
    public void updateLightning(float delta) {
        if (flashing) {
            lightningAlpha -= delta * 2f;
            if (lightningAlpha <= 0f) {
                lightningAlpha = 0f;
                flashing = false;
            }
        }

        if (boltActive) {
            animationTime += delta;
            if (animationTime >= 0.05f) {
                animationTime = 0f;
                currentFrame++;
                if (currentFrame >= TOTAL_FRAMES) {
                    boltActive = false;
                }
            }
        }
    }

    public LightningStateDTO getLightningStateDTO() {
        return new LightningStateDTO(flashing, lightningAlpha, boltActive, currentFrame, animationTime);
    }

}
