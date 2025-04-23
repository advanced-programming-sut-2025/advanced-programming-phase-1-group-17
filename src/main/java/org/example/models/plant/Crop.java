package org.example.models.plant;

import org.example.models.enums.Season;

public class Crop implements Plantable {
    private final static int measure = 100;
    private CropType type;
    private int currentStageIndex;
    private int levelGrowth;
    private int health;
    private boolean isGiant;
    private boolean isFertilized;
    private int daysWithoutWater;


}
