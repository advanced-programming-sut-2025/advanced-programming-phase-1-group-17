package io.github.StardewValley.models.foraging;

import io.github.StardewValley.models.BackPackableType;

public enum MineralType implements BackPackableType  {
    Quartz("A clear crystal commonly found in caves and mines.", 25, "Mineral/Quartz.png"),
    EarthCrystal("A resinous substance found near the surface.", 50, "Mineral/Earth_Crystal.png"),
    FrozenTear("A crystal fabled to be the frozen tears of a yeti.", 75, "Mineral/Frozen_Tear.png"),
    FireQuartz("A glowing red crystal commonly found near hot lava.", 100, "Mineral/Fire_Quartz.png"),
    Emerald("A precious stone with a brilliant green color.", 250, "Mineral/Emerald.png"),
    Aquamarine("A shimmery blue-green gem.", 180, "Mineral/Aquamarine.png"),
    Ruby("A precious stone that is sought after for its rich color and beautiful luster.", 250, "Mineral/Ruby.png"),
    Amethyst("A purple variant of quartz.", 100, "Mineral/Amethyst.png"),
    Topaz("Fairly common but still prized for its beauty.", 80, "Mineral/Topaz.png"),
    Jade("A pale green ornamental stone.", 200, "Mineral/Jade.png"),
    Diamond("A rare and valuable gem.", 750, "Mineral/Diamond.png"),
    PrismaticShard("A very rare and powerful substance with unknown origins.", 2000, "Mineral/Prismatic_Shard.png"),
    CopperOre("A common ore that can be smelted into bars.", 5, "Mineral/Copper_Ore.png"),
    IronOre("A fairly common ore that can be smelted into bars.", 10, "Mineral/Iron_Ore.png"),
    GoldOre("A precious ore that can be smelted into bars.", 25, "Mineral/Gold_Ore.png"),
    IridiumOre("An exotic ore with many curious properties. Can be smelted into bars.", 100, "Mineral/Iridium_Ore.png"),
    Coal("A combustible rock that is useful for crafting and smelting.", 150, "Mineral/Coal.png"),
    Stone("Normal Stone", 20, "Mineral/Stone.png");

    private final String description;
    private final double price;
    private final String texturePath;

    MineralType(String description, double price, String texturePath) {
        this.description = description;
        this.price = price;
        this.texturePath = texturePath;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getName() {
        return name();
    }

    public String getTexturePath() {
        return texturePath;
    }
}
