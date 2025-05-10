package org.example.models.market;

import org.example.models.BackPackable;
import org.example.models.BackPackableType;
import org.example.models.enums.Season;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShopItem implements BackPackable {
    private final BackPackableType type;
    private final int dailyLimit;
    private final double price;
    private final String description;
    private final List<Season> availableSeasons;

    public ShopItem(BackPackableType type, int dailyLimit, double price, String description) {
        this(type, dailyLimit, price, description, List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter));
    }

    public ShopItem(BackPackableType type, int dailyLimit, double price, String description, List<Season> availableSeasons) {
        this.type = type;
        this.dailyLimit = dailyLimit;
        this.price = price;
        this.description = description;
        this.availableSeasons = new ArrayList<>(availableSeasons);
    }

    @Override
    public BackPackableType getType() {
        return type;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getName() {
        return type.getName();
    }

    public String getDescription() {
        return description;
    }

    public int getDailyLimit() {
        return dailyLimit;
    }

    public List<Season> getAvailableSeasons() {
        return new ArrayList<>(availableSeasons);
    }

    public boolean isAvailableInSeason(Season season) {
        return availableSeasons.isEmpty() || availableSeasons.contains(season);
    }
}
