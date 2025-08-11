package io.github.StardewValley.shared.dto;

import io.github.StardewValley.shared.models.enums.FishType;
import io.github.StardewValley.shared.models.market.Fish;
import io.github.StardewValley.shared.models.market.ItemQuality;

public class FishingResultDTO {
    private FishType fishType;
    private ItemQuality quality;
    private boolean isPerfectCatch;
    private int fishCount;
    private Fish fish;

    public FishingResultDTO() {}
    public FishingResultDTO(Fish fish,Boolean isPerfectCatch,int fishCount,ItemQuality itemQuality){
        this.fish = fish;
        this.isPerfectCatch = isPerfectCatch;
        this.fishCount = fishCount;
        this.quality = itemQuality;
    }
    public FishingResultDTO(Fish fish,int fishCount,ItemQuality itemQuality){
        this.fish = fish;
        this.fishCount = fishCount;
        this.quality = itemQuality;
    }

    public FishingResultDTO(FishType fishType, ItemQuality quality, boolean isPerfect, int count) {
        this.fishType = fishType;
        this.quality = quality;
        this.isPerfectCatch = isPerfect;
        this.fishCount = count;
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

    public boolean isPerfectCatch() {
        return isPerfectCatch;
    }

    public void setPerfectCatch(boolean perfectCatch) {
        isPerfectCatch = perfectCatch;
    }

    public int getFishCount() {
        return fishCount;
    }

    public void setFishCount(int fishCount) {
        this.fishCount = fishCount;
    }

    public Fish getFish() {
        return fish;
    }

    public void setFish(Fish fish) {
        this.fish = fish;
    }
}
