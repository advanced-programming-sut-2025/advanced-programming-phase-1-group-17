package org.example.models.tools;

import org.example.models.BackPackableType;

public enum ToolType implements BackPackableType {
    Pickaxe(new int[]{0, 1, 2, 3, 4}),
    Scythe(new int[]{0, 1, 2, 3, 4}),
    Hoe(new int[]{0, 1, 2, 3, 4}),
    Axe(new int[]{0, 1, 2, 3, 4}),
    WateringCan(new int[]{0, 1, 2, 3, 4}),
    FishingPole(new int[]{0, 1, 2, 3, 4}),
    MilkPail(new int[]{0, 1, 2, 3, 4}),
    Shear(new int[]{0, 1, 2, 3, 4}),
    TrashCan(new int[]{40, 1, 2, 3, 4});

    private final int[] levels; // آرایه‌ی مقادیر

    ToolType(int[] levels) {
        this.levels = levels;
    }

    public int getLevel(ToolMaterial material) {
        int index = material.ordinal();
        return levels[index];
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
