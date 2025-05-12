package org.example.models.market;

public enum ItemQuality {
    Regular(1),
    Silver(1.25),
    Gold(1.5),
    Iridium(2);

    private final double leverage;

    ItemQuality(double leverage) {
        this.leverage = leverage;
    }

    public double getLeverage() {
        return leverage;
    }

}
