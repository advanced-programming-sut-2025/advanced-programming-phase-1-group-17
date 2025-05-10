package org.example.models.plant;

import org.example.models.BackPackableType;

public enum SaplingType implements BackPackableType {
    ApricotSapling(TreeType.ApricotTree),
    CherrySapling(TreeType.CherryTree),
    BananaSapling(TreeType.BananaTree),
    MangoSapling(TreeType.MangoTree),
    OrangeSapling(TreeType.OrangeTree),
    PeachSapling(TreeType.PeachTree),
    AppleSapling(TreeType.AppleTree),
    PomegranateSapling(TreeType.PomegranateTree),
    Acorns(TreeType.OakTree),
    MapleSeeds(TreeType.MapleTree),
    PineCones(TreeType.PineTree),
    MahoganySeeds(TreeType.MahoganyTree),
    MushroomTreeSeeds(TreeType.MushroomTree),
    MysticTreeSeeds(TreeType.MysticTree);

    private final TreeType treeType;

    SaplingType(TreeType treeType) {
        this.treeType = treeType;
    }

    public static SaplingType getTypeByName(String source) {
        for (SaplingType value : SaplingType.values()) {
            if (value.name().equals(source))
                return value;
        }
        return null;
    }

    public TreeType getTreeType() {
        return treeType;
    }

    @Override
    public double getPrice() {
        return 0;
    }

    @Override
    public String getName() {
        return name();
    }
}
