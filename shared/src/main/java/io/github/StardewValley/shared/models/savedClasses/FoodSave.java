package io.github.StardewValley.shared.models.savedClasses;

import io.github.StardewValley.shared.models.cooking.Food;
import io.github.StardewValley.shared.models.cooking.FoodType;

public class FoodSave {
    private FoodType foodtype;
    private int count;

    public FoodSave() {}

    public FoodSave(Food food) {
        this.foodtype = food.getFoodtype();
        this.count = food.getCount();
    }

    public FoodType getFoodtype() {
        return foodtype;
    }

    public int getCount() {
        return count;
    }
}
