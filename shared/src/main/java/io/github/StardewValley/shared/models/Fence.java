package io.github.StardewValley.shared.models;

import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.shared.models.map.Placeable;
import io.github.StardewValley.shared.models.savedClasses.FenceSave;
import io.github.StardewValley.shared.models.savedClasses.PlaceableSave;

public class Fence implements Placeable {
    private String fenceTexture = GameAssetManager.getGameAssetManager().getFenceTexture();
    private String fenceTexture2 = GameAssetManager.getGameAssetManager().getFenceTexture2();
    boolean isHorizontal;
    public Fence(boolean isHorizontal) {
        this.isHorizontal = isHorizontal;
    }
    @Override
    public String getTexture() {
        if (!isHorizontal)
            return fenceTexture;
        else
            return fenceTexture2;
    }

    @Override
    public PlaceableSave toDTO() {
        PlaceableSave placeableSave = new PlaceableSave(Fence.class.getSimpleName());
        placeableSave.setFenceSave(new FenceSave(isHorizontal));
        return placeableSave;
    }

    @Override
    public void loadFromDTO(PlaceableSave dto) {
        Fence fence = new Fence(dto.getFenceSave().isHorizontal());
    }
}
