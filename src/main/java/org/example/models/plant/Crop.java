package org.example.models.plant;

import org.example.models.BackPackable;
import org.example.models.Placeable;

public class Crop implements BackPackable, Placeable {
    private boolean isFullyGrown;
    private boolean isForaging;
    private CropType type;
    private int currentStageIndex = 0;
    private int whichDayOfStage = 1;
    private boolean isGiant = false;
    private boolean isFertilized;
    private int daysWithoutWater = 0;


    public Crop(boolean isForaging, CropType type, boolean isFertilized) {
        if(checkCouldBeGiant())
            return;
        this.isForaging = isForaging;
        this.isFullyGrown = isForaging;
        this.type = type;
        this.isFertilized = isFertilized;
    }

    private boolean checkCouldBeGiant() {
        if () {
            this.isGiant = true;
            return true;
        }
        return false;
    }

    public void goToNextDay() {
        if (this.isFullyGrown)
            return;

        this.daysWithoutWater++;

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

    public boolean isFertilized() {
        return isFertilized;
    }

    @Override
    public boolean isForaging() {
        return false;
    }
}
