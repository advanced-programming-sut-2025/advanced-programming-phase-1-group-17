package io.github.StardewValley.controllers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.StardewValley.GameClient;
import io.github.StardewValley.Main;
import io.github.StardewValley.views.GameView;
import io.github.StardewValley.views.TalkView;

import java.util.Objects;

public class TalkController {
    private TalkView view;
    private GameView gameView;
    private String targetPlayer;
    private GameMenuController gameMenuController;

    public TalkController(String player) {
        this.targetPlayer = player;
    }

    public TalkController() {

    }

    public void setView(TalkView view, GameView gameView) {
        if (targetPlayer != null) {
            view.getSend().setVisible(true);
            view.getTextField().setVisible(true);
        }
        this.view = view;
        this.gameView = gameView;
        this.gameMenuController = new GameMenuController();
        setupButtonListener();
    }

    private void setupButtonListener() {
        view.getBackButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                view.getWindow().remove();
                view.getButton16().setVisible(false);
                view.getGiftNumber().setVisible(false);
                view.getGiftRate().setVisible(false);
                view.getSend().setVisible(false);
                view.getTextField().setVisible(false);
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(gameView);
            }
        });
        view.getButton9().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.getWindow().remove();
                view.getButton16().setVisible(false);
                view.getGiftNumber().setVisible(false);
                view.getGiftRate().setVisible(false);
                view.getSend().setVisible(false);
                view.getTextField().setVisible(false);
                GameClient.getGameStateApiClient().setNewMessage(false);
                gameView.setError("");
                view.getStage().addActor(view.getWindow());
                view.getWindow().getTitleLabel().setText("                     messages");
                view.setText(gameMenuController.showMessage().getMessage());
            }
        });
        view.getButton1().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.getWindow().remove();
                view.getButton16().setVisible(false);
                view.getGiftNumber().setVisible(false);
                view.getGiftRate().setVisible(false);
                view.getSend().setVisible(false);
                view.getTextField().setVisible(false);
                targetPlayer = view.getPlayers()[0];
                view.getWindow().remove();
                view.setError(gameMenuController.friendship(targetPlayer));
            }
        });
        view.getButton2().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.getWindow().remove();
                view.getButton16().setVisible(false);
                view.getGiftNumber().setVisible(false);
                view.getGiftRate().setVisible(false);
                view.getSend().setVisible(false);
                view.getTextField().setVisible(false);
                targetPlayer = view.getPlayers()[1];
                view.getWindow().remove();
                view.setError(gameMenuController.friendship(targetPlayer));
            }
        });
        view.getButton3().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.getWindow().remove();
                view.getButton16().setVisible(false);
                view.getGiftNumber().setVisible(false);
                view.getGiftRate().setVisible(false);
                view.getSend().setVisible(false);
                view.getTextField().setVisible(false);
                targetPlayer = view.getPlayers()[2];
                view.getWindow().remove();
                view.setError(gameMenuController.friendship(targetPlayer));
            }
        });

        view.getButton10().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.getWindow().remove();
                view.getButton16().setVisible(false);
                view.getGiftNumber().setVisible(false);
                view.getGiftRate().setVisible(false);
                view.getSend().setVisible(false);
                view.getTextField().setVisible(false);
                view.getStage().addActor(view.getWindow());
                view.getWindow().getTitleLabel().setText("                     Trade History");
                view.setText(gameMenuController.tradeHistory());
            }
        });
        view.getButton11().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.getWindow().remove();
                view.getButton16().setVisible(false);
                view.getGiftNumber().setVisible(false);
                view.getGiftRate().setVisible(false);
                view.getSend().setVisible(false);
                view.getTextField().setVisible(false);
                view.getStage().addActor(view.getWindow());
                view.getWindow().getTitleLabel().setText("                     Trade List");
                view.setText(gameMenuController.tradeList());

            }
        });
        view.getButton12().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.getWindow().remove();
                view.getButton16().setVisible(false);
                view.getGiftNumber().setVisible(false);
                view.getGiftRate().setVisible(false);
                view.getSend().setVisible(false);
                view.getTextField().setVisible(false);
                if (targetPlayer == null) {
                    view.setError("please choose a player");
                    return;
                }

                String userName = targetPlayer;
                view.getStage().addActor(view.getWindow());
                view.getWindow().getTitleLabel().setText("              Gift History with");
                view.setText(gameMenuController.giftHistory(userName));
            }
        });
        view.getButton13().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.getWindow().remove();
                view.getButton16().setVisible(false);
                view.getGiftNumber().setVisible(false);
                view.getGiftRate().setVisible(false);
                view.getSend().setVisible(false);
                view.getTextField().setVisible(false);
                if (targetPlayer == null) {
                    view.setError("please choose a player");
                    return;
                }
                String userName = targetPlayer;
                view.getStage().addActor(view.getWindow());
                view.getWindow().getTitleLabel().setText("                 Talk History with ");
                view.setText(gameMenuController.talkHistory(userName));
            }
        });
        view.getButton14().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.getWindow().remove();
                view.getButton16().setVisible(false);
                view.getGiftNumber().setVisible(false);
                view.getGiftRate().setVisible(false);
                view.getSend().setVisible(false);
                view.getTextField().setVisible(false);
                if (targetPlayer == null) {
                    view.setError("please choose a player");
                    return;
                }
                view.setError(Objects.equals(GameClient.getGameStateApiClient().getNearbyPlayer(), targetPlayer) ?
                    "please enter your message" : "you can't talk from this distance");
                if (Objects.equals(GameClient.getGameStateApiClient().getNearbyPlayer(), targetPlayer)) {
                    view.getSend().setVisible(true);
                    view.getTextField().setVisible(true);
                }

            }
        });
        view.getSend().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (targetPlayer == null) {
                    view.setError("please choose a player");
                    return;
                }
                String Message = view.getTextField().getText();
                if (Message.isEmpty()) return;
                view.setError(gameMenuController.talk(targetPlayer, Message).toString());
            }
        });
        view.getButton15().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.getWindow().remove();
                view.getButton16().setVisible(true);
                view.getGiftNumber().setVisible(true);
                view.getGiftRate().setVisible(true);
            }
        });
        view.getButton16().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                String number = view.getGiftNumber().getText();
                String rate = view.getGiftRate().getText();
                if (number.isEmpty() || rate.isEmpty()) {
                    view.setError("please enter number and rate");
                    return;
                }
                view.setError(gameMenuController.giftRate(number, rate).toString());

                view.getButton16().setVisible(false);
                view.getGiftNumber().setVisible(false);
                view.getGiftRate().setVisible(false);

            }
        });
        view.getButton17().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (targetPlayer == null) {
                    view.setError("please choose a player");
                    return;
                }
                String userName = targetPlayer;
                view.setError(gameMenuController.respond("accept", userName).toString());
            }
        });
        view.getButton18().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (targetPlayer == null) {
                    view.setError("please choose a player");
                    return;
                }
                String userName = targetPlayer;
                view.setError(gameMenuController.respond("reject", userName).toString());
            }
        });

        view.getCloseX().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.getWindow().remove();
            }
        });
    }

}
