package io.github.StardewValley.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import io.github.StardewValley.Main;
import io.github.StardewValley.views.SkillMenu;

public class SkillMenuController {
    private SkillMenu view;

    public void setView(SkillMenu skillMenu) {
        this.view = skillMenu;
    }

    public void handlePlayerInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(view.getGameView());
        }
    }

    public void goToGameView() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(view.getGameView());
    }
}
