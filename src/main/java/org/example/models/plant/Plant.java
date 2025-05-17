package org.example.models.plant;

import org.example.models.Placeable;
import org.example.models.map.Tile;
import org.example.models.tools.ToolMaterial;

import java.util.Random;

public abstract class Plant implements Placeable {
    protected boolean isInsideGreenhouse;
    protected boolean isWateredToday = false;
    protected Tile tile;
    protected boolean hasFruit = false;
    protected boolean isFullyGrown;
    protected boolean isForaging;
    protected int currentStageIndex = 0;
    protected int whichDayOfStage = 1;
    protected FertilizerType fertilizerType = null;
    protected int daysWithoutWater = 0;
    protected int daysTillNextHarvest;

    Plant(boolean isForaging, Tile tile, boolean isInsideGreenhouse) {
        this.isInsideGreenhouse = isInsideGreenhouse;
        this.tile = tile;
        this.isForaging = isForaging;
    }

    public abstract int getDaysTillFullGrowth();

    public void goToNextDay() {
        handleDaysWithoutWater();
        handleFruitCycle();
        if (!this.isWateredToday)
            return;
        //stage Handling
        handleStages();
        this.isWateredToday = false;
    }

    private void handleDaysWithoutWater() {
        Random random = new Random();
        int randInt = random.nextInt(100);
        if (fertilizerType != null) {
            if (fertilizerType.equals(FertilizerType.BasicRetainingSoil)) {
                if (randInt < 30) {
                    wateringPlant();
                    return;
                }
            } else if (fertilizerType.equals(FertilizerType.QualityRetainingSoil)) {
                if (randInt < 60) {
                    wateringPlant();
                    return;
                }
            } else if (fertilizerType.equals(FertilizerType.DeluxeRetainingSoil)) {
                wateringPlant();
                return;
            }
        }

        this.daysWithoutWater++;
        if (this.daysWithoutWater > 2) {
            if (this.isInsideGreenhouse)
                tile.setPlaceable(Tile.getTile(this.tile.getX(), this.tile.getY()).getOwner().getPlayerMap().getGreenHouse());
            else
                tile.setPlaceable(null);
        }
    }

    abstract void handleFruitCycle();

    abstract void handleStages();

    public Tile getTile() {
        return tile;
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

    public FertilizerType getFertilizerType() {
        return fertilizerType;
    }

    public void setFertilizerType(FertilizerType fertilizerType) {
        this.fertilizerType = fertilizerType;
    }

    public boolean isInsideGreenhouse() {
        return isInsideGreenhouse;
    }

    public void setInsideGreenhouse(boolean insideGreenhouse) {
        isInsideGreenhouse = insideGreenhouse;
    }

    public void wateringPlant() {
        this.daysWithoutWater = 0;
        isWateredToday = true;
    }

    public abstract void harvest(ToolMaterial scytheMaterial);
    //TODO: quality when harvesting

}
