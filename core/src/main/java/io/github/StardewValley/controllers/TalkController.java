package io.github.StardewValley.controllers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.StardewValley.Main;
import io.github.StardewValley.models.App;
import io.github.StardewValley.views.GameView;
import io.github.StardewValley.views.TalkView;

public class TalkController {
    private TalkView view;
    private GameView gameView;

    public void setView(TalkView view, GameView gameView) {
        this.view = view;
        this.gameView = gameView;
        setupButtonListener();
    }

    private void setupButtonListener() {
        view.getBackButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(gameView);
            }
        });
        view.getButton1().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.getStage().addActor(view.getWindow());
                view.setText(gameView.getMenuController().talkHistory(App.getCurrentGame().getPlayers().get(0).getUser().getUsername()));
            }
        });
        //TODO
        view.getCloseX().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.getWindow().remove();
            }
        });
    }

}
