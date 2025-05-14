package org.example.models.crafting;

import org.example.models.App;
import org.example.models.BackPackable;
import org.example.models.BackPackableType;
import org.example.models.Placeable;
import org.example.models.artisan.ArtisanProduct;
import org.example.models.artisan.ArtisanProductType;

import java.util.ArrayList;

public class CraftingItem implements BackPackable, Placeable {
    private CraftingItemType targetItem;
    private ArtisanProduct artisanProductInProgress = null;
    private static ArrayList<ArtisanProduct> allArtisanProductsInProgress = new ArrayList<>();

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

    public ArtisanProduct getArtisanProductInProgress() {
        return artisanProductInProgress;
    }

    public void setArtisanProductInProgress(ArtisanProduct artisanProductInProgress) {
        this.artisanProductInProgress = artisanProductInProgress;
    }

    public static ArrayList<ArtisanProduct> getAllArtisanProductsInProgress() {
        return allArtisanProductsInProgress;
    }
}