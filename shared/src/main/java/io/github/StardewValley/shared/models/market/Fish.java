package io.github.StardewValley.shared.models.market;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.StardewValley.shared.models.backpack.BackPackable;
import io.github.StardewValley.shared.models.backpack.BackPackableType;
import io.github.StardewValley.shared.models.enums.FishType;

public class Fish implements BackPackable {
    private FishType fishType;
    private ItemQuality quality = ItemQuality.Regular;
    private int count=1;
    public Fish(){

    }

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
    @JsonIgnore
    public String getName() {
        return fishType.getName();
    }

    @Override
    @JsonIgnore
    public double getPrice() {
        return fishType.getPrice();
    }

    @Override
    @JsonIgnore
    public BackPackableType getType() {
        return this.fishType;
    }

}
