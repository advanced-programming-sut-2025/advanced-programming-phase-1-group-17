package io.github.StardewValley.controllers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.StardewValley.Main;
import io.github.StardewValley.models.App;
import io.github.StardewValley.models.NPCS.NPC;
import io.github.StardewValley.models.Player;
import io.github.StardewValley.views.GameView;
import io.github.StardewValley.views.TalkView;

import java.security.PublicKey;

public class TalkController {
    private TalkView view;
    private GameView gameView;
    private Player targetPlayer;
    private NPC targetNpc;
    private GameMenuController gameMenuController;
    public TalkController (Player player) {
        this.targetPlayer = player;
    }
    public TalkController () {

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
                view.getSend().setVisible(false);
                view.getTextField().setVisible(false);
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(gameView);
            }
        });
        view.getButton9().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.getWindow().remove();
                view.getSend().setVisible(false);
                view.getTextField().setVisible(false);
                view.getStage().addActor(view.getWindow());
                view.getWindow().getTitleLabel().setText("                     messages");
                view.setText(App.getCurrentGame().getCurrentPlayingPlayer().getStringMessage());
            }
        });
        view.getButton1().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.getWindow().remove();
                view.getSend().setVisible(false);
                view.getTextField().setVisible(false);
                targetPlayer = view.getPlayers()[0];
                targetNpc = null;
                view.getWindow().remove();
                view.setError(gameMenuController.friendship(targetPlayer));
            }
        });
        view.getButton2().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.getWindow().remove();
                view.getSend().setVisible(false);
                view.getTextField().setVisible(false);
                targetPlayer = view.getPlayers()[1];
                targetNpc = null;
                view.getWindow().remove();
                view.setError(gameMenuController.friendship(targetPlayer));
            }
        });
        view.getButton3().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.getWindow().remove();
                view.getSend().setVisible(false);
                view.getTextField().setVisible(false);
                targetPlayer = view.getPlayers()[2];
                targetNpc = null;
                view.getWindow().remove();
                view.setError(gameMenuController.friendship(targetPlayer));
            }
        });
        view.getButton4().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.getWindow().remove();
                view.getSend().setVisible(false);
                view.getTextField().setVisible(false);
                targetNpc = App.getCurrentGame().getNPCs().get(0);
                targetPlayer = null;
                view.getWindow().remove();
                view.setError(gameMenuController.friendshipNPCList(targetNpc));
            }
        });
        view.getButton5().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.getWindow().remove();
                view.getSend().setVisible(false);
                view.getTextField().setVisible(false);
                targetNpc = App.getCurrentGame().getNPCs().get(1);
                targetPlayer = null;
                view.getWindow().remove();
                view.setError(gameMenuController.friendshipNPCList(targetNpc));
            }
        });
        view.getButton6().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.getWindow().remove();
                view.getSend().setVisible(false);
                view.getTextField().setVisible(false);
                targetNpc = App.getCurrentGame().getNPCs().get(2);
                targetPlayer = null;
                view.getWindow().remove();
                view.setError(gameMenuController.friendshipNPCList(targetNpc));
            }
        });
        view.getButton7().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.getWindow().remove();
                view.getSend().setVisible(false);
                view.getTextField().setVisible(false);
                targetNpc = App.getCurrentGame().getNPCs().get(3);
                targetPlayer = null;
                view.getWindow().remove();
                view.setError(gameMenuController.friendshipNPCList(targetNpc));
            }
        });
        view.getButton8().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.getWindow().remove();
                view.getSend().setVisible(false);
                view.getTextField().setVisible(false);
                targetNpc = App.getCurrentGame().getNPCs().get(4);
                targetPlayer = null;
                view.getWindow().remove();
                view.setText(gameMenuController.friendshipNPCList(targetNpc));
            }
        });

        view.getButton10().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.getWindow().remove();
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
                view.getSend().setVisible(false);
                view.getTextField().setVisible(false);
                if (targetPlayer == null) {
                    view.setError("please choose a player");
                    return;
                }

                String userName = targetPlayer.getUser().getUsername();
                view.getStage().addActor(view.getWindow());
                view.getWindow().getTitleLabel().setText("              Gift History with");
                view.setText(gameMenuController.giftHistory(userName));
            }
        });
        view.getButton13().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.getWindow().remove();
                view.getSend().setVisible(false);
                view.getTextField().setVisible(false);
                if (targetPlayer == null) {
                    view.setError("please choose a player");
                    return;
                }
                String userName = targetPlayer.getUser().getUsername();
                view.getStage().addActor(view.getWindow());
                view.getWindow().getTitleLabel().setText("                 Talk History with ");
                view.setText(gameMenuController.talkHistory(userName));
            }
        });
        view.getButton14().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.getWindow().remove();
                view.getSend().setVisible(false);
                view.getTextField().setVisible(false);
                if (targetPlayer == null) {
                    view.setError("please choose a player");
                    return;
                }
                view.setError(gameMenuController.sideBySide(App.getCurrentGame().getCurrentPlayingPlayer(), targetPlayer) ?
                    "please enter your message" : "you can't talk from this distance");
                if (gameMenuController.sideBySide(App.getCurrentGame().getCurrentPlayingPlayer(), targetPlayer)) {
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
                view.setError(gameMenuController.talk(targetPlayer.getUser().getUsername(), Message).toString());
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
