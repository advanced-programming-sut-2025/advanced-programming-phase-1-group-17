package io.github.StardewValley.shared.models.map;

import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.saveClasses.PlaceableSave;

import java.util.List;

public class Lake implements Placeable {
    @Override
    public String getTexture() {
        return GameAssetManager.getGameAssetManager().getLakeTexture();
    }

    @Override
    public PlaceableSave toDTO() {
        PlaceableSave placeableSave = new PlaceableSave(Lake.class.getSimpleName());
        placeableSave.setLake(this);
        return placeableSave;
    }

    @Override
    public Placeable loadFromDTO(PlaceableSave dto, List<Player> playerList) {
        return this;
    }
}
