package io.github.StardewValley.shared.models.plant;

import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.map.Placeable;
import io.github.StardewValley.shared.models.map.Tile;

public class Tree extends Plant implements Placeable {
    private TreeType type;
    private final String[] textures;
    private final String hasFruitTexture;

    public Tree(boolean isForaging, TreeType treeType, Tile tile, boolean isInsideGreenHouse) {
        super(isForaging, tile, isInsideGreenHouse);
        this.type = treeType;
        this.daysTillNextHarvest = type.getFruitHarvestCycle();

        String[] paths = type.getStageTexturePaths();
        this.textures = new String[paths.length];
        for (int i = 0; i < paths.length; i++) {
            this.textures[i] =(paths[i]);
        }
        this.hasFruitTexture = type.getHasFruitTexturePath();
        if (isForaging)
            this.currentStageIndex = type.getStages().size() - 1;
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
        if (getDaysTillFullGrowth() <= 0) {
            this.isFullyGrown = true;
            return;
        }

        if (this.whichDayOfStage > this.type.getStages().get(this.currentStageIndex)) {
            this.currentStageIndex++;
            this.whichDayOfStage = 1;
        }
    }

    void handleFruitCycle() {
        if (!isFullyGrown || isForaging)
            return;

        if (daysTillNextHarvest == 0) {
            daysTillNextHarvest = type.getFruitHarvestCycle();
            hasFruit = true;
        } else {
            if (!hasFruit)
                daysTillNextHarvest--;
        }
    }

    public TreeType getType() {
        return type;
    }

    public void setType(TreeType type) {
        this.type = type;
    }

    @Override
    public void harvest(Player player) {
        if(!hasFruit || !isFullyGrown)
            return;
        daysTillNextHarvest = type.getFruitHarvestCycle();
        this.hasFruit = false;
    }

    public String getTexture() {
        if (isFullyGrown) {
            if (hasFruit) {
                return TreeAssetManager.getTreeAssetManager().getHasFruitTexture(type);
            }
            if (type.equals(TreeType.MushroomTree))
                return TreeAssetManager.getTreeAssetManager().getStageTexture(type, 3);
            return null; //for fully Grown
        }
        return TreeAssetManager.getTreeAssetManager().getStageTexture(type, currentStageIndex);
    }
}
