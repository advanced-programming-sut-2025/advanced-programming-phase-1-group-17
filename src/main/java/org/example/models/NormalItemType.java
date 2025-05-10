package org.example.models;

public enum NormalItemType implements BackPackableType{
    //TODO: Alaf
    CopperOre,
    IronOre,
    GoldOre,
    CopperBar,
    IronBar,
    GoldBar,
    IridiumBar,
    Wood,
    Fibre;

    @Override
    public double getPrice() {
        //TODO
        return 0;
    }

    @Override
    public String getName() {
        return name();
    }
}
