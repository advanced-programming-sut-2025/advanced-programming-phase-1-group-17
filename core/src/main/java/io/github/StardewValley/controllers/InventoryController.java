package io.github.StardewValley.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import io.github.StardewValley.GameAssetManager;
import io.github.StardewValley.Main;
import io.github.StardewValley.models.*;
import io.github.StardewValley.models.tools.BackPack;
import io.github.StardewValley.models.tools.FishingPoleType;
import io.github.StardewValley.models.tools.Tool;
import io.github.StardewValley.models.tools.ToolType;
import io.github.StardewValley.views.*;

public class InventoryController {
    private InventoryView view;

    public void setView(InventoryView view) {
        this.view = view;
    }

    public void handleItemClick(BackPackableType backPackableType, Player player) {
        //TODO: maybe we can delete player.currentTool
        player.setEquippedItem(player.getBackPack().getBackPackItems().get(backPackableType).get(0));
        player.setCurrentTool(null);
        if (backPackableType instanceof ToolType toolType)
            toolEquip(toolType, player);
        else if (backPackableType instanceof FishingPoleType fishingPoleType)
            fishingPoleEquip(fishingPoleType, player);

    }


    public void handleItemTrash(Player player) {
        if (player.getEquippedItem() == null) {
            this.view.getItemPickLabel().setText("You haven't picked any item.");
            return;
        } else {
            BackPackable backPackable = player.getEquippedItem();
            if (player.getEquippedItem() instanceof Tool tool)
                player.setCurrentTool(null);
            player.setEquippedItem(null);

            if (player.getBackPack().getInventorySize(backPackable.getType().getName()) == 1)
                player.getBackPack().getBackPackItems().remove(backPackable.getType());
            else
                player.getBackPack().getBackPackItems().get(backPackable.getType()).removeFirst();


            this.view.getItemPickLabel().setText("Item deleted from Inventory");
            handleRefund(backPackable, player);

            //for updating the view
            view.refreshInventoryItems();
        }
    }

    private void toolEquip(ToolType toolType, Player player) {
        BackPack backPack = player.getBackPack();
        Tool tool = (Tool) backPack.getBackPackItems().get(toolType).get(0);
        player.setCurrentTool(tool);
    }

    private void fishingPoleEquip(FishingPoleType fishingPoleType, Player player) {
        BackPack backPack = player.getBackPack();
        Tool tool = (Tool) backPack.getBackPackItems().get(fishingPoleType).get(0);
        player.setCurrentTool(tool);
    }


    public void handleRefund(BackPackable backPackable, Player player) {
        double refundPercentage = player.getTrashCan().getTrashCanRefundPercentage() / 100.0;
        double refund = backPackable.getType().getPrice() * refundPercentage;
        player.getBackPack().addCoin(refund);
    }


    public void handleSkillMenu() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new SkillMenu(new SkillMenuController(),
            GameAssetManager.getGameAssetManager().getSkin(), view.getPlayer(), Main.getGameView()));
    }

    public void handleSocialMenu() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new TalkView(new TalkController(),
            GameAssetManager.getGameAssetManager().getSkin(), view.getGameView()));
    }

    public void handleMap() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new MapView(new MapViewController(), Main.getGameView()));
    }

    public void handlePlayerInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(Main.getGameView());
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            view.showOnlyTools();
        }
    }

    public void goToGameView() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(Main.getGameView());
    }

    public void saveAndExitButton() {
        GameMenuController gameMenuController = new GameMenuController();
        Result result =  gameMenuController.exitGame();
        this.view.getItemPickLabel().setText(result.toString());
        if (result.isSuccessful()) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new MainMenu(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
        }
    }
}
