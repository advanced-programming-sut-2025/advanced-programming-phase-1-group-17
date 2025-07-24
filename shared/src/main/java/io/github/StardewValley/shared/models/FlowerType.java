package io.github.StardewValley.shared.models;

import com.badlogic.gdx.graphics.Texture;

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
    public Texture getInventoryTexture() {
        return new Texture(this.texturePath);
    }
}
