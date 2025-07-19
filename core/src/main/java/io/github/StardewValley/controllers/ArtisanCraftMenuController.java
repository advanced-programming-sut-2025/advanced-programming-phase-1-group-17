package io.github.StardewValley.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import io.github.StardewValley.Main;
import io.github.StardewValley.models.*;
import io.github.StardewValley.models.artisan.ArtisanProduct;
import io.github.StardewValley.models.artisan.ArtisanProductType;
import io.github.StardewValley.models.crafting.CraftingItem;
import io.github.StardewValley.views.ArtisanCraftMenu;

import java.util.ArrayList;
import java.util.HashMap;

public class ArtisanCraftMenuController {
    private ArtisanCraftMenu view;
    private CraftingItem artisan;

    public void setView(ArtisanCraftMenu view) {
        this.view = view;
    }

    public void setArtisan(CraftingItem artisan) {
        this.artisan = artisan;
    }

    public void handlePlayerInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Player player = App.getCurrentGame().getCurrentPlayingPlayer();
            view.getSelectedItems().forEach(((backPackableType, backPackableArrayList) -> {
                for (BackPackable backPackable : backPackableArrayList) {
                    player.getBackPack().addItemToInventory(backPackable);
                }
            }));
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(Main.getGameView());
        }
    }

    public void selectItem(BackPackableType item) {
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        HashMap<BackPackableType, ArrayList<BackPackable>> selectedItems = view.getSelectedItems();
        selectedItems.putIfAbsent(item, new ArrayList<>());
        selectedItems.get(item).add(player.getBackPack().getBackPackItems().get(item).removeFirst());
    }

    public void craft() {
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        if (!artisan.getOwner().equals(player))
            return;

        if (artisan.getArtisanProductInProgress() != null) {
            view.getErrorLabel().setText("Artisan is already crafting a product!");
            view.getErrorLabel().setColor(255, 0, 0, 1);
        }

        // Try to match an ArtisanProductType with given artisan and ingredients
        for (ArtisanProductType product : ArtisanProductType.values()) {
            if (!product.getArtisan().equals(artisan.getTargetItem())) continue;

            boolean matched = true;
            for (BackPackableType backPackableType : view.getSelectedItems().keySet()) {
                if (!product.getIngredients().containsKey(backPackableType)) {
                    matched = false;
                    break;
                } else if (view.getSelectedItems().get(backPackableType).size() < product.getIngredients().get(backPackableType)) {
                    matched = false;
                    break;
                }
            }
            if (!matched || product.getIngredients().size() != view.getSelectedItems().size())
                continue;

            ArtisanProduct artisanProduct = new ArtisanProduct(product, ArtisanProduct.getIngredient
                (product, new ArrayList<>(view.getSelectedItems().keySet())));
            artisan.setArtisanProductInProgress(artisanProduct);
            view.getSelectedItems().clear();
            view.getErrorLabel().setText("%s is now being crafted".formatted(product.getName()));
            view.getErrorLabel().setColor(255, 255, 255, 1);
            return;
        }
        view.getErrorLabel().setText("Items given do not match any of the artisan product ingredients.");
        view.getErrorLabel().setColor(255, 0, 0, 1);
    }
}
