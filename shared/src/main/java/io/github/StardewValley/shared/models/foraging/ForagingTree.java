package io.github.StardewValley.shared.models.foraging;

import io.github.StardewValley.shared.models.plant.TreeType;

public enum ForagingTree {
    Acorns(TreeType.OakTree),
    MapleSeeds(TreeType.MapleTree),
    PineCones(TreeType.PineTree),
    MahoganySeeds(TreeType.MahoganyTree),
    MushroomTreeSeeds(TreeType.MushroomTree);

    private final TreeType treeType;

    ForagingTree(TreeType treeType) {
        this.treeType = treeType;
    }

    public TreeType getTreeType() {
        return treeType;
    }
}
