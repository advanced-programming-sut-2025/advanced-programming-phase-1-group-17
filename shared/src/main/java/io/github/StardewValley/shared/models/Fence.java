package io.github.StardewValley.shared.models;

import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.shared.models.map.Placeable;

public class Fence implements Placeable {
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
