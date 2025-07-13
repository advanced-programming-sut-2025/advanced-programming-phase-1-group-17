package org.example.models.cooking;

import org.example.models.App;

import java.util.ArrayList;

public class Recipe {
    private FoodType foodToBeCooked;

    public Recipe(FoodType foodToBeCooked) {
        this.foodToBeCooked = foodToBeCooked;
    }

    public FoodType getFoodToBeCooked() {
        return foodToBeCooked;
    }

    public void setFoodToBeCooked(FoodType foodToBeCooked) {
        this.foodToBeCooked = foodToBeCooked;
    }

    public static Recipe findRecipe(String recipeName) {
        for(Recipe recipe : App.getCurrentGame().getCurrentPlayingPlayer().getRecipes()){
            if(recipe.getFoodToBeCooked().name().equals(recipeName)){
                return recipe;
            }
        }
        return null;
    }
}
