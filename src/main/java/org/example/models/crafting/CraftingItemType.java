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
    Coal,
    IronOre,
    GoldOre,
    CopperBar,
    IronBar,
    GoldBar,
    IridiumBar,
    Wood,
    Stone,
    Fibre,
    Acorn,
    MapleSeed,
    PineCone,
    MahoganySeed;

    @Override
    public String getName() {
        return name();
    }

    @Override
    public double getPrice() {
        return 0;
    }
}