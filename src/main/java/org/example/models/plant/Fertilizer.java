package org.example.models.plant;

import org.example.models.BackPackable;

public class Fertilizer implements BackPackable {
    private FertilizerType type;

    public Fertilizer(FertilizerType type) {
        this.type = type;
    }

    @Override
    public FertilizerType getType() {
        return type;
    }

    public void setType(FertilizerType type) {
        this.type = type;
    }

    @Override
    public String getName() {
        return type.getName();
    }

    @Override
    public double getPrice() {
        return type.getPrice();
    }
}
