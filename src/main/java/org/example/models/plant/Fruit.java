package org.example.models.plant;

import org.example.models.BackPackable;

public class Fruit implements BackPackable {
    private FruitType type;

    public Fruit(FruitType type) {
        this.type = type;
    }

    @Override
    public double getPrice() {
        return 0;
    }

    @Override
    public String getName() {
        return type.name();
    }

    @Override
    public FruitType getType() {
        return type;
    }

    public void setType(FruitType type) {
        this.type = type;
    }
}
