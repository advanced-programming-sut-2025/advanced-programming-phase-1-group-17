package io.github.StardewValley.shared.models.savedClasses;

import io.github.StardewValley.shared.models.market.Store;
import io.github.StardewValley.shared.models.market.StoreType;

public class StoreSave {
    private StoreType type;
    private int start_x;
    private int start_y;
    private int width;
    private int height;

    public StoreSave() {
    }

    public StoreSave(Store store) {
        this.type = store.getType();
        this.start_x = store.getStart_x();
        this.start_y = store.getStart_y();
        this.width = store.getWidth();
        this.height = store.getHeight();
    }

    public StoreType getType() {
        return type;
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
