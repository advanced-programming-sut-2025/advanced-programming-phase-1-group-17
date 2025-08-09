package io.github.StardewValley.shared.models.map;

import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.shared.models.savedClasses.LakeSave;
import io.github.StardewValley.shared.models.savedClasses.PlaceableSave;

public class Lake implements Placeable {
    @Override
    public String getTexture() {
        return GameAssetManager.getGameAssetManager().getLakeTexture();
    }

    @Override
    public PlaceableSave toDTO() {
        PlaceableSave placeableSave = new PlaceableSave(Lake.class.getSimpleName());
        placeableSave.setLakeSave(new LakeSave());
        return placeableSave;
    }

    @Override
    public void loadFromDTO(PlaceableSave dto) {
        Lake lake = new Lake();
    }
}
