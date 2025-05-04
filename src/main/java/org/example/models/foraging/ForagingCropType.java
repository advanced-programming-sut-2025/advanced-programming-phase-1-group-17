package org.example.models.foraging;

import org.example.models.BackPackable;
import org.example.models.BackPackableType;
import org.example.models.enums.Season;
import org.example.models.plant.CropType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum ForagingCropType implements BackPackableType {
    COMMON_MUSHROOM(allSeasons(), 40, 38, CropType.CommonMushroom),
    DAFFODIL(spring(), 30, 0, CropType.Daffodil),
    DANDELION(spring(), 40, 25, CropType.Dandelion),
    LEEK(spring(), 60, 40, CropType.Leek),
    MOREL(spring(), 150, 20, CropType.Morel),
    SALMONBERRY(spring(), 5, 25, CropType.Salmonberry),
    SPRING_ONION(spring(), 8, 13, CropType.SpringOnion),
    WILD_HORSERADISH(spring(), 50, 13, CropType.WildHorseradish),
    FIDDLEHEAD_FERN(summer(), 90, 25, CropType.FiddleheadFern),
    GRAPE(summer(), 80, 38, CropType.GrapeForage),
    RED_MUSHROOM(summer(), 75, -50, CropType.RedMushroom),
    SPICE_BERRY(summer(), 80, 25, CropType.SpiceBerry),
    SWEET_PEA(summer(), 50, 0, CropType.SweetPea),
    BLACKBERRY(fall(), 25, 25, CropType.Blackberry),
    CHANTERELLE(fall(), 160, 75, CropType.Chanterelle),
    HAZELNUT(fall(), 40, 38, CropType.Hazelnut),
    PURPLE_MUSHROOM(fall(), 90, 30, CropType.PurpleMushroom),
    WILD_PLUM(fall(), 80, 25, CropType.WildPlum),
    CROCUS(winter(), 60, 0, CropType.Crocus),
    CRYSTAL_FRUIT(winter(), 150, 63, CropType.CrystalFruit),
    HOLLY(winter(), 80, -37, CropType.Holly),
    SNOW_YAM(winter(), 100, 30, CropType.SnowYam),
    WINTER_ROOT(winter(), 70, 25, CropType.WinterRoot);

    private final ArrayList<Season> seasons;
    private final double price;
    private final int energy;
    private final CropType cropType;

    ForagingCropType(List<Season> seasons, double price, int energy, CropType cropType) {
        this.seasons = new ArrayList<>(seasons);
        this.price = price;
        this.energy = energy;
        this.cropType = cropType;
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

    public CropType getCropType() {
        return cropType;
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
