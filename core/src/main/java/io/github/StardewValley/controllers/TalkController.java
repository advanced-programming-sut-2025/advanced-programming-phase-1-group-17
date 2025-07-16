package io.github.StardewValley.controllers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.StardewValley.Main;
import io.github.StardewValley.models.App;
import io.github.StardewValley.models.NPCS.NPC;
import io.github.StardewValley.models.Player;
import io.github.StardewValley.views.GameView;
import io.github.StardewValley.views.TalkView;

public class TalkController {
    private TalkView view;
    private GameView gameView;
    private Player targetPlayer;
    private NPC targetNpc;
    private GameMenuController gameMenuController;

    public void setView(TalkView view, GameView gameView) {
        this.view = view;
        this.gameView = gameView;
        this.gameMenuController = new GameMenuController();
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
        view.getButton9().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.getStage().addActor(view.getWindow());
                view.getWindow().getTitleLabel().setText("                     messages");
                view.setText(App.getCurrentGame().getCurrentPlayingPlayer().getStringMessage());
            }
        });
        view.getButton1().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                targetPlayer = view.getPlayers()[0];
                targetNpc = null;
                view.getWindow().remove();
            }
        });
        view.getButton2().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                targetPlayer = view.getPlayers()[1];
                targetNpc = null;
                view.getWindow().remove();
            }
        });
        view.getButton3().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                targetPlayer = view.getPlayers()[2];
                targetNpc = null;
                view.getWindow().remove();
            }
        });
        view.getButton4().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                targetNpc = App.getCurrentGame().getNPCs().get(0);
                targetPlayer = null;
                view.getWindow().remove();
            }
        });
        view.getButton5().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                targetNpc = App.getCurrentGame().getNPCs().get(1);
                targetPlayer = null;
                view.getWindow().remove();
            }
        });
        view.getButton6().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                targetNpc = App.getCurrentGame().getNPCs().get(2);
                targetPlayer = null;
                view.getWindow().remove();
            }
        });
        view.getButton7().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                targetNpc = App.getCurrentGame().getNPCs().get(3);
                targetPlayer = null;
                view.getWindow().remove();
            }
        });
        view.getButton8().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                targetNpc = App.getCurrentGame().getNPCs().get(4);
                targetPlayer = null;
                view.getWindow().remove();
            }
        });

        view.getButton10().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.getStage().addActor(view.getWindow());
                view.getWindow().getTitleLabel().setText("                     Trade History");
                view.setText(gameMenuController.tradeHistory());
            }
        });
        view.getButton11().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.getStage().addActor(view.getWindow());
                view.getWindow().getTitleLabel().setText("                     Trade List");
                view.setText(gameMenuController.tradeList());

            }
        });
        view.getButton12().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (targetPlayer == null) {
                    view.setError("please choose a player");
                    return;
                }

                String userName = targetPlayer.getUser().getUsername();
                view.getStage().addActor(view.getWindow());
                view.getWindow().getTitleLabel().setText("              Gift History with"+ " " + userName);
                view.setText(gameMenuController.giftHistory(userName));
            }
        });
        view.getButton13().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (targetPlayer == null) {
                    view.setError("please choose a player");
                    return;
                }
                String userName = targetPlayer.getUser().getUsername();
                view.getStage().addActor(view.getWindow());
                view.getWindow().getTitleLabel().setText("                 Talk History with "+userName);
                view.setText(gameMenuController.talkHistory(userName));
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
