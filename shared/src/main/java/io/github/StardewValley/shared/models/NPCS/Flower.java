package io.github.StardewValley.shared.models.NPCS;

import io.github.StardewValley.shared.models.backpack.BackPackable;
import io.github.StardewValley.shared.models.backpack.BackPackableType;
import io.github.StardewValley.shared.models.saveClasses.BackPackSave;
import io.github.StardewValley.shared.models.saveClasses.BackPackableSave;

public class Flower implements BackPackable {

    private FlowerType flowerType = FlowerType.FLOWER;

    public Flower(FlowerType flowerType) {
        this.flowerType = flowerType;
    }

    public String getName(){
        return FlowerType.FLOWER.getName();
    }
    public double getPrice(){
        return 0;
    }
    public BackPackableType getType(){
        return FlowerType.FLOWER;
    }

    @Override
    public BackPackableSave toBackpackableSave() {
        BackPackableSave backPackableSave = new BackPackableSave(Flower.class.getSimpleName());
        backPackableSave.setFlower(this);
        return backPackableSave;
    }

    public FlowerType getFlowerType() {
        return flowerType;
    }

    public void setFlowerType(FlowerType flowerType) {
        this.flowerType = flowerType;
    }
}
