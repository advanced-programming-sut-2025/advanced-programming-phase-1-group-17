package org.example.models.market;

import org.example.models.BackPackableType;

public class UpgradeService {
    private final String name;
    private final BackPackableType requiredMaterial;
    private final int requiredQuantity;
    private final int cost;
    private final int dailyLimit;

    public UpgradeService(String name, BackPackableType requiredMaterial, int requiredQuantity, int cost, int dailyLimit) {
        this.name = name;
        this.requiredMaterial = requiredMaterial;
        this.requiredQuantity = requiredQuantity;
        this.cost = cost;
        this.dailyLimit = dailyLimit;
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
}
