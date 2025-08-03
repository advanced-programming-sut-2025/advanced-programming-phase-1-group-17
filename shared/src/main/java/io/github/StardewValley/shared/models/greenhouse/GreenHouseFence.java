package io.github.StardewValley.shared.models.greenhouse;

import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.shared.models.map.Placeable;

public class GreenHouseFence implements Placeable {
    @Override
    public String  getTexture() {
        return GameAssetManager.getGameAssetManager().getGreenHouseFenceTexture();
    }
}
