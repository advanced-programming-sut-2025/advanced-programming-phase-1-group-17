package org.example.models.enums;

import org.example.models.BackPackableType;

public enum FishType implements BackPackableType {
    // Regular Fish
    Salmon(75, Season.Fall, false),
    Sardine(40, Season.Fall, false),
    Shad(60, Season.Fall, false),
    BlueDiscus(120, Season.Fall, false),
    MidnightCarp(150, Season.Winter, false),
    Squid(80, Season.Winter, false),
    Tuna(100, Season.Winter, false),
    Perch(55, Season.Winter, false),
    Flounder(100, Season.Spring, false),
    LionFish(100, Season.Spring, false),
    Herring(30, Season.Spring, false),
    GhostFish(45, Season.Spring, false),
    Tilapia(75, Season.Summer, false),
    Dorado(100, Season.Summer, false),
    SunFish(30, Season.Summer, false),
    RainbowTrout(65, Season.Summer, false),

    // Legendary Fish
    Legend(5000, Season.Spring, true),
    GlacierFish(1000, Season.Winter, true),
    Angler(900, Season.Fall, true),
    CrimsonFish(1500, Season.Summer, true);

    private final int price;
    private final Season season;
    private final boolean isLegendary;

    FishType(int price, Season season, boolean isLegendary) {
        this.price = price;
        this.season = season;
        this.isLegendary = isLegendary;
    }

    public Season getSeason() {
        return season;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String getName() {
        return name();
    }

    public boolean isLegendary() {
        return isLegendary;
    }
}
