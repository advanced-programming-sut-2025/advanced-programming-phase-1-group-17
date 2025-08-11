package io.github.StardewValley.shared.models.greenhouse;

import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.map.Placeable;
import io.github.StardewValley.shared.models.saveClasses.PlaceableSave;

import java.util.List;

public class GreenHouseFence implements Placeable {
    @Override
    public String  getTexture() {
        return GameAssetManager.getGameAssetManager().getGreenHouseFenceTexture();
    }

    @Override
    public PlaceableSave toDTO() {
        PlaceableSave placeableSave = new PlaceableSave(GreenHouseFence.class.getSimpleName());
        placeableSave.setGreenHouseFence(this);
        return placeableSave;
    }

    @Override
    public Placeable loadFromDTO(PlaceableSave dto, List<Player> playerList) {
        return this;
    }
}
