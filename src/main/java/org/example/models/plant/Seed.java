package org.example.models.plant;

import org.example.models.BackPackable;
import org.example.models.Placeable;

public class Seed implements BackPackable, Placeable {
    private SeedType type;
    private double price;

    public Seed(SeedType type) {
        this.type = type;
    }

    public SeedType getType() {
        return type;
    }

    public void setType(SeedType type) {
        this.type = type;
    }

    @Override
    public String getName() {
        return type.name();
    }

    @Override
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
