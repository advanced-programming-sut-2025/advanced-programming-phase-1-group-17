package io.github.StardewValley.shared.models.cooking;

import io.github.StardewValley.shared.models.savedClasses.RefrigeratorSave;

import java.util.ArrayList;

public class Refrigerator {
    private ArrayList<Food> foods = new ArrayList<>();

    public Refrigerator() {}

    public Refrigerator(RefrigeratorSave save) {
        save.getFoods().forEach((foodSave -> {
            this.foods.add(new Food(foodSave));
        }));
    }

    public ArrayList<Food> getFoods() {
        return foods;
    }

    public void setFoods(ArrayList<Food> foods) {
        this.foods = foods;
    }
}
