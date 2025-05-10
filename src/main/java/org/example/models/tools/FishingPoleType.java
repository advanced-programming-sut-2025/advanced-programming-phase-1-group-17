package org.example.models.tools;

import org.example.models.BackPackableType;

public enum FishingPoleType implements BackPackableType {
    Bamboo(0.5),
    Training(0.1),
    Fiberglass(0.9),
    Iridium(1.2);
    private double pole;
    FishingPoleType(double pole) {
        this.pole = pole;
    }
    public double getPole() {
        return pole;
    }

    @Override
    public double getPrice() {
        return 0;
    }

    @Override
    public String getName() {
        return name();
    }

}
