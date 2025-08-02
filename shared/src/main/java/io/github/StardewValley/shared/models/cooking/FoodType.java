package io.github.StardewValley.shared.models.cooking;

import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.shared.models.artisan.IngredientGroup;
import io.github.StardewValley.shared.models.backpack.BackPackableType;

import java.util.Map;

import static io.github.StardewValley.shared.models.artisan.IngredientGroup.*;
import static io.github.StardewValley.shared.models.backpack.NormalItemType.*;
import static io.github.StardewValley.shared.models.animal.AnimalProductType.*;
import static io.github.StardewValley.shared.models.artisan.ArtisanProductType.*;
import static io.github.StardewValley.shared.models.enums.FishType.*;
import static io.github.StardewValley.shared.models.plant.CropType.*;
import static io.github.StardewValley.shared.models.plant.FruitType.*;
import io.github.StardewValley.shared.models.plant.CropType;

public enum FoodType implements BackPackableType {
    FriedEgg(Map.of(Egg, 1), 50, 35,  "Recipe/Fried_Egg.png" ),
    BakedFish(Map.of(Sardine, 1, Salmon, 1, Wheat, 1), 75, 100,  "Recipe/Baked_Fish.png" ),
    Salad(Map.of(Leek, 1, Dandelion, 1), 113, 110,  "Recipe/Salad.png" ),
    Omelet(Map.of(Egg, 1, Milk, 1), 100, 125,  "Recipe/Omelet.png" ),
    PumpkinPie(Map.of(Pumpkin, 1, WheatFlour, 1, Milk, 1, Sugar, 1), 225, 385,  "Recipe/Pumpkin_Pie.png" ),
    Spaghetti(Map.of(WheatFlour, 1, Tomato, 1), 75, 120,  "Recipe/Spaghetti.png" ),
    Pizza(Map.of(WheatFlour, 1, Tomato, 1, Cheese, 1), 150, 300,  "Recipe/Pizza.png" ),
    Tortilla(Map.of(Corn, 1), 50, 50,  "Recipe/Tortilla.png" ),
    MakiRoll(Map.of(AnyFish, 1, Rice, 1, Fiber, 1), 100, 220,  "Recipe/Maki_Roll.png" ),
    TripleShotEspresso(Map.of(Coffee, 3), 200, 450,  "Recipe/Triple_Shot_Espresso.png" ),
    Cookie(Map.of(WheatFlour, 1, Sugar, 1, Egg, 1), 90, 140,  "Recipe/Cookie.png" ),
    HashBrowns(Map.of(Potato, 1, Oil, 1), 90, 120,  "Recipe/Hashbrowns.png" ),
    Pancakes(Map.of(WheatFlour, 1, Egg, 1), 90, 80,  "Recipe/Pancakes.png" ),
    FruitSalad(Map.of(Blueberry, 1, Melon, 2, Apricot, 1), 263, 450,  "Recipe/Fruit_Salad.png" ),
    RedPlate(Map.of(RedCabbage, 1, Radish, 1), 240, 400,  "Recipe/Red_Plate.png" ),
    Bread(Map.of(WheatFlour, 1), 50, 60,  "Recipe/Bread.png" ),
    SalmonDinner(Map.of(Salmon, 1, Amaranth, 1, Kale, 1), 125, 300,  "Recipe/Salmon_Dinner.png" ),
    VegetableMedley(Map.of(Tomato, 1, Beet, 1), 165, 120,  "Recipe/Vegetable_Medley.png" ),
    FarmersLunch(Map.of(Omelet, 1, Parsnip, 1), 200, 150,  "Recipe/Farmer%27s_Lunch.png" ),
    SurvivalBurger(Map.of(Bread, 1, CropType.Carrot, 1, Eggplant, 1), 125, 180,  "Recipe/Survival_Burger.png" ),
    DishOTheSea(Map.of(Sardine, 2, HashBrowns, 1), 150, 220,  "Recipe/Dish_O%27_The_Sea.png" ),
    SeafoamPudding(Map.of(Flounder, 1, MidnightCarp, 1), 175, 300,  "Recipe/Seafoam_Pudding.png" ),
    MinersTreat(Map.of(Carrot, 2, Sugar, 1, Milk, 1), 125, 200,  "Recipe/Miner%27s_Treat.png" );

    private final Map<BackPackableType, Integer> ingredients;
    private final double energy;
    private final int sellPrice;
    private final String texture;

    FoodType(Map<BackPackableType, Integer> ingredients, double energy, int sellPrice, String  texture) {
        this.ingredients = ingredients;
        this.energy = energy;
        this.sellPrice = sellPrice;
        this.texture = texture;
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
    public String getInventoryTexture() {
        return texture;
    }

    @Override
    public String getName() {
        return name();
    }
}
