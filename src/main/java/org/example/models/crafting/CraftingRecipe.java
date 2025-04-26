package org.example.models.crafting;

import org.example.models.BackPackable;

import java.util.ArrayList;
import java.util.HashMap;

public class CraftingRecipe {
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
}
