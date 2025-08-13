package io.github.StardewValley.shared.models.Deserializers;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import io.github.StardewValley.shared.models.NPCS.FlowerType;
import io.github.StardewValley.shared.models.NPCS.RingType;
import io.github.StardewValley.shared.models.animal.AnimalPlaceType;
import io.github.StardewValley.shared.models.animal.AnimalProductType;
import io.github.StardewValley.shared.models.animal.AnimalType;
import io.github.StardewValley.shared.models.artisan.ArtisanProductType;
import io.github.StardewValley.shared.models.artisan.IngredientGroup;
import io.github.StardewValley.shared.models.backpack.BackPackType;
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

public class BackPackableTypeKeyDeserializer extends KeyDeserializer {
    @Override
    public Object deserializeKey(String key, DeserializationContext ctxt) throws IOException {
        // If you know the enum type from the context:
        // return Enum.valueOf(MyEnum.class, key);

        // If you have multiple enums implementing BackPackableType:
        for (Class<? extends Enum<?>> enumClass : List.of(
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
            )) {
            try {
                return Enum.valueOf((Class) enumClass, key);
            } catch (IllegalArgumentException ignored) {}
        }

        throw new IOException("Unknown BackPackableType key: " + key);
    }
}
