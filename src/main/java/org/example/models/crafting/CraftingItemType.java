package org.example.models.crafting;

import org.example.models.BackPackableType;

public enum CraftingItemType implements BackPackableType {
    CherryBomb,
    Bomb,
    MegaBomb,
    Sprinkler,
    QualitySprinkler,
    IridiumSprinkler,
    CharcoalKlin,
    Furnace,
    Scarecrow,
    DeluxeScarecrow,
    BeeHouse,
    CheesePress,
    Keg,
    Loom,
    MayonnaiseMachine,
    OilMaker,
    PreservesJar,
    Dehydrator,
    FishSmoker,
    MysticTreeSeed,
    CopperOre,
    IronOre,
    GoldOre,
    CopperBar,
    IronBar,
    GoldBar,
    IridiumBar,
    Wood,
    Fibre;

    @Override
    public String getName() {
        return name();
    }

    @Override
    public double getPrice() {
        return 0;
    }

    public static CraftingItemType getCraftingItemTypeByName(String artisanName) {
        for (CraftingItemType type : CraftingItemType.values()) {
            if (type.name().equalsIgnoreCase(artisanName)) {
                return type;
            }
        }
        return null;
    }
}