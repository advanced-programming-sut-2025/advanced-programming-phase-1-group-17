package io.github.StardewValley.shared.models.savedClasses;

import io.github.StardewValley.shared.models.backpack.NormalItem;
import io.github.StardewValley.shared.models.backpack.NormalItemType;

public class NormalItemSave {
    private NormalItemType type;
    private int grassTextureID;

    public NormalItemSave() {}

    public NormalItemSave(NormalItem normalItem) {
        this.type = normalItem.getType();
        this.grassTextureID = normalItem.getGrassTextureID();
    }

    public NormalItemType getType() {
        return type;
    }

    public int getGrassTextureID() {
        return grassTextureID;
    }
}
