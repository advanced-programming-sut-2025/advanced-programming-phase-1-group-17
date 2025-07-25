package io.github.StardewValley.shared.models.backpack;

import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.shared.models.map.Placeable;

public class NormalItem implements BackPackable, Placeable {
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
        return null;
    }
}
