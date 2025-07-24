package io.github.StardewValley.shared.models.plant;

import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.shared.models.BackPackable;
import io.github.StardewValley.shared.models.Placeable;

public class Seed implements BackPackable, Placeable {
    private SeedType type;
    private final Texture texture = new Texture(type.getTexturePath());
    public Seed(SeedType type) {
        this.type = type;
    }

    public SeedType getType() {
        return type;
    }

    public void setType(SeedType type) {
        this.type = type;
    }

    @Override
    public String getName() {
        return type.name();
    }

    @Override
    public double getPrice() {
        return type.getPrice();
    }

    @Override
    public Texture getTexture() {
        return texture;
    }
}
