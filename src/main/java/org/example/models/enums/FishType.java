package org.example.models.enums;

public enum FishType {
    private final int price;
    private final Season season;
    FishType(int price,Season season) {}
    public Season getSeason() {}
    public int getPrice() {}
}

