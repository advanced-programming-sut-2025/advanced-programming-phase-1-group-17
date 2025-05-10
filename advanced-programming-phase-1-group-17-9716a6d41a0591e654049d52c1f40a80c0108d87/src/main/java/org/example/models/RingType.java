package org.example.models;

public enum RingType implements BackPackableType{
    Ring;

    @Override
    public String getName() {
        return name();
    }

    @Override
    public double getPrice() {
        return 0;
    }
}
