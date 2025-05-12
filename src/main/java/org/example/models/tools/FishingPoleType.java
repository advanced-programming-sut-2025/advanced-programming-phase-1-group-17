package org.example.models.tools;

import org.example.models.BackPackableType;

public enum FishingPoleType implements BackPackableType {
    Training(0.1, 12.5),
    Bamboo(0.5, 250),
    Fiberglass(0.9, 900),
    Iridium(1.2, 3750);

    private final double pole;
    private final double price;

    FishingPoleType(double pole, double price) {
        this.pole = pole;
        this.price = price;
    }

    public double getPole() {
        return pole;
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
