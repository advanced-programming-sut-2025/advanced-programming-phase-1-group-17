package io.github.StardewValley.shared.models.map;

import io.github.StardewValley.shared.models.savedClasses.PlaceableSave;

public interface Placeable {
    String getTexture();
    PlaceableSave toDTO();
    void loadFromDTO(PlaceableSave dto);
}
