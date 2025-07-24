package io.github.StardewValley.shared.models.map;

import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.shared.models.Placeable;

public class GreenHouseFence implements Placeable {
    @Override
    public Texture getTexture() {
        return GameAssetManager.getGameAssetManager().getGreenHouseFenceTexture();
    }
}
