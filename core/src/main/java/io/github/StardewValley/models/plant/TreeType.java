package io.github.StardewValley.models.plant;

import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.models.enums.Season;

import java.util.List;

public enum TreeType {
    ApricotTree(SaplingType.ApricotSapling, List.of(7, 7, 7, 7), 28, FruitType.Apricot, 1, List.of(Season.Spring),
        new String[] {
            "assets/Trees/Apricot_Stage_1.png", "assets/Trees/Apricot_Stage_2.png", "assets/Trees/Apricot_Stage_3.png",
            "assets/Trees/Apricot_Stage_4.png", "assets/Trees/Apricot_Stage_5.png"
        },
        "assets/Trees/Apricot_Stage_5_Fruit.png"),
    CherryTree(SaplingType.CherrySapling, List.of(7, 7, 7, 7), 28, FruitType.Cherry, 1, List.of(Season.Spring),
        new String[] {
            "assets/Trees/Cherry_Stage_1.png", "assets/Trees/Cherry_Stage_2.png", "assets/Trees/Cherry_Stage_3.png",
            "assets/Trees/Cherry_Stage_4.png", "assets/Trees/Cherry_Stage_5.png"
        },
        "assets/Trees/Cherry_Stage_5_Fruit.png"),
    BananaTree(SaplingType.BananaSapling, List.of(7, 7, 7, 7), 28, FruitType.Banana, 1, List.of(Season.Summer),
        new String[] {
            "assets/Trees/Banana_Stage_1.png", "assets/Trees/Banana_Stage_2.png", "assets/Trees/Banana_Stage_3.png",
            "assets/Trees/Banana_Stage_4.png", "assets/Trees/Banana_Stage_5.png"
        },
        "assets/Trees/Banana_Stage_5_Fruit.png"),
    MangoTree(SaplingType.MangoSapling, List.of(7, 7, 7, 7), 28, FruitType.Mango, 1, List.of(Season.Summer),
        new String[] {
            "assets/Trees/Mango_Stage_1.png", "assets/Trees/Mango_Stage_2.png", "assets/Trees/Mango_Stage_3.png",
            "assets/Trees/Mango_Stage_4.png", "assets/Trees/Mango_Stage_5.png"
        },
        "assets/Trees/Mango_Stage_5_Fruit.png"),
    OrangeTree(SaplingType.OrangeSapling, List.of(7, 7, 7, 7), 28, FruitType.Orange, 1, List.of(Season.Summer),
        new String[] {
            "assets/Trees/Orange_Stage_1.png", "assets/Trees/Orange_Stage_2.png", "assets/Trees/Orange_Stage_3.png",
            "assets/Trees/Orange_Stage_4.png", "assets/Trees/Orange_Stage_5.png"
        },
        "assets/Trees/Orange_Stage_5_Fruit.png"),
    PeachTree(SaplingType.PeachSapling, List.of(7, 7, 7, 7), 28, FruitType.Peach, 1, List.of(Season.Summer),
        new String[] {
            "assets/Trees/Peach_Stage_1.png", "assets/Trees/Peach_Stage_2.png", "assets/Trees/Peach_Stage_3.png",
            "assets/Trees/Peach_Stage_4.png", "assets/Trees/Peach_Stage_5.png"
        },
        "assets/Trees/Peach_Stage_5_Fruit.png"),
    AppleTree(SaplingType.AppleSapling, List.of(7, 7, 7, 7), 28, FruitType.Apple, 1, List.of(Season.Fall),
        new String[] {
            "assets/Trees/Apple_Stage_1.png", "assets/Trees/Apple_Stage_2.png", "assets/Trees/Apple_Stage_3.png",
            "assets/Trees/Apple_Stage_4.png", "assets/Trees/Apple_Stage_5.png"
        },
        "assets/Trees/Apple_Stage_5_Fruit.png"),
    PomegranateTree(SaplingType.PomegranateSapling, List.of(7, 7, 7, 7), 28, FruitType.Pomegranate, 1, List.of(Season.Fall),
        new String[] {
            "assets/Trees/Pomegranate_Stage_1.png", "assets/Trees/Pomegranate_Stage_2.png", "assets/Trees/Pomegranate_Stage_3.png",
            "assets/Trees/Pomegranate_Stage_4.png", "assets/Trees/Pomegranate_Stage_5.png"
        },
        "assets/Trees/Pomegranate_Stage_5_Fruit.png"),
    OakTree(SaplingType.Acorns, List.of(7, 7, 7, 7), 28, FruitType.OakResin, 7, List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter),
        new String[] {
            "assets/Trees/Oak_Stage_1.png", "assets/Trees/Oak_Stage_2.png", "assets/Trees/Oak_Stage_3.png",
            "assets/Trees/Oak_Stage_4.png", "assets/Trees/Oak_Stage_5.png"
        },
        "assets/Trees/Oak_Stage_5_Resin.png"),
    MapleTree(SaplingType.MapleSeeds, List.of(7, 7, 7, 7), 28, FruitType.MapleSyrup, 9, List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter),
        new String[] {
            "assets/Trees/Maple_Stage_1.png", "assets/Trees/Maple_Stage_2.png", "assets/Trees/Maple_Stage_3.png",
            "assets/Trees/Maple_Stage_4.png", "assets/Trees/Maple_Stage_5.png"
        },
        "assets/Trees/Maple_Stage_5_Syrup.png"),
    PineTree(SaplingType.PineCones, List.of(7, 7, 7, 7), 28, FruitType.PineTar, 5, List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter),
        new String[] {
            "assets/Trees/Pine_Stage_1.png", "assets/Trees/Pine_Stage_2.png", "assets/Trees/Pine_Stage_3.png",
            "assets/Trees/Pine_Stage_4.png", "assets/Trees/Pine_Stage_5.png"
        },
        "assets/Trees/Pine_Stage_5_Tar.png"),
    MahoganyTree(SaplingType.MahoganySeeds, List.of(7, 7, 7, 7), 28, FruitType.Sap, 1, List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter),
        new String[] {
            "assets/Trees/Mahogany_Stage_1.png", "assets/Trees/Mahogany_Stage_2.png", "assets/Trees/Mahogany_Stage_3.png",
            "assets/Trees/Mahogany_Stage_4.png", "assets/Trees/Mahogany_Stage_5.png"
        },
        "assets/Trees/Mahogany_Stage_5_Sap.png"),
    MushroomTree(SaplingType.MushroomTreeSeeds, List.of(7, 7, 7, 7), 28, FruitType.CommonMushroom, 1, List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter),
        new String[] {
            "assets/Trees/Mushroom_Stage_1.png", "assets/Trees/Mushroom_Stage_2.png", "assets/Trees/Mushroom_Stage_3.png",
            "assets/Trees/Mushroom_Stage_4.png", "assets/Trees/Mushroom_Stage_5.png"
        },
        "assets/Trees/Mushroom_Stage_5_Fruit.png"),
    MysticTree(SaplingType.MysticTreeSeeds, List.of(7, 7, 7, 7), 28, FruitType.MysticSyrup, 7, List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter),
        new String[] {
            "assets/Trees/Mystic_Stage_1.png", "assets/Trees/Mystic_Stage_2.png", "assets/Trees/Mystic_Stage_3.png",
            "assets/Trees/Mystic_Stage_4.png", "assets/Trees/Mystic_Stage_5.png"
        },
        "assets/Trees/Mystic_Stage_5_Syrup.png");

    private final SaplingType sapling;
    private final List<Integer> stages;
    private final int totalGrowthTime;
    private final FruitType fruitType;
    private final int fruitHarvestCycle;
    private final List<Season> seasons;
    private final String[] imageAddresses;
    private final transient Texture[] textures;
    private final String hasFruitImageAddress;
    private final transient Texture hasFruitTexture;

    TreeType(SaplingType sapling, List<Integer> stages, int totalGrowthTime,
             FruitType fruitType, int fruitHarvestCycle, List<Season> seasons,
             String[] imageAddresses, String hasFruitImageAddress) {
        this.sapling = sapling;
        this.stages = stages;
        this.totalGrowthTime = totalGrowthTime;
        this.fruitType = fruitType;
        this.fruitHarvestCycle = fruitHarvestCycle;
        this.seasons = seasons;
        this.imageAddresses = imageAddresses;
        this.textures = new Texture[imageAddresses.length];
        for (int i = 0; i < imageAddresses.length; i++) {
            this.textures[i] = new Texture(imageAddresses[i]);
        }
        this.hasFruitImageAddress = hasFruitImageAddress;
        this.hasFruitTexture = new Texture(hasFruitImageAddress);
    }

    // Constructor overload for TreeTypes missing imageAddresses and fruitImage (e.g. in your original code)
    TreeType(SaplingType sapling, List<Integer> stages, int totalGrowthTime,
             FruitType fruitType, int fruitHarvestCycle, List<Season> seasons) {
        this(sapling, stages, totalGrowthTime, fruitType, fruitHarvestCycle, seasons,
            new String[] {}, "");
    }

    public static TreeType getTreeTypeBySaplingType(SaplingType saplingType) {
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

    public static TreeType getTreeTypeByName(String name) {
        for (TreeType value : TreeType.values()) {
            if (value.name().equalsIgnoreCase(name))
                return value;
        }
        return null;
    }

    public static TreeType getTreeTypeByFruitType(FruitType fruitType) {
        for (TreeType value : TreeType.values()) {
            if (value.getFruitType().equals(fruitType))
                return value;
        }
        return null;
    }

    public Texture[] getTextures() {
        return textures;
    }

    public Texture getHasFruitTexture() {
        return hasFruitTexture;
    }
}
