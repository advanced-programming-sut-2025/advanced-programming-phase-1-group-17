package org.example.models.plant;

import org.example.models.Placeable;
import org.example.models.map.Tile;

public abstract class Plant implements Placeable {
    Tile tile;
    boolean isFullyGrown;
    boolean isForaging;
    int currentStageIndex = 0;
    int whichDayOfStage = 1;
    boolean isFertilized;
    int daysWithoutWater = 0;

    Plant(boolean isForaging, boolean isFertilized, Tile tile) {
        this.isFertilized = isFertilized;
        this.tile = tile;
        this.isForaging = isForaging;
    }

    public abstract int getDaysTillFullGrowth();
    public abstract void goToNextDay();

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
    public void wateringPlant(){
        this.daysWithoutWater=0;
    }


}
