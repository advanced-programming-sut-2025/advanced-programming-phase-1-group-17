package org.example.models.foraging;

import org.example.models.BackPackable;
import org.example.models.BackPackableType;

public enum ForagingMineral implements BackPackable {
    QUARTZ("A clear crystal commonly found in caves and mines.", 25),
    EARTH_CRYSTAL("A resinous substance found near the surface.", 50),
    FROZEN_TEAR("A crystal fabled to be the frozen tears of a yeti.", 75),
    FIRE_QUARTZ("A glowing red crystal commonly found near hot lava.", 100),
    EMERALD("A precious stone with a brilliant green color.", 250),
    AQUAMARINE("A shimmery blue-green gem.", 180),
    RUBY("A precious stone that is sought after for its rich color and beautiful luster.", 250),
    AMETHYST("A purple variant of quartz.", 100),
    TOPAZ("Fairly common but still prized for its beauty.", 80),
    JADE("A pale green ornamental stone.", 200),
    DIAMOND("A rare and valuable gem.", 750),
    PRISMATIC_SHARD("A very rare and powerful substance with unknown origins.", 2000),
    COPPER("A common ore that can be smelted into bars.", 5),
    IRON("A fairly common ore that can be smelted into bars.", 10),
    GOLD("A precious ore that can be smelted into bars.", 25),
    IRIDIUM("An exotic ore with many curious properties. Can be smelted into bars.", 100),
    COAL("A combustible rock that is useful for crafting and smelting.", 15);

    private final String description;
    private final double price;

    ForagingMineral(String description, double price) {
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

    @Override
    public BackPackableType getType() {
        return null;
    }
}
