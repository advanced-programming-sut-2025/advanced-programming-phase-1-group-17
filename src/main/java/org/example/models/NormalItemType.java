package org.example.models;

public enum NormalItemType implements BackPackableType{
    //TODO: Alaf
    Wood,
    Stone;

    @Override
    public double getPrice() {
        //TODo
        return 0;
    }

    @Override
    public String getName() {
        return name();
    }
}
