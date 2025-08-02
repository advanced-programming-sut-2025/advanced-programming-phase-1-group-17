package io.github.StardewValley.views;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.controllers.helperControllers.FarmingController;
import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.Main;
import io.github.StardewValley.controllers.*;
import io.github.StardewValley.shared.models.App;
import io.github.StardewValley.shared.models.NPCS.NPC;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.Result;
import io.github.StardewValley.controllers.StoreMenuController;
import io.github.StardewValley.shared.models.animal.Animal;
import io.github.StardewValley.shared.models.animal.AnimalPlace;
import io.github.StardewValley.shared.models.animal.AnimalPlaceType;
import io.github.StardewValley.shared.models.animal.AnimalType;
import io.github.StardewValley.shared.models.crafting.CraftingItem;
import io.github.StardewValley.shared.models.greenhouse.GreenHouse;
import io.github.StardewValley.shared.models.market.ShippingBin;
import io.github.StardewValley.shared.models.market.StoreType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.github.StardewValley.shared.models.plant.Fertilizer;
import io.github.StardewValley.shared.models.plant.Sapling;
import io.github.StardewValley.shared.models.plant.Seed;
import io.github.StardewValley.shared.models.tools.Tool;
import io.github.StardewValley.shared.models.enums.Gender;

public class GameView implements Screen, InputProcessor {
    private Stage stage;
    private final GameController controller;
    private final FarmingController farmingController = new FarmingController();
    private final GameMenuController menuController;
    private HUD hud;
    private Window window;
    private TextButton talkButton, tradeButton, hugButton, giftButton, givingFlower, askMarriageButton;
    private Label error;
    private Table content;
    private boolean activeWindow = true;
    private Player currentTargetPlayer;
    private boolean isPlayerNearNPC = false;
    private float dialogueTimer = 0;
    private int dialogueCharIndex = 0;
    private String currentDialogueText = "";
    private Label dialogueLabel;
    private NPC currentNPC = null;
    private boolean dialogueActive = false;
    private Table dialogueTable;
    private BitmapFont font;


    public GameView(GameController controller, GameMenuController menuController) {
        this.font = new BitmapFont();
        int i=0;
        for(AnimalPlaceType animalPlaceType : AnimalPlaceType.values()) {
            AnimalPlace ap = new AnimalPlace(animalPlaceType);
            ap.setX(1000+500*i);
            ap.setY(1000+500*i);
            i++;
            App.getCurrentGame().getCurrentPlayingPlayer().getPlayerMap().getAnimalPlaces().add(ap);
            Animal animal = new Animal("test" + i, AnimalType.values()[i],ap);
            animal.setX(100+20*i);
            animal.setY(100+20*i);
            ap.getAnimals().add(animal);

        }



        this.controller = controller;
        this.menuController = menuController;
        this.controller.setView(this);
        Main.setGameView(this);

        initUI();
        setupListeners(this);
    }

    private void initUI() {
        Skin skin = GameAssetManager.getGameAssetManager().getSkin();
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
                    GameAssetManager.getGameAssetManager().getSkin(), currentTargetPlayer, gameView, null));
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
                Result r = menuController.askMarriage(currentTargetPlayer.getUser().getUsername(), "Ring");
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

        controller.handlePlayerInput();
        for(AnimalPlace animalPlace : App.getCurrentGame().getCurrentPlayingPlayer().getPlayerMap().getAnimalPlaces()) {
            animalPlace.render(delta);
            for(Animal animal:animalPlace.getAnimals()){
                animal.render(Main.getBatch(),delta);
                animal.update(delta);
            }
        }
        //font.draw(Main.getBatch(),"hello",120,120);

        Main.getBatch().end();
        if (App.getCurrentGame().getCurrentPlayingPlayer().isNewMessage()) {
            error.setText("you have a new message");
        }
        if (activeWindow) updateInteractions();
        updateDialogue(delta);

        stage.addActor(dialogueTable);
        error.setPosition(10, 1000);
        stage.addActor(error);
        hud.render(Main.getBatch(),delta);
        controller.handlePlayerInput();
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
                , GameAssetManager.getGameAssetManager().getSkin(), this));
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
        if (button == Input.Buttons.RIGHT)
            return checkCraftingItemBounds(worldCoordinates, false);

        if (checkGreenHouseBounds(worldCoordinates))
            return true;
        if (checkCraftingItemBounds(worldCoordinates, true))
            return true;
        if(handleToolUse(worldCoordinates))
            return true;
        if (handleShippingBin(worldCoordinates)) {
            return true;
        }
        return checkStoreBounds(worldCoordinates);
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

    private boolean checkStoreBounds(Vector3 worldCoordinates) {
        for (Map.Entry<StoreType, Rectangle> entry : controller.getStoreBounds().entrySet()) {
            if (entry.getValue().contains(worldCoordinates.x, worldCoordinates.y)) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new StoreMenu(new StoreMenuController(),
                    GameAssetManager.getGameAssetManager().getSkin(), entry.getKey()));
                return true;
            }
        }
        return false;
    }

    private boolean checkCraftingItemBounds(Vector3 worldCoordinates , boolean isLeftClick) {
        for (Map.Entry<CraftingItem, Rectangle> entry:  CraftingItem.getCraftingItemBounds().entrySet()) {
            if (entry.getValue().contains(worldCoordinates.x, worldCoordinates.y)) {
                Main.getMain().getScreen().dispose();
                if (isLeftClick)
                    Main.getMain().setScreen(new ArtisanCraftMenu(new ArtisanCraftMenuController(),
                        GameAssetManager.getGameAssetManager().getSkin(), entry.getKey()));
                else
                    Main.getMain().setScreen(new ArtisanInfoMenu(new ArtisanInfoMenuController(),
                        GameAssetManager.getGameAssetManager().getSkin(), entry.getKey()));
            }
        }
        return false;
    }

    private boolean handleToolUse(Vector3 worldCoordinates) {
        float tileWidth = controller.getWorldController().getTileWidth();
        float tileHeight = controller.getWorldController().getTileHeight();
        int clickedTileX = (int)(worldCoordinates.x / tileWidth);
        int clickedTileY = (int)(worldCoordinates.y / tileHeight);

        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        int dx = clickedTileX - player.getTileX();
        int dy = clickedTileY - player.getTileY();

        if (Math.abs(dx) + Math.abs(dy) == 1) {
            Result result = null;
            if (player.getEquippedItem() instanceof Tool)
                result = controller.getToolController().toolUse(dx, dy);
            else if (player.getEquippedItem() instanceof CraftingItem)
                result = controller.placeItem(dx, dy);
            else if (player.getEquippedItem() instanceof Seed seed)
                result = farmingController.plantSeed(seed, dx, dy);
            else if (player.getEquippedItem() instanceof Sapling sapling)
                result = farmingController.plantSapling(sapling, dx, dy);
            else if (player.getEquippedItem() instanceof Fertilizer fertilizer)
                result = farmingController.fertilize(fertilizer, dx, dy);
            if (result != null && !result.isSuccessful())
                showNotification(result.getMessage());
            return true;
        }
        return false;
    }


    public void setError(String error) {
        this.error.setText(error);
    }

    public NPC getNearbyNPC() {
        for (NPC npc : App.getCurrentGame().getNPCs()) {
            if (menuController.sideBySide(App.getCurrentGame().getCurrentPlayingPlayer(), npc)) {
                return npc;
            }
        }
        return null;
    }

    private void updateDialogue(float delta) {
        NPC nearbyNPC = getNearbyNPC();
        if (nearbyNPC != null ) {
            if (!dialogueActive || currentNPC != nearbyNPC) {
                currentNPC = nearbyNPC;
                currentDialogueText = currentNPC.getDialogueText();
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

    private boolean checkGreenHouseBounds(Vector3 worldCoordinates) {
        HashMap<GreenHouse, Rectangle> bounds = GreenHouse.getGreenHouseBounds();
        for (GreenHouse greenHouse : bounds.keySet()) {
            if (bounds.get(greenHouse).contains(worldCoordinates.x, worldCoordinates.y)) {
                if (greenHouse.isActive()) {
                    GreenHouse.getGreenHouseBounds().remove(greenHouse);
                    return true;
                }
                if (!greenHouse.getOwner().equals(App.getCurrentGame().getCurrentPlayingPlayer())) {
                    showNotification("This greenhouse is not yours.");
                    return true;
                }
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new GreenHouseBuildScreen(
                    new GreenHouseBuildController(),
                    GameAssetManager.getGameAssetManager().getSkin()
                ));
                return true;
            }
        }
        return false;
    }


    private boolean handleShippingBin(Vector3 worldCoordinates) {
        HashMap<ShippingBin, Rectangle> bounds = ShippingBin.getShippingBinBounds();
        for (ShippingBin shippingBin : bounds.keySet()) {
            if (bounds.get(shippingBin).contains(worldCoordinates.x, worldCoordinates.y)) {
                if (shippingBin.getTodayItemOwner() != null && !shippingBin.getTodayItemOwner().equals(App.getCurrentGame().getCurrentPlayingPlayer())) {
                    showNotification("Player %s has put some items inside this shipping Bin today.\n Try using another shipping Bin."
                        .formatted(shippingBin.getTodayItemOwner().getUser().getUsername()));
                    return true;
                }
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new ShippingBinScreen(
                    shippingBin,
                    new ShippingBinScreenController(),
                    GameAssetManager.getGameAssetManager().getSkin()
                ));
                return true;
            }
        }
        return false;
    }

    public GameController getController() {
        return controller;
    }

    public void showNotification(String message) {
        Skin skin = GameAssetManager.getGameAssetManager().getSkin();
        NotificationWindow window = new NotificationWindow(message, skin, () -> {
            System.out.println("Notification dismissed!");
        });
        stage.addActor(window); // Add to current stage
    }

    public HUD getHud() {
        return hud;
    }
}
