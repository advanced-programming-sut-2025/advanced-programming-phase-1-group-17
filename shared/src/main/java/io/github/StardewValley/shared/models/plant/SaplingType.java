package io.github.StardewValley.shared.models.plant;

import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.shared.models.backpack.BackPackableType;

public enum SaplingType implements BackPackableType {
    ApricotSapling("Trees/Apricot_Sapling.png"),
    CherrySapling("Trees/Cherry_Sapling.png"),
    BananaSapling("Trees/Banana_Sapling.png"),
    MangoSapling("Trees/Mango_Sapling.png"),
    OrangeSapling("Trees/Orange_Sapling.png"),
    PeachSapling("Trees/Peach_Sapling.png"),
    AppleSapling("Trees/Apple_Sapling.png"),
    PomegranateSapling("Trees/Pomegranate_Sapling.png"),
    Acorns("Trees/Oak_Resin.png"),
    MapleSeeds("Trees/Maple_Seed.png"),
    PineCones("Trees/Pine_Tar.png"),
    MahoganySeeds("Trees/Mahogany_Seed.png"),
    MushroomTreeSeeds("Trees/Mushroom_Tree_Seed.png"),
    MysticTreeSeeds("Trees/Mystic_Tree_Seed.png");

    private final String texturePath;

    SaplingType(String texturePath) {
        this.texturePath = texturePath;
    }

    public static SaplingType getTypeByName(String source) {
        for (SaplingType value : SaplingType.values()) {
            if (value.name().equalsIgnoreCase(source))
                return value;
        }
        return null;
    }

    @Override
    public double getPrice() {
        return 0;
    }

    @Override
    public String getName() {
        return name();
    }

    public String getTexturePath() {
        return texturePath;
    }

    @Override
    public String getInventoryTexturePath() {
        return TreeAssetManager.getTreeAssetManager().getSaplingTexture(this);
    }
}
