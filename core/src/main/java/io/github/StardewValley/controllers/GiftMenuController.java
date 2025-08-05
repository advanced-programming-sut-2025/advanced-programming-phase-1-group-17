package io.github.StardewValley.controllers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.models.App;
import io.github.StardewValley.shared.models.backpack.BackPackableType;
import io.github.StardewValley.shared.models.NPCS.NPC;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.views.GameView;
import io.github.StardewValley.views.GiftMenu;

public class GiftMenuController {
    private GiftMenu view;
    private GameView gameView;
    private Player targetPlayer;
    private Player currentPlayer;
    private NPC targetNpc;
    private BackPackableType backPackableType;
    private GameMenuController gameMenuController = new GameMenuController();
    private int amount;

    public void setView(GiftMenu view, Player targetPlayer, GameView gameView) {
        this.targetPlayer = targetPlayer;
        this.view = view;
        this.gameView = gameView;
        //TODO
        //currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
        currentPlayer = null;
        setListener();
    }

    public void setView(GiftMenu view, NPC npc, GameView gameView) {
        this.view = view;
        this.gameView = gameView;
        this.targetNpc = npc;
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
                    if (targetPlayer != null)
                        view.setText(gameMenuController.gift(targetPlayer.getUser().getUsername(), backPackableType.getName(), amount).toString());
                    else
                        view.setText(gameMenuController.giftNPC(targetNpc, backPackableType.getName(), amount).toString());
                } else {
                    view.setText("please choose a gift");
                }
            }
        });

    }


}
