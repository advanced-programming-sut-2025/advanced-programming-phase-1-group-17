package org.example.models.cooking;

import org.example.models.BackPackableType;

public enum RecipeType implements BackPackableType {

    FriedEgg(FoodType.FriedEgg),
    BakedFish(FoodType.BakedFish),
    Salad(FoodType.Salad),
    Olmelet(FoodType.Olmelet),
    PumpkinPie(FoodType.PumpkinPie),
    Spaghetti(FoodType.Spaghetti),
    Pizza(FoodType.Pizza),
    Tortilla(FoodType.Tortilla),
    MakiRoll(FoodType.MakiRoll),
    TripleShotEspresso(FoodType.TripleShotEspresso),
    Cookie(FoodType.Cookie),
    HashBrowns(FoodType.HashBrowns),
    Pancakes(FoodType.Pancakes),
    FruitSalad(FoodType.FruitSalad),
    RedPlate(FoodType.RedPlate),
    Bread(FoodType.Bread),
    SalmonDinner(FoodType.SalmonDinner),
    VegetableMedley(FoodType.VegetableMedley),
    FarmersLunch(FoodType.FarmersLunch),
    SurvivalBurger(FoodType.SurvivalBurger),
    DishOTheSea(FoodType.DishOTheSea),
    SeafoamPudding(FoodType.SeafoamPudding),

    MinersTreat(FoodType.MinersTreat);

    private final FoodType foodType;

    RecipeType(FoodType foodType) {
        this.foodType = foodType;
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
        return 0;
    }
}
