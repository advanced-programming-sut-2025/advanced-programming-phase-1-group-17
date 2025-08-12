package io.github.StardewValley.shared.models.saveClasses;

import io.github.StardewValley.shared.models.plant.FertilizerType;
import io.github.StardewValley.shared.models.plant.Tree;
import io.github.StardewValley.shared.models.plant.TreeType;

public class TreeSave {
    private boolean isInsideGreenhouse;
    private boolean isWateredToday;
    private boolean hasFruit;
    private boolean isFullyGrown;
    private boolean isForaging;
    private int currentStageIndex;
    private int whichDayOfStage;
    private FertilizerType fertilizerType;
    private int daysWithoutWater;
    private int daysTillNextHarvest;
    private TreeType type;

    private int tileX;
    private int tileY;

    public TreeSave() {}

    public TreeSave(Tree tree, int tileX, int tileY) {
        this.isInsideGreenhouse = tree.isInsideGreenhouse();
        this.isWateredToday = tree.isWateredToday();
        this.hasFruit = tree.isHasFruit();
        this.isFullyGrown = tree.isFullyGrown();
        this.isForaging = tree.isForaging();
        this.currentStageIndex = tree.getCurrentStageIndex();
        this.whichDayOfStage = tree.getWhichDayOfStage();
        this.fertilizerType = tree.getFertilizerType();
        this.daysWithoutWater = tree.getDaysWithoutWater();
        this.daysTillNextHarvest = tree.getDaysTillNextHarvest();
        this.type = tree.getType();

//        this.tileX = tileX;
//        this.tileY = tileY;
    }
    public boolean isInsideGreenhouse() {
        return isInsideGreenhouse;
    }

    public boolean isWateredToday() {
        return isWateredToday;
    }

    public boolean isHasFruit() {
        return hasFruit;
    }

    public boolean isFullyGrown() {
        return isFullyGrown;
    }

    public boolean isForaging() {
        return isForaging;
    }

    public int getCurrentStageIndex() {
        return currentStageIndex;
    }

    public int getWhichDayOfStage() {
        return whichDayOfStage;
    }

    public FertilizerType getFertilizerType() {
        return fertilizerType;
    }

    public int getDaysWithoutWater() {
        return daysWithoutWater;
    }

    public int getDaysTillNextHarvest() {
        return daysTillNextHarvest;
    }

    public TreeType getType() {
        return type;
    }

    public int getTileX() {
        return tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public void setInsideGreenhouse(boolean insideGreenhouse) {
        isInsideGreenhouse = insideGreenhouse;
    }

    public void setWateredToday(boolean wateredToday) {
        isWateredToday = wateredToday;
    }

    public void setHasFruit(boolean hasFruit) {
        this.hasFruit = hasFruit;
    }

    public void setFullyGrown(boolean fullyGrown) {
        isFullyGrown = fullyGrown;
    }

    public void setForaging(boolean foraging) {
        isForaging = foraging;
    }

    public void setCurrentStageIndex(int currentStageIndex) {
        this.currentStageIndex = currentStageIndex;
    }

    public void setWhichDayOfStage(int whichDayOfStage) {
        this.whichDayOfStage = whichDayOfStage;
    }

    public void setFertilizerType(FertilizerType fertilizerType) {
        this.fertilizerType = fertilizerType;
    }

    public void setDaysWithoutWater(int daysWithoutWater) {
        this.daysWithoutWater = daysWithoutWater;
    }

    public void setDaysTillNextHarvest(int daysTillNextHarvest) {
        this.daysTillNextHarvest = daysTillNextHarvest;
    }

    public void setType(TreeType type) {
        this.type = type;
    }

    public void setTileX(int tileX) {
        this.tileX = tileX;
    }

    public void setTileY(int tileY) {
        this.tileY = tileY;
    }
}
