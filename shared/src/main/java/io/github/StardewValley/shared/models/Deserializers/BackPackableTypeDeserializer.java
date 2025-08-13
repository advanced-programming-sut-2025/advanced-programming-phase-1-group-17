package io.github.StardewValley.shared.models.Deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import io.github.StardewValley.shared.models.NPCS.FlowerType;
import io.github.StardewValley.shared.models.NPCS.RingType;
import io.github.StardewValley.shared.models.animal.AnimalPlaceType;
import io.github.StardewValley.shared.models.animal.AnimalProductType;
import io.github.StardewValley.shared.models.animal.AnimalType;
import io.github.StardewValley.shared.models.artisan.ArtisanProductType;
import io.github.StardewValley.shared.models.artisan.IngredientGroup;
import io.github.StardewValley.shared.models.backpack.BackPackType;
import io.github.StardewValley.shared.models.backpack.BackPackableType;
import io.github.StardewValley.shared.models.backpack.NormalItemType;
import io.github.StardewValley.shared.models.cooking.FoodType;
import io.github.StardewValley.shared.models.cooking.RecipeType;
import io.github.StardewValley.shared.models.crafting.CraftingItemType;
import io.github.StardewValley.shared.models.crafting.CraftingRecipeType;
import io.github.StardewValley.shared.models.enums.FishType;
import io.github.StardewValley.shared.models.foraging.ForagingCropType;
import io.github.StardewValley.shared.models.foraging.MineralType;
import io.github.StardewValley.shared.models.market.ShippingBinType;
import io.github.StardewValley.shared.models.plant.*;
import io.github.StardewValley.shared.models.tools.FishingPoleType;
import io.github.StardewValley.shared.models.tools.ToolType;

import java.io.IOException;
import java.util.List;

public class BackPackableTypeDeserializer extends JsonDeserializer<BackPackableType> {

    private static final List<Class<? extends Enum<?>>> ENUM_CLASSES = List.of(
        AnimalPlaceType.class,
        AnimalProductType.class,
        AnimalType.class,
        ArtisanProductType.class,
        BackPackType.class,
        CraftingItemType.class,
        CraftingRecipeType.class,
        CropType.class,
        FertilizerType.class,
        FishType.class,
        FishingPoleType.class,
        FlowerType.class,
        FoodType.class,
        ForagingCropType.class,
        FruitType.class,
        IngredientGroup.class,
        MineralType.class,
        NormalItemType.class,
        RecipeType.class,
        RingType.class,
        SeedType.class,
        SaplingType.class,
        ShippingBinType.class,
        ToolType.class
    );

    @Override
    public BackPackableType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getText();
        for (Class<? extends Enum<?>> enumClass : ENUM_CLASSES) {
            try {
                return (BackPackableType) Enum.valueOf((Class) enumClass, value);
            } catch (IllegalArgumentException ignored) {}
        }
        throw new IOException("Unknown BackPackableType value: " + value);
    }
}
