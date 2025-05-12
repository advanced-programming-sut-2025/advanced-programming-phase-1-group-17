package org.example.models;

public class NormalItem implements BackPackable, Placeable{
    private NormalItemType type;

    @Override
    public String getName() {
        return type.getName();
    }

    @Override
    public double getPrice() {
        return type.getPrice();
    }

    @Override
    public NormalItemType getType() {
        return type;
    }
}
