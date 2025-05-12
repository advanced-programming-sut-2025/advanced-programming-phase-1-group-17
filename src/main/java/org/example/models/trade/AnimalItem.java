package org.example.models.trade;

import org.example.models.animal.AnimalType;

public class AnimalItem {
    //TODO: may this class be deleted and integrated with ShopItem

    private final AnimalType animalType;
    private final String description;
    private final double price;
    private final int dailyLimit;

    public AnimalItem(AnimalType animalType, String description, double price, int dailyLimit) {
        this.animalType = animalType;
        this.description = description;
        this.price = price;
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


    public int getDailyLimit() {
        return dailyLimit;
    }
}

