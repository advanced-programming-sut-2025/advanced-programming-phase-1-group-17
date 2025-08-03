package io.github.StardewValley.shared.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import io.github.StardewValley.shared.models.App;

public class LightningController {
    private static LightningController lightningController = null;

    private float lightningAlpha = 0f;
    private boolean flashing = false;

    private final Texture whitePixelTexture;
    private Texture[] lightningFrames;
    private float animationTime = 0f;
    private int currentFrame = 0;
    private boolean boltActive = false;

    private LightningController() {
        // white texture for flash
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        whitePixelTexture = new Texture(pixmap);
        pixmap.dispose();

        generateLightningAnimation(); // build bolt frames
    }

    public static LightningController getLightningController() {
        if (lightningController == null)
            lightningController = new LightningController();
        return lightningController;
    }

    public void triggerLightning() {
        lightningAlpha = 1f;
        flashing = true;

        animationTime = 0f;
        currentFrame = 0;
        boltActive = true;
    }

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
                if (currentFrame >= lightningFrames.length) {
                    boltActive = false;
                }
            }
        }
    }

    public void renderLightning(SpriteBatch batch) {
        // Flash (white screen)
        if (lightningAlpha > 0f) {
            Color prev = batch.getColor().cpy();
            batch.setColor(1f, 1f, 1f, lightningAlpha);
            int x = App.getCurrentGame().getCurrentPlayingPlayer().getX() - Gdx.graphics.getWidth() / 2;
            int y = App.getCurrentGame().getCurrentPlayingPlayer().getY() - Gdx.graphics.getHeight() / 2;
            batch.draw(whitePixelTexture, x, y, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.setColor(prev);
        }

        // Lightning bolt animation
        if (boltActive && currentFrame < lightningFrames.length) {
            Texture frame = lightningFrames[currentFrame];
            int x = App.getCurrentGame().getCurrentPlayingPlayer().getX();
            int y = App.getCurrentGame().getCurrentPlayingPlayer().getY() + 150;
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
