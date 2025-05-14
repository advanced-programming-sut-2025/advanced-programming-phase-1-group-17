package org.example.models.artisan;

import org.example.models.BackPackableType;
import org.example.models.NormalItemType;
import org.example.models.crafting.CraftingItemType;
import org.example.models.animal.AnimalProductType;
import org.example.models.foraging.MineralType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum ArtisanProductType implements BackPackableType {
    Honey("Honey",  75, 4, 0,
            mapOf(), 350, CraftingItemType.BeeHouse),

    Cheese("Cheese", 100, 0, 3,
            mapOf(entry(IngredientGroup.MilkOrLargeMilk, 1)), 230, CraftingItemType.CheesePress),

    Cloth("Cloth", -1, 0, 4, mapOf(entry(AnimalProductType.Wool, 1)),
            470, CraftingItemType.Loom),

    GoatCheese("Goat Cheese",  100, 0, 3,
            mapOf(entry(IngredientGroup.GoatMilkOrLargeGoatMilk, 1)), 400, CraftingItemType.CheesePress),

    Mayonnaise("Mayonnaise", 50, 0, 3,
            mapOf(entry(IngredientGroup.EggOrLargeEgg, 1)), 190, CraftingItemType.MayonnaiseMachine),

    DyckMayonnaise("Duck Mayonnaise", 75, 0, 3,
            mapOf(entry(AnimalProductType.DuckEgg, 1)), 375, CraftingItemType.MayonnaiseMachine),

    DinosaurMayonnaise("Dinosaur Mayonnaise", 125, 0, 3,
            mapOf(entry(AnimalProductType.DinosaurEgg, 1)), 800, CraftingItemType.MayonnaiseMachine),

    Beer("Beer", 50, 1, 0,
            mapOf(entry(org.example.models.plant.CropType.Wheat, 1)), 200, CraftingItemType.Keg),

    Coffee("Coffee", 75, 0, 2,
            mapOf(entry(org.example.models.plant.CropType.CoffeeBean, 5)), 150, CraftingItemType.Keg),

    Coal("Coal", -1, 0, 1,
            mapOf(entry(NormalItemType.Wood, 10)), 50, CraftingItemType.CharcoalKlin),

    Mead("Mead", 100, 0, 10,
            mapOf(entry(Honey, 1)), 300, CraftingItemType.Keg),

    PaleAle("Pale Ale", 50, 3, 0,
            mapOf(entry(org.example.models.plant.CropType.Hops, 1)), 300, CraftingItemType.Keg),

    Wine("Wine", 0, 7, 0,
            mapOf(entry(IngredientGroup.AnyFruit, 1)), 0, CraftingItemType.Keg), // dynamic price

    Vinegar("Vinegar", 13, 0, 10,
            mapOf(entry(NormalItemType.Rice, 1)), 100, CraftingItemType.Keg),

    Oil("Oil", 13, 2, 0,
            mapOf(entry(org.example.models.plant.CropType.Corn, 1)), 100, CraftingItemType.OilMaker),

    TruffleOil("Truffle Oil", 38, 0, 6,
            mapOf(entry(AnimalProductType.Truffle, 1)), 1065, CraftingItemType.OilMaker),

    Juice("Juice",0, 4, 0,
            mapOf(entry(IngredientGroup.AnyVegetable, 1)), 0, CraftingItemType.Keg), // dynamic price

    Pickles("Pickles", 0, 0, 6,
            mapOf(entry(IngredientGroup.AnyVegetable, 1)), 0, CraftingItemType.PreservesJar), // dynamic price

    Jelly("Jelly",0, 3, 0,
            mapOf(entry(IngredientGroup.AnyFruit, 1)), 0, CraftingItemType.PreservesJar), // dynamic price

    DriedFruit("Dried Fruit",75, 0, 0,
            mapOf(entry(IngredientGroup.AnyFruit, 1)), 0, CraftingItemType.Dehydrator), // dynamic price

    DriedMushrooms("Dried Mushrooms",50, 0, 0,
            mapOf(entry(IngredientGroup.AnyMushroom, 1)), 0, CraftingItemType.Dehydrator), // dynamic price

    Raisins("Raisins",125, 0, 0,
            mapOf(entry(IngredientGroup.Grapes, 1)), 600, CraftingItemType.Dehydrator),

    SmokedFish("Smoked Fish",0, 0, 1,
            mapOf(entry(IngredientGroup.AnyFish, 1)), 0, CraftingItemType.FishSmoker), // dynamic price

    IridiumBar("Iridium Bar", -1, 0, 4,
            mapOf(entry(MineralType.IridiumOre, 5), entry(MineralType.Coal, 1)), 1000, CraftingItemType.Furnace),

    GoldBar("Gold Bar", -1, 0, 4,
            mapOf(entry(MineralType.GoldOre, 5), entry(MineralType.Coal, 1)), 250, CraftingItemType.Furnace),

    IronBar("Iron Bar", -1, 0, 4,
            mapOf(entry(MineralType.IronOre, 5), entry(MineralType.Coal, 1)), 100, CraftingItemType.Furnace),

    CopperBar("Copper Bar", -1, 0, 4,
            mapOf(entry(MineralType.CopperOre, 5), entry(MineralType.Coal, 1)), 50, CraftingItemType.Furnace);

    private final String name;
    private final int energy;
    private final int processingDays;
    private final int processingHours;
    private final Map<Object, Integer> ingredients;
    private final double sellPrice;
    private final CraftingItemType artisan;

    ArtisanProductType(String name, int energy, int days, int hours,
                       Map<Object, Integer> ingredients, double sellPrice, CraftingItemType artisan) {
        if (artisan == null) throw new IllegalArgumentException("Artisan cannot be null for " + name);
        this.name = name;
        this.energy = energy;
        this.processingDays = days;
        this.processingHours = hours;
        this.ingredients = ingredients;
        this.sellPrice = sellPrice;
        this.artisan = artisan;
    }

    public int getEnergy() {
        return energy;
    }

    public int getProcessingDays() {
        return processingDays;
    }

    public int getProcessingHours() {
        return processingHours;
    }

    public Map<Object, Integer> getIngredients() {
        return ingredients;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public CraftingItemType getArtisan() {
        return artisan;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return getSellPrice();
    }

    // Utility methods
    @SafeVarargs
    private static Map<Object, Integer> mapOf(Map.Entry<Object, Integer>... entries) {
        Map<Object, Integer> map = new HashMap<>();
        for (Map.Entry<Object, Integer> entry : entries) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    public static Map.Entry<Object, Integer> entry(Object key, Integer value) {
        return Map.entry(key, value);
    }

    private static Map<Object, Integer> mapOf() {
        return Collections.emptyMap();
    }
}
