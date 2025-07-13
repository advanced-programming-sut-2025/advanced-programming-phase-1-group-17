package io.github.StardewValley.models.market;

import io.github.StardewValley.models.Placeable;
import io.github.StardewValley.models.Product;

import java.util.ArrayList;
public class Store implements Placeable {
    private StoreType type;
    private ArrayList<Product> products;

    public Store(StoreType type) {
        this.type = type;
    }

    public StoreType getType() {
        return type;
    }

    public void setType(StoreType type) {
        this.type = type;
    }


}
