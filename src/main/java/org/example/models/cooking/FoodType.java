package org.example.models.cooking;

import org.example.models.BackPackableType;

public enum FoodType implements BackPackableType {
    friedEgg,
    bakedFish,
    salad,
    olmelet,
    pumpkinPie,
    spaghetti,
    pizza,
    tortilla,
    makiRoll,
    tripleShotEspresso,
    cookie,
    hashBrowns,
    pancakes,
    fruitSalad,
    redPlate,
    bread,
    salmonDinner,
    vegetableMedley,
    farmersLunch,
    survivalBurger,
    dishOTheSea,
    seafoamPudding,
    minersTreat;

    @Override
    public String getName() {
        return "";
    }

    @Override
    public double getPrice() {
        return 0;
    }
}
