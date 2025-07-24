package io.github.StardewValley.shared.models.plant;

import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.shared.models.BackPackableType;

public enum SaplingType implements BackPackableType {
    ApricotSapling,
    CherrySapling,
    BananaSapling,
    MangoSapling,
    OrangeSapling,
    PeachSapling,
    AppleSapling,
    PomegranateSapling,
    Acorns,
    MapleSeeds,
    PineCones,
    MahoganySeeds,
    MushroomTreeSeeds,
    MysticTreeSeeds;

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

    @Override
    public Texture getInventoryTexture() {
        return TreeAssetManager.getTreeAssetManager().getInventoryTexture(this);
    }
}
