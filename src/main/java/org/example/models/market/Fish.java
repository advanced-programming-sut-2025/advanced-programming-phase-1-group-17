package org.example.models.market;

import org.example.models.BackPackable;
import org.example.models.BackPackableType;
import org.example.models.enums.FishType;

public class Fish implements BackPackable {
    private FishType fishType;
    private ItemQuality shippingBinType;
    private int count=1;

    public FishType getFishType() {
        return fishType;
    }

    public void setFishType(FishType fishType) {
        this.fishType = fishType;
    }

    public ItemQuality getShippingBinType() {
        return shippingBinType;
    }

    public void setShippingBinType(ItemQuality shippingBinType) {
        this.shippingBinType = shippingBinType;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public double getPrice() {
        return 0;
    }

    @Override
    public BackPackableType getType() {
        return null;
    }
}
