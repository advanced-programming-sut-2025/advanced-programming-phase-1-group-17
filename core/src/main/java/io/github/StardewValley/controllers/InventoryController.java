package io.github.StardewValley.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import io.github.StardewValley.GameAssetManagerClient;
import io.github.StardewValley.GameClient;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.models.Result;
import io.github.StardewValley.shared.models.backpack.BackpackableTypeDTO;
import io.github.StardewValley.views.*;

public class InventoryController {
    private InventoryView view;

    public void setView(InventoryView view) {
        this.view = view;
    }

    public void handleItemClick(BackpackableTypeDTO backPackableTypeDTO) {
        GameClient.getGameStateApiClient().equipItem(backPackableTypeDTO);
    }

    public void handleItemTrash() {
        String result = GameClient.getGameStateApiClient().trashItem();
        view.getItemPickLabel().setText(result);
        if (result.equals("Item deleted from Inventory"))
            view.refreshInventoryItems();
    }


    public void handleSkillMenu() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new SkillMenu(new SkillMenuController(),
              GameAssetManagerClient.getGameAssetManager().getSkin(), Main.getGameView()));
    }

    public void handleSocialMenu() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new TalkView(new TalkController(),
              GameAssetManagerClient.getGameAssetManager().getSkin(), Main.getGameView()));
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
        if (result.successful()) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new MainMenu(new MainMenuController(),   GameAssetManagerClient.getGameAssetManager().getSkin()));
        } else {
            view.getItemPickLabel().setText(result.message());
            view.getItemPickLabel().setColor(255, 0, 0, 1);
        }
    }

    public void startVoting() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new SelectCandidateMenu(new SelectCandidateController(),
            GameAssetManagerClient.getGameAssetManager().getSkin()));
    }

    public void forceTerminate() {
        Main.getMain().getScreen().dispose();
        GameClient.getGameStateApiClient().forceTerminate();
        Main.getMain().setScreen(new ForceTerminateMenu(new ForceTerminateController(),
            GameAssetManagerClient.getGameAssetManager().getSkin()));
    }
}
