package io.github.StardewValley.shared.models.market;

public class UpgradeServiceDTO {
    private String name;
    private String requiredMaterial;  // Use name instead of enum reference
    private int requiredQuantity;
    private int cost;
    private int dailyLimit;
    private int soldToday;

    public UpgradeServiceDTO() {}

    public UpgradeServiceDTO(String name, String requiredMaterial, int requiredQuantity, int cost, int dailyLimit, int soldToday) {
        this.name = name;
        this.requiredMaterial = requiredMaterial;
        this.requiredQuantity = requiredQuantity;
        this.cost = cost;
        this.dailyLimit = dailyLimit;
        this.soldToday = soldToday;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public String getRequiredMaterial() {
        return requiredMaterial;
    }

    public int getRequiredQuantity() {
        return requiredQuantity;
    }

    public int getCost() {
        return cost;
    }

    public int getDailyLimit() {
        return dailyLimit;
    }

    public int getSoldToday() {
        return soldToday;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRequiredMaterial(String requiredMaterial) {
        this.requiredMaterial = requiredMaterial;
    }

    public void setRequiredQuantity(int requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setDailyLimit(int dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    public void setSoldToday(int soldToday) {
        this.soldToday = soldToday;
    }
}
