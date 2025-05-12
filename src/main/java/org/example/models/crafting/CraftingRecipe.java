package org.example.models.crafting;

import org.example.models.App;

public class CraftingRecipe {
    private CraftingItemType targetItem;
    public CraftingRecipe(CraftingItemType targetItem) {
        this.targetItem = targetItem;
    }

    public CraftingItemType getTargetItem() {
        return targetItem;
    }

    public void setTargetItem(CraftingItemType targetItem) {
        this.targetItem = targetItem;
    }
    public static CraftingRecipe findRecipe(String recipeName) {
        for(CraftingRecipe craftingRecipe : App.getCurrentGame().getCurrentPlayingPlayer().getCraftingRecipes()) {
            if(craftingRecipe.getTargetItem().name().equals(recipeName)) {
                return craftingRecipe;
            }
        }
        return null;
    }
}
