package io.github.StardewValley.controllers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.StardewValley.GameClient;
import io.github.StardewValley.Main;
import io.github.StardewValley.controllers.helperControllers.GameStateApiClient;
import io.github.StardewValley.shared.models.App;
import io.github.StardewValley.views.GameView;
import io.github.StardewValley.views.chooseMap;


public class ChooseMapController {
    private chooseMap view;
    public boolean done = false;

    public void setView(chooseMap view) {
        this.view = view;
        setupButtonListener();
    }

    private void setupButtonListener() {
        view.getCheckBox1().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.getCheckBox1().setChecked(true);
                view.getCheckBox2().setChecked(false);
            }
        });
        view.getCheckBox2().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.getCheckBox1().setChecked(false);
                view.getCheckBox2().setChecked(true);
            }
        });
        view.getNext().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {

                if (!(GameClient.getGameStateApiClient().isStarted())) {
                    try {
                        GameClient.getGameStateApiClient().selectMap(view.getCheckBox1().isChecked() ? 1 : 2);
                        done = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Main.getMain().getScreen().dispose();
                    Main.getMain().setScreen(new GameView(new GameController(), new GameMenuController()));
                }
            }
        });
    }
}
