package io.github.StardewValley.models.plant;

import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.models.BackPackableType;

public enum FertilizerType implements BackPackableType {
    SpeedGro, BasicRetainingSoil, QualityRetainingSoil, DeluxeRetainingSoil;

    @Override
    public double getPrice() {
        return 0;
    }

    @Override
    public String getName() {
        return name();
    }

    public static FertilizerType getFertilizerTypeByName(String name) {
        for (FertilizerType value : FertilizerType.values()) {
            if (value.name().equalsIgnoreCase(name)) {
                return value;
            }
        }
        return null;
    }

    @Override
    public Texture getInventoryTexture() {
        return CropAssetManager.getCropAssetManager().getFertilizerTexture(this);
    }
}
