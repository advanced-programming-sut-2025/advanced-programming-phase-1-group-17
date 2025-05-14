package org.example.models.cooking;

import org.example.models.BackPackableType;

import java.util.Map;

import static org.example.models.NormalItemType.*;
import static org.example.models.animal.AnimalProductType.*;
import static org.example.models.artisan.ArtisanProductType.*;
import static org.example.models.enums.FishType.*;
import static org.example.models.plant.CropType.*;
import static org.example.models.plant.FruitType.*;

public enum FoodType implements BackPackableType {
    FriedEgg(Map.of(Egg, 1), 50, 35),
    BakedFish(Map.of(Sardine, 1, Salmon, 1, Wheat, 1), 75, 100),
    Salad(Map.of(Leek, 1, Dandelion, 1), 113, 110),
    Omelet(Map.of(Egg, 1, Milk, 1), 100, 125),
    PumpkinPie(Map.of(Pumpkin, 1, WheatFlour, 1, Milk, 1, Sugar, 1), 225, 385),
    Spaghetti(Map.of(WheatFlour, 1, Tomato, 1), 75, 120),
    Pizza(Map.of(WheatFlour, 1, Tomato, 1, Cheese, 1), 150, 300),
    Tortilla(Map.of(Corn, 1), 50, 50),
    MakiRoll(Map.of(AnyFish, 1, Rice, 1, Fiber, 1), 100, 220),
    TripleShotEspresso(Map.of(Coffee, 3), 200, 450),
    Cookie(Map.of(WheatFlour, 1, Sugar, 1, Egg, 1), 90, 140),
    HashBrowns(Map.of(Potato, 1, Oil, 1), 90, 120),
    Pancakes(Map.of(WheatFlour, 1, Egg, 1), 90, 80),
    FruitSalad(Map.of(Blueberry, 1, Melon, 2, Apricot, 1), 263, 450),
    RedPlate(Map.of(RedCabbage, 1, Radish, 1), 240, 400),
    Bread(Map.of(WheatFlour, 1), 50, 60),
    SalmonDinner(Map.of(Salmon, 1, Amaranth, 1, Kale, 1), 125, 300),
    VegetableMedley(Map.of(Tomato, 1, Beet, 1), 165, 120),
    FarmersLunch(Map.of(Omelet, 1, Parsnip, 1), 200, 150),
    SurvivalBurger(Map.of(Bread, 1, Carrot, 1, Eggplant, 1), 125, 180),
    DishOTheSea(Map.of(Sardine, 2, HashBrowns, 1), 150, 220),
    SeafoamPudding(Map.of(Flounder, 1, MidnightCarp, 1), 175, 300),
    MinersTreat(Map.of(Carrot, 2, Sugar, 1, Milk, 1), 125, 200);

    private final Map<BackPackableType, Integer> ingredients;
    private final double energy;
    private final int sellPrice;

    FoodType(Map<BackPackableType, Integer> ingredients, double energy, int sellPrice) {
        this.ingredients = ingredients;
        this.energy = energy;
        this.sellPrice = sellPrice;
    }

    public Map<BackPackableType, Integer> getIngredients() {
        return ingredients;
    }

    public double getEnergy() {
        return energy;
    }

    @Override
    public double getPrice() {
        return sellPrice;
    }

    @Override
    public String getName() {
        return name();
    }
}
