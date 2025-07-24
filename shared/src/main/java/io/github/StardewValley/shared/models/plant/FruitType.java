package io.github.StardewValley.shared.models.plant;

import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.shared.models.BackPackableType;

public enum FruitType implements BackPackableType {
    Apricot(true, 38, 59),
    Cherry(true, 38, 80),
    Banana(true, 75, 150),
    Mango(true, 100, 130),
    Orange(true, 38, 100),
    Peach(true, 38, 140),
    Apple(true, 38, 100),
    Pomegranate(true, 38, 140),
    Oak_Resin(false, 0, 150),
    Maple_Syrup(false, 0, 200),
    Pine_Tar(false, 0, 100),
    Sap(true, -2, 2),
    Common_Mushroom(true, 38, 40),
    Mystic_Syrup(true, 500, 1000);

    private final boolean isEdible;
    private final int energy;
    private final double baseSellPrice;

    FruitType(boolean isEdible, int energy, double baseSellPrice) {
        this.isEdible = isEdible;
        this.energy = energy;
        this.baseSellPrice = baseSellPrice;
    }

    public static FruitType getFruitTypeByName(String name) {
        for (FruitType value : FruitType.values()) {
            if (value.name().equalsIgnoreCase(name))
                return value;
        }
        return null;
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

    @Override
    public String getName() {
        return name();
    }

    @Override
    public double getPrice() {
        return 0;
    }

    @Override
    public Texture getInventoryTexture() {
        return TreeAssetManager.getTreeAssetManager().getFruitTexture(this);
    }
}
