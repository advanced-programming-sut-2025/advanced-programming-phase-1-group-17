package io.github.StardewValley.models.crafting;

import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.models.BackPackableType;
import io.github.StardewValley.models.artisan.ArtisanProductType;

import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static io.github.StardewValley.models.NormalItemType.*;
import static io.github.StardewValley.models.foraging.MineralType.*;
import static io.github.StardewValley.models.plant.SaplingType.*;

public enum CraftingItemType implements BackPackableType {
    CherryBomb(Map.of(() -> CopperOre, 4, () -> Coal, 1), 50, new Texture("Craftable_item/Cherry_Bomb.png")),
    Bomb(Map.of(() -> IronOre, 4, () -> Coal, 1), 50, new Texture("Craftable_item/Bomb.png")),
    MegaBomb(Map.of(() -> GoldOre, 4, () -> Coal, 1), 50, new Texture("Craftable_item/Mega_Bomb.png")),
    Sprinkler(Map.of(() -> ArtisanProductType.CopperBar, 1, () -> ArtisanProductType.IronBar, 1), 0, new Texture("Craftable_item/Sprinkler.png")),
    QualitySprinkler(Map.of(() -> ArtisanProductType.IronBar, 1, () -> ArtisanProductType.GoldBar, 1), 0, new Texture("Craftable_item/Quality_Sprinkler.png")),
    IridiumSprinkler(Map.of(() -> ArtisanProductType.GoldBar, 1, () -> ArtisanProductType.IridiumBar, 1), 0, new Texture("Craftable_item/Iridium_Sprinkler.png")),
    CharcoalKlin(Map.of(() -> Wood, 20, () -> ArtisanProductType.CopperBar, 2), 0, new Texture("Craftable_item/Charcoal_Kiln.png")),
    Furnace(Map.of(() -> CopperOre, 20, () -> Stone, 25), 0, new Texture("Craftable_item/Furnace.png")),
    Scarecrow(Map.of(() -> Wood, 50, () -> Coal, 1, () -> Fiber, 20), 0, new Texture("Craftable_item/Scarecrow.png")),
    DeluxeScarecrow(Map.of(() -> Wood, 50, () -> Coal, 1, () -> Fiber, 20, () -> IridiumOre, 1), 0, new Texture("Craftable_item/Deluxe_Scarecrow.png")),
    BeeHouse(Map.of(() -> Wood, 40, () -> Coal, 8, () -> ArtisanProductType.IronBar, 1), 0, new Texture("Craftable_item/Bee_House.png")),
    CheesePress(Map.of(() -> Wood, 45, () -> Stone, 45, () -> ArtisanProductType.CopperBar, 1), 0, new Texture("Craftable_item/Cheese_Press.png")),
    Keg(Map.of(() -> Wood, 30, () -> ArtisanProductType.CopperBar, 1, () -> ArtisanProductType.IronBar, 1), 0, new Texture("Craftable_item/Keg.png")),
    Loom(Map.of(() -> Wood, 60, () -> Fiber, 30), 0, new Texture("Craftable_item/Loom.png")),
    MayonnaiseMachine(Map.of(() -> Wood, 15, () -> Stone, 15, () -> ArtisanProductType.CopperBar, 1), 0, new Texture("Craftable_item/Mayonnaise_Machine.png")),
    OilMaker(Map.of(() -> Wood, 100, () -> ArtisanProductType.GoldBar, 1, () -> ArtisanProductType.IronBar, 1), 0, new Texture("Craftable_item/Oil_Maker.png")),
    PreservesJar(Map.of(() -> Wood, 50, () -> Stone, 40, () -> Coal, 8), 0, new Texture("Craftable_item/Preserves_Jar.png")),
    Dehydrator(Map.of(() -> Wood, 30, () -> Stone, 20, () -> Fiber, 30), 0, new Texture("Craftable_item/Dehydrator.png")),
    GrassStarter(Map.of(() -> Wood, 1, () -> Fiber, 1), 0, new Texture("Craftable_item/Bomb.png")),
    FishSmoker(Map.of(() -> Wood, 50, () -> ArtisanProductType.IronBar, 3, () -> Coal, 10), 0, new Texture("Craftable_item/Fish_Smoker.png")),
    MysticTreeSeed(Map.of(() -> Acorns, 5, () -> MapleSeeds, 5, () -> PineCones, 5, () -> MahoganySeeds, 5), 100, new Texture("Craftable_item/Bomb.png"));

    private final Map<Supplier<BackPackableType>, Integer> ingredientSuppliers;
    private final int sellPrice;
    private final Texture icon;

    CraftingItemType(Map<Supplier<BackPackableType>, Integer> ingredientSuppliers, int sellPrice, Texture icon) {
        this.ingredientSuppliers = ingredientSuppliers;
        this.sellPrice = sellPrice;
        this.icon = icon;
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public double getPrice() {
        return sellPrice;
    }

    @Override
    public Texture getInventoryTexture() {
        return icon;
    }

    public static CraftingItemType getCraftingItemTypeByName(String artisanName) {
        for (CraftingItemType type : CraftingItemType.values()) {
            if (type.name().equalsIgnoreCase(artisanName)) {
                return type;
            }
        }
        return null;
    }

    public Map<BackPackableType, Integer> getIngredients() {
        return ingredientSuppliers.entrySet().stream()
            .collect(Collectors.toMap(
                e -> e.getKey().get(),
                Map.Entry::getValue
            ));
    }

}
