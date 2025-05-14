package org.example.models.tools;

import org.example.models.BackPackableType;

public enum ToolType implements BackPackableType {
    Pickaxe(new int[]{0, 1, 2, 3, 4},new int[]{5,4,3,2,1}),
    Scythe(new int[]{0, 1, 2, 3, 4},new int[]{5,4,3,2,1}),
    Hoe(new int[]{0, 1, 2, 3, 4},new int[]{5,4,3,2,1}),
    Axe(new int[]{0, 1, 2, 3, 4},new int[]{5,4,3,2,1}),
    WateringCan(new int[]{0, 1, 2, 3, 4},new int[]{5,4,3,2,1}),
    FishingPole(new int[]{0, 1, 2, 3, 4},new int[]{5,4,3,2,1}),
    MilkPail(new int[]{0, 1, 2, 3, 4},new int[]{5,4,3,2,1}),
    Shear(new int[]{0, 1, 2, 3, 4},new int[]{5,4,3,2,1}),
    TrashCan(new int[]{40, 1, 2, 3, 4},new int[]{5,4,3,2,1});

    private final int[] levels;
    private final int[] energyCosts;

    ToolType(int[] levels, int[] energyCosts) {

        this.levels = levels;
        this.energyCosts = energyCosts;
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

    public int[] getLevels() {
        return levels;
    }

    public int[] getEnergyCosts() {
        return energyCosts;
    }
}
