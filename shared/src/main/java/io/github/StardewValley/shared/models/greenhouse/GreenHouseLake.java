package io.github.StardewValley.shared.models.greenhouse;

import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.shared.models.map.Placeable;
import io.github.StardewValley.shared.models.savedClasses.GreenHouseLakeSave;
import io.github.StardewValley.shared.models.savedClasses.PlaceableSave;

public class GreenHouseLake implements Placeable {
    @Override
    public String  getTexture() {
        //TODO
        return null;
    }

    @Override
    public PlaceableSave toDTO() {
        PlaceableSave placeableSave = new PlaceableSave(GreenHouseLake.class.getSimpleName());
        placeableSave.setGreenHouseLake(new GreenHouseLakeSave());
        return placeableSave;
    }

    @Override
    public void loadFromDTO(PlaceableSave dto) {
        GreenHouseLake greenHouseLake = new GreenHouseLake();
    }
}
