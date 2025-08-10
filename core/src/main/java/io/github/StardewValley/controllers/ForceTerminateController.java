package io.github.StardewValley.controllers;

import io.github.StardewValley.GameAssetManagerClient;
import io.github.StardewValley.GameClient;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.models.Result;
import io.github.StardewValley.views.MainMenu;

public class ForceTerminateController {
    public void vote(boolean vote) {
        Result result = GameClient.getGameStateApiClient().forceTerminateVote(vote);
        if (result.successful()) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new MainMenu(new MainMenuController(), GameAssetManagerClient.getGameAssetManager().getSkin()));
        } else {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(Main.getGameView());
        }
    }
}
