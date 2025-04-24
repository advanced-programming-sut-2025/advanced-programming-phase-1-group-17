package org.example.models.plant;

import org.example.models.BackPackable;

public class Fruit implements BackPackable {
    private FruitType type;


    @Override
    public double getPrice() {
        return 0;
    }

    @Override
    public String getName() {
        return type.name();
    }
}
