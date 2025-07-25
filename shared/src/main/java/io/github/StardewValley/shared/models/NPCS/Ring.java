package io.github.StardewValley.shared.models.NPCS;

import io.github.StardewValley.shared.models.backpack.BackPackable;
import io.github.StardewValley.shared.models.backpack.BackPackableType;

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

}
