package io.github.StardewValley.controllers.UIControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import io.github.StardewValley.GameClient;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.models.*;
import io.github.StardewValley.views.CheatCodeTerminal;

public class CheatCodeTerminalController {
    private CheatCodeTerminal view;

    public void setView(CheatCodeTerminal cheatCodeTerminal) {
        this.view = cheatCodeTerminal;
    }

    public void handlePlayerInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(Main.getGameView());
        }
    }

    public void handleCommand(String command) {
        Result result = new Result(false, "Invalid Command");
        try {
            result = GameClient.getGameStateApiClient().handleCheatCode(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        view.getOutputArea().setText("%s\n>%s\n%s"
            .formatted(view.getOutputArea().getText().trim(), view.getInputField().getText(), result.getMessage()));
    }


    public void exit() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(Main.getGameView());
    }
}
