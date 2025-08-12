package io.github.StardewValley.shared.models.map;

import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.saveClasses.PlaceableSave;

import java.util.List;

public class Lake implements Placeable {
    private String texture = GameAssetManager.getGameAssetManager().getLakeTexture();

    public void setTexture(String texture) {
        this.texture = texture;
    }

    @Override
    public String getTexture() {
        return texture;
    }

    @Override
    public PlaceableSave toDTO(int x, int y, String ownerUsername) {
        PlaceableSave placeableSave = new PlaceableSave(Lake.class.getSimpleName());
        placeableSave.setLake(this);
        return placeableSave;
    }

    @Override
    public Placeable loadFromDTO(PlaceableSave dto, List<Player> playerList) {
        return this;
    }
}
