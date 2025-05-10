package org.example.models.plant;

import org.example.models.Placeable;
import org.example.models.map.Tile;

public abstract class Plant implements Placeable {
    //TODO: use this boolean
    protected boolean isInsideGreenhouse;
    protected boolean isWateredToday = false;
    protected Tile tile;
    protected boolean hasFruit = false;
    protected boolean isFullyGrown;
    protected boolean isForaging;
    protected int currentStageIndex = 0;
    protected int whichDayOfStage = 1;
    protected boolean isFertilized;
    protected int daysWithoutWater = 0;
    protected int daysTillNextHarvest;

    Plant(boolean isForaging, boolean isFertilized, Tile tile, boolean isInsideGreenhouse) {
        this.isInsideGreenhouse = isInsideGreenhouse;
        this.isFertilized = isFertilized;
        this.tile = tile;
        this.isForaging = isForaging;
    }

    public abstract int getDaysTillFullGrowth();

    public void goToNextDay() {
        if (this.isFullyGrown)
            return;

        this.daysWithoutWater++;
        if (this.daysWithoutWater >= 2) {
            tile.setPlaceable(null);
            return;
        }

        if (!this.isWateredToday)
            return;

        //stage Handling
        handleStages();
        handleFruitCycle();
    }

    abstract void handleFruitCycle();

    abstract void handleStages();

    public Tile getTile() {
        return tile;
    }

    public void setFertilized(boolean fertilized) {
        isFertilized = fertilized;
    }

    public int getDaysWithoutWater() {
        return daysWithoutWater;
    }

    public void setDaysWithoutWater(int daysWithoutWater) {
        this.daysWithoutWater = daysWithoutWater;
    }


    public int getCurrentStageIndex() {
        return currentStageIndex;
    }

    public void setCurrentStageIndex(int currentStageIndex) {
        this.currentStageIndex = currentStageIndex;
    }

    public int getWhichDayOfStage() {
        return whichDayOfStage;
    }

    public void setWhichDayOfStage(int whichDayOfStage) {
        this.whichDayOfStage = whichDayOfStage;
    }


    public boolean isFertilized() {
        return isFertilized;
    }

    public boolean isFullyGrown() {
        return isFullyGrown;
    }

    public void setFullyGrown(boolean fullyGrown) {
        isFullyGrown = fullyGrown;
    }

    public boolean isForaging() {
        return isForaging;
    }

    public void setForaging(boolean foraging) {
        isForaging = foraging;
    }

    public void wateringPlant() {
        this.daysWithoutWater = 0;
        isWateredToday = true;
    }

    public abstract void harvest();
    //TODO: quality when harvesting
}
