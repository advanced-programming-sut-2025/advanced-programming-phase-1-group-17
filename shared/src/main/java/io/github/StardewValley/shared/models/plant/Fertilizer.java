package io.github.StardewValley.shared.models.plant;

import io.github.StardewValley.shared.models.backpack.BackPackable;
import io.github.StardewValley.shared.models.saveClasses.BackPackableSave;

public class Fertilizer implements BackPackable {
    private FertilizerType type;

    public Fertilizer(FertilizerType type) {
        this.type = type;
    }

    @Override
    public FertilizerType getType() {
        return type;
    }

    @Override
    public BackPackableSave toBackpackableSave() {
        BackPackableSave backPackableSave = new BackPackableSave(Fertilizer.class.getSimpleName());
        backPackableSave.setFertilizer(this);
        return backPackableSave;
    }

    public void setType(FertilizerType type) {
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
}
