package io.github.StardewValley.views;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.GameAssetManager;
import io.github.StardewValley.Main;
import io.github.StardewValley.controllers.*;
import io.github.StardewValley.models.App;
import io.github.StardewValley.models.Player;
import io.github.StardewValley.models.Result;
import io.github.StardewValley.models.enums.Gender;
import io.github.StardewValley.models.market.StoreType;

import java.util.ArrayList;
import java.util.Map;

public class GameView implements Screen, InputProcessor {
    private Stage stage;
    private final GameController controller;
    private final GameMenuController menuController;
    private HUD hud;
    private Window window;
    private TextButton talkButton, tradeButton, hugButton, giftButton, givingFlower, askMarriageButton;
    private Label error;
    private Table content;
    private boolean activeWindow = true;
    private Player currentTargetPlayer;

    public GameView(GameController controller, GameMenuController menuController) {
        this.controller = controller;
        this.menuController = menuController;
        this.controller.setView(this);
        Main.setGameView(this);

        initUI();
        setupListeners(this);
    }

    private void initUI() {
        Skin skin = GameAssetManager.getGameAssetManager().getSkin();

        window = new Window("Interactions", skin);
        talkButton = new TextButton("Talk", skin);
        tradeButton = new TextButton("Trade", skin);
        hugButton = new TextButton("Hug", skin);
        giftButton = new TextButton("Gift", skin);
        givingFlower = new TextButton("Giving Flower", skin);
        askMarriageButton = new TextButton("Ask Marriage", skin);
        error = new Label("", skin);
        content = new Table(skin);

        window.add(talkButton).width(60).height(30).pad(10).row();
        window.add(tradeButton).width(60).height(30).pad(10).row();
        window.add(giftButton).width(60).height(30).pad(10).row();
        window.add(hugButton).width(60).height(30).pad(10).row();
        window.add(givingFlower).width(60).height(30).pad(10).row();
        window.add(askMarriageButton).width(60).height(30).pad(10).row();
    }

    private void setupListeners(GameView gameView) {
        talkButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (currentTargetPlayer != null) {
                    Main.getMain().getScreen().dispose();
                    Main.getMain().setScreen(new TalkView(new TalkController(currentTargetPlayer),
                        GameAssetManager.getGameAssetManager().getSkin(), GameView.this));
                }
            }
        });

        hugButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (currentTargetPlayer == null) return;

                Result r = menuController.hug(currentTargetPlayer.getUser().getUsername());
                error.setText(r.toString());

                com.badlogic.gdx.utils.Timer.schedule(new com.badlogic.gdx.utils.Timer.Task() {
                    @Override
                    public void run() {
                        if (r.isSuccessful()) {
                            Gender playerGender = App.getCurrentGame().getCurrentPlayingPlayer().getUser().getGender();
                            Gender targetGender = currentTargetPlayer.getUser().getGender();
                            String imagePath;
                            if (playerGender == targetGender) {
                                imagePath = (targetGender == Gender.Male) ? "hug1.png" : "hug3.png";
                            } else {
                                imagePath = "hug2.png";
                            }

                            Image image = new Image(new Texture(Gdx.files.internal(imagePath)));
                            image.setSize(500, 500);
                            image.setPosition(stage.getWidth() / 2f - image.getWidth() / 2f,
                                stage.getHeight() / 2f - image.getHeight() / 2f);

                            window.setVisible(false);
                            stage.addActor(image);

                            com.badlogic.gdx.utils.Timer.schedule(new com.badlogic.gdx.utils.Timer.Task() {
                                @Override
                                public void run() {
                                    image.remove();
                                    window.setVisible(true);
                                }
                            }, 3);
                        }
                        error.setText("");
                    }
                }, 2);
            }
        });
        giftButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (currentTargetPlayer == null) return;
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new GiftMenu(App.getCurrentGame().getCurrentPlayingPlayer(), new GiftMenuController(),
                    GameAssetManager.getGameAssetManager().getSkin(), currentTargetPlayer, gameView,null));
            }
        });
        givingFlower.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (currentTargetPlayer == null) return;
                Result r = menuController.flower(currentTargetPlayer.getUser().getUsername());
                error.setText(r.toString());
                com.badlogic.gdx.utils.Timer.schedule(new com.badlogic.gdx.utils.Timer.Task() {
                    @Override
                    public void run() {
                        if (r.isSuccessful()) {
                            Image image = new Image(new Texture(Gdx.files.internal("Flower.jpg")));
                            image.setSize(500, 500);
                            image.setPosition(stage.getWidth() / 2f - image.getWidth() / 2f,
                                stage.getHeight() / 2f - image.getHeight() / 2f);

                            window.setVisible(false);
                            stage.addActor(image);

                            com.badlogic.gdx.utils.Timer.schedule(new com.badlogic.gdx.utils.Timer.Task() {
                                @Override
                                public void run() {
                                    image.remove();
                                    window.setVisible(true);
                                }
                            }, 3);

                        }
                    }
                }, 2);


            }
        });
        askMarriageButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (currentTargetPlayer == null) return;
                Result r = menuController.askMarriage(currentTargetPlayer.getUser().getUsername(),"Ring");
                error.setText(r.toString());
            }
        });
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        InputMultiplexer multiplexer = new InputMultiplexer(stage, this);
        Gdx.input.setInputProcessor(multiplexer);
        hud = new HUD();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        controller.getCamera().update();

        Main.getBatch().setProjectionMatrix(controller.getCamera().combined);
        Main.getBatch().begin();
        controller.updateGame(delta);
        Main.getBatch().end();
        if (App.getCurrentGame().getCurrentPlayingPlayer().isNewMessage()) {
            error.setText("you have a new message");
        }
        if (activeWindow) updateInteractions();

        error.setPosition(10, 1000);
        stage.addActor(error);
        hud.render(Main.getBatch());
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        handleKeys();
    }

    private void handleKeys() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new MapView(new MapViewController(), this));
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new TalkView(new TalkController(),
                GameAssetManager.getGameAssetManager().getSkin(), this));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.N)) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new NPCMenu(new NPCMenuController()
                ,GameAssetManager.getGameAssetManager().getSkin(), this));
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
            try {
                menuController.nextTurn();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void updateInteractions() {
        ArrayList<Player> targetPlayers = new ArrayList<>();
        Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();

        for (Player player : App.getCurrentGame().getPlayers()) {
            if (!player.getUser().getUsername().equals("NPC") && !player.equals(currentPlayer)) {
                if (menuController.sideBySide(player, currentPlayer)) {
                    targetPlayers.add(player);
                }
            }
        }
        if (targetPlayers.isEmpty()) {
            window.setVisible(false);
        }

        if (!targetPlayers.isEmpty()) {
            window.setVisible(true);
            currentTargetPlayer = targetPlayers.get(0);
            showInteractionWindow(currentTargetPlayer);
        }
    }

    private void showInteractionWindow(Player targetPlayer) {
        Vector3 screenPos = controller.getCamera().project(new Vector3(targetPlayer.getX(), targetPlayer.getY(), 0));
        window.setSize(250, 350);
        window.setPosition(screenPos.x - window.getWidth() / 2f, screenPos.y);
        window.setMovable(true);
        window.setResizable(false);

        if (!stage.getActors().contains(window, true)) {
            stage.addActor(window);
        }
    }

    @Override
    public void resize(int width, int height) {
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
                    GameAssetManager.getGameAssetManager().getSkin(), this, entry.getKey()));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int x, int y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int x, int y) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
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

    public void setError(String error) {
        this.error.setText(error);
    }

}
