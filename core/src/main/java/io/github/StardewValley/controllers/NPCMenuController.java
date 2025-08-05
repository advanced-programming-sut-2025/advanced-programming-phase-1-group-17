package io.github.StardewValley.controllers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.StardewValley.GameAssetManagerClient;
import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.models.App;
import io.github.StardewValley.shared.models.NPCS.NPC;
import io.github.StardewValley.shared.models.Result;
import io.github.StardewValley.views.GameView;
import io.github.StardewValley.views.GiftMenu;
import io.github.StardewValley.views.NPCMenu;

public class NPCMenuController {
    private NPCMenu view;
    private GameView gameView;
    private NPC targetNpc;
    private GameMenuController gameMenuController;

    public void setView(NPCMenu view, GameView gameView) {
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
        view.getButton4().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                targetNpc = App.getCurrentGame().getNPCs().get(0);
                view.setError(gameMenuController.friendshipNPCList(targetNpc));
            }
        });
        view.getButton5().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                targetNpc = App.getCurrentGame().getNPCs().get(1);
                view.setError(gameMenuController.friendshipNPCList(targetNpc));
            }
        });
        view.getButton6().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                targetNpc = App.getCurrentGame().getNPCs().get(2);
                view.setError(gameMenuController.friendshipNPCList(targetNpc));
            }
        });
        view.getButton7().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                targetNpc = App.getCurrentGame().getNPCs().get(3);
                view.setError(gameMenuController.friendshipNPCList(targetNpc));
            }
        });
        view.getButton8().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                targetNpc = App.getCurrentGame().getNPCs().get(4);
                view.setError(gameMenuController.friendshipNPCList(targetNpc));
            }
        });
        view.getButton1().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (targetNpc == null) return;
                Main.getMain().getScreen().dispose();
                //TODO
//                Main.getMain().setScreen(new GiftMenu(App.getCurrentGame().getCurrentPlayingPlayer(), new GiftMenuController(),
//                      GameAssetManagerClient.getGameAssetManager().getSkin(), null, gameView,targetNpc));
            }
        });
        view.getButton10().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (targetNpc == null) view.setError("please select a npc");
                else {
                    view.showQuestDialog(targetNpc);
                }
            }
        });


    }
    public void onQuestSelected(NPC npc, int index) {
        Result result = gameMenuController.questFinish(String.valueOf(index));
        if (result.isSuccessful()) {
            view.getQuestFinishLabel().setText(result.toString());
            view.showQuestFinishAnimation();

        }else {
            view.setError(result.toString());
        }
    }
}
