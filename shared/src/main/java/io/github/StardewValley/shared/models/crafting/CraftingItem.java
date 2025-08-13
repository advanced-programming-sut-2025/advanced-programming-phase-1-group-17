package io.github.StardewValley.shared.models.crafting;

import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.shared.dto.CraftingItemDTO;
import io.github.StardewValley.shared.models.*;
import io.github.StardewValley.shared.models.artisan.ArtisanProduct;
import io.github.StardewValley.shared.models.backpack.BackPackable;
import io.github.StardewValley.shared.models.backpack.BackPackableType;
import io.github.StardewValley.shared.models.game.Game;
import io.github.StardewValley.shared.models.map.Placeable;
import io.github.StardewValley.shared.models.saveClasses.BackPackableSave;
import io.github.StardewValley.shared.models.saveClasses.CraftingItemSave;
import io.github.StardewValley.shared.models.saveClasses.PlaceableSave;

import java.util.List;

public class CraftingItem implements BackPackable, Placeable {
    private final CraftingItemType type;
    private ArtisanProduct artisanProductInProgress = null;

    private Player owner;
    private int start_x = 0;
    private int start_y = 0;
    private final int width;
    private final int height;

    public CraftingItem(CraftingItemType type, Player owner, Game game) {
        this.owner = owner;
        this.type = type;
//        this.width = targetItem.getInventoryTexture().getWidth();
//        this.height = targetItem.getInventoryTexture().getHeight();
        this.width = GameAssetManager.getGameAssetManager().getTileWidth();
        this.height = GameAssetManager.getGameAssetManager().getTileHeight();

        game.addCraftingItem(this);
    }

    public CraftingItem(CraftingItemSave save, List<Player> playerList) {
        this.type = save.getType();
        this.artisanProductInProgress = save.getArtisanProductInProgress();
        this.start_x = save.getStart_x();
        this.start_y = save.getStart_y();
        this.width = save.getWidth();
        this.height = save.getHeight();

        for (Player player : playerList) {
            if (player.getUser().getUsername().equals(save.getOwnerUsername())) {
                this.owner = player;
                break;
            }
        }
    }

    public static CraftingItemDTO getCraftingItemDTO(CraftingItem craftingItem) {
        boolean isArtisanProductNull = craftingItem.artisanProductInProgress == null;
        return new CraftingItemDTO(
            !isArtisanProductNull,
            !isArtisanProductNull && craftingItem.artisanProductInProgress.isReady(),
            craftingItem.type.getName(),
            isArtisanProductNull ? "" : craftingItem.artisanProductInProgress.getName(),
            isArtisanProductNull ? "" : craftingItem.artisanProductInProgress.getType().getInventoryTexturePath(),
            craftingItem.start_x,
            craftingItem.start_y,
            isArtisanProductNull ? -1 : craftingItem.artisanProductInProgress.getHoursInProgress(),
            isArtisanProductNull ? -1 : craftingItem.artisanProductInProgress.getDaysInProgress(),
            isArtisanProductNull ? -1 : craftingItem.artisanProductInProgress.getType().getProcessingHours(),
            isArtisanProductNull ? -1 : craftingItem.artisanProductInProgress.getType().getProcessingDays()
        );
    }

    @Override
    public String getName() {
        return type.getName();
    }

    @Override
    public double getPrice() {
        return type.getPrice();
    }

    @Override
    public BackPackableType getType() {
        return type;
    }

    @Override
    public BackPackableSave toBackpackableSave() {
        BackPackableSave backPackableSave = new BackPackableSave(CraftingItem.class.getSimpleName());
        backPackableSave.setCraftingItemSave(new CraftingItemSave(this));
        return backPackableSave;
    }

    public CraftingItemType getCraftingItemType() {
        return type;
    }

    public ArtisanProduct getArtisanProductInProgress() {
        return artisanProductInProgress;
    }

    public void setArtisanProductInProgress(ArtisanProduct artisanProductInProgress) {
        this.artisanProductInProgress = artisanProductInProgress;
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

    @Override
    public String getTexture() {
        return type.getInventoryTexturePath();
    }

    @Override
    public PlaceableSave toDTO(int x, int y, String ownerUsername) {
        PlaceableSave placeableSave = new PlaceableSave(CraftingItem.class.getSimpleName());
        placeableSave.setCraftingItemSave(new CraftingItemSave(this));
        return placeableSave;
    }

    @Override
    public Placeable loadFromDTO(PlaceableSave dto, List<Player> playerList) {
        return new CraftingItem(dto.getCraftingItemSave(), playerList);
    }
}
