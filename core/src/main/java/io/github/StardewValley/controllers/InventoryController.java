package io.github.StardewValley.controllers;

import io.github.StardewValley.GameAssetManager;
import io.github.StardewValley.Main;
import io.github.StardewValley.models.*;
import io.github.StardewValley.models.tools.BackPack;
import io.github.StardewValley.models.tools.FishingPoleType;
import io.github.StardewValley.models.tools.Tool;
import io.github.StardewValley.models.tools.ToolType;
import io.github.StardewValley.views.InventoryView;
import io.github.StardewValley.views.MapView;
import io.github.StardewValley.views.SkillMenu;
import io.github.StardewValley.views.SocialMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InventoryController {
    private InventoryView view;

    public void setView(InventoryView view) {
        this.view = view;
    }

    public void handleItemClick(BackPackableType backPackableType, Player player) {
        //TODO: maybe we can delete player.currentTool
        player.setEquippedItem(backPackableType);
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
            BackPackableType backPackableType = player.getEquippedItem();
            if (player.getEquippedItem() instanceof ToolType toolType)
                player.setCurrentTool(null);
            player.setEquippedItem(null);
            player.getBackPack().getBackPackItems().get(backPackableType).removeFirst();
            this.view.getItemPickLabel().setText("Item deleted from Inventory");
            handleRefund(backPackableType, player);
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


    public void handleRefund(BackPackableType backPackableType, Player player) {
        double refundPercentage = player.getTrashCan().getTrashCanRefundPercentage() / 100.0;
        double refund = backPackableType.getPrice() * refundPercentage;
        player.getBackPack().addCoin(refund);
    }


    public void handleSkillMenu() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new SkillMenu(new SkillMenuController(),
            GameAssetManager.getGameAssetManager().getSkin(), view.getPlayer()));
    }

    public void handleSocialMenu() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new SocialMenu(new SocialMenuController(),
            GameAssetManager.getGameAssetManager().getSkin(),  view.getPlayer()));
    }

    public void handleMap() {
//        Main.getMain().getScreen().dispose();
//        Main.getMain().setScreen(new MapView(new MapController(),
//            GameAssetManager.getGameAssetManager().getSkin(), view.getPlayer(), view.getGameView()));
    }
}
