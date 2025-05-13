package org.example.models.cooking;

import org.example.models.App;

import java.util.ArrayList;

public class Recipe {
    private RecipeType type;
    private String name;
    private Food foodToBeCooked;
    private ArrayList<Food> ingredients = new ArrayList<>();

    public Recipe(RecipeType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Food getFoodToBeCooked() {
        return foodToBeCooked;
    }

    public void setFoodToBeCooked(Food foodToBeCooked) {
        this.foodToBeCooked = foodToBeCooked;
    }

    public ArrayList<Food> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Food> ingredients) {
        this.ingredients = ingredients;
    }

    public static Recipe findRecipe(String recipeName) {
        for(Recipe recipe : App.getCurrentGame().getCurrentPlayingPlayer().getRecipes()){
            if(recipe.getName().equals(recipeName)){
                return recipe;
            }
        }
        return null;
    }
}
