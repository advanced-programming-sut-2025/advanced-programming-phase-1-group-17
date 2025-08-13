package io.github.StardewValley.shared.models.market;

import io.github.StardewValley.shared.models.backpack.BackPackableType;

public class UpgradeService {
    private int soldToday = 0;
    private String name;
    private BackPackableType requiredMaterial;
    private int requiredQuantity;
    private int cost;
    private int dailyLimit;

    public UpgradeService() {
    }

    public UpgradeService(String name, BackPackableType requiredMaterial, int requiredQuantity, int cost, int dailyLimit) {
        this.name = name;
        this.requiredMaterial = requiredMaterial;
        this.requiredQuantity = requiredQuantity;
        this.cost = cost;
        this.dailyLimit = dailyLimit;
    }

    public static UpgradeServiceDTO getUpgradeServiceDTO(UpgradeService upgradeService) {
        return new UpgradeServiceDTO(
            upgradeService.name,
            upgradeService.requiredMaterial.getName(),
            upgradeService.requiredQuantity,
            upgradeService.cost,
            upgradeService.requiredQuantity,
            upgradeService.soldToday
        );
    }

    public String getName() {
        return name;
    }

    public BackPackableType getRequiredMaterial() {
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

    public void setSoldToday(int soldToday) {
        this.soldToday = soldToday;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRequiredMaterial(BackPackableType requiredMaterial) {
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
}
