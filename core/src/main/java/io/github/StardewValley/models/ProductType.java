package io.github.StardewValley.models;

public enum ProductType implements BackPackableType{
    ;

    @Override
    public String getName() {
        return name();
    }

    @Override
    public double getPrice() {
        return 0;
    }
}
