package io.github.StardewValley.views;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.GameAssetManagerClient;
import io.github.StardewValley.GameClient;
import io.github.StardewValley.Main;
import io.github.StardewValley.controllers.*;
import io.github.StardewValley.shared.dto.HandleWorldClickResponse;
import io.github.StardewValley.shared.models.PlayerDto;
import io.github.StardewValley.shared.models.Result;
import java.util.Objects;

import io.github.StardewValley.shared.models.enums.Gender;

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
    private String currentTargetPlayer;
    private boolean isPlayerNearNPC = false;
    private float dialogueTimer = 0;
    private int dialogueCharIndex = 0;
    private String currentDialogueText = "";
    private Label dialogueLabel;
    private String currentNPC = null;
    private boolean dialogueActive = false;
    private Table dialogueTable;
    private BitmapFont font;
    private boolean isSthBuilding = false;


    public GameView(GameController controller, GameMenuController menuController) {
        this.font = new BitmapFont();
        int i = 0;
        this.controller = controller;
        this.menuController = menuController;
        this.controller.setView(this);
        Main.setGameView(this);

        initUI();
        setupListeners(this);
    }

    private void initUI() {
        Skin skin = GameAssetManagerClient.getGameAssetManager().getSkin();
        dialogueLabel = new Label("", skin);
        dialogueLabel.setColor(Color.WHITE);
        dialogueLabel.setFontScale(1.2f);
        dialogueLabel.setVisible(false);
        dialogueTable = new Table();

        dialogueTable.top();
        dialogueTable.add(dialogueLabel).padTop(50);


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
                        GameAssetManagerClient.getGameAssetManager().getSkin(), GameView.this));
                }
            }
        });

        hugButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (currentTargetPlayer == null) return;

                Result r = menuController.hug(currentTargetPlayer);
                error.setText(r.toString());

                com.badlogic.gdx.utils.Timer.schedule(new com.badlogic.gdx.utils.Timer.Task() {
                    @Override
                    public void run() {
                        if (r.isSuccessful()) {
                            Gender playerGender = GameClient.getPlayer().getUser().getGender();
                            Gender targetGender = GameClient.getGameStateApiClient().getGender(currentTargetPlayer).equals("Male") ? Gender.Male : Gender.Female;
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
                Main.getMain().setScreen(new GiftMenu( new GiftMenuController(),
                      GameAssetManagerClient.getGameAssetManager().getSkin(), currentTargetPlayer, gameView, null));
            }
        });
        givingFlower.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (currentTargetPlayer == null) return;
                Result r = menuController.flower(currentTargetPlayer);
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
                Result r = menuController.askMarriage(currentTargetPlayer, "Ring");
                error.setText(r.toString());
            }
        });
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        dialogueTable.setFillParent(true);
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
        //TODO handle playe
//        controller.handlePlayerInput();
        //TODO handel app.getCurrentGame()...
//        for(AnimalPlace animalPlace : App.getCurrentGame().getCurrentPlayingPlayer().getPlayerMap().getAnimalPlaces()) {
//            animalPlace.render(delta);
//            for(Animal animal:animalPlace.getAnimals()){
//                animal.render(Main.getBatch(),delta);
//                animal.update(delta);
//            }
//        }
        font.draw(Main.getBatch(),"hello",120,120);
        Main.getBatch().end();
        if (GameClient.getPlayer().isNewMessage()) {
            error.setText("you have a new message");
        }
        if (activeWindow) updateInteractions();
        updateDialogue(delta);

        stage.addActor(dialogueTable);
        error.setPosition(10, 1000);
        stage.addActor(error);
        hud.render(Main.getBatch(), delta);
        //TODO handle player
//        controller.handlePlayerInput();
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
                GameAssetManagerClient.getGameAssetManager().getSkin(), this));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.N)) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new NPCMenu(new NPCMenuController()
                , GameAssetManagerClient.getGameAssetManager().getSkin(), this));
        }
    }

    public void updateInteractions() {
        String targetPlayer = GameClient.getGameStateApiClient().getNearbyPlayer();
        if (targetPlayer.isEmpty()) {
            window.setVisible(false);
        }

        if (!targetPlayer.isEmpty()) {
            window.setVisible(true);
            currentTargetPlayer = targetPlayer;
            showInteractionWindow(currentTargetPlayer);
        }
    }

    private void showInteractionWindow(String targetPlayer) {
        PlayerDto playerDto = GameClient.getGameStateApiClient().getPlayerDTOByUserName(targetPlayer);
        Vector3 screenPos = controller.getCamera().project(new Vector3(playerDto.getX(), playerDto.getY(), 0));
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
        return handleClick(worldCoordinates, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
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

    private boolean handleClick(Vector3 worldCoordinates, int button) {
        HandleWorldClickResponse result = null;
        try {
            result = GameClient.getGameStateApiClient()
                .handleWorldClick(worldCoordinates.x, worldCoordinates.y, button);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return controller.handleClickAction(result);
    }


    public void setError(String error) {
        this.error.setText(error);
    }

    public String getNearbyNPC() {
        return GameClient.gameStateApiClient.getNearbyNPC();
    }

    public String getDialogueTextNPCByName(String Name) {
        return GameClient.gameStateApiClient.getDialogueTextNPCByName(Name);
    }

    private void updateDialogue(float delta) {
        String nearbyNPC = getNearbyNPC();
        if (nearbyNPC != null) {
            if (!dialogueActive || !Objects.equals(currentNPC, nearbyNPC)) {
                currentNPC = nearbyNPC;
                currentDialogueText = getDialogueTextNPCByName(currentNPC);
                dialogueCharIndex = 0;
                dialogueTimer = 0;
                dialogueLabel.setText("");
                dialogueLabel.setVisible(true);
                dialogueActive = true;
            }

            dialogueTimer += delta;
            if (dialogueTimer > 0.05f && dialogueCharIndex < currentDialogueText.length()) {
                dialogueLabel.setText(currentDialogueText.substring(0, dialogueCharIndex + 1));
                dialogueCharIndex++;
                dialogueTimer = 0;
            }

        } else {
            dialogueLabel.setVisible(false);
            dialogueActive = false;
            currentNPC = null;
        }
    }

    public GameController getController() {
        return controller;
    }

    public void showNotification(String message) {
        Skin skin = GameAssetManagerClient.getGameAssetManager().getSkin();
        NotificationWindow window = new NotificationWindow(message, skin, () -> {
            System.out.println("Notification dismissed!");
        });
        stage.addActor(window); // Add to current stage
    }

    public HUD getHud() {
        return hud;
    }

    public boolean isSthBuilding() {
        return isSthBuilding;
    }

    public void setSthBuilding(boolean sthBuilding) {
        isSthBuilding = sthBuilding;
    }
}
