package io.github.StardewValley.controllers;

import io.github.StardewValley.models.App;
import io.github.StardewValley.models.Player;
import io.github.StardewValley.views.ArtisanInfoMenu;

public class ArtisanInfoMenuController {
    private ArtisanInfoMenu view;

    public void setView(ArtisanInfoMenu artisanInfoMenu) {
        this.view = artisanInfoMenu;
    }

    public void cancel() {
        view.getMessageLabel().setText("Crafting item %s has been cancelled.".formatted(view.getCraftingItem().getName()));
        view.getCraftingItem().setArtisanProductInProgress(null);
    }

    public void takeProduct() {
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        player.getBackPack().addItemToInventory(view.getCraftingItem().getArtisanProductInProgress());
        view.getMessageLabel().setText("Item %s added to inventory successfully.".formatted(view.getCraftingItem().getName()));
        view.getCraftingItem().setArtisanProductInProgress(null);
    }
}
