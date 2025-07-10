package io.github.StardewValley.controllers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.StardewValley.models.App;
import io.github.StardewValley.views.chooseMap;



public class ChooseMapController {
    private chooseMap view;
    public int playerChoice = 0;
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

                if (!done) {
                    App.getCurrentGame().getPlayers().get(playerChoice).getPlayerMap().setMapType
                        (view.getCheckBox1().isChecked() ? 1 : 2);
                    playerChoice++;
                    if (playerChoice == 4) {
                        done = true;
                        view.setPlayerUserName("Let's go");
                    }
                }else {
                    //TODO enter the game
                }
            }
        });
    }
}
