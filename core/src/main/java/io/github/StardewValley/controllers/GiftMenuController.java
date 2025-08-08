package io.github.StardewValley.controllers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.models.Game;
import io.github.StardewValley.shared.models.backpack.BackpackableTypeDTO;
import io.github.StardewValley.views.GameView;
import io.github.StardewValley.views.GiftMenu;

public class GiftMenuController {
    private GiftMenu view;
    private GameView gameView;
    private String targetPlayer;
    private String targetNpc;
    private BackpackableTypeDTO backPackableType;
    private GameMenuController gameMenuController = new GameMenuController();
    private int amount;

    public void setView(GiftMenu view, String targetPlayer, GameView gameView) {
        this.targetPlayer = targetPlayer;
        this.view = view;
        this.gameView = gameView;
        setListener();
    }

    public void setView(GiftMenu view, String npc, GameView gameView, Game game) {
        this.view = view;
        this.gameView = gameView;
        this.targetNpc = npc;
        setListener();
    }

    public void handleItemClick(BackpackableTypeDTO backPackableType) {
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
                        view.setText(gameMenuController.gift(targetPlayer, backPackableType.getName(), amount).toString());
                    else
                        view.setText(gameMenuController.giftNPC(targetNpc, backPackableType.getName(), amount).toString());
                } else {
                    view.setText("please choose a gift");
                }
            }
        });

    }


}
