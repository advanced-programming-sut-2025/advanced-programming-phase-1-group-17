package org.example.models.crafting;

import org.example.models.App;
import org.example.models.BackPackable;
import org.example.models.BackPackableType;
import org.example.models.Placeable;

import java.util.HashMap;

public class CraftingItem implements BackPackable , Placeable {
    private ItemType targetItem;
    private HashMap<ItemType,Integer> craftIngredients = new HashMap<>();

    public ItemType getTargetItem() {
        return targetItem;
    }

    public void setTargetItem(ItemType targetItem) {
        this.targetItem = targetItem;
    }

    public HashMap<ItemType, Integer> getCraftIngredients() {
        return craftIngredients;
    }

    public void setCraftIngredients(HashMap<ItemType, Integer> craftIngredients) {
        this.craftIngredients = craftIngredients;
    }


    public static CraftingItem findItemTypeByName(String itemTypeName) {
        for(CraftingItem recipe : App.getCurrentGame().getCurrentPlayingPlayer().getCraftingRecipes()){
            if(recipe.getTargetItem().getName().equals(itemTypeName)){
                return recipe;
            }
        }
        return null;
}

    @Override
    public String getName() {
        return "";
    }

    @Override
    public double getPrice() {
        return 0;
    }

    @Override
    public BackPackableType getType() {
        return null;
    }
}
