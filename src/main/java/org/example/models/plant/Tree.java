package org.example.models.plant;

import org.example.models.Placeable;
import org.example.models.map.Tile;

public class Tree extends Plant implements Placeable {
    private TreeType type;
    private boolean isHitByLightning = false;

    public Tree(boolean isForaging, TreeType treeType, boolean isFertilized, Tile tile, boolean isInsideGreenHouse) {
        super(isForaging, isFertilized, tile, isInsideGreenHouse);
        this.type = treeType;
        this.daysTillNextHarvest = type.getFruitHarvestCycle();
    }

    public int getDaysTillFullGrowth() {
        if (isFullyGrown)
            return 0;
        int daysPassed = 0;
        for (int i = 0; i < currentStageIndex; i++) {
            daysPassed += type.getStages().get(i);
        }
        daysPassed += whichDayOfStage;
        return type.getTotalGrowthTime() - daysPassed;
    }

    void handleStages() {
        this.whichDayOfStage++;
        if (this.whichDayOfStage > this.type.getStages().get(this.currentStageIndex)) {
            if (this.currentStageIndex == this.type.getStages().size() - 1) {
                this.isFullyGrown = true;
                return;
            }
            this.currentStageIndex++;
            this.whichDayOfStage = 1;
        }
    }

    void handleFruitCycle() {
        if (!isFullyGrown)
            return;

        if (daysTillNextHarvest == 0) {
            daysTillNextHarvest = type.getFruitHarvestCycle();
            hasFruit = true;
        } else
            daysTillNextHarvest--;
    }

    public TreeType getType() {
        return type;
    }

    public void setType(TreeType type) {
        this.type = type;
    }

    public boolean isHitByLightning() {
        return isHitByLightning;
    }

    public void setHitByLightning(boolean hitByLightning) {
        isHitByLightning = hitByLightning;
    }

    @Override
    public void harvest() {
        daysTillNextHarvest = type.getFruitHarvestCycle();
    }
}
