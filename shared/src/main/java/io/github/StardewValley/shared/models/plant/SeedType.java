package io.github.StardewValley.shared.models.plant;

import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.shared.models.backpack.BackPackableType;

public enum SeedType implements BackPackableType {
    BlueJazzSeeds("Crops/Blueberry_Seeds.png"),
    CarrotSeeds("Crops/Carrot_Seeds.png"),
    CauliflowerSeeds("Crops/Cauliflower_Seeds.png"),
    CoffeeBean("Crops/Coffee_Bean.png"),
    GarlicSeeds("Crops/Garlic_Seeds.png"),
    BeanStarter("Crops/Bean_Starter.png"),
    KaleSeeds("Crops/Kale_Seeds.png"),
    ParsnipSeeds("Crops/Parsnip_Seeds.png"),
    PotatoSeeds("Crops/Potato_Seeds.png"),
    RhubarbSeeds("Crops/Rhubarb_Seeds.png"),
    StrawberrySeeds("Crops/Strawberry_Seeds.png"),
    TulipBulb("Crops/Tulip_Bulb.png"),
    RiceShoot("Crops/Rice_Shoot.png"),
    BlueberrySeeds("Crops/Blueberry_Seeds.png"),
    CornSeeds("Crops/Corn_Seeds.png"),
    HopsStarter("Crops/Hops_Starter.png"),
    PepperSeeds("Crops/Pepper_Seeds.png"),
    MelonSeeds("Crops/Melon_Seeds.png"),
    PoppySeeds("Crops/Poppy_Seeds.png"),
    RadishSeeds("Crops/Radish_Seeds.png"),
    RedCabbageSeeds("Crops/Red_Cabbage_Seeds.png"),
    StarfruitSeeds("Crops/Starfruit_Seeds.png"),
    SpangleSeeds("Crops/Spangle_Seeds.png"),
    SummerSquashSeeds("Crops/Summer_Squash_Seeds.png"),
    SunflowerSeeds("Crops/Sunflower_Seeds.png"),
    TomatoSeeds("Crops/Tomato_Seeds.png"),
    WheatSeeds("Crops/Wheat_Seeds.png"),
    AmaranthSeeds("Crops/Amaranth_Seeds.png"),
    ArtichokeSeeds("Crops/Artichoke_Seeds.png"),
    BeetSeeds("Crops/Beet_Seeds.png"),
    BokChoySeeds("Crops/Bok_Choy_Seeds.png"),
    BroccoliSeeds("Crops/Broccoli_Seeds.png"),
    CranberrySeeds("Crops/Cranberry_Seeds.png"),
    EggplantSeeds("Crops/Eggplant_Seeds.png"),
    FairySeeds("Crops/Fairy_Seeds.png"),
    GrapeStarter("Crops/Grape_Starter.png"),
    PumpkinSeeds("Crops/Pumpkin_Seeds.png"),
    YamSeeds("Crops/Yam_Seeds.png"),
    RareSeed("Crops/Rare_Seed.png"),
    PowdermelonSeeds("Crops/Powdermelon_Seeds.png"),
    AncientSeeds("Crops/Ancient_Seeds.png"),
    MixedSeed("Crops/Mixed_Seeds.png"); // Special case: not tied to a single crop

    private final String texturePath;

    SeedType(String texturePath) {
        this.texturePath = texturePath;
    }

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
        // TODO: Set price logic based on your design
        return 0;
    }

    public String getTexturePath() {
        return texturePath;
    }

    @Override
    public Texture getInventoryTexture() {
        return CropAssetManager.getCropAssetManager().getSeedTexture(this);
    }
}
