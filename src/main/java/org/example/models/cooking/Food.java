package org.example.models.cooking;

import org.example.models.App;
import org.example.models.BackPackable;
import org.example.models.BackPackableType;
import org.example.models.tools.BackPack;

public class Food implements BackPackable {
    private FoodType foodtype;
    private int count;
    private Recipe recipe;

    public String getName() {
        return foodtype.name();
    }

    @Override
    public double getPrice() {
        return 0;
    }

    @Override
    public BackPackableType getType() {
        return null;
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public FoodType getFoodtype() {
        return foodtype;
    }

    public void setFoodtype(FoodType foodtype) {
        this.foodtype = foodtype;
    }

    public Food findFoodInBackPack(String foodName) {
        BackPack backPack = App.getCurrentGame().getCurrentPlayingPlayer().getBackPack();

        for (BackPackableType backPackableType : backPack.getBackPackItems().keySet()) {
            if (backPackableType instanceof FoodType foodType) {
                Food food = (Food) backPack.getBackPackItems().get(foodType).get(0);
                if(food.getFoodtype().getName().equals(foodName)) {
                    return food;
                }
            }
        }
        return null;
    }
}
