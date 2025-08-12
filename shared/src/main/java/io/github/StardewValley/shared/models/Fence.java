package io.github.StardewValley.shared.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.shared.models.map.Placeable;
import io.github.StardewValley.shared.models.saveClasses.PlaceableSave;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Fence implements Placeable {
    private String fenceTexture = GameAssetManager.getGameAssetManager().getFenceTexture();
    private String fenceTexture2 = GameAssetManager.getGameAssetManager().getFenceTexture2();
    boolean isHorizontal;

    public Fence() {}

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
    public PlaceableSave toDTO(int x, int y, String ownerUsername) {
        PlaceableSave placeableSave = new PlaceableSave(Fence.class.getSimpleName());
        placeableSave.setFence(this);
        return placeableSave;
    }

    @Override
    public Placeable loadFromDTO(PlaceableSave dto, List<Player> playerList) {
        return this;
    }

    public String getFenceTexture() {
        return fenceTexture;
    }

    public void setFenceTexture(String fenceTexture) {
        this.fenceTexture = fenceTexture;
    }

    public String getFenceTexture2() {
        return fenceTexture2;
    }

    public void setFenceTexture2(String fenceTexture2) {
        this.fenceTexture2 = fenceTexture2;
    }

    public boolean isHorizontal() {
        return isHorizontal;
    }

    public void setHorizontal(boolean horizontal) {
        isHorizontal = horizontal;
    }
}
