package org.example.models.plant;

import org.example.models.Placeable;
import org.example.models.map.Tile;

public class Tree extends Plant implements Placeable {
    private TreeType type;
    private boolean isHitByLightning = false;
    private boolean isFertilized = false;
    private boolean isForaging = false;

    public Tree(boolean isForaging, TreeType treeType, boolean isFertilized, Tile tile){
        super(isForaging, isFertilized, tile);
        this.type = treeType;
    }

    public int getDaysTillFullGrowth() {
        int daysPassed = 0;
        for (int i = 0; i < currentStageIndex; i++){
            daysPassed += type.getStages().get(i);
        }
        daysPassed += whichDayOfStage;
        return type.getTotalGrowthTime() - daysPassed;
    }

    public boolean isFertilized() {
        return isFertilized;
    }

    public void setFertilized(boolean fertilized) {
        isFertilized = fertilized;
    }

    public void goToNextDay(){
        if (this.isFullyGrown)
            return;

        this.daysWithoutWater++;
        if (this.daysWithoutWater >= 2)
            tile.setPlaceable(null);

        //stage Handling
        this.whichDayOfStage++;
        if (this.whichDayOfStage > this.type.getStages().get(this.currentStageIndex)) {
            if (this.currentStageIndex > this.type.getStages().size()) {
                this.isFullyGrown = true;
                return;
            }
            this.currentStageIndex++;
            this.whichDayOfStage = 1;
        }
        this.whichDayOfStage++;
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
}
