package io.github.StardewValley.controllers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.StardewValley.GameAssetManager;
import io.github.StardewValley.Main;
import io.github.StardewValley.models.App;
import io.github.StardewValley.models.BackPackableType;
import io.github.StardewValley.models.Player;
import io.github.StardewValley.views.GameView;
import io.github.StardewValley.views.GiftMenu;
import io.github.StardewValley.views.SignUpMenu;

import javax.swing.*;
import javax.swing.plaf.SplitPaneUI;

public class GiftMenuController {
    private GiftMenu view;
    private GameView gameView;
    private Player targetPlayer;
    private Player currentPlayer;
    private BackPackableType backPackableType;
    private GameMenuController gameMenuController = new GameMenuController();
    private int amount;

    public void setView(GiftMenu view, Player targetPlayer, GameView gameView) {
        this.targetPlayer = targetPlayer;
        this.view = view;
        this.gameView = gameView;
        currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
        setListener();
    }

    public void handleItemClick(BackPackableType backPackableType) {
        this.backPackableType = backPackableType;
    }

    public void setListener() {
        view.getBackButton().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(gameView);
            }
        });
        view.getGift().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (backPackableType != null) {
                    String amount = view.getAmountTextField().getText().trim();
                    view.setText(gameMenuController.gift(targetPlayer.getUser().getUsername(), backPackableType.getName(), amount).toString());
                }else{
                    view.setText("please choose a gift");
                }
            }
        });

    }


}
