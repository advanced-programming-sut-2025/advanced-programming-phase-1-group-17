package org.example.models.crafting;

import org.example.models.BackPackableType;
import org.example.models.artisan.ArtisanProductType;

import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.example.models.NormalItemType.*;
import static org.example.models.foraging.MineralType.*;
import static org.example.models.plant.SaplingType.*;

public enum CraftingItemType implements BackPackableType {
    CherryBomb(Map.of(() -> CopperOre, 4, () -> Coal, 1), 50),
    Bomb(Map.of(() -> IronOre, 4, () -> Coal, 1), 50),
    MegaBomb(Map.of(() -> GoldOre, 4, () -> Coal, 1), 50),
    Sprinkler(Map.of(() -> ArtisanProductType.CopperBar, 1, () -> ArtisanProductType.IronBar, 1), 0),
    QualitySprinkler(Map.of(() -> ArtisanProductType.IronBar, 1, () -> ArtisanProductType.GoldBar, 1), 0),
    IridiumSprinkler(Map.of(() -> ArtisanProductType.GoldBar, 1, () -> ArtisanProductType.IridiumBar, 1), 0),
    CharcoalKlin(Map.of(() -> Wood, 20, () -> ArtisanProductType.CopperBar, 2), 0),
    Furnace(Map.of(() -> CopperOre, 20, () -> Stone, 25), 0),
    Scarecrow(Map.of(() -> Wood, 50, () -> Coal, 1, () -> Fiber, 20), 0),
    DeluxeScarecrow(Map.of(() -> Wood, 50, () -> Coal, 1, () -> Fiber, 20, () -> IridiumOre, 1), 0),
    BeeHouse(Map.of(() -> Wood, 40, () -> Coal, 8, () -> ArtisanProductType.IronBar, 1), 0),
    CheesePress(Map.of(() -> Wood, 45, () -> Stone, 45, () -> ArtisanProductType.CopperBar, 1), 0),
    Keg(Map.of(() -> Wood, 30, () -> ArtisanProductType.CopperBar, 1, () -> ArtisanProductType.IronBar, 1), 0),
    Loom(Map.of(() -> Wood, 60, () -> Fiber, 30), 0),
    MayonnaiseMachine(Map.of(() -> Wood, 15, () -> Stone, 15, () -> ArtisanProductType.CopperBar, 1), 0),
    OilMaker(Map.of(() -> Wood, 100, () -> ArtisanProductType.GoldBar, 1, () -> ArtisanProductType.IronBar, 1), 0),
    PreservesJar(Map.of(() -> Wood, 50, () -> Stone, 40, () -> Coal, 8), 0),
    Dehydrator(Map.of(() -> Wood, 30, () -> Stone, 20, () -> Fiber, 30), 0),
    //TODO: Complete Grass Starter(Merge Conflict)
    GrassStarter(Map.of(), 100),
    FishSmoker(Map.of(() -> Wood, 50, () -> ArtisanProductType.IronBar, 3, () -> Coal, 10), 0),
    MysticTreeSeed(Map.of(() -> Acorns, 5, () -> MapleSeeds, 5, () -> PineCones, 5, () -> MahoganySeeds, 5), 100);

    private final Map<Supplier<BackPackableType>, Integer> ingredientSuppliers;
    private final int sellPrice;

    CraftingItemType(Map<Supplier<BackPackableType>, Integer> ingredientSuppliers, int sellPrice) {
        this.ingredientSuppliers = ingredientSuppliers;
        this.sellPrice = sellPrice;
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public double getPrice() {
        return sellPrice;
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
