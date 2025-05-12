package org.example.models.crafting;

import org.example.models.App;
import org.example.models.BackPackable;
import org.example.models.BackPackableType;
import org.example.models.Placeable;
public class CraftingItem implements BackPackable, Placeable {
    private CraftingItemType targetItem;
    public CraftingItem(CraftingItemType targetItem) {
        this.targetItem = targetItem;
    }

    public CraftingItemType getTargetItem() {
        return targetItem;
    }

    public void setTargetItem(CraftingItemType targetItem) {
        this.targetItem = targetItem;
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
        return targetItem.getName();
    }

    @Override
    public double getPrice() {
        return targetItem.getPrice();
    }

    @Override
    public BackPackableType getType() {
        return targetItem;
    }
}