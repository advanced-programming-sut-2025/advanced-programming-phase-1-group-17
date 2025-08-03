package io.github.StardewValley.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.StardewValley.GameAssetManagerClient;
import io.github.StardewValley.GameClient;
import io.github.StardewValley.controllers.helperControllers.FarmingController;
import io.github.StardewValley.controllers.helperControllers.GameStateApiClient;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.controller.LightningController;
import io.github.StardewValley.shared.models.*;
import io.github.StardewValley.shared.models.animal.FishingController;
import io.github.StardewValley.shared.models.backpack.NormalItem;
import io.github.StardewValley.shared.models.backpack.NormalItemType;
import io.github.StardewValley.shared.models.map.Lake;
import io.github.StardewValley.shared.models.map.Placeable;
import io.github.StardewValley.shared.models.plant.*;
import io.github.StardewValley.views.*;
import io.github.StardewValley.shared.models.crafting.CraftingItem;
import io.github.StardewValley.shared.models.map.Tile;
import io.github.StardewValley.shared.models.market.Store;
import io.github.StardewValley.shared.models.market.StoreType;
import io.github.StardewValley.views.GameView;
import io.github.StardewValley.views.MapView;
import io.github.StardewValley.shared.models.tools.Tool;
import io.github.StardewValley.models.plant.CrowAttackEffect;

import java.util.HashMap;

public class GameController {
    public GameView view;
    private OrthographicCamera camera;
    int mapWidthInPixels;
    int mapHeightInPixels;
    private final HashMap<StoreType, Rectangle> storeBounds = new HashMap<>();
    public static GameStateApiClient gameStateApiClient = new GameStateApiClient(Main.getJwtToken());
    private static GameClient gameClient = new GameClient();

    private  WorldController worldController;
    private ToolRenderController toolRenderController;
    private  LightningController lightningController;
    private  FarmingController farmingController;
    private  CrowAttackEffect crowAttackEffect;

    private Player player;

    {
        create();
    }


    public GameController() {
        try {
            GameClient.setPlayer(new PlayerClient(gameStateApiClient.getUserWithUserDTO()));
            PlayerDto player = gameStateApiClient.updateStateOfPlayer(0.0001f, upPressed, downPressed, leftPressed, rightPressed);
            playerUpdate(player);
            this.camera.position.set(player.getX() , player.getY(), 0);

            this.worldController = new WorldController(this.camera);
            this.worldController.initTransients();
            //TODO
//            this.toolController = new ToolController(player);
//            this.lightningController = LightningController.getLightningController();

            this.mapWidthInPixels = worldController.getTileWidth();
            this.mapHeightInPixels = worldController.getTileHeight();

            this.farmingController = new FarmingController();
            this.crowAttackEffect = new CrowAttackEffect();
            //TODO
//            initializeStoreRectangles();

            App.setCamera(this.camera);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setView(GameView gameView) {
        this.view = gameView;
    }


    private void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private void initializeStoreRectangles() {
        int tileWidth = worldController.getTileWidth();
        int tileHeight = worldController.getTileHeight();

        Store store = App.getCurrentGame().getStoreManager().getStore(StoreType.Blacksmith);
        storeBounds.put(StoreType.Blacksmith, new Rectangle(store.getStart_x() * tileWidth, store.getStart_y() * tileHeight,
            store.getWidth() * tileWidth, store.getHeight() * tileHeight));

        store = App.getCurrentGame().getStoreManager().getStore(StoreType.Ranch);
        storeBounds.put(StoreType.Ranch, new Rectangle(store.getStart_x() * tileWidth, store.getStart_y() * tileHeight,
            store.getWidth() * tileWidth, store.getHeight() * tileHeight));

        store = App.getCurrentGame().getStoreManager().getStore(StoreType.StardropSaloon);
        storeBounds.put(StoreType.StardropSaloon, new Rectangle(store.getStart_x() * tileWidth, store.getStart_y() * tileHeight,
            store.getWidth() * tileWidth, store.getHeight() * tileHeight));

        store = App.getCurrentGame().getStoreManager().getStore(StoreType.CarpentersShop);
        storeBounds.put(StoreType.CarpentersShop, new Rectangle(store.getStart_x() * tileWidth, store.getStart_y() * tileHeight,
            store.getWidth() * tileWidth, store.getHeight() * tileHeight));

        store = App.getCurrentGame().getStoreManager().getStore(StoreType.JojaMart);
        storeBounds.put(StoreType.JojaMart, new Rectangle(store.getStart_x() * tileWidth, store.getStart_y() * tileHeight,
            store.getWidth() * tileWidth, store.getHeight() * tileHeight));

        store = App.getCurrentGame().getStoreManager().getStore(StoreType.PierresGeneralStore);
        storeBounds.put(StoreType.PierresGeneralStore, new Rectangle(store.getStart_x() * tileWidth, store.getStart_y() * tileHeight,
            store.getWidth() * tileWidth, store.getHeight() * tileHeight));

        store = App.getCurrentGame().getStoreManager().getStore(StoreType.FishShop);
        storeBounds.put(StoreType.FishShop, new Rectangle(store.getStart_x() * tileWidth, store.getStart_y() * tileHeight,
            store.getWidth() * tileWidth, store.getHeight() * tileHeight));
    }

    public void updateCamera(PlayerClient player) {
        float camHalfWidth = camera.viewportWidth * 0.5f * camera.zoom;
        float camHalfHeight = camera.viewportHeight * 0.5f * camera.zoom;

        float minX = camHalfWidth;
        float maxX = mapWidthInPixels * 300 - camHalfWidth;
        float minY = camHalfHeight;
        float maxY = mapHeightInPixels * 300 - camHalfHeight;

        float targetX = player.getX();
        float targetY = player.getY();
        float camX = Math.max(minX, Math.min(targetX, maxX));
        float camY = Math.max(minY, Math.min(targetY, maxY));

        camera.position.set(camX, camY, 0);
        camera.update();
        camera.position.set(player.getX(), player.getY(), 0);
        camera.update();
    }

    public void updateGame(float delta) {
        if (view != null) {
            try {
                PlayerDto pd = gameStateApiClient.updateStateOfPlayer(delta, upPressed, downPressed, leftPressed, rightPressed);
                playerUpdate(pd);
                updateCamera(GameClient.getPlayer());
                worldController.update();
            }catch (Exception e) {
                e.printStackTrace();
            }

            GameClient.getPlayer().draw(Main.getBatch());

            //TODO handle connection to server
//            toolController.update(delta, player);

            //TODO
//            lightningController.updateLightning(delta);
//            lightningController.renderLightning(Main.getBatch());
//
//            crowAttackEffect.update(delta);
//            crowAttackEffect.render(Main.getBatch());
            view.getHud().handleInventoryInput();
        }
    }
    public void playerUpdate(PlayerDto player) {
        if (player == null ){return;}
        PlayerClient playerClient = GameClient.getPlayer();
        playerClient.setX(player.getX());
        playerClient.setY(player.getY());
        playerClient.setAnimationTimer(player.getAnimationTimer());
        playerClient.setPassedOut(player.isPassedOut());
        playerClient.setCoin(player.getCoin());
        playerClient.setSpeed(player.getSpeed());
        playerClient.setPassOutTimer(player.getPassOutTimer());
        playerClient.setEnergy(player.getEnergy());
        playerClient.setMaxEnergy(player.getMaxEnergy());
        playerClient.setEnergyUnlimited(player.isEnergyUnlimited());
        playerClient.setCurrentDirection(player.getCurrentDirection());
        playerClient.setLastDirection(player.getLastDirection());
    }



    private boolean upPressed, downPressed, leftPressed, rightPressed;

    public void setKey(int keycode, boolean pressed) {
        switch (keycode) {
            case com.badlogic.gdx.Input.Keys.W:
                upPressed = pressed;
                break;
            case com.badlogic.gdx.Input.Keys.S:
                downPressed = pressed;
                break;
            case com.badlogic.gdx.Input.Keys.A:
                leftPressed = pressed;
                break;
            case com.badlogic.gdx.Input.Keys.D:
                rightPressed = pressed;
                break;
            case Input.Keys.E:
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new CookingShow(  GameAssetManagerClient.getGameAssetManager().getSkin(), view,new CookingController()));
                break;
            case Input.Keys.R:
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new CraftingShow(  GameAssetManagerClient.getGameAssetManager().getSkin(), view,new CraftingController()));
                break;
            case Input.Keys.P:
//                for(AnimalPlace animalPlace : App.getCurrentGame().getCurrentPlayingPlayer().getPlayerMap().getAnimalPlaces()) {
//                    for(Animal animal:animalPlace.getAnimals()){
//                        if(!animal.isFollowingPath())animal.startPathTo();
//                    }
//                }
        }
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public HashMap<StoreType, Rectangle> getStoreBounds() {
        return storeBounds;
    }

    public void handlePlayerInput() {
        player = App.getCurrentGame().getCurrentPlayingPlayer();
        int dx = 0, dy = 0;
        switch (player.getLastDirection()) {
            case UP:
                dy = 1;
                break;
            case RIGHT:
                dx = 1;
                break;
            case DOWN:
                dy = -1;
                break;
            case LEFT:
                dx = -1;
                break;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new MapView(new MapViewController(), view));

        } else if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            Main.getMain().getScreen().dispose();
            ScreenUtils.clear(0, 0, 0, 1);
            Main.getMain().setScreen(new TalkView(new TalkController(),   GameAssetManagerClient.getGameAssetManager().getSkin(), view));
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
            try {
                App.getCurrentGame().getDate().goToNextDay();
            } catch (Exception e){
                e.printStackTrace();
            }
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
            Result result = null;
            if (player.getEquippedItem() instanceof Tool)
                result = toolRenderController.toolUse(dx, dy);
            else if (player.getEquippedItem() instanceof CraftingItem)
                result = placeItem(dx, dy);
            else if (player.getEquippedItem() instanceof Seed seed)
                result = farmingController.plantSeed(seed, dx, dy);
            else if (player.getEquippedItem() instanceof Sapling sapling)
                result = farmingController.plantSapling(sapling, dx, dy);
            else if (player.getEquippedItem() instanceof Fertilizer fertilizer) {
                result = farmingController.fertilize(fertilizer, dx, dy);
            }
            if (result != null && !result.isSuccessful())
                view.showNotification(result.getMessage());

        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new CheatCodeTerminal(new CheatCodeTerminalController(),   GameAssetManagerClient.getGameAssetManager().getSkin()));
        }
        //TODO handle player
//        else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
//            Main.getMain().getScreen().dispose();
//            Main.getMain().setScreen(new InventoryView(new InventoryController(),
//                  GameAssetManagerClient.getGameAssetManager().getSkin(),
//                GameClient.getPlayer()));
//        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new Journal(new JournalController(), GameAssetManagerClient.getGameAssetManager().getSkin()));
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.X))
            pickForaging(dx, dy);
        else if(Gdx.input.isTouched()) {
            Vector3 vector3 = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            App.getCamera().unproject(vector3);
            Tile tile = Tile.getTileByClick((int)vector3.x,(int)vector3.y);
            if(tile != null && tile.getPlaceable() != null && tile.getPlaceable() instanceof Lake) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new FishingView(new FishingController(),GameAssetManagerClient.getGameAssetManager().getSkin()));
            }
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new FishingView(new FishingController(),GameAssetManagerClient.getGameAssetManager().getSkin()));
        }
    }

    private void pickForaging(int dx, int dy) {
        player = App.getCurrentGame().getCurrentPlayingPlayer();
        int x = player.getX() / worldController.getTileWidth() + dx;
        int y = player.getY() / worldController.getTileHeight() + dy;
        Tile tile = Tile.getTile(x, y);

        Placeable placeable = tile.getPlaceable();
        if (placeable instanceof Crop crop) {
            if (crop.isForaging()) {
                player.getBackPack().addItemToInventory(crop);
                tile.setPlaceable(null);
                tile.setWalkAble(true);
            }
        } else if (placeable instanceof NormalItem normalItem) {
            if (normalItem.getType().equals(NormalItemType.Wood)) {
                player.getBackPack().addItemToInventory(normalItem);
                tile.setPlaceable(null);
                tile.setWalkAble(true);
            }
        }
    }

    public WorldController getWorldController() {
        return worldController;
    }

    public ToolRenderController getToolController() {
        return toolRenderController;
    }

    public CrowAttackEffect getCrowAttackEffect() {
        return crowAttackEffect;
    }

    public static GameStateApiClient getGameStateApiClient() {
        return gameStateApiClient;
    }
}
