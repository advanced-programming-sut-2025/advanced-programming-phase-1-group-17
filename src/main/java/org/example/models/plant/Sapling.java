package org.example.models.plant;

import org.example.models.BackPackable;

public class Sapling implements BackPackable {
    private SaplingType type;

    public Sapling(SaplingType treeSourceType) {
        this.type = treeSourceType;
    }

    @Override
    public SaplingType getType() {
        return type;
    }

    public void setType(SaplingType type) {
        this.type = type;
    }

    @Override
    public String getName() {
        return type.name();
    }

    @Override
    public double getPrice() {
        return type.getPrice();
    }
}
