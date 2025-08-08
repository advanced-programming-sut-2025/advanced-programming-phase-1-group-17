package io.github.StardewValley.controllers.helperControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CrowAttackEffect {
    private transient Texture crowTexture;
    private float x, y;
    private float scale;
    private float time;
    private boolean active;

    public CrowAttackEffect() {
        crowTexture = new Texture(Gdx.files.internal("Animals/Crow.png"));
        active = false;
    }

    public void trigger(float targetX, float targetY) {
        this.x = targetX;
        this.y = targetY + 200; // Start higher
        this.scale = 0.3f;
        this.time = 0;
        this.active = true;
    }

    public void update(float delta) {
        if (!active) return;

        time += delta;
        y -= 80 * delta;   // fall speed
        scale += 0.3f * delta;

        if (time > 1.5f) { // animation ends
            active = false;
        }
    }

    public void render(SpriteBatch batch) {
        if (!active) return;
        batch.draw(crowTexture, x, y, crowTexture.getWidth() * scale, crowTexture.getHeight() * scale);
    }
}
