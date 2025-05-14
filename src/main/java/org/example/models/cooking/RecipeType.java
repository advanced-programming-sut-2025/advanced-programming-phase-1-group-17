package org.example.models.cooking;

import org.example.models.BackPackableType;
import org.example.models.NormalItemType;
import org.example.models.crafting.CraftingItemType;

public enum RecipeType implements BackPackableType {

    FriedEgg(FoodType.FriedEgg, 0),
    BakedFish(FoodType.BakedFish, 0),
    Salad(FoodType.Salad, 0),
    Olmelet(FoodType.Omelet, 0),
    PumpkinPie(FoodType.PumpkinPie, 0),
    Spaghetti(FoodType.Spaghetti, 0),
    Pizza(FoodType.Pizza, 0),
    Tortilla(FoodType.Tortilla, 0),
    MakiRoll(FoodType.MakiRoll, 0),
    TripleShotEspresso(FoodType.TripleShotEspresso, 0),
    Cookie(FoodType.Cookie, 0),
    HashBrowns(FoodType.HashBrowns, 0),
    Pancakes(FoodType.Pancakes, 0),
    FruitSalad(FoodType.FruitSalad, 0),
    RedPlate(FoodType.RedPlate, 0),
    Bread(FoodType.Bread, 0),
    SalmonDinner(FoodType.SalmonDinner, 0),
    VegetableMedley(FoodType.VegetableMedley, 0),
    FarmersLunch(FoodType.FarmersLunch, 0),
    SurvivalBurger(FoodType.SurvivalBurger, 0),
    DishOTheSea(FoodType.DishOTheSea, 0),
    SeafoamPudding(FoodType.SeafoamPudding, 0),

    MinersTreat(FoodType.MinersTreat, 0),

    //Crafting Item Recipes
    FishSmoker(CraftingItemType.FishSmoker, 5000), //In Fish Shop
    Dehydrator(CraftingItemType.Dehydrator, 5000), //In Pierre
    GrassStarter(NormalItemType.GrassStarter, 500); //In Pierre


    private final BackPackableType backPackableType;
    private final double price;

    RecipeType(BackPackableType foodType, double price) {
        this.backPackableType = foodType;
        this.price = price;
    }

    public BackPackableType getBackPackableType() {
        return backPackableType;
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public double getPrice() {
        //TODO
        return price;
    }
}
