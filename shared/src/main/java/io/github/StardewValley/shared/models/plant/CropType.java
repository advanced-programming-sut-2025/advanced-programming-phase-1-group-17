package io.github.StardewValley.shared.models.plant;

import io.github.StardewValley.shared.models.game.Game;
import io.github.StardewValley.shared.models.backpack.BackPackableType;
import io.github.StardewValley.shared.models.enums.Season;

import java.util.List;
import java.util.Random;

public enum CropType implements BackPackableType {
    BlueJazz(SeedType.BlueJazzSeeds, List.of(1, 2, 2, 2), 7, true, -1, 50, true, 45, List.of(Season.Spring), false,
        new String[] {"Crops/Blue_Jazz_Stage_1.png", "Crops/Blue_Jazz_Stage_2.png", "Crops/Blue_Jazz_Stage_3.png",
            "Crops/Blue_Jazz_Stage_4.png", "Crops/Blue_Jazz_Stage_5.png"}, "Crops/Blue_Jazz.png"),
    Carrot(SeedType.CarrotSeeds, List.of(1, 1, 1), 3, true, -1, 35, true, 75, List.of(Season.Spring), false,
        new String[] {"Crops/Carrot_Stage_1.png", "Crops/Carrot_Stage_2.png", "Crops/Carrot_Stage_3.png", "Crops/Carrot_Stage_4.png"}, "Crops/Carrot.png"),
    Cauliflower(SeedType.CauliflowerSeeds, List.of(1, 2, 4, 4, 1), 12, true, -1, 175, true, 75, List.of(Season.Spring), true,
        new String[] {"Crops/Cauliflower_Stage_1.png", "Crops/Cauliflower_Stage_2.png", "Crops/Cauliflower_Stage_3.png", "Crops/Cauliflower_Stage_4.png", "Crops/Cauliflower_Stage_5.png"}, "Crops/Cauliflower.png"),
    CoffeeBean(SeedType.CoffeeBean, List.of(1, 2, 2, 3, 2), 10, false, 2, 15, false, -1, List.of(Season.Spring, Season.Summer), false,
        new String[] {"Crops/Coffee_Stage_1.png", "Crops/Coffee_Stage_2.png", "Crops/Coffee_Stage_3.png", "Crops/Coffee_Stage_4.png", "Crops/Coffee_Stage_5.png"}, "Crops/Coffee_Stage_7.png"),
    Garlic(SeedType.GarlicSeeds, List.of(1, 1, 1, 1), 4, true, -1, 60, true, 20, List.of(Season.Spring), false,
        new String[] {"Crops/Garlic_Stage_1.png", "Crops/Garlic_Stage_2.png", "Crops/Garlic_Stage_3.png", "Crops/Garlic_Stage_4.png", "Crops/Garlic_Stage_5.png"}, "Crops/Garlic.png"),
    Kale(SeedType.KaleSeeds, List.of(1, 2, 2, 1), 6, true, -1, 110, true, 50, List.of(Season.Spring), false,
        new String[] {"Crops/Kale_Stage_1.png", "Crops/Kale_Stage_2.png", "Crops/Kale_Stage_3.png", "Crops/Kale_Stage_4.png", "Crops/Kale_Stage_5.png"}, "Crops/Kale.png"),
    Parsnip(SeedType.ParsnipSeeds, List.of(1, 1, 1, 1), 4, true, -1, 35, true, 25, List.of(Season.Spring), false,
        new String[] {"Crops/Parsnip_Stage_1.png", "Crops/Parsnip_Stage_2.png", "Crops/Parsnip_Stage_3.png", "Crops/Parsnip_Stage_4.png", "Crops/Parsnip_Stage_5.png"}, "Crops/Parsnip.png"),
    Potato(SeedType.PotatoSeeds, List.of(1, 1, 1, 2, 1), 6, true, -1, 80, true, 25, List.of(Season.Spring), false,
        new String[] {"Crops/Potato_Stage_1.png", "Crops/Potato_Stage_2.png", "Crops/Potato_Stage_3.png", "Crops/Potato_Stage_4.png", "Crops/Potato_Stage_5.png"}, "Crops/Potato.png"),
    Rhubarb(SeedType.RhubarbSeeds, List.of(2, 2, 2, 3, 4), 13, true, -1, 220, false, -1, List.of(Season.Spring), false,
        new String[] {"Crops/Rhubarb_Stage_1.png", "Crops/Rhubarb_Stage_2.png", "Crops/Rhubarb_Stage_3.png", "Crops/Rhubarb_Stage_4.png", "Crops/Rhubarb_Stage_5.png"}, "Crops/Rhubarb.png"),
    Strawberry(SeedType.StrawberrySeeds, List.of(1, 1, 2, 2, 2), 8, false, 4, 120, true, 50, List.of(Season.Spring), false,
        new String[] {"Crops/Strawberry_Stage_1.png", "Crops/Strawberry_Stage_2.png", "Crops/Strawberry_Stage_3.png", "Crops/Strawberry_Stage_4.png", "Crops/Strawberry_Stage_5.png"}, "Crops/Strawberry.png"),
    Tulip(SeedType.TulipBulb, List.of(1, 1, 2, 2), 6, true, -1, 30, true, 45, List.of(Season.Spring), false,
        new String[] {"Crops/Tulip_Stage_1.png", "Crops/Tulip_Stage_2.png", "Crops/Tulip_Stage_3.png", "Crops/Tulip_Stage_4.png"}, "Crops/Tulip.png"),
    UnmilledRice(SeedType.RiceShoot, List.of(1, 2, 2, 3), 8, true, -1, 30, true, 3, List.of(Season.Spring), false,
        new String[] {"Crops/Unmilled_Rice_Stage_1.png", "Crops/Unmilled_Rice_Stage_2.png", "Crops/Unmilled_Rice_Stage_3.png", "Crops/Unmilled_Rice_Stage_4.png"}, "Crops/Unmilled_Rice.png"),
    Blueberry(SeedType.BlueberrySeeds, List.of(1, 3, 3, 4, 2), 13, false, 4, 50, true, 25, List.of(Season.Summer), false,
        new String[] {"Crops/Blueberry_Stage_1.png", "Crops/Blueberry_Stage_2.png", "Crops/Blueberry_Stage_3.png", "Crops/Blueberry_Stage_4.png", "Crops/Blueberry_Stage_5.png"}, "Crops/Blueberry.png"),
    Corn(SeedType.CornSeeds, List.of(2, 3, 3, 3, 3), 14, false, 4, 50, true, 25, List.of(Season.Summer), false,
        new String[] {"Crops/Corn_Stage_1.png", "Crops/Corn_Stage_2.png", "Crops/Corn_Stage_3.png", "Crops/Corn_Stage_4.png", "Crops/Corn_Stage_5.png"}, "Crops/Corn.png"),
    Hops(SeedType.HopsStarter, List.of(1, 1, 2, 3, 4), 11, false, 1, 25, true, 45, List.of(Season.Summer), false,
        new String[] {"Crops/Hops_Stage_1.png", "Crops/Hops_Stage_3.png", "Crops/Hops_Stage_5.png", "Crops/Hops_Stage_7.png", "Crops/Hops_Stage_8.png"}, "Crops/Hops.png"),
    HotPepper(SeedType.PepperSeeds, List.of(1, 1, 1, 1, 1), 5, false, 3, 40, true, 13, List.of(Season.Summer), false,
        new String[] {"Crops/Hot_Pepper_Stage_1.png", "Crops/Hot_Pepper_Stage_3.png", "Crops/Hot_Pepper_Stage_4.png", "Crops/Hot_Pepper_Stage_5.png", "Crops/Hot_Pepper_Stage_6.png"}, "Crops/Hot_Pepper.png"),
    Melon(SeedType.MelonSeeds, List.of(1, 2, 3, 3, 3), 12, true, -1, 250, true, 113, List.of(Season.Summer), true,
        new String[] {"Crops/Melon_Stage_1.png", "Crops/Melon_Stage_2.png", "Crops/Melon_Stage_3.png", "Crops/Melon_Stage_4.png", "Crops/Melon_Stage_5.png"}, "Crops/Melon.png"),
    Poppy(SeedType.PoppySeeds, List.of(1, 2, 2, 2), 7, true, -1, 140, true, 45, List.of(Season.Summer), false,
        new String[] {"Crops/Poppy_Stage_1.png", "Crops/Poppy_Stage_2.png", "Crops/Poppy_Stage_3.png", "Crops/Poppy_Stage_4.png"}, "Crops/Poppy.png"),
    Radish(SeedType.RadishSeeds, List.of(2, 1, 2, 1), 6, true, -1, 90, true, 45, List.of(Season.Summer), false,
        new String[] {"Crops/Radish_Stage_1.png", "Crops/Radish_Stage_2.png", "Crops/Radish_Stage_3.png", "Crops/Radish_Stage_4.png"}, "Crops/Radish.png"),
    RedCabbage(SeedType.RedCabbageSeeds, List.of(2, 1, 2, 2, 2), 9, true, -1, 260, true, 75, List.of(Season.Summer), false,
        new String[] {"Crops/Red_Cabbage_Stage_2.png", "Crops/Red_Cabbage_Stage_3.png", "Crops/Red_Cabbage_Stage_4.png", "Crops/Red_Cabbage_Stage_5.png", "Crops/Red_Cabbage_Stage_6.png"}, "Crops/Red_Cabbage.png"),
    Starfruit(SeedType.StarfruitSeeds, List.of(2, 3, 2, 3, 3), 13, true, -1, 750, true, 125, List.of(Season.Summer), false,
        new String[] {"Crops/Starfruit_Stage_1.png", "Crops/Starfruit_Stage_2.png", "Crops/Starfruit_Stage_3.png", "Crops/Starfruit_Stage_4.png", "Crops/Starfruit_Stage_5.png"}, "Crops/Starfruit.png"),
    SummerSpangle(SeedType.SpangleSeeds, List.of(1, 2, 3, 1), 8, true, -1, 90, true, 45, List.of(Season.Summer), false,
        new String[] {"Crops/Summer_Spangle_Stage_1.png", "Crops/Summer_Spangle_Stage_2.png", "Crops/Summer_Spangle_Stage_3.png", "Crops/Summer_Spangle_Stage_4.png"}, "Crops/Summer_Spangle.png"),
    SummerSquash(SeedType.SummerSquashSeeds, List.of(1, 1, 1, 2, 1), 6, false, 3, 45, true, 63, List.of(Season.Summer), false,
        new String[] {"Crops/Summer_Squash_Stage_1.png", "Crops/Summer_Squash_Stage_2.png", "Crops/Summer_Squash_Stage_3.png", "Crops/Summer_Squash_Stage_4.png", "Crops/Summer_Squash_Stage_5.png"}, "Crops/Summer_Squash.png"),
    Sunflower(SeedType.SunflowerSeeds, List.of(1, 2, 3, 2), 8, true, -1, 80, true, 45, List.of(Season.Summer), false,
        new String[] {"Crops/Sunflower_Stage_1.png", "Crops/Sunflower_Stage_2.png", "Crops/Sunflower_Stage_3.png", "Crops/Sunflower_Stage_4.png"}, "Crops/Sunflower.png"),
    Tomato(SeedType.TomatoSeeds, List.of(2, 2, 2, 2, 3), 11, false, 4, 60, true, 20, List.of(Season.Summer), false,
        new String[] {"Crops/Tomato_Stage_1.png", "Crops/Tomato_Stage_2.png", "Crops/Tomato_Stage_3.png", "Crops/Tomato_Stage_4.png", "Crops/Tomato_Stage_5.png"}, "Crops/Tomato.png"),
    Wheat(SeedType.WheatSeeds, List.of(1, 1, 1, 1), 4, true, -1, 25, false, -1, List.of(Season.Summer), false,
        new String[] {"Crops/Wheat_Stage_1.png", "Crops/Wheat_Stage_2.png", "Crops/Wheat_Stage_3.png", "Crops/Wheat_Stage_4.png"}, "Crops/Wheat.png"),
    Amaranth(SeedType.AmaranthSeeds, List.of(1, 2, 2, 2), 7, true, -1, 150, true, 50, List.of(Season.Fall), false,
        new String[] {"Crops/Amaranth_Stage_1.png", "Crops/Amaranth_Stage_2.png", "Crops/Amaranth_Stage_3.png", "Crops/Amaranth_Stage_4.png", "Crops/Amaranth_Stage_5.png"}, "Crops/Amaranth.png"),
    Artichoke(SeedType.ArtichokeSeeds, List.of(2, 2, 1, 2, 1), 8, true, -1, 160, true, 30, List.of(Season.Fall), false,
        new String[] {"Crops/Artichoke_Stage_1.png", "Crops/Artichoke_Stage_2.png", "Crops/Artichoke_Stage_3.png", "Crops/Artichoke_Stage_4.png", "Crops/Artichoke_Stage_5.png"}, "Crops/Artichoke.png"),
    Beet(SeedType.BeetSeeds, List.of(1, 1, 2, 2), 6, true, -1, 100, true, 30, List.of(Season.Fall), false,
        new String[] {"Crops/Beet_Stage_1.png", "Crops/Beet_Stage_2.png", "Crops/Beet_Stage_3.png", "Crops/Beet_Stage_4.png"}, "Crops/Beet.png"),
    BokChoy(SeedType.BokChoySeeds, List.of(1, 1, 1, 1), 4, true, -1, 80, true, 25, List.of(Season.Fall), false,
        new String[] {"Crops/Bok_Choy_Stage_1.png", "Crops/Bok_Choy_Stage_2.png", "Crops/Bok_Choy_Stage_3.png", "Crops/Bok_Choy_Stage_4.png"}, "Crops/Bok_Choy.png"),
    Broccoli(SeedType.BroccoliSeeds, List.of(2, 2, 2, 2), 8, false, 4, 70, true, 63, List.of(Season.Fall), false,
        new String[] {"Crops/Broccoli_Stage_1.png", "Crops/Broccoli_Stage_2.png", "Crops/Broccoli_Stage_3.png", "Crops/Broccoli_Stage_4.png"}, "Crops/Broccoli.png"),
    Cranberries(SeedType.CranberrySeeds, List.of(1, 2, 1, 1, 2), 7, false, 5, 75, true, 38, List.of(Season.Fall), false,
        new String[] {"Crops/Cranberry_Stage_2.png", "Crops/Cranberry_Stage_3.png", "Crops/Cranberry_Stage_5.png", "Crops/Cranberry_Stage_6.png", "Crops/Cranberry_Stage_7.png"}, "Crops/Cranberries.png"),
    Eggplant(SeedType.EggplantSeeds, List.of(1, 1, 1, 1), 5, false, 5, 60, true, 20, List.of(Season.Fall), false,
        new String[] {"Crops/Eggplant_Stage_1.png", "Crops/Eggplant_Stage_2.png", "Crops/Eggplant_Stage_3.png", "Crops/Eggplant_Stage_4.png"}, "Crops/Eggplant.png"),
    FairyRose(SeedType.FairySeeds, List.of(1, 4, 4, 3), 12, true, -1, 290, true, 45, List.of(Season.Fall), false,
        new String[] {"Crops/Fairy_Rose_Stage_1.png", "Crops/Fairy_Rose_Stage_2.png", "Crops/Fairy_Rose_Stage_3.png", "Crops/Fairy_Rose_Stage_5.png"}, "Crops/Fairy_Rose.png"),
    Grape(SeedType.GrapeStarter, List.of(1, 1, 2, 3, 3), 10, false, 3, 80, true, 38, List.of(Season.Fall), false,
        new String[] {"Crops/Grape_Stage_2.png", "Crops/Grape_Stage_4.png", "Crops/Grape_Stage_6.png", "Crops/Grape_Stage_7.png", "Crops/Grape_Stage_5.png"}, "Crops/Grape.png"),
    Pumpkin(SeedType.PumpkinSeeds, List.of(1, 2, 3, 4, 3), 13, true, -1, 320, false, -1, List.of(Season.Fall), true,
        new String[] {"Crops/Pumpkin_Stage_2.png", "Crops/Pumpkin_Stage_3.png", "Crops/Pumpkin_Stage_5.png", "Crops/Pumpkin_Stage_6.png", "Crops/Pumpkin_Stage_5.png"}, "Crops/Pumpkin.png"),
    Yam(SeedType.YamSeeds, List.of(1, 3, 3, 3), 10, true, -1, 160, true, 45, List.of(Season.Fall), false,
        new String[] {"Crops/Yam_Stage_1.png", "Crops/Yam_Stage_2.png", "Crops/Yam_Stage_3.png", "Crops/Yam_Stage_5.png"}, "Crops/Yam.png"),
    SweetGemBerry(SeedType.RareSeed, List.of(2, 4, 6, 6, 6), 24, true, -1, 3000, false, -1, List.of(Season.Fall), false,
        new String[] {"Crops/Sweet_Gem_Berry_Stage_2.png", "Crops/Sweet_Gem_Berry_Stage_3.png", "Crops/Sweet_Gem_Berry_Stage_5.png", "Crops/Sweet_Gem_Berry_Stage_6.png", "Crops/Sweet_Gem_Berry_Stage_5.png"}, "Crops/Sweet_Gem_Berry.png"),
    Powdermelon(SeedType.PowdermelonSeeds, List.of(1, 2, 1, 2, 1), 7, true, -1, 60, true, 63, List.of(Season.Winter), true,
        new String[] {"Crops/Powdermelon_Stage_2.png", "Crops/Powdermelon_Stage_3.png", "Crops/Powdermelon_Stage_5.png", "Crops/Powdermelon_Stage_6.png", "Crops/Powdermelon_Stage_5.png"}, "Crops/Powdermelon.png"),
    AncientFruit(SeedType.AncientSeeds, List.of(2, 7, 7, 7, 5), 28, false, 7, 550, false, -1, List.of(Season.Spring, Season.Summer, Season.Fall), false,
        new String[] {"Crops/Ancient_Fruit_Stage_2.png", "Crops/Ancient_Fruit_Stage_3.png", "Crops/Ancient_Fruit_Stage_5.png", "Crops/Ancient_Fruit_Stage_7.png", "Crops/Ancient_Fruit_Stage_5.png"}, "Crops/Ancient_Fruit.png"),


    // Foraging crops
    CommonMushroom(null, List.of(0, 0, 0), 0, true, -1, 40, true, 38,
        List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter), false,
        new String[] {}, "Foraging/Common_Mushroom.png"),

    Daffodil(null, List.of(0, 0, 0), 0, true, -1, 30, true, 0,
        List.of(Season.Spring), false,
        new String[] {}, "Foraging/Daffodil.png"),

    Dandelion(null, List.of(0, 0, 0), 0, true, -1, 40, true, 25,
        List.of(Season.Spring), false,
        new String[] {}, "Foraging/Dandelion.png"),

    Leek(null, List.of(0, 0, 0), 0, true, -1, 60, true, 40,
        List.of(Season.Spring), false,
        new String[] {}, "Foraging/Leek.png"),

    Morel(null, List.of(0, 0, 0), 0, true, -1, 150, true, 20,
        List.of(Season.Spring), false,
        new String[] {}, "Foraging/Morel.png"),

    Salmonberry(null, List.of(0, 0, 0), 0, true, -1, 5, true, 25,
        List.of(Season.Spring), false,
        new String[] {}, "Foraging/Salmonberry.png"),

    SpringOnion(null, List.of(0, 0, 0), 0, true, -1, 8, true, 13,
        List.of(Season.Spring), false,
        new String[] {}, "Foraging/Spring_Onion.png"),

    WildHorseradish(null, List.of(0, 0, 0), 0, true, -1, 50, true, 13,
        List.of(Season.Spring), false,
        new String[] {}, "Foraging/Wild_Horseradish.png"),

    FiddleheadFern(null, List.of(0, 0, 0), 0, true, -1, 90, true, 25,
        List.of(Season.Summer), false,
        new String[] {}, "Foraging/Fiddlehead_Fern.png"),

    GrapeForage(null, List.of(0, 0, 0), 0, true, -1, 80, true, 38,
        List.of(Season.Summer), false,
        new String[] {}, "Foraging/Grape.png"),

    RedMushroom(null, List.of(0, 0, 0), 0, true, -1, 75, true, -50,
        List.of(Season.Summer), false,
        new String[] {}, "Foraging/Red_Mushroom.png"),

    SpiceBerry(null, List.of(0, 0, 0), 0, true, -1, 80, true, 25,
        List.of(Season.Summer), false,
        new String[] {}, "Foraging/Spice_Berry.png"),

    SweetPea(null, List.of(0, 0, 0), 0, true, -1, 50, true, 0,
        List.of(Season.Summer), false,
        new String[] {}, "Foraging/Sweet_Pea.png"),

    Blackberry(null, List.of(0, 0, 0), 0, true, -1, 25, true, 25,
        List.of(Season.Fall), false,
        new String[] {}, "Foraging/Blackberry.png"),

    Chanterelle(null, List.of(0, 0, 0), 0, true, -1, 160, true, 75,
        List.of(Season.Fall), false,
        new String[] {}, "Foraging/Chanterelle.png"),

    Hazelnut(null, List.of(0, 0, 0), 0, true, -1, 40, true, 38,
        List.of(Season.Fall), false,
        new String[] {}, "Foraging/Hazelnut.png"),

    PurpleMushroom(null, List.of(0, 0, 0), 0, true, -1, 90, true, 30,
        List.of(Season.Fall), false,
        new String[] {}, "Foraging/Purple_Mushroom.png"),

    WildPlum(null, List.of(0, 0, 0), 0, true, -1, 80, true, 25,
        List.of(Season.Fall), false,
        new String[] {}, "Foraging/Wild_Plum.png"),

    Crocus(null, List.of(0, 0, 0), 0, true, -1, 60, true, 0,
        List.of(Season.Winter), false,
        new String[] {}, "Foraging/Crocus.png"),

    CrystalFruit(null, List.of(0, 0, 0), 0, true, -1, 150, true, 63,
        List.of(Season.Winter), false,
        new String[] {}, "Foraging/Crystal_Fruit.png"),

    Holly(null, List.of(0, 0, 0), 0, true, -1, 80, true, -37,
        List.of(Season.Winter), false,
        new String[] {}, "Foraging/Holly.png"),

    SnowYam(null, List.of(0, 0, 0), 0, true, -1, 100, true, 30,
        List.of(Season.Winter), false,
        new String[] {}, "Foraging/Snow_Yam.png"),

    WinterRoot(null, List.of(0, 0, 0), 0, true, -1, 70, true, 25,
        List.of(Season.Winter), false,
        new String[] {}, "Foraging/Winter_Root.png");


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
    private final String[] stageTextureAddresses;
    private final String inventoryTexturePath;

    CropType(SeedType Source, List<Integer> stages, int totalHarvestTime, boolean oneTime, int regrowthTime,
             double baseSellPrice, boolean isEdible, int energy, List<Season> season, boolean canBecomeGiant,
             String[] stageTextureAddresses, String harvestedCropAddress) {
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
        this.stageTextureAddresses = stageTextureAddresses;
        this.inventoryTexturePath = harvestedCropAddress;
    }

    public static CropType getCropTypeBySeedType(SeedType seedType, Game game) {
        if (seedType.equals(SeedType.MixedSeed)) {
            Season season = game.getDate().getSeason();
            Random random = new Random();
            CropType type;
            //Filtering Foraging Types
            do {
                int randInt = random.nextInt(MixedSeedPossibleCrops.getCropsForSeason(season).size());
                type = MixedSeedPossibleCrops.getCropsForSeason(season).get(randInt);
            } while (type.Source == null);
            return type;
        }
        for (CropType cropType : CropType.values()) {
            if (cropType.getSource() == null)
                continue;
            if (cropType.getSource().equals(seedType))
                return cropType;
        }
        return null;
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

    public String[] getStageTexturePaths() {
        return stageTextureAddresses;
    }

    @Override
    public String getInventoryTexturePath() {
        return inventoryTexturePath;
    }
}
