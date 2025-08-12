package io.github.StardewValley.controllers.UIControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import models.PlayerClient;
import io.github.StardewValley.shared.dto.LightningStateDTO;

public class LightningRenderController {
    private static LightningRenderController lightningRenderController = null;

    private float lightningAlpha = 0f;
    private boolean flashing = false;

    private final Texture whitePixelTexture;
    private Texture[] lightningFrames;
    private float animationTime = 0f;
    private int currentFrame = 0;
    private boolean boltActive = false;

    private LightningRenderController() {
        // white texture for flash
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        whitePixelTexture = new Texture(pixmap);
        pixmap.dispose();

        generateLightningAnimation(); // build bolt frames
    }

    public static LightningRenderController getLightningController() {
        if (lightningRenderController == null)
            lightningRenderController = new LightningRenderController();
        return lightningRenderController;
    }

    public void applyLightningState(LightningStateDTO state) {
        this.flashing = state.isFlashing();
        this.lightningAlpha = state.getLightningAlpha();
        this.boltActive = state.isBoltActive();
        this.currentFrame = state.getCurrentFrame();
        this.animationTime = state.getAnimationTime();
    }


    public void renderLightning(SpriteBatch batch, PlayerClient player) {
        // Flash (white screen)
        if (lightningAlpha > 0f) {
            System.out.println("Lightning: Alpha: " + lightningAlpha);
            Color prev = batch.getColor().cpy();
            batch.setColor(1f, 1f, 1f, lightningAlpha);
            int x = player.getX() - Gdx.graphics.getWidth() / 2;
            int y = player.getY() - Gdx.graphics.getHeight() / 2;
            batch.draw(whitePixelTexture, x, y, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.setColor(prev);
        }

        // Lightning bolt animation
        if (boltActive && currentFrame < lightningFrames.length) {
            Texture frame = lightningFrames[currentFrame];
            int x = player.getX();
            int y = player.getY() + 150;
            batch.draw(frame, x - frame.getWidth() / 2, y);
        }
    }

    private void generateLightningAnimation() {
        lightningFrames = new Texture[4];
        for (int i = 0; i < lightningFrames.length; i++) {
            Pixmap pixmap = new Pixmap(16, 64, Pixmap.Format.RGBA8888);
            pixmap.setColor(Color.WHITE);
            int x = 8;
            for (int y = 0; y < 64; y += 8) {
                int dx = MathUtils.random(-4, 4);
                pixmap.drawLine(x, y, x + dx, y + 8);
                x += dx;
            }
            lightningFrames[i] = new Texture(pixmap);
            pixmap.dispose();
        }
    }

    public float getLightningAlpha() {
        return lightningAlpha;
    }

    public boolean isFlashing() {
        return flashing;
    }

    public Texture getWhitePixelTexture() {
        return whitePixelTexture;
    }
}
