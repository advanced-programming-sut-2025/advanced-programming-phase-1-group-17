package org.example.models.enums;

public enum ShippingBinType {
    Regular(1),
    Silver(1.25),
    Gold(1.5),
    Iridium(2);
    private double leverage;
    ShippingBinType(double leverage) {}
    public double getLeverage() {}

}
