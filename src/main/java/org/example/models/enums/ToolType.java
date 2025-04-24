package org.example.models.enums;

import org.example.models.BackPackableType;

public enum ToolType implements BackPackableType {
    Pichaxe,
    Scythe,
    Hoe,
    Axe,
    WateringCan,
    FishingPole,
    MilkPail,
    Shear,
    TrashCan;

    @Override
    public String getName() {
        return name();
    }

    @Override
    public double getPrice() {
        return 0;
    }
}
