package io.github.StardewValley.controllers;

import com.badlogic.gdx.Gdx;
import io.github.StardewValley.GameAssetManagerClient;
import io.github.StardewValley.GameClient;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.models.Result;
import io.github.StardewValley.views.MainMenu;
import io.github.StardewValley.views.VotingMenu;

public class VotingMenuController {
    private VotingMenu view;

    public void setView(VotingMenu view) {
        this.view = view;
    }

    public void vote(boolean vote) {
        Result result = GameClient.getGameStateApiClient().kickVote(vote);

        if (!result.successful()) {
            view.getMessageLabel().setColor(255, 255, 255, 1);
            view.getMessageLabel().setText("Waiting for others to vote...");
            return;
        }

        // Voting finished
        view.getMessageLabel().setColor(255, 255, 255, 1);
        view.getMessageLabel().setText(result.message());

        Gdx.app.postRunnable(() -> {
            if (!result.message().equals("Game resuming...")) {
                Main.getMain().getScreen().dispose();
                if (GameClient.getPlayer().getUser().getUsername().equals(view.getTargetUsername()))
                    Main.getMain().setScreen(new MainMenu(
                        new MainMenuController(),
                        GameAssetManagerClient.getGameAssetManager().getSkin()
                    ));
                else {
                    Main.getGameView().showNotification(result.message());
                    Main.getMain().setScreen(Main.getGameView());
                }
            } else {
                Main.getMain().getScreen().dispose();
                Main.getGameView().showNotification(result.message());
                Main.getMain().setScreen(Main.getGameView());
            }
        });
    }
}
