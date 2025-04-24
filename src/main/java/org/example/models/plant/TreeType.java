package org.example.models.plant;

import org.example.models.enums.Season;

import java.util.List;

public enum TreeType {
    ApricotTree(TreeSourceType.ApricotSapling, List.of(7, 7, 7, 7), 28, FruitType.Apricot, 1, List.of(Season.Spring)),
    CherryTree(TreeSourceType.CherrySapling, List.of(7, 7, 7, 7), 28, FruitType.Cherry, 1, List.of(Season.Spring)),
    BananaTree(TreeSourceType.BananaSapling, List.of(7, 7, 7, 7), 28, FruitType.Banana, 1, List.of(Season.Summer)),
    MangoTree(TreeSourceType.MangoSapling, List.of(7, 7, 7, 7), 28, FruitType.Mango, 1, List.of(Season.Summer)),
    OrangeTree(TreeSourceType.OrangeSapling, List.of(7, 7, 7, 7), 28, FruitType.Orange, 1, List.of(Season.Summer)),
    PeachTree(TreeSourceType.PeachSapling, List.of(7, 7, 7, 7), 28, FruitType.Peach, 1, List.of(Season.Summer)),
    AppleTree(TreeSourceType.AppleSapling, List.of(7, 7, 7, 7), 28, FruitType.Apple, 1, List.of(Season.Fall)),
    PomegranateTree(TreeSourceType.PomegranateSapling, List.of(7, 7, 7, 7), 28, FruitType.Pomegranate, 1, List.of(Season.Fall)),
    OakTree(TreeSourceType.Acorns, List.of(7, 7, 7, 7), 28, FruitType.OakResin, 7, List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter)),
    MapleTree(TreeSourceType.MapleSeeds, List.of(7, 7, 7, 7), 28, FruitType.MapleSyrup, 9, List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter)),
    PineTree(TreeSourceType.PineCones, List.of(7, 7, 7, 7), 28, FruitType.PineTar, 5, List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter)),
    MahoganyTree(TreeSourceType.MahoganySeeds, List.of(7, 7, 7, 7), 28, FruitType.Sap, 1, List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter)),
    MushroomTree(TreeSourceType.MushroomTreeSeeds, List.of(7, 7, 7, 7), 28, FruitType.CommonMushroom, 1, List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter)),
    MysticTree(TreeSourceType.MysticTreeSeeds, List.of(7, 7, 7, 7), 28, FruitType.MysticSyrup, 7, List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter));

    private final TreeSourceType source;
    private final List<Integer> stages;
    private final int totalHarvestTime;
    private final FruitType fruitType;
    private final int fruitHarvestCycle;
    private final List<Season> seasons;

    TreeType(TreeSourceType source, List<Integer> stages, int totalHarvestTime,
             FruitType fruitType, int fruitHarvestCycle, List<Season> seasons) {
        this.source = source;
        this.stages = stages;
        this.totalHarvestTime = totalHarvestTime;
        this.fruitType = fruitType;
        this.fruitHarvestCycle = fruitHarvestCycle;
        this.seasons = seasons;
    }

    public TreeSourceType getSource() {
        return source;
    }

    public List<Integer> getStages() {
        return stages;
    }

    public int getTotalHarvestTime() {
        return totalHarvestTime;
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
