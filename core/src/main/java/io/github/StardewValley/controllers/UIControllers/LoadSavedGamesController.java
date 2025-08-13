package io.github.StardewValley.controllers.UIControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import io.github.StardewValley.GameAssetManagerClient;
import io.github.StardewValley.GameClient;
import io.github.StardewValley.Main;
import io.github.StardewValley.controllers.GameController;
import io.github.StardewValley.controllers.GameMenuController;
import io.github.StardewValley.controllers.MainMenuController;
import io.github.StardewValley.shared.dto.SavedGameInfo;
import io.github.StardewValley.views.GameView;
import io.github.StardewValley.views.LoadSavedGamesScreen;
import io.github.StardewValley.views.MainMenu;

import java.util.List;
import java.util.UUID;

public class LoadSavedGamesController {
    private LoadSavedGamesScreen view;

    public void setView(LoadSavedGamesScreen view) {
        this.view = view;
    }

    public List<SavedGameInfo> getSavedGames() {
        return GameClient.getGameStateApiClient().getSavedGames();
    }

    public void waitForLoadGame(UUID id) {
        if (GameClient.getGameStateApiClient().waitForLoadGame(id)) {
            if (GameClient.getGameStateApiClient().loadGame(id)) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new GameView(new GameController(), new GameMenuController()));
            }
        }
    }

    public void handlePlayerInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new MainMenu(new MainMenuController(), GameAssetManagerClient.getGameAssetManager().getSkin()));
        }
    }
}
