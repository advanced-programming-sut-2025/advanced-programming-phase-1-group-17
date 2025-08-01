package io.github.StardewValley.models.market;

import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.GameAssetManager;
import io.github.StardewValley.models.BackPackableType;

public enum ShippingBinType implements BackPackableType {
    Basic;

    @Override
    public String getName() {
        return name();
    }

    @Override
    public double getPrice() {
        return 125;
    }

    @Override
    public Texture getInventoryTexture() {
        return GameAssetManager.getGameAssetManager().getShippingBinTexture();
    }
}
