package org.example.models.plant;

import org.example.models.App;
import org.example.models.BackPackableType;
import org.example.models.Placeable;
import org.example.models.enums.Season;

import java.util.Random;

public enum SeedType implements BackPackableType {
    JazzSeeds(CropType.BlueJazz),
    CarrotSeeds(CropType.Carrot),
    CauliflowerSeeds(CropType.Cauliflower),
    CoffeeBean(CropType.CoffeeBean),
    GarlicSeeds(CropType.Garlic),
    BeanStarter(CropType.GreenBean),
    KaleSeeds(CropType.Kale),
    ParsnipSeeds(CropType.Parsnip),
    PotatoSeeds(CropType.Potato),
    RhubarbSeeds(CropType.Rhubarb),
    StrawberrySeeds(CropType.Strawberry),
    TulipBulb(CropType.Tulip),
    RiceShoot(CropType.UnmilledRice),
    BlueberrySeeds(CropType.Blueberry),
    CornSeeds(CropType.Corn),
    HopsStarter(CropType.Hops),
    PepperSeeds(CropType.HotPepper),
    MelonSeeds(CropType.Melon),
    PoppySeeds(CropType.Poppy),
    RadishSeeds(CropType.Radish),
    RedCabbageSeeds(CropType.RedCabbage),
    StarfruitSeeds(CropType.Starfruit),
    SpangleSeeds(CropType.SummerSpangle),
    SummerSquashSeeds(CropType.SummerSquash),
    SunflowerSeeds(CropType.Sunflower),
    TomatoSeeds(CropType.Tomato),
    WheatSeeds(CropType.Wheat),
    AmaranthSeeds(CropType.Amaranth),
    ArtichokeSeeds(CropType.Artichoke),
    BeetSeeds(CropType.Beet),
    BokChoySeeds(CropType.BokChoy),
    BroccoliSeeds(CropType.Broccoli),
    CranberrySeeds(CropType.Cranberries),
    EggplantSeeds(CropType.Eggplant),
    FairySeeds(CropType.FairyRose),
    GrapeStarter(CropType.Grape),
    PumpkinSeeds(CropType.Pumpkin),
    YamSeeds(CropType.Yam),
    RareSeed(CropType.SweetGemBerry),
    PowdermelonSeeds(CropType.Powdermelon),
    AncientSeeds(CropType.AncientFruit),
    Mixed(null); // Special case: not tied to a single crop

    private final CropType cropType;

    SeedType(CropType cropType) {
        this.cropType = cropType;
    }

    public static SeedType getSeedTypeByName(String source) {
        for (SeedType seedType : SeedType.values()) {
            if (seedType.name().equals(source)) {
                return seedType;
            }
        }
        return null;
    }

    public CropType getCropType() {
        if (cropType == null) { //Mixed
            Season season = App.getCurrentGame().getDate().getSeason();
            Random random = new Random();
            int randInt = random.nextInt(MixedSeedPossibleCrops.getCropsForSeason(season).size());
            return MixedSeedPossibleCrops.getCropsForSeason(season).get(randInt);
        }
        return cropType;
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
