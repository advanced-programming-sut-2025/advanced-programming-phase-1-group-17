package org.example.models.cooking;

import java.util.ArrayList;

public class Refrigerator {
    private ArrayList<Food> foods = new ArrayList<>();

    public ArrayList<Food> getFoods() {
        return foods;
    }

    public void setFoods(ArrayList<Food> foods) {
        this.foods = foods;
    }
}
