package io.github.StardewValley.models.map;

import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.GameAssetManager;
import io.github.StardewValley.models.Placeable;

public class GreenHouseFence implements Placeable {
    @Override
    public Texture getTexture() {
        return GameAssetManager.getGameAssetManager().getGreenHouseFenceTexture();
    }
}
