package org.example.models.plant;

import org.example.models.BackPackable;
import org.example.models.BackPackableType;

public enum FruitType implements BackPackableType {
    Apricot(true, 38, 59, TreeType.ApricotTree),
    Cherry(true, 38, 80, TreeType.CherryTree),
    Banana(true, 75, 150, TreeType.BananaTree),
    Mango(true, 100, 130, TreeType.MangoTree),
    Orange(true, 38, 100, TreeType.OrangeTree),
    Peach(true, 38, 140, TreeType.PeachTree),
    Apple(true, 38, 100, TreeType.AppleTree),
    Pomegranate(true, 38, 140, TreeType.PomegranateTree),
    OakResin(false, 0, 150, TreeType.OakTree),
    MapleSyrup(false, 0, 200, TreeType.MapleTree),
    PineTar(false, 0, 100, TreeType.PineTree),
    Sap(true, -2, 2, TreeType.MahoganyTree),
    CommonMushroom(true, 38, 40, TreeType.MushroomTree),
    MysticSyrup(true, 500, 1000, TreeType.MysticTree);

    private final boolean isEdible;
    private final int energy;
    private final double baseSellPrice;
    private final TreeType sourceTreeType;

    FruitType(boolean isEdible, int energy, double baseSellPrice, TreeType sourceTreeType) {
        this.isEdible = isEdible;
        this.energy = energy;
        this.baseSellPrice = baseSellPrice;
        this.sourceTreeType = sourceTreeType;
    }

    public boolean isEdible() {
        return isEdible;
    }

    public int getEnergy() {
        return energy;
    }

    public double getBaseSellPrice() {
        return baseSellPrice;
    }

    public TreeType getSourceTreeType() {
        return sourceTreeType;
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
