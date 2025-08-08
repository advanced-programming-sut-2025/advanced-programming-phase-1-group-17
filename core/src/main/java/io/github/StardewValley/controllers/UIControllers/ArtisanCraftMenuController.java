package io.github.StardewValley.controllers.UIControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import io.github.StardewValley.GameClient;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.dto.CraftingItemDTO;
import io.github.StardewValley.shared.models.Result;
import io.github.StardewValley.shared.models.backpack.BackpackableTypeDTO;
import io.github.StardewValley.shared.models.crafting.CraftingItem;
import io.github.StardewValley.views.ArtisanCraftMenu;

import java.util.HashMap;

public class ArtisanCraftMenuController {
    private ArtisanCraftMenu view;
    private CraftingItemDTO artisan;

    public void setView(ArtisanCraftMenu view) {
        this.view = view;
    }

    public void setArtisan(CraftingItemDTO artisan) {
        this.artisan = artisan;
    }

    public void handlePlayerInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(Main.getGameView());
        }
    }

    public void selectItem(BackpackableTypeDTO item) {
        if (item.getClassName().equals("ToolType")) {
            view.getErrorLabel().setText("You can't use tools when crafting.");
            view.getErrorLabel().setColor(255, 0, 0, 1);
            return;
        }
        HashMap<BackpackableTypeDTO, Integer> selectedItems = view.getSelectedItems();
        selectedItems.putIfAbsent(item, 1);
    }

    public void craft() {
        Result result = GameClient.getGameStateApiClient().craftArtisan(view.getSelectedItems(), view.getCraftingItemDTO());
        if (result.successful()) {
            view.getSelectedItems().clear();
            view.getErrorLabel().setColor(255, 255, 255, 1);
        } else {
            view.getErrorLabel().setColor(255, 0, 0, 1);
        }
        view.getErrorLabel().setText(result.message());
    }
}
