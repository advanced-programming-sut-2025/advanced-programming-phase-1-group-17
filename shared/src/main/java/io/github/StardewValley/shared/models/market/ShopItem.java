package io.github.StardewValley.shared.models.market;

import io.github.StardewValley.shared.models.backpack.BackPackable;
import io.github.StardewValley.shared.models.backpack.BackPackableType;
import io.github.StardewValley.shared.models.enums.Season;
import io.github.StardewValley.shared.models.saveClasses.BackPackableSave;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShopItem implements BackPackable {
    private boolean isAvailable = true;
    private int soldToday = 0;
    private final BackPackableType type;
    private final int dailyLimit;
    private final double price;
    private final HashMap<BackPackableType, Integer> cost;
    private final String description;
    private final List<Season> availableSeasons;

    public ShopItem(BackPackableType type, double price, int dailyLimit, String description) {
        this(type, price, dailyLimit, description, List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter));
    }

    public ShopItem(BackPackableType type, double price, int dailyLimit, String description, List<Season> availableSeasons) {
        this.type = type;
        this.dailyLimit = dailyLimit;
        this.price = price;
        this.description = description;
        this.availableSeasons = new ArrayList<>(availableSeasons);
        this.cost = new HashMap<>();
    }

    public ShopItem(BackPackableType type, double price, HashMap<BackPackableType, Integer> cost, int dailyLimit) {
        this.type = type;
        this.price = price;
        this.dailyLimit = dailyLimit;
        this.cost = cost;
        this.description = "";
        this.availableSeasons = new ArrayList<>();
    }

    @Override
    public BackPackableType getType() {
        return type;
    }

    @Override
    public BackPackableSave toBackpackableSave() {
        BackPackableSave backPackableSave = new BackPackableSave(ShopItem.class.getSimpleName());
        backPackableSave.setShopItem(this);
        return backPackableSave;
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

    public void setSoldToday(int soldToday) {
        this.soldToday = soldToday;
    }

    public int getSoldToday() {
        return soldToday;
    }

    public HashMap<BackPackableType, Integer> getCost() {
        return cost;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
