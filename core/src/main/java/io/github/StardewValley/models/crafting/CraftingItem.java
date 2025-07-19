package io.github.StardewValley.models.crafting;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import io.github.StardewValley.GameAssetManager;
import io.github.StardewValley.Main;
import io.github.StardewValley.models.*;
import io.github.StardewValley.models.artisan.ArtisanProduct;

import java.util.ArrayList;
import java.util.HashMap;

public class CraftingItem implements BackPackable, Placeable {
    private static HashMap<CraftingItem, Rectangle> craftingItemBounds = new HashMap<>();
    private static ArrayList<CraftingItem> allCraftingItems = new ArrayList<>();
    private CraftingItemType targetItem;
    private ArtisanProduct artisanProductInProgress = null;

    private final Player owner;
    private int start_x = 0;
    private int start_y = 0;
    private int width;
    private int height;

    public CraftingItem(CraftingItemType targetItem, Player owner) {
        this.owner = owner;
        this.targetItem = targetItem;
        this.width = targetItem.getInventoryTexture().getWidth();
        this.height = targetItem.getInventoryTexture().getHeight();

        allCraftingItems.add(this);
        craftingItemBounds.put(this, new Rectangle(
            start_x * GameAssetManager.getGameAssetManager().getTileWidth(),
            start_y * GameAssetManager.getGameAssetManager().getTileHeight(),
            width * GameAssetManager.getGameAssetManager().getTileWidth(),
            height * GameAssetManager.getGameAssetManager().getTileHeight()
        ));
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

    public static HashMap<CraftingItem, Rectangle> getCraftingItemBounds() {
        return craftingItemBounds;
    }

    public static ArrayList<CraftingItem> getAllCraftingItems() {
        return allCraftingItems;
    }

    public Player getOwner() {
        return owner;
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

    @Override
    public Texture getTexture() {
        return targetItem.getInventoryTexture();
    }
}
