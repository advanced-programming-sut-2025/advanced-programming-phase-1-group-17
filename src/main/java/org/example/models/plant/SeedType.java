package org.example.models.plant;

import org.example.models.App;
import org.example.models.BackPackableType;
import org.example.models.Placeable;
import org.example.models.enums.Season;

import java.util.Random;

public enum SeedType implements BackPackableType {
    JazzSeeds,
    CarrotSeeds,
    CauliflowerSeeds,
    CoffeeBean,
    GarlicSeeds,
    BeanStarter,
    KaleSeeds,
    ParsnipSeeds,
    PotatoSeeds,
    RhubarbSeeds,
    StrawberrySeeds,
    TulipBulb,
    RiceShoot,
    BlueberrySeeds,
    CornSeeds,
    HopsStarter,
    PepperSeeds,
    MelonSeeds,
    PoppySeeds,
    RadishSeeds,
    RedCabbageSeeds,
    StarfruitSeeds,
    SpangleSeeds,
    SummerSquashSeeds,
    SunflowerSeeds,
    TomatoSeeds,
    WheatSeeds,
    AmaranthSeeds,
    ArtichokeSeeds,
    BeetSeeds,
    BokChoySeeds,
    BroccoliSeeds,
    CranberrySeeds,
    EggplantSeeds,
    FairySeeds,
    GrapeStarter,
    PumpkinSeeds,
    YamSeeds,
    RareSeed,
    PowdermelonSeeds,
    AncientSeeds,
    Mixed; // Special case: not tied to a single crop

    public static SeedType getSeedTypeByName(String source) {
        for (SeedType seedType : SeedType.values()) {
            if (seedType.name().equalsIgnoreCase(source)) {
                return seedType;
            }
        }
        return null;
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public double getPrice() {
        //TODO
        return 0;
    }
}
