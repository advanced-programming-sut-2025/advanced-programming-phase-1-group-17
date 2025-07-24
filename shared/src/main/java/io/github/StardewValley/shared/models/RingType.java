package io.github.StardewValley.shared.models;

import com.badlogic.gdx.graphics.Texture;

public enum RingType implements BackPackableType{
    Ring ("Ring/Glow_Ring.png") ;

    private final String texturePath;

    RingType(String texturePath) {
        this.texturePath = texturePath;
    }

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
        return new Texture(this.texturePath);
    }
}
