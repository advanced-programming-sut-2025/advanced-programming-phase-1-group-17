package org.example.models.enums;

import org.example.models.BackPackableType;

public enum BackPackType implements BackPackableType {
    PrimaryBackpack(12, 0),
    LargeBackPack(24, 1000),
    DeluxeBackPack((int)Double.POSITIVE_INFINITY, 5000);

    private final int capacity;
    private final double price;

    BackPackType(int capacity, double price) {
        this.capacity = capacity;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public String getName() {
        return name();
    }
}
