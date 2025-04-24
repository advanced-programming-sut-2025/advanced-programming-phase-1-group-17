package org.example.foraging;

import org.example.models.BackPackable;
import org.example.models.enums.Season;
import org.example.models.plant.CropType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum ForagingCrop implements BackPackable {
    COMMON_MUSHROOM(allSeasons(), 40, 38),
    DAFFODIL(spring(), 30, 0),
    DANDELION(spring(), 40, 25),
    LEEK(spring(), 60, 40),
    MOREL(spring(), 150, 20),
    SALMONBERRY(spring(), 5, 25),
    SPRING_ONION(spring(), 8, 13),
    WILD_HORSERADISH(spring(), 50, 13),
    FIDDLEHEAD_FERN(summer(), 90, 25),
    GRAPE(summer(), 80, 38),
    RED_MUSHROOM(summer(), 75, -50),
    SPICE_BERRY(summer(), 80, 25),
    SWEET_PEA(summer(), 50, 0),
    BLACKBERRY(fall(), 25, 25),
    CHANTERELLE(fall(), 160, 75),
    HAZELNUT(fall(), 40, 38),
    PURPLE_MUSHROOM(fall(), 90, 30),
    WILD_PLUM(fall(), 80, 25),
    CROCUS(winter(), 60, 0),
    CRYSTAL_FRUIT(winter(), 150, 63),
    HOLLY(winter(), 80, -37),
    SNOW_YAM(winter(), 100, 30),
    WINTER_ROOT(winter(), 70, 25);

    private final ArrayList<Season> seasons;
    private final double price;
    private final int energy;

    ForagingCrop(List<Season> seasons, double price, int energy) {
        this.seasons = new ArrayList<>(seasons);
        this.price = price;
        this.energy = energy;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public double getPrice() {
        return price;
    }

    public int getEnergy() {
        return energy;
    }

    private static List<Season> spring() { return Collections.singletonList(Season.Spring); }
    private static List<Season> summer() { return Collections.singletonList(Season.Summer); }
    private static List<Season> fall() { return Collections.singletonList(Season.Fall); }
    private static List<Season> winter() { return Collections.singletonList(Season.Winter); }
    private static List<Season> allSeasons() {
        return Arrays.asList(Season.Spring, Season.Summer, Season.Fall, Season.Winter);
    }


    @Override
    public String getName() {
        return name();
    }
}


