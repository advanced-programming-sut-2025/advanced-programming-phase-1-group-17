package org.example.models.Crafting;

import org.example.models.App;
import org.example.models.BackPackable;
import org.example.models.BackPackableType;
import org.example.models.Placeable;

import java.util.HashMap;

public class CraftingItem implements BackPackable, Placeable {
    private CraftingItemType targetItem;
    private HashMap<CraftingItemType, Integer> craftIngredients = new HashMap<>();

    public CraftingItemType getTargetItem() {
        return targetItem;
    }

    public void setTargetItem(CraftingItemType targetItem) {
        this.targetItem = targetItem;
    }

    public HashMap<CraftingItemType, Integer> getCraftIngredients() {
        return craftIngredients;
    }

    public void setCraftIngredients(HashMap<CraftingItemType, Integer> craftIngredients) {
        this.craftIngredients = craftIngredients;
    }


    public static CraftingItem findCraftingItemTypeByName(String CraftingItemTypeName) {
        for (CraftingItem Item : App.getCurrentGame().getCurrentPlayingPlayer().getCraftingRecipes()) {
            if (Item.getTargetItem().getName().equals(CraftingItemTypeName)) {
                return Item;
            }
        }
        return null;
    }

    public static CraftingItem findItemInBackPack(CraftingItem craftingItem) {
        for (BackPackableType backPackableType : App.getCurrentGame().getCurrentPlayingPlayer().getBackPack().getBackPackItems().keySet()) {
            if (backPackableType.equals(craftingItem.getType())) {
                return craftingItem;
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
