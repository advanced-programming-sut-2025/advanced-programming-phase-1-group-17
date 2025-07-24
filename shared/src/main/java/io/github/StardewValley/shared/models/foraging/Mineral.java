package io.github.StardewValley.shared.models.foraging;

import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.shared.models.BackPackable;
import io.github.StardewValley.shared.models.Placeable;

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
        return MineralAssetManager.getMineralAssetManager().getTexture(type);
    }
}
