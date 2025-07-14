package io.github.StardewValley.models.map;

import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.GameAssetManager;
import io.github.StardewValley.models.Placeable;

public class GreenHouseFence implements Placeable {
    private final Texture texture = new Texture(GameAssetManager.getGameAssetManager().getGreenHouseFenceTexture()) ;
    @Override
    public Texture getTexture() {
        return texture;
    }
}
