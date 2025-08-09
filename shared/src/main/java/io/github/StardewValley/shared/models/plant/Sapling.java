package io.github.StardewValley.shared.models.plant;

import io.github.StardewValley.shared.models.backpack.BackPackable;
import io.github.StardewValley.shared.models.saveClasses.BackPackableSave;

public class Sapling implements BackPackable {
    private SaplingType type;

    public Sapling(SaplingType treeSourceType) {
        this.type = treeSourceType;
    }

    @Override
    public SaplingType getType() {
        return type;
    }

    @Override
    public BackPackableSave toBackpackableSave() {
        BackPackableSave backPackableSave = new BackPackableSave(Sapling.class.getSimpleName());
        backPackableSave.setSapling(this);
        return backPackableSave;
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
