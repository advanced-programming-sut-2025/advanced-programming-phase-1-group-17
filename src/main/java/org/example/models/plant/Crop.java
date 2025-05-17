package org.example.models.plant;

import org.example.models.App;
import org.example.models.BackPackable;
import org.example.models.Placeable;
import org.example.models.Player;
import org.example.models.map.Tile;
import org.example.models.market.ItemQuality;
import org.example.models.tools.Tool;
import org.example.models.tools.ToolMaterial;
import org.example.models.tools.ToolType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Crop extends Plant implements BackPackable, Placeable {
    private CropType type;
    private boolean isGiant = false;
    private ItemQuality quality;

    ArrayList<Crop> neighborGiantTiles = new ArrayList<>();


    public Crop(boolean isForaging, CropType type, Tile tile, boolean isInsideGreenHouse) {
        super(isForaging, tile, isInsideGreenHouse);
        this.isFullyGrown = isForaging;
        this.type = type;
        this.daysTillNextHarvest = 0;
    }

    public void checkCouldBeGiant() {
        if (isForaging || type == null || !type.isCanBecomeGiant() || isInsideGreenhouse)
            return;

        int x = tile.getX();
        int y = tile.getY();

        // Check all 2x2 squares that include this tile as a member
        int[][] directions = {{0, 0}, {-1, 0}, {0, -1}, {-1, -1}};

        for (int[] dir : directions) {
            int baseX = x + dir[0];
            int baseY = y + dir[1];

            // Get all four tiles of the potential 2x2 square
            Tile t1 = Tile.getTile(baseX, baseY);
            Tile t2 = Tile.getTile(baseX + 1, baseY);
            Tile t3 = Tile.getTile(baseX, baseY + 1);
            Tile t4 = Tile.getTile(baseX + 1, baseY + 1);

            if (t1 == null || t2 == null || t3 == null || t4 == null) continue;

            // Check if all four are crops of the same type and not already giant
            Crop c1 = getCropFromTile(t1);
            Crop c2 = getCropFromTile(t2);
            Crop c3 = getCropFromTile(t3);
            Crop c4 = getCropFromTile(t4);

            if (c1 != null && c2 != null && c3 != null && c4 != null &&
                    !c1.isGiant && !c2.isGiant && !c3.isGiant && !c4.isGiant &&
                    c1.type == this.type && c2.type == this.type &&
                    c3.type == this.type && c4.type == this.type) {
                makeGiant(t1, t2, t3, t4, c1, c2, c3, c4);
            }
        }
    }

    public void makeGiant(Tile t1, Tile t2, Tile t3, Tile t4,
                          Crop c1, Crop c2, Crop c3, Crop c4) {
        // Mark all as part of a giant crop (you may want to remove 3 and replace with one big one)
        c1.setGiant(true);
        c2.setGiant(true);
        c3.setGiant(true);
        c4.setGiant(true);

        if (c1.isFullyGrown || c2.isFullyGrown || c3.isFullyGrown || c4.isFullyGrown) {
            c1.setFullyGrown(true);
            c1.hasFruit = true;
            c2.setFullyGrown(true);
            c2.hasFruit = true;
            c3.setFullyGrown(true);
            c3.hasFruit = true;
            c4.setFullyGrown(true);
            c4.hasFruit = true;
            return;
        }
        Crop maxGrowedCrop = c1;
        FertilizerType fertilizerType1 = c1.fertilizerType;
        boolean isWateredToday = c1.isWateredToday;
        int maxStage = c1.getCurrentStageIndex();
        if (c2.getCurrentStageIndex() > maxStage) {
            maxStage = c2.getCurrentStageIndex();
            maxGrowedCrop = c2;
            if (fertilizerType1 == null)
                fertilizerType1 = c2.fertilizerType;
            if (c2.isWateredToday)
                isWateredToday = true;
        }
        if (c3.getCurrentStageIndex() > maxStage) {
            maxStage = c3.getCurrentStageIndex();
            maxGrowedCrop = c3;
            if (fertilizerType1 == null)
                fertilizerType1 = c3.fertilizerType;
            if (c3.isWateredToday)
                isWateredToday = true;
        }
        if (c4.getCurrentStageIndex() > maxStage) {
            maxStage = c4.getCurrentStageIndex();
            maxGrowedCrop = c4;
            if (fertilizerType1 == null)
                fertilizerType1 = c4.fertilizerType;
            if (c4.isWateredToday)
                isWateredToday = true;
        }
        c1.isWateredToday = isWateredToday;
        c1.fertilizerType = fertilizerType1;
        c1.setCurrentStageIndex(maxStage);
        c1.setWhichDayOfStage(maxGrowedCrop.whichDayOfStage);
        c1.neighborGiantTiles = new ArrayList<>(List.of(c2, c3, c4));

        c2.isWateredToday = isWateredToday;
        c2.fertilizerType = fertilizerType1;
        c2.setCurrentStageIndex(maxStage);
        c2.setWhichDayOfStage(maxGrowedCrop.whichDayOfStage);
        c2.neighborGiantTiles = new ArrayList<>(List.of(c1, c3, c4));

        c3.isWateredToday = isWateredToday;
        c3.fertilizerType = fertilizerType1;
        c3.setCurrentStageIndex(maxStage);
        c3.setWhichDayOfStage(maxGrowedCrop.whichDayOfStage);
        c3.neighborGiantTiles = new ArrayList<>(List.of(c2, c1, c4));

        c4.isWateredToday = isWateredToday;
        c4.fertilizerType = fertilizerType1;
        c4.setCurrentStageIndex(maxStage);
        c4.setWhichDayOfStage(maxGrowedCrop.whichDayOfStage);
        c4.neighborGiantTiles = new ArrayList<>(List.of(c2, c3, c1));
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

    private Crop getCropFromTile(Tile t) {
        if (t.getPlaceable() instanceof Crop crop)
            return crop;
        return null;
    }

    void handleFruitCycle() {
        if (!isFullyGrown)
            return;
        if (type.isOneTime())
            hasFruit = true;

        if (daysTillNextHarvest == 0) {
            daysTillNextHarvest = type.getRegrowthTime();
            hasFruit = true;
        } else
            daysTillNextHarvest--;
    }

    void handleStages() {
        if (isFullyGrown)
            return;
        this.whichDayOfStage++;
        if (getDaysTillFullGrowth() == 0){
            this.isFullyGrown = true;
            return;
        }

        if (this.whichDayOfStage > this.type.getStages().get(this.currentStageIndex)) {
            this.currentStageIndex++;
            this.whichDayOfStage = 1;
        }
    }

    @Override
    public String getName() {
        return type.getName();
    }

    @Override
    public double getPrice() {
        return 0;
    }

    public CropType getType() {
        return type;
    }

    public void setType(CropType type) {
        this.type = type;
    }

    public boolean isGiant() {
        return isGiant;
    }

    public void setGiant(boolean giant) {
        isGiant = giant;
    }

    public ArrayList<Crop> getNeighborGiantTiles() {
        return neighborGiantTiles;
    }

    public void setNeighborGiantTiles(ArrayList<Crop> neighborGiantTiles) {
        this.neighborGiantTiles = neighborGiantTiles;
    }

    public void harvest(ToolMaterial scytheMaterial) {
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();

        if (!hasFruit)
            return;
        if (this.isForaging())
            player.getAbilities().increaseForagingAbility();

        if (type.isOneTime()) {
            if (isGiant) {
                for (int i = 0; i < 10; i++) {
                    player.getBackPack().addItemToInventory(this);
                }
                for (Crop neighborGiantTile : neighborGiantTiles) {
                    neighborGiantTile.getTile().setPlaceable(null);
                }
            } else {
                player.getBackPack().addItemToInventory(this);
            }
            if (this.isInsideGreenhouse)
                tile.setPlaceable(Tile.getTile(tile.getX(), tile.getY()).getOwner().getPlayerMap().getGreenHouse());
            else
                tile.setPlaceable(null);
        }
        else {
            if (isGiant) {
                for (int i = 0; i < 10; i++) {
                    player.getBackPack().addItemToInventory(this);
                }
                for (Crop neighborGiantTile : neighborGiantTiles) {
                    neighborGiantTile.hasFruit = false;
                    neighborGiantTile.daysTillNextHarvest = type.getRegrowthTime();
                }
            } else
                player.getBackPack().addItemToInventory(this);
            daysTillNextHarvest = type.getRegrowthTime();
            this.hasFruit = false;
        }
        setItemQuality(scytheMaterial);
    }

    private void setItemQuality(ToolMaterial scytheMaterial) {
        Random random = new Random();
        int randInt = random.nextInt(100);

        if (scytheMaterial.equals(ToolMaterial.Basic)) {
            if (randInt< 25)
                quality = ItemQuality.Regular;
            else if (randInt < 50)
                quality = ItemQuality.Silver;
            else if (randInt < 75)
                quality = ItemQuality.Gold;
            else
                quality = ItemQuality.Iridium;
        } else if (scytheMaterial.equals(ToolMaterial.Copper)) {
            if (randInt< 20)
                quality = ItemQuality.Regular;
            else if (randInt < 40)
                quality = ItemQuality.Silver;
            else if (randInt < 60)
                quality = ItemQuality.Gold;
            else
                quality = ItemQuality.Iridium;
        } else if (scytheMaterial.equals(ToolMaterial.Iron)) {
            if (randInt< 15)
                quality = ItemQuality.Regular;
            else if (randInt < 30)
                quality = ItemQuality.Silver;
            else if (randInt < 45)
                quality = ItemQuality.Gold;
            else
                quality = ItemQuality.Iridium;
        } else if (scytheMaterial.equals(ToolMaterial.Gold)) {
            if (randInt< 10)
                quality = ItemQuality.Regular;
            else if (randInt < 20)
                quality = ItemQuality.Silver;
            else if (randInt < 30)
                quality = ItemQuality.Gold;
            else
                quality = ItemQuality.Iridium;
        } else if (scytheMaterial.equals(ToolMaterial.Iridium)) {
            if (randInt< 5)
                quality = ItemQuality.Regular;
            else if (randInt < 10)
                quality = ItemQuality.Silver;
            else if (randInt < 15)
                quality = ItemQuality.Gold;
            else
                quality = ItemQuality.Iridium;
        }
    }

    @Override
    public void wateringPlant() {
        this.daysWithoutWater = 0;
        isWateredToday = true;
        if (isGiant) {
            for (Crop neighborGiantTile : neighborGiantTiles) {
                neighborGiantTile.isWateredToday = true;
                neighborGiantTile.daysWithoutWater = 0;
            }
        }
    }

    public ItemQuality getQuality() {
        return quality;
    }

    public void setQuality(ItemQuality quality) {
        this.quality = quality;
    }
}
