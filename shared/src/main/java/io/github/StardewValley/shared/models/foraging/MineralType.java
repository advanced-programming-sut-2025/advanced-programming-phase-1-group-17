package io.github.StardewValley.shared.models.foraging;

import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.shared.models.BackPackableType;

public enum MineralType implements BackPackableType  {
    Quartz("A clear crystal commonly found in caves and mines.", 25, "Mineral/Quartz.png"),
    EarthCrystal("A resinous substance found near the surface.", 50, "Mineral/Earth_Crystal.png"),
    FrozenTear("A crystal fabled to be the frozen tears of a yeti.", 75, "Mineral/Frozen_Tear.png"),
    FireQuartz("A glowing red crystal commonly found near hot lava.", 100, "Mineral/Fire_Quartz.png"),
    Emerald("A precious stone with a brilliant green color.", 250, "Gem/Emerald.png"),
    Aquamarine("A shimmery blue-green gem.", 180, "Gem/Aquamarine.png"),
    Ruby("A precious stone that is sought after for its rich color and beautiful luster.", 250, "Gem/Ruby.png"),
    Amethyst("A purple variant of quartz.", 100, "Gem/Amethyst.png"),
    Topaz("Fairly common but still prized for its beauty.", 80, "Gem/Topaz.png"),
    Jade("A pale green ornamental stone.", 200, "Gem/Jade.png"),
    Diamond("A rare and valuable gem.", 750, "Gem/Diamond.png"),
    PrismaticShard("A very rare and powerful substance with unknown origins.", 2000, "Gem/Prismatic_Shard.png"),
    CopperOre("A common ore that can be smelted into bars.", 5, "Resource/Copper_Ore.png"),
    IronOre("A fairly common ore that can be smelted into bars.", 10, "Resource/Iron_Ore.png"),
    GoldOre("A precious ore that can be smelted into bars.", 25, "Resource/Gold_Ore.png"),
    IridiumOre("An exotic ore with many curious properties. Can be smelted into bars.", 100, "Resource/Iridium_Ore.png"),
    Coal("A combustible rock that is useful for crafting and smelting.", 150, "Resource/Coal.png"),
    Stone("Normal Stone", 20, "Resource/Stone.png");

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
    public Texture getInventoryTexture() {
        return MineralAssetManager.getMineralAssetManager().getTexture(this);
    }

    @Override
    public String getName() {
        return name();
    }

    public String getTexturePath() {
        return texturePath;
    }
}
