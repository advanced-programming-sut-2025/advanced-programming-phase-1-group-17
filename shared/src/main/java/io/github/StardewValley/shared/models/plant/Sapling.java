package io.github.StardewValley.shared.models.plant;

import io.github.StardewValley.shared.models.backpack.BackPackable;

public class Sapling implements BackPackable {
    private SaplingType type;

    public Sapling(SaplingType treeSourceType) {
        this.type = treeSourceType;
    }

    @Override
    public SaplingType getType() {
        return type;
    }

    public void setType(SaplingType type) {
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
}
