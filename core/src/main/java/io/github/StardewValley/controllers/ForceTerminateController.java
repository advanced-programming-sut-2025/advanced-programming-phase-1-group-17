package io.github.StardewValley.controllers;

import com.badlogic.gdx.Gdx;
import io.github.StardewValley.GameAssetManagerClient;
import io.github.StardewValley.GameClient;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.models.Result;
import io.github.StardewValley.views.ForceTerminateMenu;
import io.github.StardewValley.views.MainMenu;

public class ForceTerminateController {
    private ForceTerminateMenu view;

    public void setView(ForceTerminateMenu view) {
        this.view = view;
    }

    public void vote(boolean vote) {
        Result result = GameClient.getGameStateApiClient().forceTerminateVote(vote);

        if (!result.successful()) {
            view.getErrorLabel().setColor(255, 255, 255, 1);
            view.getErrorLabel().setText("Waiting for others to vote...");
            return;
        }

        // Voting finished
        view.getErrorLabel().setColor(255, 255, 255, 1);
        view.getErrorLabel().setText(result.message());

        Gdx.app.postRunnable(() -> {
            if (result.message().equals("Game Terminated successfully")) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new MainMenu(
                        new MainMenuController(),
                        GameAssetManagerClient.getGameAssetManager().getSkin()
                ));
            } else {
                Main.getMain().getScreen().dispose();
                Main.getGameView().showNotification(result.message());
                Main.getMain().setScreen(Main.getGameView());
            }
        });
    }
}
