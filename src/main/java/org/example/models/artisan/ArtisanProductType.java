package org.example.models.artisan;

import org.example.models.BackPackableType;
import org.example.models.crafting.CraftingItemType;
import org.example.models.animal.AnimalProductType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum ArtisanProductType implements BackPackableType {
    Honey("Honey", "It's a sweet syrup produced by bees.", 75, 4, 0,
            mapOf(), 350, CraftingItemType.BeeHouse),

    Cheese("Cheese", "It's your basic cheese.", 100, 0, 3,
            mapOf(entry(IngredientGroup.MilkOrLargeMilk, 1)), 230, CraftingItemType.CheesePress),

    GoatCheese("Goat Cheese", "Soft cheese made from goat's milk.", 100, 0, 3,
            mapOf(entry(IngredientGroup.GoatMilkOrLargeGoatMilk, 1)), 400, CraftingItemType.CheesePress),

    Mayonnaise("Mayonnaise", "It looks spreadable.", 50, 0, 3,
            mapOf(entry(IngredientGroup.EggOrLargeEgg, 1)), 190, CraftingItemType.MayonnaiseMachine),

    DyckMayonnaise("Duck Mayonnaise", "It's a rich, yellow mayonnaise.", 75, 0, 3,
            mapOf(entry(AnimalProductType.DuckEgg, 1)), 375, CraftingItemType.MayonnaiseMachine),

    DinosaurMayonnaise("Dinosaur Mayonnaise", "It's thick and creamy, with a vivid green hue.", 125, 0, 3,
            mapOf(entry(AnimalProductType.DinosaurEgg, 1)), 800, CraftingItemType.MayonnaiseMachine),

    Beer("Beer", "Drink in moderation.", 50, 1, 0,
            mapOf(entry(org.example.models.plant.CropType.Wheat, 1)), 200, CraftingItemType.Keg),

    Coffee("Coffee", "It smells delicious. This is sure to give you a boost.", 75, 0, 2,
            mapOf(entry(org.example.models.plant.CropType.CoffeeBean, 5)), 150, CraftingItemType.Keg),

    Mead("Mead", "A fermented beverage made from honey.", 100, 0, 10,
            mapOf(entry(Honey, 1)), 300, CraftingItemType.Keg),

    PaleAle("Pale Ale", "Drink in moderation.", 50, 3, 0,
            mapOf(entry(org.example.models.plant.CropType.Hops, 1)), 300, CraftingItemType.Keg),

    Wine("Wine", "Drink in moderation.", 0, 7, 0,
            mapOf(entry(IngredientGroup.AnyFruit, 1)), 0, CraftingItemType.Keg), // dynamic price
//TODO: find out what Rice is
//    Vinegar("Vinegar", "An aged fermented liquid.", 13, 0, 10,
//            mapOf(entry(CraftingItemType.Rice, 1)), 100, CraftingItemType.Keg),

    Oil("Oil", "All purpose cooking oil.", 13, 0, 6,
            mapOf(entry(org.example.models.plant.CropType.Corn, 1)), 100, CraftingItemType.OilMaker),

    TruffleOil("Truffle Oil", "A gourmet cooking ingredient.", 38, 0, 6,
            mapOf(entry(AnimalProductType.Truffle, 1)), 1065, CraftingItemType.OilMaker),

    Juice("Juice", "A sweet, nutritious beverage.", 0, 4, 0,
            mapOf(entry(IngredientGroup.AnyVegetable, 1)), 0, CraftingItemType.Keg), // dynamic price

    Pickles("Pickles", "A jar of your home-made pickles.", 0, 0, 6,
            mapOf(entry(IngredientGroup.AnyVegetable, 1)), 0, CraftingItemType.PreservesJar), // dynamic price

    Jelly("Jelly", "Gooey.", 0, 3, 0,
            mapOf(entry(IngredientGroup.AnyFruit, 1)), 0, CraftingItemType.PreservesJar), // dynamic price

    DriedFruit("Dried Fruit", "Chewy pieces of dried fruit.", 75, 0, 0,
            mapOf(entry(IngredientGroup.AnyFruit, 1)), 0, CraftingItemType.Dehydrator), // dynamic price

    DriedMushrooms("Dried Mushrooms", "A package of gourmet mushrooms.", 50, 0, 0,
            mapOf(entry(IngredientGroup.AnyMushroom, 1)), 0, CraftingItemType.Dehydrator), // dynamic price

    Raisins("Raisins", "It's said to be the Junimos' favorite food.", 125, 0, 0,
            mapOf(entry(IngredientGroup.Grapes, 1)), 600, CraftingItemType.Dehydrator),

    SmokedFish("Smoked Fish", "A whole fish, smoked to perfection.", 0, 0, 1,
            mapOf(entry(IngredientGroup.AnyFish, 1)), 0, CraftingItemType.FishSmoker); // dynamic price

    private final String name;
    private final String description;
    private final int energy;
    private final int processingDays;
    private final int processingHours;
    private final Map<Object, Integer> ingredients;
    private final double sellPrice;
    private final CraftingItemType artisan;

    ArtisanProductType(String name, String description, int energy, int days, int hours,
                       Map<Object, Integer> ingredients, double sellPrice, CraftingItemType artisan) {
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
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
