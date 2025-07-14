package io.github.StardewValley.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import io.github.StardewValley.GameAssetManager;

public class Fence implements Placeable{
    private Texture fenceTexture = new Texture(GameAssetManager.getGameAssetManager().getFenceTexture());
    private Texture fenceTexture2 = new Texture(GameAssetManager.getGameAssetManager().getFenceTexture2());
    boolean isHorizontal;
    public Fence(boolean isHorizontal) {
        this.isHorizontal = isHorizontal;
    }
    @Override
    public Texture getTexture() {
        if (!isHorizontal)
            return fenceTexture;
        else
            return fenceTexture2;
    }
}
