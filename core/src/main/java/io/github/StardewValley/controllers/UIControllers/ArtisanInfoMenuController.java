package io.github.StardewValley.controllers.UIControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import io.github.StardewValley.GameClient;
import io.github.StardewValley.Main;
import io.github.StardewValley.views.ArtisanInfoMenu;

public class ArtisanInfoMenuController {
    private ArtisanInfoMenu view;

    public void setView(ArtisanInfoMenu artisanInfoMenu) {
        this.view = artisanInfoMenu;
    }

    public void cancel() {
        if (nothingIsBeingPreparedMessage())
            return;

        boolean result = GameClient.getGameStateApiClient().cancelArtisanProduct(view.getCraftingItem());
        if (result) {
            view.getMessageLabel().setColor(255, 255, 255, 1);
            view.getMessageLabel().setText("Crafting item %s has been cancelled."
                .formatted(view.getCraftingItem().getArtisanProductType()));
            view.getCraftingItem().setInProgress(false);
        } else {
            view.getMessageLabel().setColor(255, 0, 0, 1);
            view.getMessageLabel().setText("Operation failed");
        }
    }

    public void takeProduct() {
        if (nothingIsBeingPreparedMessage())
            return;

        boolean result = GameClient.getGameStateApiClient().takeArtisanProduct(view.getCraftingItem());
        if (result) {
            view.getMessageLabel().setColor(255, 255, 255, 1);
            view.getMessageLabel().setText("Item %s added to inventory successfully."
                .formatted(view.getCraftingItem().getArtisanProductType()));
            view.getCraftingItem().setInProgress(false);
        } else {
            view.getMessageLabel().setColor(255, 0, 0, 1);
            view.getMessageLabel().setText("Operation failed");
        }
    }

    public void handlePlayerInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(Main.getGameView());
        }
    }

    private boolean nothingIsBeingPreparedMessage() {
        if (!view.getCraftingItem().isInProgress()) {
            view.getMessageLabel().setText("Nothing is being prepared in this %s"
                .formatted(view.getCraftingItem().getType()));
            view.getMessageLabel().setColor(255, 0, 0, 1);
            return true;
        }
        return false;
    }
}
