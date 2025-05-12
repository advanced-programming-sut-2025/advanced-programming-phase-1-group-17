package org.example.models.foraging;

import org.example.models.BackPackableType;

public enum MineralType implements BackPackableType {
    Quartz("A clear crystal commonly found in caves and mines.", 25),
    EarthCrystal("A resinous substance found near the surface.", 50),
    FrozenTear("A crystal fabled to be the frozen tears of a yeti.", 75),
    FireQuartz("A glowing red crystal commonly found near hot lava.", 100),
    Emerald("A precious stone with a brilliant green color.", 250),
    Aquamarine("A shimmery blue-green gem.", 180),
    Ruby("A precious stone that is sought after for its rich color and beautiful luster.", 250),
    Amethyst("A purple variant of quartz.", 100),
    Topaz("Fairly common but still prized for its beauty.", 80),
    Jade("A pale green ornamental stone.", 200),
    Diamond("A rare and valuable gem.", 750),
    PrismaticShard("A very rare and powerful substance with unknown origins.", 2000),
    CopperOre("A common ore that can be smelted into bars.", 5),
    IronOre("A fairly common ore that can be smelted into bars.", 10),
    GoldOre("A precious ore that can be smelted into bars.", 25),
    IridiumOre("An exotic ore with many curious properties. Can be smelted into bars.", 100),
    Coal("A combustible rock that is useful for crafting and smelting.", 150),
    Stone("Normal Stone", 20);

    private final String description;
    private final double price;

    MineralType(String description, double price) {
        this.description = description;
        this.price = price;
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
}
