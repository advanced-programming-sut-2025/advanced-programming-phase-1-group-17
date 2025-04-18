package org.example.models.plant;

import org.example.models.Placeable;
import org.example.models.enums.Season;
import org.example.models.enums.VegetableType;

public class Vegetable implements Placeable {
    private String name;
    private Seed resource;
    private final static int measure = 100;
    private VegetableType type;
    private double stage;
    private double daysToFullGrowth;
    private boolean multipleHarvest;
    private int harvestInterval;
    private double basePrice;
    private boolean eatable;
    private double energy;
    private Season season;
    private boolean isAbleToGetGiant;
    private int levelGrowth;
    private int health;
    private boolean isGiant;
    private boolean isFertilized;
    private int daysWithoutWater;
}
