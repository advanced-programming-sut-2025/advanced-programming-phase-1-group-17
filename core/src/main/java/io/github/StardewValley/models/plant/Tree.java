package io.github.StardewValley.models.plant;

import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.models.Placeable;
import io.github.StardewValley.models.map.Tile;

public class Tree extends Plant implements Placeable {
    private TreeType type;
    private final Texture[] textures;
    private final Texture hasFruitTexture;

    public Tree(boolean isForaging, TreeType treeType, Tile tile, boolean isInsideGreenHouse) {
        super(isForaging, tile, isInsideGreenHouse);
        this.type = treeType;
        this.daysTillNextHarvest = 0;

        String[] paths = type.getStageTexturePaths();
        this.textures = new Texture[paths.length];
        for (int i = 0; i < paths.length; i++) {
            this.textures[i] = new Texture(paths[i]);
        }
        this.hasFruitTexture = new Texture(type.getHasFruitTexturePath());
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
        if (!isFullyGrown || isForaging)
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
        if(!hasFruit)
            return;
        daysTillNextHarvest = type.getFruitHarvestCycle();
        this.hasFruit = false;
    }

    public Texture getTexture() {
        if (hasFruit) {
            return TreeAssetManager.getTreeAssetManager().getHasFruitTexture(type);
        }
        return TreeAssetManager.getTreeAssetManager().getStageTexture(type, currentStageIndex);
    }
}
