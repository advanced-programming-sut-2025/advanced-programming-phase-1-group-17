package org.example.models.enums;

public enum BackPackType {
    PrimaryBackpack(12, false),
    BigBackPack(24,false),
    DeluxeBackPack(0,true);
    private int capacity;
    private boolean isInfinity;
    BackPackType(int capacity, boolean isInfinity) {
        this.capacity = capacity;
        this.isInfinity = isInfinity;
    }

}
