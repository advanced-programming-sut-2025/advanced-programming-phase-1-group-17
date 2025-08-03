package io.github.StardewValley.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.github.StardewValley.GameAssetManagerClient;
import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.models.App;
import io.github.StardewValley.shared.models.backpack.BackPackable;
import io.github.StardewValley.shared.models.backpack.BackPackableType;
import io.github.StardewValley.shared.models.artisan.ArtisanProduct;
import io.github.StardewValley.shared.models.artisan.ArtisanProductType;
import io.github.StardewValley.shared.models.crafting.CraftingItem;
import io.github.StardewValley.shared.models.tools.ToolType;
import io.github.StardewValley.shared.models.Player;
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
        if (item instanceof ToolType) {
            view.getErrorLabel().setText("You can't use tools when crafting.");
            view.getErrorLabel().setColor(255, 0, 0, 1);
            return;
        }
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
            return;
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
            if (!matched || (product.getIngredients().size() != view.getSelectedItems().size()))
                continue;

            ArtisanProduct artisanProduct = new ArtisanProduct(product, ArtisanProduct.getIngredient
                (product, new ArrayList<>(view.getSelectedItems().keySet())));
            artisan.setArtisanProductInProgress(artisanProduct);

            Skin skin = GameAssetManagerClient.getGameAssetManager().getSkin();

            ProgressBar.ProgressBarStyle style = new ProgressBar.ProgressBarStyle();
            style.background = skin.newDrawable("white", Color.DARK_GRAY);  // Replace "white" with a texture name in your atlas
            style.knob = skin.newDrawable("white", Color.GRAY); // or whatever color
            style.knobBefore = skin.newDrawable("white", Color.GREEN);      // Replace with fill texture
            style.background.setMinHeight(15); // adjust height
            style.knob.setMinHeight(15);
            style.knobBefore.setMinHeight(15);

            ProgressBar progressBar = new ProgressBar(0f, 1f, 0.01f, false, style);
            progressBar.setValue(0.01f);
            progressBar.setAnimateDuration(0.25f);
            artisan.setProgressBar(progressBar);

            view.getSelectedItems().clear();
            view.getErrorLabel().setText("%s is now being crafted".formatted(product.getName()));
            view.getErrorLabel().setColor(255, 255, 255, 1);
            return;
        }
        view.getErrorLabel().setText("Items given do not match any of the artisan product ingredients.");
        view.getErrorLabel().setColor(255, 0, 0, 1);
    }
}
