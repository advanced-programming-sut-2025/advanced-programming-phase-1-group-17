package org.example.models.tools;

import org.example.models.BackPackableType;

public enum FishingPoleType implements BackPackableType {
    TrainingFishingPole(0.1, 12.5),
    BambooFishingPole(0.5, 250),
    FiberglassFishingPole(0.9, 900),
    IridiumFishingPole(1.2, 3750);

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
