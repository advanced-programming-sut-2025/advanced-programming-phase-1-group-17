package io.github.StardewValley.controllers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.StardewValley.GameClient;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.models.backpack.BackpackableTypeDTO;
import io.github.StardewValley.views.GameView;
import io.github.StardewValley.views.TradeMenu;


public class TradeMenuController {
    private BackpackableTypeDTO backPackableType;
    private TradeMenu tradeMenu;
    private GameView gameView;
    private final int coin = GameClient.getPlayer().getCoin();
    public void setView(TradeMenu tradeMenu, GameView gameView) {
        this.tradeMenu = tradeMenu;
        this.gameView = gameView;
        setListener();
    }
    public void handleItemClick(BackpackableTypeDTO backPackableTypeDTO) {
        backPackableType = backPackableTypeDTO;
    }
    public void setListener() {
        tradeMenu.getAddCoin().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (tradeMenu.getCoin() + 10  > coin) {
                    return;
                }
                tradeMenu.setCoin(String.valueOf(tradeMenu.getCoin() + 10));
                tradeMenu.refreshLabel();
            }
        });
        tradeMenu.getRemoveCoin().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (tradeMenu.getCoin() - 10  < 0) {
                    tradeMenu.refreshLabel();
                    return;
                }
                tradeMenu.setCoin(String.valueOf(tradeMenu.getCoin() - 10));
                tradeMenu.refreshLabel();
            }
        });
        tradeMenu.getAddItem().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (backPackableType != null) {
                    tradeMenu.addItem(backPackableType);
                } else {
                    tradeMenu.setText("please choose a item");
                }
                tradeMenu.refreshLabel();
            }
        });
        tradeMenu.getRemoveitem().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (backPackableType != null) {
                    tradeMenu.removeItem(backPackableType.getName());
                }else {
                    tradeMenu.setText("please choose a item");
                }
                tradeMenu.refreshLabel();
            }
        });
        tradeMenu.getBack().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(gameView);
            }
        });
    }
}
