package io.github.StardewValley.shared.models.saveClasses;

import io.github.StardewValley.shared.models.market.ItemQuality;
import io.github.StardewValley.shared.models.plant.Crop;
import io.github.StardewValley.shared.models.plant.CropType;
import io.github.StardewValley.shared.models.plant.FertilizerType;

public class CropSave {
    private CropType type;
    private boolean isGiant;
    private boolean isLeftBottomTileOfGiant;
    private ItemQuality quality;
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

    private int TileX;
    private int TileY;
    //private ArrayList<Pair<Integer, Integer>> neighbourGiantTilesCoordinates;

    public CropSave() {}

    public CropSave(Crop crop, int tileX, int tileY) {
        this.type = crop.getType();
        this.isGiant = crop.isGiant();
        this.isLeftBottomTileOfGiant = crop.isLeftBottomTileOfGiant();
        this.quality = crop.getQuality();
        this.isInsideGreenhouse = crop.isInsideGreenhouse();
        this.isWateredToday = crop.isWateredToday();
        this.hasFruit = crop.hasFruit();
        this.isFullyGrown = crop.isFullyGrown();
        this.isForaging = crop.isForaging();
        this.currentStageIndex = crop.getCurrentStageIndex();
        this.whichDayOfStage = crop.getWhichDayOfStage();
        this.fertilizerType = crop.getFertilizerType();
        this.daysWithoutWater = crop.getDaysWithoutWater();
        this.daysTillNextHarvest = crop.getDaysTillNextHarvest();

        this.TileX = tileX;
        this.TileY = tileY;

//        if (crop.getNeighborGiantTiles().isEmpty()) {
//            this.neighbourGiantTilesCoordinates = new ArrayList<>();
//        } else {
//            this.neighbourGiantTilesCoordinates = new ArrayList<>();
//            crop.getNeighborGiantTiles().forEach(giantTile -> {
//                this.neighbourGiantTilesCoordinates.add(
//                    new Pair<>(giantTile.getTile().getX(), giantTile.getTile().getY())
//                );
//            });
//        }
    }

    public CropType getType() {
        return type;
    }

    public boolean isGiant() {
        return isGiant;
    }

    public boolean isLeftBottomTileOfGiant() {
        return isLeftBottomTileOfGiant;
    }

    public ItemQuality getQuality() {
        return quality;
    }

    public boolean isInsideGreenhouse() {
        return isInsideGreenhouse;
    }

    public boolean isWateredToday() {
        return isWateredToday;
    }

    public boolean hasFruit() {
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

    public int getTileX() {
        return TileX;
    }

    public int getTileY() {
        return TileY;
    }

//    public ArrayList<Pair<Integer, Integer>> getNeighbourGiantTilesCoordinates() {
//        return neighbourGiantTilesCoordinates;
//    }


    public void setType(CropType type) {
        this.type = type;
    }

    public void setGiant(boolean giant) {
        isGiant = giant;
    }

    public void setLeftBottomTileOfGiant(boolean leftBottomTileOfGiant) {
        isLeftBottomTileOfGiant = leftBottomTileOfGiant;
    }

    public void setQuality(ItemQuality quality) {
        this.quality = quality;
    }

    public void setInsideGreenhouse(boolean insideGreenhouse) {
        isInsideGreenhouse = insideGreenhouse;
    }

    public void setWateredToday(boolean wateredToday) {
        isWateredToday = wateredToday;
    }

    public boolean isHasFruit() {
        return hasFruit;
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

    public void setTileX(int tileX) {
        TileX = tileX;
    }

    public void setTileY(int tileY) {
        TileY = tileY;
    }
}

