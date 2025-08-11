package io.github.StardewValley.shared.models.greenhouse;

import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.map.Placeable;
import io.github.StardewValley.shared.models.saveClasses.PlaceableSave;

import java.util.List;

public class GreenHouseLake implements Placeable {
    @Override
    public String  getTexture() {
        //TODO
        return null;
    }

    @Override
    public PlaceableSave toDTO() {
        PlaceableSave placeableSave = new PlaceableSave(GreenHouseLake.class.getSimpleName());
        placeableSave.setGreenHouseLake(this);
        return placeableSave;
    }

    @Override
    public Placeable loadFromDTO(PlaceableSave dto, List<Player> playerList) {
        return this;
    }
}
