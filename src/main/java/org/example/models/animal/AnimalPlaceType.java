package org.example.models.animal;

import org.example.models.BackPackableType;

public enum AnimalPlaceType implements BackPackableType {
    Coop(4,4000),
    BigCoop(8,10000),
    DeluxeCoop(12,20000),
    Barn(4,6000),
    BigBarn(8,12000),
    DeluxeBarn(12,25000);
    private final int capacity;
    private final int price;
    AnimalPlaceType(int capacity, int price) {
        this.capacity = capacity;
        this.price = price;
    }
    public int getCapacity() {
        return capacity;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getName() {
        return name();
    }
}
