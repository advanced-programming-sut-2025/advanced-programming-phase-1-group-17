package io.github.StardewValley.models.plant;

import io.github.StardewValley.models.BackPackable;
import io.github.StardewValley.models.market.ItemQuality;

import java.util.Random;

public class Fruit implements BackPackable {
    private FruitType type;
    private double price;
    private ItemQuality quality = ItemQuality.Regular;

    public Fruit(FruitType type) {
        this.type = type;
        this.price = type.getPrice();
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getName() {
        return type.name();
    }

    @Override
    public FruitType getType() {
        return type;
    }

    public void setType(FruitType type) {
        this.type = type;
    }

    public ItemQuality getQuality() {
        return quality;
    }

    public void setQuality(ItemQuality quality) {
        this.quality = quality;
    }

    public void setItemQuality() {
        Random random = new Random();
        int randInt = random.nextInt(100);

        if (randInt < 25)
            quality = ItemQuality.Regular;
        else if (randInt < 50)
            quality = ItemQuality.Silver;
        else if (randInt < 75)
            quality = ItemQuality.Gold;
        else
            quality = ItemQuality.Iridium;
    }
}
