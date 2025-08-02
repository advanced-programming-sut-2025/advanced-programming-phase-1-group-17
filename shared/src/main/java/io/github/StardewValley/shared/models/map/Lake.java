package io.github.StardewValley.shared.models.map;

import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.shared.GameAssetManager;

public class Lake implements Placeable {
    @Override
    public String getTexture() {
        return GameAssetManager.getGameAssetManager().getLakeTexture();
    }
}
