package org.example.models.plant;

import org.example.models.BackPackableType;
import org.example.models.enums.Season;

import java.util.List;

public enum CropType implements BackPackableType {
    BlueJazz(SeedType.JazzSeeds, List.of(1, 2, 2, 2), 7, true, -1, 50, true, 45, List.of(Season.Spring), false),
    Carrot(SeedType.CarrotSeeds, List.of(1, 1, 1), 3, true, -1, 35, true, 75, List.of(Season.Spring), false),
    Cauliflower(SeedType.CauliflowerSeeds, List.of(1, 2, 4, 4, 1), 12, true, -1, 175, true, 75, List.of(Season.Spring), true),
    CoffeeBean(SeedType.CoffeeBean, List.of(1, 2, 2, 3, 2), 10, false, 2, 15, false, -1, List.of(Season.Spring, Season.Summer), false),
    Garlic(SeedType.GarlicSeeds, List.of(1, 1, 1, 1), 4, true, -1, 60, true, 20, List.of(Season.Spring), false),
    GreenBean(SeedType.BeanStarter, List.of(1, 1, 1, 3, 4), 10, false, 3, 40, true, 25, List.of(Season.Spring), false),
    Kale(SeedType.KaleSeeds, List.of(1, 2, 2, 1), 6, true, -1, 110, true, 50, List.of(Season.Spring), false),
    Parsnip(SeedType.ParsnipSeeds, List.of(1, 1, 1, 1), 4, true, -1, 35, true, 25, List.of(Season.Spring), false),
    Potato(SeedType.PotatoSeeds, List.of(1, 1, 1, 2, 1), 6, true, -1, 80, true, 25, List.of(Season.Spring), false),
    Rhubarb(SeedType.RhubarbSeeds, List.of(2, 2, 2, 3, 4), 13, true, -1, 220, false, -1, List.of(Season.Spring), false),
    Strawberry(SeedType.StrawberrySeeds, List.of(1, 1, 2, 2, 2), 8, false, 4, 120, true, 50, List.of(Season.Spring), false),
    Tulip(SeedType.TulipBulb, List.of(1, 1, 2, 2), 6, true, -1, 30, true, 45, List.of(Season.Spring), false),
    UnmilledRice(SeedType.RiceShoot, List.of(1, 2, 2, 3), 8, true, -1, 30, true, 3, List.of(Season.Spring), false),
    Blueberry(SeedType.BlueberrySeeds, List.of(1, 3, 3, 4, 2), 13, false, 4, 50, true, 25, List.of(Season.Summer), false),
    Corn(SeedType.CornSeeds, List.of(2, 3, 3, 3, 3), 14, false, 4, 50, true, 25, List.of(Season.Summer), false),
    Hops(SeedType.HopsStarter, List.of(1, 1, 2, 3, 4), 11, false, 1, 25, true, 45, List.of(Season.Summer), false),
    HotPepper(SeedType.PepperSeeds, List.of(1, 1, 1, 1, 1), 5, false, 3, 40, true, 13, List.of(Season.Summer), false),
    Melon(SeedType.MelonSeeds, List.of(1, 2, 3, 3, 3), 12, true, -1, 250, true, 113, List.of(Season.Summer), true),
    Poppy(SeedType.PoppySeeds, List.of(1, 2, 2, 2), 7, true, -1, 140, true, 45, List.of(Season.Summer), false),
    Radish(SeedType.RadishSeeds, List.of(2, 1, 2, 1), 6, true, -1, 90, true, 45, List.of(Season.Summer), false),
    RedCabbage(SeedType.RedCabbageSeeds, List.of(2, 1, 2, 2, 2), 9, true, -1, 260, true, 75, List.of(Season.Summer), false),
    Starfruit(SeedType.StarfruitSeeds, List.of(2, 3, 2, 3, 3), 13, true, -1, 750, true, 125, List.of(Season.Summer), false),
    SummerSpangle(SeedType.SpangleSeeds, List.of(1, 2, 3, 1), 8, true, -1, 90, true, 45, List.of(Season.Summer), false),
    SummerSquash(SeedType.SummerSquashSeeds, List.of(1, 1, 1, 2, 1), 6, false, 3, 45, true, 63, List.of(Season.Summer), false),
    Sunflower(SeedType.SunflowerSeeds, List.of(1, 2, 3, 2), 8, true, -1, 80, true, 45, List.of(Season.Summer), false),
    Tomato(SeedType.TomatoSeeds, List.of(2, 2, 2, 2, 3), 11, false, 4, 60, true, 20, List.of(Season.Summer), false),
    Wheat(SeedType.WheatSeeds, List.of(1, 1, 1, 1), 4, true, -1, 25, false, -1, List.of(Season.Summer), false),
    Amaranth(SeedType.AmaranthSeeds, List.of(1, 2, 2, 2), 7, true, -1, 150, true, 50, List.of(Season.Fall), false),
    Artichoke(SeedType.ArtichokeSeeds, List.of(2, 2, 1, 2, 1), 8, true, -1, 160, true, 30, List.of(Season.Fall), false),
    Beet(SeedType.BeetSeeds, List.of(1, 1, 2, 2), 6, true, -1, 100, true, 30, List.of(Season.Fall), false),
    BokChoy(SeedType.BokChoySeeds, List.of(1, 1, 1, 1), 4, true, -1, 80, true, 25, List.of(Season.Fall), false),
    Broccoli(SeedType.BroccoliSeeds, List.of(2, 2, 2, 2), 8, false, 4, 70, true, 63, List.of(Season.Fall), false),
    Cranberries(SeedType.CranberrySeeds, List.of(1, 2, 1, 1, 2), 7, false, 5, 75, true, 38, List.of(Season.Fall), false),
    Eggplant(SeedType.EggplantSeeds, List.of(1, 1, 1, 1), 5, false, 5, 60, true, 20, List.of(Season.Fall), false),
    FairyRose(SeedType.FairySeeds, List.of(1, 4, 4, 3), 12, true, -1, 290, true, 45, List.of(Season.Fall), false),
    Grape(SeedType.GrapeStarter, List.of(1, 1, 2, 3, 3), 10, false, 3, 80, true, 38, List.of(Season.Fall), false),
    Pumpkin(SeedType.PumpkinSeeds, List.of(1, 2, 3, 4, 3), 13, true, -1, 320, false, -1, List.of(Season.Fall), true),
    Yam(SeedType.YamSeeds, List.of(1, 3, 3, 3), 10, true, -1, 160, true, 45, List.of(Season.Fall), false),
    SweetGemBerry(SeedType.RareSeed, List.of(2, 4, 6, 6, 6), 24, true, -1, 3000, false, -1, List.of(Season.Fall), false),
    Powdermelon(SeedType.PowdermelonSeeds, List.of(1, 2, 1, 2, 1), 7, true, -1, 60, true, 63, List.of(Season.Winter), true),
    AncientFruit(SeedType.AncientSeeds, List.of(2, 7, 7, 7, 5), 28, false, 7, 550, false, -1, List.of(Season.Spring, Season.Summer, Season.Fall), false),

    // Foraging crops
    CommonMushroom(null, List.of(0, 0, 0), 0, true, -1, 40, true, 38, List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter), false),
    Daffodil(null, List.of(0, 0, 0), 0, true, -1, 30, true, 0, List.of(Season.Spring), false),
    Dandelion(null, List.of(0, 0, 0), 0, true, -1, 40, true, 25, List.of(Season.Spring), false),
    Leek(null, List.of(0, 0, 0), 0, true, -1, 60, true, 40, List.of(Season.Spring), false),
    Morel(null, List.of(0, 0, 0), 0, true, -1, 150, true, 20, List.of(Season.Spring), false),
    Salmonberry(null, List.of(0, 0, 0), 0, true, -1, 5, true, 25, List.of(Season.Spring), false),
    SpringOnion(null, List.of(0, 0, 0), 0, true, -1, 8, true, 13, List.of(Season.Spring), false),
    WildHorseradish(null, List.of(0, 0, 0), 0, true, -1, 50, true, 13, List.of(Season.Spring), false),
    FiddleheadFern(null, List.of(0, 0, 0), 0, true, -1, 90, true, 25, List.of(Season.Summer), false),
    GrapeForage(null, List.of(0, 0, 0), 0, true, -1, 80, true, 38, List.of(Season.Summer), false),
    RedMushroom(null, List.of(0, 0, 0), 0, true, -1, 75, true, -50, List.of(Season.Summer), false),
    SpiceBerry(null, List.of(0, 0, 0), 0, true, -1, 80, true, 25, List.of(Season.Summer), false),
    SweetPea(null, List.of(0, 0, 0), 0, true, -1, 50, true, 0, List.of(Season.Summer), false),
    Blackberry(null, List.of(0, 0, 0), 0, true, -1, 25, true, 25, List.of(Season.Fall), false),
    Chanterelle(null, List.of(0, 0, 0), 0, true, -1, 160, true, 75, List.of(Season.Fall), false),
    Hazelnut(null, List.of(0, 0, 0), 0, true, -1, 40, true, 38, List.of(Season.Fall), false),
    PurpleMushroom(null, List.of(0, 0, 0), 0, true, -1, 90, true, 30, List.of(Season.Fall), false),
    WildPlum(null, List.of(0, 0, 0), 0, true, -1, 80, true, 25, List.of(Season.Fall), false),
    Crocus(null, List.of(0, 0, 0), 0, true, -1, 60, true, 0, List.of(Season.Winter), false),
    CrystalFruit(null, List.of(0, 0, 0), 0, true, -1, 150, true, 63, List.of(Season.Winter), false),
    Holly(null, List.of(0, 0, 0), 0, true, -1, 80, true, -37, List.of(Season.Winter), false),
    SnowYam(null, List.of(0, 0, 0), 0, true, -1, 100, true, 30, List.of(Season.Winter), false),
    WinterRoot(null, List.of(0, 0, 0), 0, true, -1, 70, true, 25, List.of(Season.Winter), false);


    private final SeedType Source;
    private final List<Integer> stages;
    private final int totalGrowthTime;
    private final boolean oneTime;
    private final int regrowthTime;
    private final double baseSellPrice;
    private final boolean isEdible;
    private final int energy;
    private final List<Season> seasons;
    private final boolean canBecomeGiant;

    CropType(SeedType Source, List<Integer> stages, int totalHarvestTime, boolean oneTime, int regrowthTime,
             double baseSellPrice, boolean isEdible, int energy, List<Season> season, boolean canBecomeGiant) {
        this.Source = Source;
        this.stages = stages;
        this.totalGrowthTime = totalHarvestTime;
        this.oneTime = oneTime;
        this.regrowthTime = regrowthTime;
        this.baseSellPrice = baseSellPrice;
        this.isEdible = isEdible;
        this.energy = energy;
        this.seasons = season;
        this.canBecomeGiant = canBecomeGiant;
    }


    public SeedType getSource() {
        return Source;
    }

    public List<Integer> getStages() {
        return stages;
    }

    public int getTotalGrowthTime() {
        return totalGrowthTime;
    }

    public boolean isOneTime() {
        return oneTime;
    }

    public int getRegrowthTime() {
        return regrowthTime;
    }

    public double getBaseSellPrice() {
        return baseSellPrice;
    }

    public boolean isEdible() {
        return isEdible;
    }

    public int getEnergy() {
        return energy;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public boolean isCanBecomeGiant() {
        return canBecomeGiant;
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public double getPrice() {
        return 0;
    }
}
