package io.github.StardewValley.shared.models.savedClasses;

import io.github.StardewValley.shared.models.plant.SeedType;

public class SeedSave {
    private SeedType type;

    public SeedSave() {
    }

    public SeedSave(SeedType type) {
        this.type = type;
    }

    public SeedType getType() {
        return type;
    }
}
