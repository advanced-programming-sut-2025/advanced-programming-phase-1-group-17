package io.github.StardewValley.controllers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.StardewValley.GameClient;
import io.github.StardewValley.Main;
import io.github.StardewValley.views.GameView;
import io.github.StardewValley.views.TradeResponseView;

import java.util.HashMap;

public class TradeResponseController {
    private TradeResponseView view;
    private GameView gameView;
    private String targetPlayer;

    public void setView(TradeResponseView view, GameView gameView, String targetPlayer) {
        this.view = view;
        this.gameView = gameView;
        this.targetPlayer = targetPlayer;
        setListener();
    }

    public void setListener() {
        view.getAcceptButton().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                GameClient.getGameStateApiClient().acceptTrade(view.getSuggestions(), view.getRequests(), targetPlayer);
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(gameView);
            }
        });
        view.getRejectButton().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                GameClient.getGameStateApiClient().updateRequestAndSuggestions(new HashMap<String, Integer>(), new HashMap<String, Integer>());
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(gameView);
            }
        });
    }
}
