package io.github.StardewValley.controllers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.StardewValley.GameAssetManagerClient;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.models.*;
import io.github.StardewValley.shared.models.tools.ToolAssetManager;

public class ToolRenderController {
    private boolean isToolAnimating = false;
    private float toolRotation = 0;
    private float toolAnimationTimer = 0;
    private final float TOOL_ANIMATION_DURATION = 0.4f;

    private PlayerClient player;

    private Sprite toolSprite;

    public ToolRenderController(PlayerClient player) {
        this.player = player;
        //TODO
        //this.toolSprite = ToolAssetManager.getToolAssetManager().getToolSprite(player.getToolType());
        //toolSprite.setOriginCenter(); // Rotation around center — adjust if needed
    }


    public void startToolAnimation() {
        isToolAnimating = true;
        toolRotation = 0;
        toolAnimationTimer = 0;
    }

    public void updateToolAnimation(float delta) {
        if (!isToolAnimating) return;
        //TODO
        //toolSprite = ToolAssetManager.getToolAssetManager().getToolSprite(player.getCurrentTool().getToolType());

        toolAnimationTimer += delta;
        float progress = toolAnimationTimer / TOOL_ANIMATION_DURATION;

        toolRotation = progress * -90f; // You can tweak this arc

        if (toolAnimationTimer >= TOOL_ANIMATION_DURATION) {
            isToolAnimating = false;
        }

        if (toolSprite != null) {
            toolSprite.setRotation(toolRotation);
        }
    }


    public boolean isToolAnimating() {
        return isToolAnimating;
    }

    public float getToolRotation() {
        return toolRotation;
    }

    public void update(float delta, PlayerClient player) {
        this.player = player;
        updateToolAnimation(delta);
        if (!player.getToolTexturePath().isEmpty())
            drawTool();
    }

    private void drawTool() {
        if (!isToolAnimating()) {
            Texture texture = GameAssetManagerClient.getGameAssetManager().getTexture(player.getToolTexturePath());
            Main.getBatch().draw(texture, player.getX(), player.getY());
        } else {
            if (toolSprite == null) return;
            // Position tool sprite relative to player position
            float playerX = player.getX();
            float playerY = player.getY();

            toolSprite.setPosition(playerX, playerY);

            toolSprite.draw(Main.getBatch());
        }
    }
}
