package io.github.StardewValley.shared.models.saveClasses;

import io.github.StardewValley.shared.models.artisan.ArtisanProduct;
import io.github.StardewValley.shared.models.crafting.CraftingItem;
import io.github.StardewValley.shared.models.crafting.CraftingItemType;

public class CraftingItemSave {
    private CraftingItemType type;
    private ArtisanProduct artisanProductInProgress;
    private int start_x;
    private int start_y;
    private int width;
    private int height;

    private String ownerUsername;

    public CraftingItemSave () {}

    public CraftingItemSave(CraftingItem craftingItem) {
        this.type = craftingItem.getCraftingItemType();
        this.artisanProductInProgress = craftingItem.getArtisanProductInProgress() == null ? null :
            craftingItem.getArtisanProductInProgress();
        this.start_x = craftingItem.getStart_x();
        this.start_y = craftingItem.getStart_y();
        this.width = craftingItem.getWidth();
        this.height = craftingItem.getHeight();

        this.ownerUsername = craftingItem.getOwner().getUser().getUsername();
    }

    public CraftingItemType getType() {
        return type;
    }

    public ArtisanProduct getArtisanProductInProgress() {
        return artisanProductInProgress;
    }

    public int getStart_x() {
        return start_x;
    }

    public int getStart_y() {
        return start_y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setType(CraftingItemType type) {
        this.type = type;
    }

    public void setArtisanProductInProgress(ArtisanProduct artisanProductInProgress) {
        this.artisanProductInProgress = artisanProductInProgress;
    }

    public void setStart_x(int start_x) {
        this.start_x = start_x;
    }

    public void setStart_y(int start_y) {
        this.start_y = start_y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }
}
