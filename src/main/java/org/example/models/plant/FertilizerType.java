package org.example.models.plant;

import org.example.models.BackPackableType;

public enum FertilizerType implements BackPackableType {
    SpeedGro;

    @Override
    public double getPrice() {
        return 0;
    }

    @Override
    public String getName() {
        return name();
    }
}
