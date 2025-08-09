package io.github.StardewValley.shared.models.greenhouse;

import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.shared.models.map.Placeable;
import io.github.StardewValley.shared.models.savedClasses.GreenHouseFenceSave;
import io.github.StardewValley.shared.models.savedClasses.PlaceableSave;

public class GreenHouseFence implements Placeable {
    @Override
    public String  getTexture() {
        return GameAssetManager.getGameAssetManager().getGreenHouseFenceTexture();
    }

    @Override
    public PlaceableSave toDTO() {
        PlaceableSave placeableSave = new PlaceableSave(GreenHouseFence.class.getSimpleName());
        placeableSave.setGreenHouseFenceSave(new GreenHouseFenceSave());
        return placeableSave;
    }

    @Override
    public void loadFromDTO(PlaceableSave dto) {
        GreenHouseFence greenHouseFence = new GreenHouseFence();
    }
}
