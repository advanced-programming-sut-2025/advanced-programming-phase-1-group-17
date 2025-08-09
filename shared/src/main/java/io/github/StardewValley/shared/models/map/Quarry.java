package io.github.StardewValley.shared.models.map;

import io.github.StardewValley.shared.models.savedClasses.PlaceableSave;
import io.github.StardewValley.shared.models.savedClasses.QuarrySave;

public class Quarry implements Placeable {
    private String texture = "assets/Rock/Quarry_Boulder.png";
    @Override
    public String getTexture() {
        return texture;
    }

    @Override
    public PlaceableSave toDTO() {
        PlaceableSave placeableSave = new PlaceableSave(Quarry.class.getSimpleName());
        placeableSave.setQuarrySave(new QuarrySave());
        return placeableSave;
    }

    @Override
    public void loadFromDTO(PlaceableSave dto) {
        Quarry quarry = new Quarry();
    }
}
