package org.example.models;

public class Ring implements BackPackable{

    @Override
    public String getName() {
        return RingType.Ring.getName();
    }

    @Override
    public double getPrice() {
        return 0;
    }

    @Override
    public BackPackableType getType() {
        return RingType.Ring;
    }
}
