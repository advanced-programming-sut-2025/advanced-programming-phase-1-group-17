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
    private final double coin = GameClient.getPlayer().getCoin();
    private boolean register = false;

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
                register = false;
                if (tradeMenu.getCoin() + 10 > coin) {
                    return;
                }
                tradeMenu.setCoin(String.valueOf(tradeMenu.getCoin() + 10));
                tradeMenu.refreshLabel();
            }
        });
        tradeMenu.getRemoveCoin().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                register = false;
                if (tradeMenu.getCoin() - 10 < 0) {
                    tradeMenu.refreshLabel();
                    return;
                }
                tradeMenu.setCoin(String.valueOf(tradeMenu.getCoin() - 10));
                tradeMenu.refreshLabel();
            }
        });
        tradeMenu.getAddItem().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                register = false;
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
                register = false;
                if (backPackableType != null) {
                    tradeMenu.removeItem(backPackableType.getName());
                } else {
                    tradeMenu.setText("please choose a item");
                }
                tradeMenu.refreshLabel();
            }
        });
        tradeMenu.getBack().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                register = false;
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(gameView);
            }
        });
        tradeMenu.getAddItem2().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                register = false;
                if (tradeMenu.getNameItem().getText().isEmpty()) {
                    return;
                }
                String item = tradeMenu.getNameItem().getText();
                String amount = tradeMenu.getAmountItem().getText();
                try {
                    tradeMenu.getRequired().put(item, Integer.parseInt(amount));
                    tradeMenu.refreshRequired();
                } catch (Exception e) {
                    tradeMenu.setText(e.getMessage());
                }
            }
        });
        tradeMenu.getRemoveItem2().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                register = false;
                if (tradeMenu.getRequired().isEmpty()) {
                    return;
                }
                for (String item : tradeMenu.getRequired().keySet()) {
                    tradeMenu.getRequired().remove(item);
                    break;
                }
                tradeMenu.refreshRequired();
            }
        });
        tradeMenu.getRegistertheOffer().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(gameView);
            }
        });

    }

    public boolean isRegister() {
        return register;
    }

    public void setRegister(boolean register) {
        this.register = register;
    }
}
