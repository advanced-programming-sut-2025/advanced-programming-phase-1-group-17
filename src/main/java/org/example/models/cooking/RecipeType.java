package org.example.models.cooking;

import org.example.models.BackPackableType;

public enum RecipeType implements BackPackableType {
    FriedEggRecipe(FoodType.FriedEgg, 0),
    Recipe(FoodType.BakedFish, 0),
    SaladRecipe(FoodType.Salad, 0),
    OmeletRecipe(FoodType.Omelet, 0),
    PumpkinPieRecipe(FoodType.PumpkinPie, 0),
    SpaghettiRecipe(FoodType.Spaghetti, 0),
    PizzaRecipe(FoodType.Pizza, 0),
    TortillaRecipe(FoodType.Tortilla, 0),
    MakiRollRecipe(FoodType.MakiRoll, 0),
    TripleShotEspressoRecipe(FoodType.TripleShotEspresso, 0),
    CookieRecipe(FoodType.Cookie, 0),
    HashBrownsRecipe(FoodType.HashBrowns, 0),
    PancakesRecipe(FoodType.Pancakes, 0),
    FruitSaladRecipe(FoodType.FruitSalad, 0),
    RedPlateRecipe(FoodType.RedPlate, 0),
    BreadRecipe(FoodType.Bread, 0),
    SalmonDinnerRecipe(FoodType.SalmonDinner, 0),
    VegetableMedleyRecipe(FoodType.VegetableMedley, 0),
    FarmersLunchRecipe(FoodType.FarmersLunch, 0),
    SurvivalBurgerRecipe(FoodType.SurvivalBurger, 0),
    DishOTheSeaRecipe(FoodType.DishOTheSea, 0),
    SeafoamPuddingRecipe(FoodType.SeafoamPudding, 0),

    MinersTreatRecipe(FoodType.MinersTreat, 0);


    private final FoodType foodType;
    private final double price;

    RecipeType(FoodType foodType, double price) {
        this.foodType = foodType;
        this.price = price;
    }

    public FoodType getFoodType() {
        return foodType;
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
