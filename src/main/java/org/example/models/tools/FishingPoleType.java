package org.example.models.tools;

import org.example.models.BackPackableType;

public enum FishingPoleType implements BackPackableType {
    Training(0.1),
    Bamboo(0.5),
    Fiberglass(0.9),
    Iridium(1.2);
    private final double pole;
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
