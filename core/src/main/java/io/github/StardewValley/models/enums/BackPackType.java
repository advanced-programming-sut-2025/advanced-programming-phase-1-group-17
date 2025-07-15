package io.github.StardewValley.models.enums;

import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.GameAssetManager;
import io.github.StardewValley.models.BackPackableType;

public enum BackPackType implements BackPackableType {
    PrimaryBackpack(12, 0),
    LargeBackPack(24, 1000),
    DeluxeBackPack((int)Double.POSITIVE_INFINITY, 5000);

    private final int capacity;
    private final double price;


    BackPackType(int capacity, double price) {
        this.capacity = capacity;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public Texture getInventoryTexture() {
        return GameAssetManager.getGameAssetManager().getBackPackTexture();
    }
}
