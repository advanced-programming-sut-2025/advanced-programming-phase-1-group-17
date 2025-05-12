package org.example.models.plant;

import org.example.models.enums.Season;

import java.util.List;

public enum TreeType {
    ApricotTree(SaplingType.ApricotSapling, List.of(7, 7, 7, 7), 28, FruitType.Apricot, 1, List.of(Season.Spring)),
    CherryTree(SaplingType.CherrySapling, List.of(7, 7, 7, 7), 28, FruitType.Cherry, 1, List.of(Season.Spring)),
    BananaTree(SaplingType.BananaSapling, List.of(7, 7, 7, 7), 28, FruitType.Banana, 1, List.of(Season.Summer)),
    MangoTree(SaplingType.MangoSapling, List.of(7, 7, 7, 7), 28, FruitType.Mango, 1, List.of(Season.Summer)),
    OrangeTree(SaplingType.OrangeSapling, List.of(7, 7, 7, 7), 28, FruitType.Orange, 1, List.of(Season.Summer)),
    PeachTree(SaplingType.PeachSapling, List.of(7, 7, 7, 7), 28, FruitType.Peach, 1, List.of(Season.Summer)),
    AppleTree(SaplingType.AppleSapling, List.of(7, 7, 7, 7), 28, FruitType.Apple, 1, List.of(Season.Fall)),
    PomegranateTree(SaplingType.PomegranateSapling, List.of(7, 7, 7, 7), 28, FruitType.Pomegranate, 1, List.of(Season.Fall)),
    OakTree(SaplingType.Acorns, List.of(7, 7, 7, 7), 28, FruitType.OakResin, 7, List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter)),
    MapleTree(SaplingType.MapleSeeds, List.of(7, 7, 7, 7), 28, FruitType.MapleSyrup, 9, List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter)),
    PineTree(SaplingType.PineCones, List.of(7, 7, 7, 7), 28, FruitType.PineTar, 5, List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter)),
    MahoganyTree(SaplingType.MahoganySeeds, List.of(7, 7, 7, 7), 28, FruitType.Sap, 1, List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter)),
    MushroomTree(SaplingType.MushroomTreeSeeds, List.of(7, 7, 7, 7), 28, FruitType.CommonMushroom, 1, List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter)),
    MysticTree(SaplingType.MysticTreeSeeds, List.of(7, 7, 7, 7), 28, FruitType.MysticSyrup, 7, List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter));

    private final SaplingType sapling;
    private final List<Integer> stages;
    private final int totalGrowthTime;
    private final FruitType fruitType;
    private final int fruitHarvestCycle;
    private final List<Season> seasons;

    TreeType(SaplingType source, List<Integer> stages, int totalHarvestTime,
             FruitType fruitType, int fruitHarvestCycle, List<Season> seasons) {
        this.sapling = source;
        this.stages = stages;
        this.totalGrowthTime = totalHarvestTime;
        this.fruitType = fruitType;
        this.fruitHarvestCycle = fruitHarvestCycle;
        this.seasons = seasons;
    }

    public static TreeType getTreeTypeBySaplingType(SaplingType saplingType){
        for (TreeType treeType : TreeType.values()) {
            if (treeType.getSapling().equals(saplingType))
                return treeType;
        }
        return null;
    }

    public SaplingType getSapling() {
        return sapling;
    }

    public List<Integer> getStages() {
        return stages;
    }

    public int getTotalGrowthTime() {
        return totalGrowthTime;
    }

    public FruitType getFruitType() {
        return fruitType;
    }

    public int getFruitHarvestCycle() {
        return fruitHarvestCycle;
    }

    public List<Season> getSeasons() {
        return seasons;
    }
}
