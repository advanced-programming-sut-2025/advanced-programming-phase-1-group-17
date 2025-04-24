package org.example.foraging;

import org.example.models.BackPackable;
import org.example.models.BackPackableType;
import org.example.models.enums.Season;

public class ForagingItem implements BackPackable {
    private final String name;
    private final double price;
    private final Season season;
    private final int energy;
    private final boolean randomlyGenerated;

    public ForagingItem(String name, double price, Season season, int energy, boolean randomlyGenerated) {
        this.name = name;
        this.price = price;
        this.season = season;
        this.energy = energy;
        this.randomlyGenerated = randomlyGenerated;
    }

    @Override public String getName() { return name; }
    @Override public double getPrice() { return price; }
    @Override public BackPackableType getType() { return () -> "Foraging"; }

    @Override public Season getSeason() { return season; }
    @Override public boolean isRandomlyGenerated() { return randomlyGenerated; }

    public int getEnergy() { return energy; }
}

