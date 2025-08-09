package io.github.StardewValley.shared.models.NPCS;

import io.github.StardewValley.shared.models.backpack.BackPackable;
import io.github.StardewValley.shared.models.backpack.BackPackableType;
import io.github.StardewValley.shared.models.saveClasses.BackPackableSave;

public class Ring implements BackPackable {

    @Override
    public String getName() {
        return RingType.Ring.getName();
    }

    @Override
    public double getPrice() {
        return 0;
    }

    @Override
    public BackPackableType getType() {
        return RingType.Ring;
    }

    @Override
    public BackPackableSave toBackpackableSave() {
        BackPackableSave backPackableSave = new BackPackableSave(Ring.class.getSimpleName());
        backPackableSave.setRing(this);
        return backPackableSave;
    }

}
