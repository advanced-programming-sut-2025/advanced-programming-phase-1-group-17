package io.github.StardewValley.controllers.UIControllers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.StardewValley.GameAssetManagerClient;
import io.github.StardewValley.GameClient;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.dto.GetMarketInventoryResponse;
import io.github.StardewValley.shared.models.market.*;
import io.github.StardewValley.views.ItemMenu;
import io.github.StardewValley.views.StoreMenu;

import java.util.List;

public class StoreMenuController {
    private StoreMenu view;
    private Table itemsTable;
    private StoreType storeType;
    private Table upgradeTable;
    private GetMarketInventoryResponse marketInventory;


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

        List<ShopItemDTO> items = marketInventory.getItems();
        int rank = 1;

        for (ShopItemDTO item : items) {
            TextButton textButton = new TextButton(item.getType(), GameAssetManagerClient.getGameAssetManager().getSkin());
            if (!(item.isAvailable()) ||
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
            itemsTable.add(new Label("%.0f".formatted(item.getPrice()),   GameAssetManagerClient.getGameAssetManager().getSkin())).width(80).padRight(30);
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
            for (UpgradeServiceDTO upgradeServiceDTO : marketInventory.getUpgradeServices()) {
                TextButton textButton = new TextButton(upgradeServiceDTO.getName(),   GameAssetManagerClient.getGameAssetManager().getSkin());
                textButton.addListener(new ClickListener() {
                    // TODO Upgrade Tool
                });

                upgradeTable.add(new Label(rank + ".",   GameAssetManagerClient.getGameAssetManager().getSkin())).width(40).padRight(30);
                upgradeTable.add(textButton).width(400).padRight(30);
                upgradeTable.add(new Label("%d".formatted(upgradeServiceDTO.getCost()),   GameAssetManagerClient.getGameAssetManager().getSkin())).width(80).padRight(30);
                if (upgradeServiceDTO.getDailyLimit() > 200)
                    upgradeTable.add(new Label("INFINITY",   GameAssetManagerClient.getGameAssetManager().getSkin())).width(40).row();
                else
                    upgradeTable.add(new Label("%d".formatted(upgradeServiceDTO.getDailyLimit()),   GameAssetManagerClient.getGameAssetManager().getSkin())).width(40).row();
                rank++;
            }
        }
    }

    public void showAllAvailableProducts() {
        itemsTable.clear();
        List<ShopItemDTO> items = marketInventory.getItems();

        int rank = 1;

        for (ShopItemDTO item : items) {
            if (!(item.isAvailable()) ||
                (item.getSoldToday() >= item.getDailyLimit()))
                continue;

            TextButton textButton = new TextButton(item.getType(),   GameAssetManagerClient.getGameAssetManager().getSkin());
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
            itemsTable.add(new Label("%.0f".formatted(item.getPrice()),   GameAssetManagerClient.getGameAssetManager().getSkin())).width(80).padRight(30);
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
            for (UpgradeServiceDTO upgradeServiceDTO : marketInventory.getUpgradeServices()) {
                if (upgradeServiceDTO.getSoldToday() >= upgradeServiceDTO.getDailyLimit())
                    continue;
                TextButton textButton = new TextButton(upgradeServiceDTO.getName(),   GameAssetManagerClient.getGameAssetManager().getSkin());
                textButton.addListener(new ClickListener() {
                    // TODO Upgrade Tool
                });
                upgradeTable.add(new Label(rank + ".",   GameAssetManagerClient.getGameAssetManager().getSkin())).width(40).padRight(30);
                upgradeTable.add(textButton).width(400).padRight(30);
                upgradeTable.add(new Label("%d".formatted(upgradeServiceDTO.getCost()),   GameAssetManagerClient.getGameAssetManager().getSkin())).width(80).padRight(30);
                if (upgradeServiceDTO.getDailyLimit() > 200)
                    upgradeTable.add(new Label("INFINITY",   GameAssetManagerClient.getGameAssetManager().getSkin())).width(40).row();
                else
                    upgradeTable.add(new Label("%d".formatted(upgradeServiceDTO.getDailyLimit()),   GameAssetManagerClient.getGameAssetManager().getSkin())).width(40).row();
                rank++;
            }
        }
    }


    public void getMarketInventory(StoreType storeType) {
        marketInventory = GameClient.getGameStateApiClient().getInventory(storeType);
    }
}
