package io.github.StardewValley.controllers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.models.App;
import io.github.StardewValley.shared.models.BackPackableType;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.crafting.CraftingItem;
import io.github.StardewValley.shared.models.crafting.CraftingItemType;
import io.github.StardewValley.shared.models.tools.BackPack;
import io.github.StardewValley.views.CraftingShow;
import io.github.StardewValley.views.GameView;

import java.util.Map;

public class CraftingController {
    private CraftingShow view;
    private GameView gameView;
    public void setView(CraftingShow view, GameView gameView) {
        this.view = view;
        this.gameView = gameView;
        handleButtons();

    }
    public void handleIngredients(CraftingItemType item) {
        StringBuilder sb = new StringBuilder();
        BackPack backPack = App.getCurrentGame().getCurrentPlayingPlayer().getBackPack();
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        for (Map.Entry<BackPackableType, Integer> entry : item.getIngredients().entrySet()) {
            if (!(player.getBackPack().getBackPackItems().containsKey(entry.getKey())
                && player.getBackPack().getBackPackItems().get(entry.getKey()).size() >= entry.getValue())) {
                //return new Result(false, "not enough ingredient");
                view.getErrorMessage().setText("not enough ingredients");
                return;
            }
        }
        for (Map.Entry<BackPackableType, Integer> entry : item.getIngredients().entrySet()) {
            sb.append(entry.getKey().getName()).append(": ").append(entry.getValue()).append("\n");
            for (int i = 0; i < entry.getValue(); i++) {
                player.getBackPack().useItem(entry.getKey());
            }
        }
        CraftingItem craftingItem = new CraftingItem(item, App.getCurrentGame().getCurrentPlayingPlayer());
        backPack.addItemToInventory(craftingItem);
        view.getErrorMessage().setText("crafted successfully");


        view.getIngredients().setText(sb.toString());
    }
    public void handleIngredientsLabel(CraftingItemType item) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<BackPackableType, Integer> entry : item.getIngredients().entrySet()) {
            sb.append(entry.getKey().getName()).append(": ").append(entry.getValue()).append("\n");
        }
        view.getIngredients().setText(sb.toString());
    }
    public void handleButtons(){
        view.getBackButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(gameView);
            }

            });
    }
}
