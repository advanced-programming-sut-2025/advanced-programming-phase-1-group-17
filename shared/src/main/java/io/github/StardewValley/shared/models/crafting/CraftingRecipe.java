package io.github.StardewValley.shared.models.crafting;

import io.github.StardewValley.shared.models.App;
import io.github.StardewValley.shared.models.Player;

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
    public static CraftingRecipe findRecipe(String recipeName, Player player) {
        for(CraftingRecipe craftingRecipe : player.getCraftingRecipes()) {
            if(craftingRecipe.getTargetItem().name().equals(recipeName)) {
                return craftingRecipe;
            }
        }
        return null;
    }
}
