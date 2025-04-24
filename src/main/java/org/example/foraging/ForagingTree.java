package org.example.foraging;

import org.example.models.plant.TreeType;

public enum ForagingTree {
    ACORNS(TreeType.OakTree),
    MAPLE_SEEDS(TreeType.MapleTree),
    PINE_CONES(TreeType.PineTree),
    MAHOGANY_SEEDS(TreeType.MahoganyTree),
    MUSHROOM_TREE_SEEDS(TreeType.MushroomTree);

    private final TreeType treeType;

    ForagingTree(TreeType treeType) {
        this.treeType = treeType;
    }

    public TreeType getTreeType() {
        return treeType;
    }
}
