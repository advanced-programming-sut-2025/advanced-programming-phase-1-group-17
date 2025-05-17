package org.example.models.market;

import org.example.models.BackPackable;
import org.example.models.BackPackableType;
import org.example.models.enums.FishType;

public class Fish implements BackPackable {
    private FishType fishType;
    private ItemQuality quality = ItemQuality.Regular;
    private int count=1;

    public Fish(FishType fishType, ItemQuality shippingBinType) {
        this.fishType = fishType;
        this.quality = shippingBinType;
    }

    public FishType getFishType() {
        return fishType;
    }

    public void setFishType(FishType fishType) {
        this.fishType = fishType;
    }

    public ItemQuality getQuality() {
        return quality;
    }

    public void setQuality(ItemQuality quality) {
        this.quality = quality;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String getName() {
        return fishType.getName();
    }

    @Override
    public double getPrice() {
        return fishType.getPrice();
    }

    @Override
    public BackPackableType getType() {
        return this.fishType;
    }
}
