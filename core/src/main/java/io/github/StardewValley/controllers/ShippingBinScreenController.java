package io.github.StardewValley.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.backpack.BackPack;
import io.github.StardewValley.shared.models.backpack.BackPackableType;
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

    public void sellItem(BackPackableType itemType, int quantity, Player player) {
        BackPack backPack = player.getBackPack();
        int stock = backPack.getBackPackItems().get(itemType).size();
        if (quantity > stock) {
            view.getErrorLabel().setColor(255, 0, 0, 1);
            view.getErrorLabel().setText("You only have %d of item %s".formatted(stock, itemType.getName()));
            return;
        }

        // Remove from player's backpack
        for (int i = 0; i < quantity; i++) {
            view.getShippingBin().addItem(backPack.getBackPackItems().get(backPack).getFirst());
            backPack.useItem(itemType);
            view.getErrorLabel().setColor(0, 0, 0, 1);
            view.getErrorLabel().setText("Items successfully added to the shipping Bin!");
        }
    }

}
