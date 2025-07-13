package io.github.StardewValley.models.foraging;

import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.models.BackPackable;
import io.github.StardewValley.models.Placeable;

public class Mineral implements BackPackable, Placeable {
    MineralType type;
    boolean isForaging;

    public Mineral(MineralType type, boolean isForaging) {
        this.type = type;
        this.isForaging = isForaging;
    }

    @Override
    public String getName() {
        return type.getName();
    }

    public boolean isForaging() {
        return isForaging;
    }

    @Override
    public MineralType getType() {
        return type;
    }

    @Override
    public double getPrice() {
        return type.getPrice();
    }

    @Override
    public Texture getTexture() {
        return null;
    }
}
