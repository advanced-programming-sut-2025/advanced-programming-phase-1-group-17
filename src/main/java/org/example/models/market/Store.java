package org.example.models.market;

import org.example.models.Placeable;
import org.example.models.Product;

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
