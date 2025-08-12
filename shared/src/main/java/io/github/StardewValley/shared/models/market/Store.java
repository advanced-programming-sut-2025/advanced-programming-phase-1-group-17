package io.github.StardewValley.shared.models.market;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.map.Placeable;
import io.github.StardewValley.shared.models.saveClasses.PlaceableSave;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Store implements Placeable {
    private StoreType type;
    private int start_x;
    private int start_y;
    private int width;
    private int height;

    public Store() {}

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
    public PlaceableSave toDTO(int x, int y, String ownerUsername) {
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

    public void setStart_x(int start_x) {
        this.start_x = start_x;
    }

    public void setStart_y(int start_y) {
        this.start_y = start_y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
