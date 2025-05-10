package org.example.models;

import org.example.models.enums.FishType;

public class Fish implements BackPackable {
    private FishType fishType;

    public FishType getFishType() {
        return fishType;
    }

    public void setFishType(FishType fishType) {
        this.fishType = fishType;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public double getPrice() {
        return 0;
    }

    @Override
    public BackPackableType getType() {
        return null;
    }
}
