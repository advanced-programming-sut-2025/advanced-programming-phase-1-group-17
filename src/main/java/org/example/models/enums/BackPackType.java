package org.example.models.enums;

public enum BackPackType {
    PrimaryBackpack(12),
    BigBackPack(24), //TODO: available in Pierre Store
    DeluxeBackPack((int)Double.POSITIVE_INFINITY);  //TODO: available in Pierre Store after we have purchased a BigBackPack

    private final int capacity;

    BackPackType(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }
}
