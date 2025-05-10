package org.example.models.trade;

import org.example.models.BackPackable;
import org.example.models.BackPackableType;
import org.example.models.enums.FishType;

public class Fish implements BackPackable {
    private FishType fishType;
    private ShippingBinType shippingBinType;
    private int count=1;

    public FishType getFishType() {
        return fishType;
    }

    public void setFishType(FishType fishType) {
        this.fishType = fishType;
    }

    public ShippingBinType getShippingBinType() {
        return shippingBinType;
    }

    public void setShippingBinType(ShippingBinType shippingBinType) {
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
