package io.github.StardewValley.shared.models.market;

import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.shared.models.backpack.BackPackableType;

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
    public String getInventoryTexture() {
        return GameAssetManager.getGameAssetManager().getShippingBinTexture();
    }
}
