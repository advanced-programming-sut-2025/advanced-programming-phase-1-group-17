package io.github.StardewValley.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import io.github.StardewValley.GameClient;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.models.backpack.BackpackableTypeDTO;
import io.github.StardewValley.views.ShippingBinScreen;

public class ShippingBinScreenController {
    private ShippingBinScreen view;

    public void setView(ShippingBinScreen view) {
        this.view = view;
    }

    public void handlePlayerInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(Main.getGameView());
        }
    }

    public void sellItem(BackpackableTypeDTO itemType, int quantity) {
        int stock = itemType.getCountInBackPack();
        if (quantity > stock) {
            view.getErrorLabel().setColor(255, 0, 0, 1);
            view.getErrorLabel().setText("You only have %d of item %s".formatted(stock, itemType.getName()));
            return;
        }
        boolean isSuccessful = GameClient.getGameStateApiClient().sellItem(view.getShippingBinTile(), itemType, quantity);
        if (isSuccessful) {
            view.getErrorLabel().setColor(0, 0, 0, 1);
            view.getErrorLabel().setText("Items successfully added to the shipping Bin!");
        } else {
            view.getErrorLabel().setColor(255, 0, 0, 1);
            view.getErrorLabel().setText("Operation not successful.");
        }
    }

}
