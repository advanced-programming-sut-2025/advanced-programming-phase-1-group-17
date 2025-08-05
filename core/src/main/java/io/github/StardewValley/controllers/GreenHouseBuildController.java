package io.github.StardewValley.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import io.github.StardewValley.GameClient;
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
        Result result = GameClient.getGameStateApiClient().buildGreenHouse();
        if (!result.isSuccessful())
            view.getMessageLabel().setColor(255, 0, 0, 1);
        else
            view.getMessageLabel().setColor(255, 255, 255, 1);

        return result;
    }
}
