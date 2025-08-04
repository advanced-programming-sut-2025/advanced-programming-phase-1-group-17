package io.github.StardewValley.controllers.UIControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import io.github.StardewValley.Main;
import io.github.StardewValley.views.Journal;

public class JournalController {
    private Journal view;

    public void setView(Journal view) {
        this.view = view;
    }

    public void update() {
        handlePlayerInput();
    }

    private void handlePlayerInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(Main.getGameView());
        }
    }
}
