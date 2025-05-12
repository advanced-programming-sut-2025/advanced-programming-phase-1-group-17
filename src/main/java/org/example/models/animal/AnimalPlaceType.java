package org.example.models.animal;

import org.example.models.BackPackableType;

public enum AnimalPlaceType implements BackPackableType {
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

    @Override
    public double getPrice() {
        return 0;
    }

    @Override
    public String getName() {
        return name();
    }
}
