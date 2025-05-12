package org.example.models.plant;

import org.example.models.BackPackableType;

public enum FertilizerType implements BackPackableType {
    SpeedGro, BasicRetainingSoil, QualityRetainingSoil, DeluxeRetainingSoil;

    @Override
    public double getPrice() {
        return 0;
    }

    @Override
    public String getName() {
        return name();
    }

    public static FertilizerType getFertilizerTypeByName(String name) {
        for (FertilizerType value : FertilizerType.values()) {
            if (value.name().equalsIgnoreCase(name)) {
                return value;
            }
        }
        return null;
    }
}
