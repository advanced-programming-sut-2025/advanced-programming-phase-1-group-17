package org.example.models.plant;

import org.example.models.BackPackable;
import org.example.models.Placeable;
import org.example.models.map.Tile;

import java.util.ArrayList;

public class Crop extends Plant implements BackPackable, Placeable {
    private CropType type;
    private boolean isGiant = false;
    ArrayList<Tile> neighborGiantTiles = new ArrayList<>();


    public Crop(boolean isForaging, CropType type, boolean isFertilized, Tile tile) {
        super(isForaging, isFertilized, tile);
        if(checkCouldBeGiant())
            return;
        this.isForaging = isForaging;
        this.isFullyGrown = isForaging;
        this.type = type;
        this.isFertilized = isFertilized;
        this.tile = tile;
    }

    private boolean checkCouldBeGiant() {
        if (isForaging || type == null || !type.isCanBecomeGiant())
            return false;

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

                // Mark all as part of a giant crop (you may want to remove 3 and replace with one big one)
                c1.setGiant(true);
                c2.setGiant(true);
                c3.setGiant(true);
                c4.setGiant(true);
                c1.setFullyGrown(true); // Optional, depending on rules
                c2.setFullyGrown(true);
                c3.setFullyGrown(true);
                c4.setFullyGrown(true);

                return true;
            }
        }

        return false;
    }

    public int getDaysTillFullGrowth() {
        if (isFullyGrown || !this.isWateredToday)
            return 0;
        int daysPassed = 0;
        for (int i = 0; i < currentStageIndex; i++){
            daysPassed += type.getStages().get(i);
        }
        daysPassed += whichDayOfStage;
        return type.getTotalGrowthTime() - daysPassed;
    }

    private Crop getCropFromTile(Tile t) {
        if (t.getPlaceable() instanceof Crop crop)
            return crop;
        return null;
    }

    public void goToNextDay() {
        if (this.isFullyGrown)
            return;

        this.daysWithoutWater++;
        if (this.daysWithoutWater >= 2) {
            tile.setPlaceable(null);
            return;
        }

        if (!this.isWateredToday)
            return;

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
}
