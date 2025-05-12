package org.example.models.plant;

import org.example.models.Placeable;
import org.example.models.map.Tile;

public class Tree extends Plant implements Placeable {
    private TreeType type;

    public Tree(boolean isForaging, TreeType treeType, Tile tile, boolean isInsideGreenHouse) {
        super(isForaging, tile, isInsideGreenHouse);
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

        int fertilizerEffect = 0;
        if (this.fertilizerType != null && this.fertilizerType.equals(FertilizerType.SpeedGro))
            fertilizerEffect = 1;

        return type.getTotalGrowthTime() - daysPassed + 1 - fertilizerEffect;
    }

    void handleStages() {
        this.whichDayOfStage++;
        if (getDaysTillFullGrowth() == 0) {
            this.isFullyGrown = true;
            return;
        }

        if (this.whichDayOfStage > this.type.getStages().get(this.currentStageIndex)) {
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

    @Override
    public void harvest() {
        daysTillNextHarvest = type.getFruitHarvestCycle();
    }
}
