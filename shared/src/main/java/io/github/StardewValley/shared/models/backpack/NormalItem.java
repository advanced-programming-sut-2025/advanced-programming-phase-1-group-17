package io.github.StardewValley.shared.models.backpack;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.shared.models.map.Placeable;

import java.util.Random;

public class NormalItem implements BackPackable, Placeable {
    private NormalItemType type;
    private transient TextureRegion grassTextureRegion = null;

    public NormalItem(NormalItemType type) {
        this.type = type;
        if (type.equals(NormalItemType.Grass)) {
            Random random = new Random();
            int randInt = random.nextInt(33);
            grassTextureRegion = GameAssetManager.getGameAssetManager().getGrassTextures().get(randInt);
        }
    }

    @Override
    public String getName() {
        return type.getName();
    }

    @Override
    public double getPrice() {
        return type.getPrice();
    }

    @Override
    public NormalItemType getType() {
        return type;
    }

    @Override
    public String getTexture() {
        return type.getInventoryTexture();
    }
    public TextureRegion getGrassTextureRegion() {
        return grassTextureRegion;
    }
}
