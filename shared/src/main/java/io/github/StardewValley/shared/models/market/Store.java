package io.github.StardewValley.shared.models.market;

import io.github.StardewValley.shared.models.map.Placeable;

public class Store implements Placeable {
    private StoreType type;
    private int start_x;
    private int start_y;
    private int width;
    private int height;

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
