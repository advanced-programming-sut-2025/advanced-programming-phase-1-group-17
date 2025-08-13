package io.github.StardewValley.shared.models.NPCS;

import io.github.StardewValley.shared.models.backpack.BackPackableType;

public enum FlowerType implements BackPackableType {
    FLOWER ("Craftable_item/Tub_o%27_Flowers.png");

    private final String texturePath;

    FlowerType(String texturePath) {
        this.texturePath = texturePath;
    }

    public String getName(){
        return name();
    }
    public double getPrice(){
        return 0;
    }

    @Override
    public String getInventoryTexturePath() {
        return this.texturePath;
    }
}
