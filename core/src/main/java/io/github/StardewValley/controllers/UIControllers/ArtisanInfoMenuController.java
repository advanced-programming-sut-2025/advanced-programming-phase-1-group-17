package io.github.StardewValley.controllers.UIControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.models.App;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.views.ArtisanInfoMenu;

public class ArtisanInfoMenuController {
    private ArtisanInfoMenu view;

    public void setView(ArtisanInfoMenu artisanInfoMenu) {
        this.view = artisanInfoMenu;
    }

    public void cancel() {
        if (nothingIsBeingPreparedMessage())
            return;

        view.getMessageLabel().setText("Crafting item %s has been cancelled."
            .formatted(view.getCraftingItem().getArtisanProductInProgress().getName()));
        view.getMessageLabel().setColor(255, 255, 255, 1);
        view.getCraftingItem().setArtisanProductInProgress(null);
    }

    public void takeProduct() {
        if (nothingIsBeingPreparedMessage())
            return;

        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        player.getBackPack().addItemToInventory(view.getCraftingItem().getArtisanProductInProgress());
        view.getMessageLabel().setText("Item %s added to inventory successfully."
            .formatted(view.getCraftingItem().getArtisanProductInProgress().getName()));
        view.getMessageLabel().setColor(255, 255, 255, 1);
        view.getCraftingItem().setArtisanProductInProgress(null);
    }

    public void handlePlayerInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(Main.getGameView());
        }
    }

    private boolean nothingIsBeingPreparedMessage() {
        if (view.getCraftingItem().getArtisanProductInProgress() == null) {
            view.getMessageLabel().setText("Nothing is being prepared in this %s"
                .formatted(view.getCraftingItem().getTargetItem().getName()));
            view.getMessageLabel().setColor(255, 0, 0, 1);
            return true;
        }
        return false;
    }
}
