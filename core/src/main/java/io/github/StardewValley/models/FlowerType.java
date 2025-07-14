package io.github.StardewValley.models;

import com.badlogic.gdx.graphics.Texture;

public enum FlowerType implements BackPackableType {
    FLOWER;
    public String getName(){
        return name();
    }
    public double getPrice(){
        return 0;
    }

    @Override
    public Texture getInventoryTexture() {
        //TODO
        return null;
    }
}
