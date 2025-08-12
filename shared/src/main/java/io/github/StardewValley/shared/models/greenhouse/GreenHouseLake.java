package io.github.StardewValley.shared.models.greenhouse;

import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.map.Placeable;
import io.github.StardewValley.shared.models.saveClasses.PlaceableSave;

import java.util.List;

public class GreenHouseLake implements Placeable {
    private String texture = null; //for saving

    public void setTexture(String texture) {
        this.texture = texture;
    }

    @Override
    public String  getTexture() {
        return texture;
    }

    @Override
    public PlaceableSave toDTO(int x, int y, String ownerUsername) {
        PlaceableSave placeableSave = new PlaceableSave(GreenHouseLake.class.getSimpleName());
        placeableSave.setGreenHouseLake(this);
        return placeableSave;
    }

    @Override
    public Placeable loadFromDTO(PlaceableSave dto, List<Player> playerList) {
        return this;
    }
}
