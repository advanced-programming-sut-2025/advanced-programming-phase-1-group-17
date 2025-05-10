package org.example.models.trade;

import org.example.models.enums.AnimalType;
import org.example.models.enums.AnimalHabitat;

public class AnimalItem {
    //TODO: may this class be deleted and integrated with ShopItem

    private int soldToday = 0;
    private final AnimalType animalType;
    private final String description;
    private final double price;
    private final AnimalHabitat requiredBuilding;
    private final int dailyLimit;

    public AnimalItem(AnimalType animalType, String description, double price, AnimalHabitat requiredBuilding, int dailyLimit) {
        this.animalType = animalType;
        this.description = description;
        this.price = price;
        this.requiredBuilding = requiredBuilding;
        this.dailyLimit = dailyLimit;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public AnimalHabitat getRequiredBuilding() {
        return requiredBuilding;
    }

    public int getDailyLimit() {
        return dailyLimit;
    }

    public void setSoldToday(int soldToday) {
        this.soldToday = soldToday;
    }

    public int getSoldToday() {
        return soldToday;
    }
}

