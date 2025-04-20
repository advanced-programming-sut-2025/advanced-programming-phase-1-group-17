package org.example.models.enums;

public enum FishType {
    // Regular Fish
    SALMON(75, Season.Fall, false),
    SARDINE(40, Season.Fall, false),
    SHAD(60, Season.Fall, false),
    BLUE_DISCUS(120, Season.Fall, false),
    MIDNIGHT_CARP(150, Season.Winter, false),
    SQUID(80, Season.Winter, false),
    TUNA(100, Season.Winter, false),
    PERCH(55, Season.Winter, false),
    FLOUNDER(100, Season.Spring, false),
    LIONFISH(100, Season.Spring, false),
    HERRING(30, Season.Spring, false),
    GHOSTFISH(45, Season.Spring, false),
    TILAPIA(75, Season.Summer, false),
    DORADO(100, Season.Summer, false),
    SUNFISH(30, Season.Summer, false),
    RAINBOW_TROUT(65, Season.Summer, false),

    // Legendary Fish
    LEGEND(5000, Season.Spring, true),
    GLACIERFISH(1000, Season.Winter, true),
    ANGLER(900, Season.Fall, true),
    CRIMSONFISH(1500, Season.Summer, true);

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

    public int getPrice() {
        return price;
    }

    public boolean isLegendary() {
        return isLegendary;
    }
}
