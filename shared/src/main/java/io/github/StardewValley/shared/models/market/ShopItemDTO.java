package io.github.StardewValley.shared.models.market;

import io.github.StardewValley.shared.models.enums.Season;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopItemDTO {
    private boolean isAvailable = true;
    private String type; // Unique identifier (e.g. "ParsnipSeeds")
    private String typeCategory; // e.g. "SeedType", "ToolType"
    private double price;
    private int dailyLimit;
    private int soldToday;
    private String description;
    private List<String> availableSeasons;
    private Map<String, Integer> cost;

    // Empty constructor for serialization
    public ShopItemDTO() {}

    public ShopItemDTO(ShopItem item, Season season) {
        this.isAvailable = item.isAvailable() && item.isAvailableInSeason(season);
        this.type = item.getType().getName();
        this.typeCategory = item.getType().getClass().getSimpleName();
        this.price = item.getPrice();
        this.dailyLimit = item.getDailyLimit();
        this.soldToday = item.getSoldToday();
        this.description = item.getDescription();
        this.availableSeasons = item.getAvailableSeasons()
            .stream().map(Enum::name).toList();
        this.cost = new HashMap<>();
        for (var entry : item.getCost().entrySet()) {
            this.cost.put(entry.getKey().getName(), entry.getValue());
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeCategory() {
        return typeCategory;
    }

    public void setTypeCategory(String typeCategory) {
        this.typeCategory = typeCategory;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(int dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    public int getSoldToday() {
        return soldToday;
    }

    public void setSoldToday(int soldToday) {
        this.soldToday = soldToday;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getAvailableSeasons() {
        return availableSeasons;
    }

    public void setAvailableSeasons(List<String> availableSeasons) {
        this.availableSeasons = availableSeasons;
    }

    public Map<String, Integer> getCost() {
        return cost;
    }

    public void setCost(Map<String, Integer> cost) {
        this.cost = cost;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
