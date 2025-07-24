package io.github.StardewValley.shared.models.crafting;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.shared.models.*;
import io.github.StardewValley.shared.models.artisan.ArtisanProduct;

import java.util.ArrayList;
import java.util.HashMap;

public class CraftingItem implements BackPackable, Placeable {
    private static HashMap<CraftingItem, Rectangle>  craftingItemBounds = new HashMap<>();
    private static ArrayList<CraftingItem> allCraftingItems = new ArrayList<>();
    private CraftingItemType targetItem;
    private ArtisanProduct artisanProductInProgress = null;
    private transient ProgressBar progressBar;

    private final Player owner;
    private int start_x = 0;
    private int start_y = 0;
    private final int width;
    private final int height;

    public CraftingItem(CraftingItemType targetItem, Player owner) {
        this.owner = owner;
        this.targetItem = targetItem;
        this.width = targetItem.getInventoryTexture().getWidth();
        this.height = targetItem.getInventoryTexture().getHeight();

        allCraftingItems.add(this);
    }

    public CraftingItemType getTargetItem() {
        return targetItem;
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

    public void setStart_x(int start_x) {
        this.start_x = start_x;
    }

    public void setStart_y(int start_y) {
        this.start_y = start_y;
    }

    public void addCraftingItemBound() {
        craftingItemBounds.put(this, new Rectangle(
            start_x * GameAssetManager.getGameAssetManager().getTileWidth(),
            start_y * GameAssetManager.getGameAssetManager().getTileHeight(),
            width, height));
    }

    @Override
    public Texture getTexture() {
        return targetItem.getInventoryTexture();
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
    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public void updateProgressBar() {
        if (progressBar == null)
            return;
        float elapsed = artisanProductInProgress.getDaysInProgress() * 24 + artisanProductInProgress.getHoursInProgress();
        float duration = artisanProductInProgress.getType().getProcessingDays() * 24 + artisanProductInProgress.getType().getProcessingHours();
        if ((elapsed / duration) == 1) {
            progressBar = null;
            return;
        }
        progressBar.setValue(elapsed / duration);
    }
}
