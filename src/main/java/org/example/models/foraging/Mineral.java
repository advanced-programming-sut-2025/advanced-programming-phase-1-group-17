package org.example.models.foraging;

import org.example.models.BackPackable;
import org.example.models.Placeable;

public class Mineral implements BackPackable, Placeable {
    MineralType type;
    boolean isForaging;

    public Mineral(MineralType type, boolean isForaging) {
        this.type = type;
        this.isForaging = isForaging;
    }

    @Override
    public String getName() {
        return type.getName();
    }

    public boolean isForaging() {
        return isForaging;
    }

    @Override
    public MineralType getType() {
        return type;
    }

    @Override
    public double getPrice() {
        return type.getPrice();
    }
}
