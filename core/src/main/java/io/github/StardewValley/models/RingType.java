package io.github.StardewValley.models;

import com.badlogic.gdx.graphics.Texture;

public enum RingType implements BackPackableType{
    Ring;

    @Override
    public String getName() {
        return name();
    }

    @Override
    public double getPrice() {
        return 0;
    }

    @Override
    public Texture getInventoryTexture() {
        //TODO
        return null;
    }
}
