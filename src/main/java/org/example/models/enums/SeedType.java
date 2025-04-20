package org.example.models.enums;

public enum SeedType {
    // Spring
    Cauliflower(Season.Spring, null),
    Parsnip(Season.Spring, null),
    Potato(Season.Spring, null),
    BlueJazz(Season.Spring, null),
    Tulip(Season.Spring, null),

    // Summer
    HotPepper(Season.Summer, null),
    Radish(Season.Summer, null),
    Wheat(Season.Summer, null),
    Poppy(Season.Summer, null),
    SummerSpangle(Season.Summer, null),

    // Fall
    Artichoke(Season.Fall, null),
    Eggplant(Season.Fall, null),
    Pumpkin(Season.Fall, null),
    FairyRose(Season.Fall, null),

    // Multi-season
    Corn(Season.Summer, Season.Fall),
    Sunflower(Season.Summer, Season.Fall),

    // Winter
    PowderMelon(Season.Winter, null),

    // Special
    Mixed(Season.Spring, Season.Summer); // Example mixed seed

    private final Season season1;
    private final Season season2;

    SeedType(Season season1, Season season2) {
        this.season1 = season1;
        this.season2 = season2;
    }

    public boolean isGrownIn(Season season) {
        return season == season1 || season == season2;
    }
}
