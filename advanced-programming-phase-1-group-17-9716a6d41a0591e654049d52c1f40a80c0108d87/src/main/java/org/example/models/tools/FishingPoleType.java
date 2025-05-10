package org.example.models.tools;

import org.example.models.BackPackableType;

public enum FishingPoleType implements BackPackableType {
    Bamboo,
    Training,
    Fiberglass,
    Iridium;

    @Override
    public double getPrice() {
        return 0;
    }

    @Override
    public String getName() {
        return name();
    }
}
