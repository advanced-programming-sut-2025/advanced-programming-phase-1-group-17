package org.example.models;

public enum NormalItemType implements BackPackableType{
    //TODO: Alaf
    //CopperOre(75),
    //IronOre(150),
    //GoldOre(150),
    CopperBar(0),
    IronBar(0),
    GoldBar(0),
    IridiumBar(0),
    Wood(10),
    Fibre(0),
    Hay(50),
    Well(500), //TODO: provides a place to fill your watering can
    ShippingBin(125),
    JojaCola(37.5),
    GrassStarter(62.5),
    Sugar(50),
    WheatFlour(50),
    Rice(100),
    TroutSoup(125);

    private final double price;

    NormalItemType(double price) {
        this.price = price;
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
