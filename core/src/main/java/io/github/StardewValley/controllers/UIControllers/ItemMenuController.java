package io.github.StardewValley.controllers.UIControllers;

import io.github.StardewValley.GameClient;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.models.Result;
import io.github.StardewValley.views.ItemMenu;

public class ItemMenuController {
    private ItemMenu view;

    public void setView(ItemMenu itemMenu) {
        this.view = itemMenu;
    }

    public void minus() {
        int count = view.getCount();
        if (count > 1) {
            count--;
            view.setCount(count);
        }
        else
            view.getErrorLabel().setText("No items selected.");
        view.getCountLabel().setText("Count: %d".formatted(count));
    }

    public void plus() {
        int count = view.getCount();
        if (count == view.getItem().getDailyLimit())
            view.getErrorLabel().setText("Daily Limit reached");
        else {
            count++;
            view.setCount(count);
        }
        view.getCountLabel().setText("Count: %d".formatted(count));
    }


    public Result purchase() {
        return GameClient.getGameStateApiClient().purchase(view.getItem(), view.getCount(), view.getStoreType());
    }


    public void exit() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(view.getStoreMenu());
    }
}
