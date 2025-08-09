package io.github.StardewValley.shared.models.backpack;

import io.github.StardewValley.shared.models.saveClasses.BackPackableSave;

public interface BackPackable {
    String getName();
    double getPrice();
    BackPackableType getType();
    BackPackableSave toBackpackableSave();
}
