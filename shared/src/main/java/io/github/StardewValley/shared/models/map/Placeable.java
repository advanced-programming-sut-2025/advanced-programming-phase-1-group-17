package io.github.StardewValley.shared.models.map;

import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.saveClasses.PlaceableSave;

import java.util.List;

public interface Placeable {
    String getTexture();
    PlaceableSave toDTO();
    Placeable loadFromDTO(PlaceableSave dto, List<Player> playerList);
}
