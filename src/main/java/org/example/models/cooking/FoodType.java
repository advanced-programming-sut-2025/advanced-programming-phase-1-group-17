package org.example.models.cooking;

import org.example.models.BackPackableType;

public enum FoodType implements BackPackableType {
    FriedEgg,
    BakedFish,
    Salad,
    Olmelet,
    PumpkinPie,
    Spaghetti,
    Pizza,
    Tortilla,
    MakiRoll,
    TripleShotEspresso,
    Cookie,
    HashBrowns,
    Pancakes,
    FruitSalad,
    RedPlate,
    Bread,
    SalmonDinner,
    VegetableMedley,
    FarmersLunch,
    SurvivalBurger,
    DishOTheSea,
    SeafoamPudding,
    MinersTreat;

    @Override
    public String getName() {
        return name();
    }

    @Override
    public double getPrice() {
        return 0;
    }
}
