package io.github.StardewValley.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.models.App;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.Result;
import io.github.StardewValley.shared.models.backpack.NormalItemType;
import io.github.StardewValley.views.GreenHouseBuildScreen;

public class GreenHouseBuildController {
    private GreenHouseBuildScreen view;

    public void setView(GreenHouseBuildScreen view) {
        this.view = view;
    }

    public void handlePlayerInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            onBackClicked();
        }
    }

    public void onBuildClicked() {
        Result result = buildGreenHouse();
        view.showMessage(result.getMessage());

        if (result.isSuccessful()) {
            // Return to game screen after success
            Main.getMain().setScreen(Main.getGameView());
        }
    }

    public void onBackClicked() {
        Main.getMain().setScreen(Main.getGameView());
    }

    private Result buildGreenHouse() {
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();

        if (player.getBackPack().getCoin() < 1000) {
            view.getMessageLabel().setColor(255, 0, 0, 1);
            return new Result(false, "You only have %.2f coin. (not enough)".formatted(
                player.getBackPack().getCoin()));
        }

        int woodCount = player.getBackPack().getInventorySize(NormalItemType.Wood.getName());
        if (woodCount < 500) {
            view.getMessageLabel().setColor(255, 0, 0, 1);
            return new Result(false, "You only have %d wood. (not enough wood)".formatted(woodCount));
        }

        player.getBackPack().addCoin(-1000);
        for (int i = 0; i < 500; i++)
            player.getBackPack().useItem(NormalItemType.Wood);

        player.getPlayerMap().getGreenHouse().setActive(true);
        view.getMessageLabel().setColor(255, 255, 255, 1);
        return new Result(true, "Greenhouse built successfully!");
    }
}
