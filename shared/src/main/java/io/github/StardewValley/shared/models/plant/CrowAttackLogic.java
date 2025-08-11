package io.github.StardewValley.shared.models.plant;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.StardewValley.shared.models.Result;

public class CrowAttackLogic {
    private float x, y;
    private float scale;
    private float time;
    private boolean active;

    public CrowAttackLogic() {
        active = false;
    }

    public void trigger(float targetX, float targetY, Result result) {
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
}

