package io.github.StardewValley.shared.models.cooking;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.StardewValley.shared.models.backpack.BackPackable;
import io.github.StardewValley.shared.models.backpack.BackPackableType;
import io.github.StardewValley.shared.models.backpack.BackPack;
import io.github.StardewValley.shared.models.saveClasses.BackPackableSave;

import java.util.UUID;

public class Food implements BackPackable {
    private FoodType type;
    private int count;
    private Recipe recipe;
    private String id;

    public Food(FoodType foodtype){
        this.type = foodtype;
        this.id = UUID.randomUUID().toString();
    }
    public Food (){

    }
    @JsonIgnore
    public String getName() {
        return type.name();
    }

    @Override
    @JsonIgnore
    public double getPrice() {
        return type.getPrice();
    }

    @Override
    public BackPackableType getType() {
        return type;
    }

    @Override
    public BackPackableSave toBackpackableSave() {
        BackPackableSave backPackableSave = new BackPackableSave(Food.class.getSimpleName());
        backPackableSave.setFood(this);
        return backPackableSave;
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


    public void setType(FoodType type) {
        this.type = type;
    }

    public Food findFoodInBackPack(String foodName) {
        //TODO
        //BackPack backPack = App.getCurrentGame().getCurrentPlayingPlayer().getBackPack();
        BackPack backPack = null;

        for (BackPackableType backPackableType : backPack.getBackPackItems().keySet()) {
            if (backPackableType instanceof FoodType foodType) {
                Food food = (Food) backPack.getBackPackItems().get(foodType).get(0);
                if(food.getType().getName().equals(foodName)) {
                    return food;
                }
            }
        }
        return null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
