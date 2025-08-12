package io.github.StardewValley.shared.models.map;

import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.saveClasses.PlaceableSave;

import java.util.List;

public class Quarry implements Placeable {
    private String texture = "assets/Rock/Quarry_Boulder.png";

    public void setTexture(String texture) {
        this.texture = texture;
    }

    @Override
    public String getTexture() {
        return texture;
    }

    @Override
    public PlaceableSave toDTO(int x, int y, String ownerUsername) {
        PlaceableSave placeableSave = new PlaceableSave(Quarry.class.getSimpleName());
        placeableSave.setQuarry(this);
        return placeableSave;
    }

    @Override
    public Placeable loadFromDTO(PlaceableSave dto, List<Player> playerList) {
        return this;
    }
}
