package org.example.models.enums;

public enum AnimalPlaceType {
    Coop(4),
    BigCoop(8),
    DeluxeCoop(12),
    Barn(4),
    BigBarn(8),
    DeluxeBarn(12);
    private final int capacity;
    AnimalPlaceType(int capacity) {
        this.capacity = capacity;
    }
    public int getCapacity() {
        return capacity;
    }
}
