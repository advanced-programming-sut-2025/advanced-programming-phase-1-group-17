package io.github.StardewValley.shared.models.savedClasses;

import io.github.StardewValley.shared.models.cooking.Refrigerator;

import java.util.ArrayList;
import java.util.List;

public class RefrigeratorSave {
    private List<FoodSave> foods;

    public RefrigeratorSave() {}

    public RefrigeratorSave(Refrigerator refrigerator) {
        this.foods = new ArrayList<>();
        refrigerator.getFoods().forEach((food -> {
            this.foods.add(new FoodSave(food));
        }));
    }

    public List<FoodSave> getFoods() {
        return foods;
    }
}
