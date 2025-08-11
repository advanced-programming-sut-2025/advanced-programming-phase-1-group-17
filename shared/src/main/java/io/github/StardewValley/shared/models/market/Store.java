package io.github.StardewValley.shared.models.market;

import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.map.Placeable;
import io.github.StardewValley.shared.models.saveClasses.PlaceableSave;

import java.util.List;

public class Store implements Placeable {
    private StoreType type;
    private final int start_x;
    private final int start_y;
    private final int width;
    private final int height;

    public Store(StoreType type, int start_x, int start_y, int width, int height) {
        this.type = type;
        this.start_x = start_x;
        this.start_y = start_y;
        this.width = width;
        this.height = height;
    }

    public StoreType getType() {
        return type;
    }

    public void setType(StoreType type) {
        this.type = type;
    }


    @Override
    public String  getTexture() {
        //handled else where
        return null;
    }

    @Override
    public PlaceableSave toDTO() {
        PlaceableSave placeableSave = new PlaceableSave(Store.class.getSimpleName());
        placeableSave.setStore(this);
        return placeableSave;
    }

    @Override
    public Placeable loadFromDTO(PlaceableSave dto, List<Player> playerList) {
        return this;
    }

    public int getStart_x() {
        return start_x;
    }

    public int getStart_y() {
        return start_y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
