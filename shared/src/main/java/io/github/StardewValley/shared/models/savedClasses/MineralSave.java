package io.github.StardewValley.shared.models.savedClasses;

import io.github.StardewValley.shared.models.foraging.Mineral;
import io.github.StardewValley.shared.models.foraging.MineralType;

public class MineralSave {
    MineralType type;
    boolean isForaging;

    public MineralSave() {}

    public MineralSave(Mineral mineral) {
        this.type = mineral.getType();
        this.isForaging = mineral.isForaging();
    }

    public MineralType getType() {
        return type;
    }

    public boolean isForaging() {
        return isForaging;
    }
}
