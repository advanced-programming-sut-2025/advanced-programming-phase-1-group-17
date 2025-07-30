package io.github.StardewValley.models;

import com.badlogic.gdx.graphics.Texture;

public class NormalItem implements BackPackable, Placeable{
    private NormalItemType type;

    public NormalItem(NormalItemType type) {
        this.type = type;
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
    public Texture getTexture() {
        return type.getInventoryTexture();
    }
}
