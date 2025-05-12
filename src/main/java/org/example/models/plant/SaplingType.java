package org.example.models.plant;

import org.example.models.BackPackableType;

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
}
