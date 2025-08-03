package io.github.StardewValley.controllers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.StardewValley.GameAssetManagerClient;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.models.App;
import io.github.StardewValley.shared.models.enums.Season;
import io.github.StardewValley.shared.models.market.*;
import io.github.StardewValley.views.ItemMenu;
import io.github.StardewValley.views.StoreMenu;

public class StoreMenuController {
    private StoreMenu view;
    private Table itemsTable;
    private StoreType storeType;
    private Table upgradeTable;


    public void exit() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(Main.getGameView());
    }

    public void setView(StoreMenu view) {
        this.view = view;
    }


    public void showAllProducts() {
        this.itemsTable = view.getItemsTable();
        itemsTable.clear();
        this.storeType = view.getStoreType();

        MarketsController manager = App.getCurrentGame().getStoreManager();
        StoreInventory inventory = manager.getInventory(storeType);
        int rank = 1;
        double price;

        for (ShopItem item : inventory.getItems()) {
            if (storeType.equals(StoreType.PierresGeneralStore))
                price = manager.getSeasonalPrice(item);
            else
                price = item.getPrice();

            TextButton textButton = new TextButton(item.getType().getName(),   GameAssetManagerClient.getGameAssetManager().getSkin());
            if (!item.isAvailableInSeason(App.getCurrentGame().getDate().getSeason()) ||
                !(item.isAvailable()) ||
                (item.getSoldToday() >= item.getDailyLimit())) {
                textButton.setColor(0.5f, 0.5f, 0.5f, 1f);
                textButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        view.getErrorLabel().setText("Item not available");
                    }
                });
            } else {
                textButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        Main.getMain().getScreen().dispose();
                        Main.getMain().setScreen(new ItemMenu(new ItemMenuController(),   GameAssetManagerClient.getGameAssetManager().getSkin(),
                            item, view, view.getStoreType()));
                    }
                });
            }
            itemsTable.add(new Label(rank + ".",   GameAssetManagerClient.getGameAssetManager().getSkin())).width(40).padRight(30);
            itemsTable.add(textButton).width(400).padRight(30);
            itemsTable.add(new Label("%.0f".formatted(price),   GameAssetManagerClient.getGameAssetManager().getSkin())).width(80).padRight(30);
            if (item.getDailyLimit() > 200)
                itemsTable.add(new Label("INFINITY",   GameAssetManagerClient.getGameAssetManager().getSkin())).width(40).row();
            else
                itemsTable.add(new Label("%d".formatted(item.getDailyLimit()),   GameAssetManagerClient.getGameAssetManager().getSkin())).width(40).row();
            rank++;
        }
    }

    public void addUpgradeServices() {
        this.upgradeTable = view.getUpgradeTable();

        if (storeType.equals(StoreType.Blacksmith)) {
            upgradeTable.clear();
            upgradeTable.add(new Label("Rank",   GameAssetManagerClient.getGameAssetManager().getSkin())).width(40);
            upgradeTable.add(new Label("Service",   GameAssetManagerClient.getGameAssetManager().getSkin())).width(200);
            upgradeTable.add(new Label("Cost",   GameAssetManagerClient.getGameAssetManager().getSkin())).width(80).row();
            int rank = 1;

            MarketsController manager = App.getCurrentGame().getStoreManager();
            for (UpgradeService upgradeService : manager.getInventory(storeType).getUpgradeServices()) {
                TextButton textButton = new TextButton(upgradeService.getName(),   GameAssetManagerClient.getGameAssetManager().getSkin());
                textButton.addListener(new ClickListener() {
                    // TODO
                });

                upgradeTable.add(new Label(rank + ".",   GameAssetManagerClient.getGameAssetManager().getSkin())).width(40).padRight(30);
                upgradeTable.add(textButton).width(400).padRight(30);
                upgradeTable.add(new Label("%d".formatted(upgradeService.getCost()),   GameAssetManagerClient.getGameAssetManager().getSkin())).width(80).padRight(30);
                if (upgradeService.getDailyLimit() > 200)
                    upgradeTable.add(new Label("INFINITY",   GameAssetManagerClient.getGameAssetManager().getSkin())).width(40).row();
                else
                    upgradeTable.add(new Label("%d".formatted(upgradeService.getDailyLimit()),   GameAssetManagerClient.getGameAssetManager().getSkin())).width(40).row();
                rank++;
            }
        }
    }

    public void showAllAvailableProducts() {
        itemsTable.clear();
        MarketsController manager = App.getCurrentGame().getStoreManager();
        StoreInventory inventory = manager.getInventory(storeType);
        Season season = App.getCurrentGame().getDate().getSeason();

        int rank = 1;
        double price;

        for (ShopItem item : inventory.getItems()) {
            if (!item.isAvailableInSeason(season) ||
                !(item.isAvailable()) ||
                (item.getSoldToday() >= item.getDailyLimit()))
                continue;

            if (storeType.equals(StoreType.PierresGeneralStore))
                price = manager.getSeasonalPrice(item);
            else
                price = item.getPrice();

            TextButton textButton = new TextButton(item.getType().getName(),   GameAssetManagerClient.getGameAssetManager().getSkin());
            textButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Main.getMain().getScreen().dispose();
                    Main.getMain().setScreen(new ItemMenu(new ItemMenuController(),   GameAssetManagerClient.getGameAssetManager().getSkin(),
                        item, view, view.getStoreType()));
                }
            });
            itemsTable.add(new Label(rank + ".",   GameAssetManagerClient.getGameAssetManager().getSkin())).width(40).padRight(30);
            itemsTable.add(textButton).width(400).padRight(30);
            itemsTable.add(new Label("%.0f".formatted(price),   GameAssetManagerClient.getGameAssetManager().getSkin())).width(80).padRight(30);
            if (item.getDailyLimit() > 200)
                itemsTable.add(new Label("INFINITY",   GameAssetManagerClient.getGameAssetManager().getSkin())).width(40).row();
            else
                itemsTable.add(new Label("%d".formatted(item.getDailyLimit()),   GameAssetManagerClient.getGameAssetManager().getSkin())).width(40).row();
            rank++;
        }
    }

    public void addAvailableUpgradeServices() {
        if (storeType.equals(StoreType.Blacksmith)) {
            upgradeTable.clear();
            upgradeTable.add(new Label("Rank",   GameAssetManagerClient.getGameAssetManager().getSkin())).width(40);
            upgradeTable.add(new Label("Service",   GameAssetManagerClient.getGameAssetManager().getSkin())).width(200);
            upgradeTable.add(new Label("Cost",   GameAssetManagerClient.getGameAssetManager().getSkin())).width(80).row();
            int rank = 1;

            MarketsController manager = App.getCurrentGame().getStoreManager();
            for (UpgradeService upgradeService : manager.getInventory(storeType).getUpgradeServices()) {
                if (upgradeService.getSoldToday() >= upgradeService.getDailyLimit())
                    continue;
                TextButton textButton = new TextButton(upgradeService.getName(),   GameAssetManagerClient.getGameAssetManager().getSkin());
                textButton.addListener(new ClickListener() {
                    // TODO
                });
                upgradeTable.add(new Label(rank + ".",   GameAssetManagerClient.getGameAssetManager().getSkin())).width(40).padRight(30);
                upgradeTable.add(textButton).width(400).padRight(30);
                upgradeTable.add(new Label("%d".formatted(upgradeService.getCost()),   GameAssetManagerClient.getGameAssetManager().getSkin())).width(80).padRight(30);
                if (upgradeService.getDailyLimit() > 200)
                    upgradeTable.add(new Label("INFINITY",   GameAssetManagerClient.getGameAssetManager().getSkin())).width(40).row();
                else
                    upgradeTable.add(new Label("%d".formatted(upgradeService.getDailyLimit()),   GameAssetManagerClient.getGameAssetManager().getSkin())).width(40).row();
                rank++;
            }
        }
    }
}
