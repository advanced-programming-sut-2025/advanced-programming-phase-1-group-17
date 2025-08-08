package io.github.StardewValley.shared.models.backpack;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.shared.models.map.Placeable;

import java.util.Random;

public class NormalItem implements BackPackable, Placeable {
    private NormalItemType type;
    private int grassTextureID;

    public NormalItem(NormalItemType type) {
        this.type = type;
        if (type.equals(NormalItemType.Grass)) {
            Random random = new Random();
            int randInt = random.nextInt(33);
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
        return type.getInventoryTexturePath();
    }

    public int getGrassTextureID() {
        return grassTextureID;
    }
}
