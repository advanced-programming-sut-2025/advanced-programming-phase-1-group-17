package io.github.StardewValley.views;

import com.badlogic.gdx.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.GameAssetManager;
import io.github.StardewValley.Main;
import io.github.StardewValley.controllers.*;
import io.github.StardewValley.models.App;
import io.github.StardewValley.display;
import io.github.StardewValley.models.Game;
import io.github.StardewValley.models.Player;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import io.github.StardewValley.models.Result;
import io.github.StardewValley.models.enums.Gender;
import io.github.StardewValley.models.market.StoreType;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

import io.github.StardewValley.models.TimeAndDate;

import java.util.Scanner;

public class GameView implements Screen, InputProcessor {
    private Stage stage;
    private final GameController controller;
    private final GameMenuController menuController;
    private HUD hud;
    private Window window = new Window("interactions", GameAssetManager.getGameAssetManager().getSkin());
    private TextButton talkButton = new TextButton("Talk", GameAssetManager.getGameAssetManager().getSkin());
    private TextButton tradeButton = new TextButton("Trade", GameAssetManager.getGameAssetManager().getSkin());
    private TextButton HugButton = new TextButton("Hug", GameAssetManager.getGameAssetManager().getSkin());
    private TextButton giftButton = new TextButton("Gift", GameAssetManager.getGameAssetManager().getSkin());
    private TextButton askMarriageButton = new TextButton("Ask Marriage", GameAssetManager.getGameAssetManager().getSkin());
    public Table content = new Table(GameAssetManager.getGameAssetManager().getSkin());
    private Label error = new Label("",GameAssetManager.getGameAssetManager().getSkin());
    private boolean activeWindow = true;

    public GameView(GameController controller, GameMenuController menuController) {
        this.controller = controller;
        this.menuController = menuController;
//        display.run(1,1,300);
        window.add(talkButton).width(60).height(30).pad(10).row();
        window.add(tradeButton).width(60).height(30).pad(10).row();
        window.add(giftButton).width(60).height(30).pad(10).row();
        window.add(HugButton).width(60).height(30).pad(10).row();
        window.add(askMarriageButton).width(60).height(30).pad(10).row();

        this.controller.setView(this);
        Main.setGameView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);

        Gdx.input.setInputProcessor(multiplexer);
        this.hud = new HUD();

    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);
        controller.getCamera().update();
        Main.getBatch().setProjectionMatrix(controller.getCamera().combined);
        Main.getBatch().begin();
        controller.updateGame(v);
        window.remove();

        if (activeWindow)
            updateInteractions();

        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new MapView(new MapViewController(), this));

        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            Main.getMain().getScreen().dispose();
            ScreenUtils.clear(0, 0, 0, 1);
            Main.getMain().setScreen(new TalkView(new TalkController(), GameAssetManager.getGameAssetManager().getSkin(), this));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
            try {
                menuController.nextTurn();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //TODO handle input key

        Main.getBatch().end();

        // فقط این خط کافیه
        hud.render(Main.getBatch());
        error.setPosition(10,1000);
        stage.addActor(error);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }


    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }


    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 worldCoordinates = controller.getCamera().unproject(new Vector3(screenX, screenY, 0));
        for (Map.Entry<StoreType, Rectangle> entry : controller.getStoreBounds().entrySet()) {
            if (entry.getValue().contains(worldCoordinates.x, worldCoordinates.y)) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new StoreMenu(new StoreMenuController(),
                    GameAssetManager.getGameAssetManager().getSkin(),
                    this, entry.getKey()));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        controller.setKey(keycode, true);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        controller.setKey(keycode, false);
        return true;
    }

    public GameController getController() {
        return controller;
    }

    public GameMenuController getMenuController() {
        return menuController;
    }

    public void updateInteractions() {
        ArrayList<Player> targetPlayers = new ArrayList<>();
        Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
        for (Player player : App.getCurrentGame().getPlayers()) {
            if (player.getUser().getUsername().equals("NPC") || player.equals(currentPlayer)) continue;
            if (menuController.sideBySide(player, currentPlayer)) {
                targetPlayers.add(player);
            }
        }
        if (!targetPlayers.isEmpty()) {
            for (Player player : targetPlayers) {
                showInteractionWindow(player,this);
            }
        }

    }

    private void showInteractionWindow(Player targetPlayer,GameView gameView) {


        talkButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().getScreen().dispose();
                ScreenUtils.clear(0, 0, 0, 1);
                Main.getMain().setScreen(new TalkView(new TalkController(targetPlayer), GameAssetManager.getGameAssetManager().getSkin(), gameView));
            }
        });

        HugButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Result r = menuController.hug(targetPlayer.getUser().getUsername());
                error.setText(r.toString());
                com.badlogic.gdx.utils.Timer.schedule(new com.badlogic.gdx.utils.Timer.Task() {
                    @Override
                    public void run() {
                        if (r.isSuccessful()) {
                            if (App.getCurrentGame().getCurrentPlayingPlayer().getUser().getGender().equals(
                                targetPlayer.getUser().getGender())) {
                                if (targetPlayer.getUser().getGender().equals(Gender.Male)) {
                                    //TODO
                                }else{

                                }


                            }else if (!App.getCurrentGame().getCurrentPlayingPlayer().getUser().getGender().equals(
                                targetPlayer.getUser().getGender())) {

                            }
                        }
                        error.setText("");
                    }
                }, 2);

            }
        });
        Vector3 worldPos = new Vector3(targetPlayer.getX(), targetPlayer.getY(), 0);
        Vector3 screenPos = controller.getCamera().project(worldPos);
        window.setMovable(true);
        window.setResizable(false);
        window.setSize(250,300);
        window.setPosition(screenPos.x - window.getWidth() / 2f, screenPos.y);
        window.add(content).expand().center();

        stage.addActor(window);
    }
}
