package io.github.StardewValley.shared.models.plant;

import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.shared.models.backpack.BackPackable;
import io.github.StardewValley.shared.models.map.Placeable;

public class Seed implements BackPackable, Placeable {
    private SeedType type;
    private final String texture = type.getTexturePath();
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
    public String  getTexture() {
        return texture;
    }
}
