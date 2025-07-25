package io.github.StardewValley.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.controller.LightningController;
import io.github.StardewValley.shared.models.App;
import io.github.StardewValley.shared.models.Game;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.views.*;
import io.github.StardewValley.shared.models.crafting.CraftingItem;
import io.github.StardewValley.shared.models.crafting.CraftingItemType;
import io.github.StardewValley.shared.models.map.Tile;
import io.github.StardewValley.shared.models.market.Store;
import io.github.StardewValley.shared.models.market.StoreType;
import io.github.StardewValley.views.GameView;
import io.github.StardewValley.views.InventoryView;
import io.github.StardewValley.views.MapView;
import io.github.StardewValley.shared.models.plant.Plant;
import io.github.StardewValley.shared.models.tools.Tool;

import java.util.HashMap;

public class GameController {
    public GameView view;
    private OrthographicCamera camera;
    int mapWidthInPixels;
    int mapHeightInPixels;
    private final Game game;
    private final HashMap<StoreType, Rectangle> storeBounds = new HashMap<>();

    private final WorldController worldController;
    private final ToolController toolController;
    private final LightningController lightningController;

    private Player player;

    {
        create();
    }


    public GameController(Game game) {
        this.game = game;
        Player player = game.getCurrentPlayingPlayer();
        this.camera.position.set(player.getX() , player.getY(), 0);

        this.worldController = new WorldController(this.camera);
        this.worldController.initTransients();

        this.toolController = new ToolController(player);
        this.lightningController = LightningController.getLightningController();

        this.mapWidthInPixels = worldController.getTileWidth();
        this.mapHeightInPixels = worldController.getTileHeight();

        initializeStoreRectangles();
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

    public void updateCamera(Player player) {
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
            player = App.getCurrentGame().getCurrentPlayingPlayer();
            game.getCurrentPlayingPlayer().update(delta, upPressed, downPressed, leftPressed, rightPressed);
            updateCamera(game.getCurrentPlayingPlayer());
            worldController.update();

            game.getCurrentPlayingPlayer().draw(Main.getBatch());
            toolController.update(delta, player);

            lightningController.updateLightning(delta);
            lightningController.renderLightning(Main.getBatch());
        }
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
                Main.getMain().setScreen(new CookingShow(GameAssetManager.getGameAssetManager().getSkin(), view,new CookingController()));
                break;
            case Input.Keys.R:
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new CraftingShow(GameAssetManager.getGameAssetManager().getSkin(), view,new CraftingController()));
                break;
        }
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public HashMap<StoreType, Rectangle> getStoreBounds() {
        return storeBounds;
    }

    public void handlePlayerInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new MapView(new MapViewController(), view));

        } else if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            Main.getMain().getScreen().dispose();
            ScreenUtils.clear(0, 0, 0, 1);
            Main.getMain().setScreen(new TalkView(new TalkController(), GameAssetManager.getGameAssetManager().getSkin(), view));
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
            try {
                App.getCurrentGame().getDate().goToNextDay();
            } catch (Exception e){
                e.printStackTrace();
            }
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
            Player player = App.getCurrentGame().getCurrentPlayingPlayer();
            int dx = 0, dy = 0;
            switch (player.getCurrentDirection()) {
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
                case IDLE:
                    //return;
                    break;
            }

            if (player.getEquippedItem() instanceof Tool)
                toolController.toolUse(dx, dy);
            else if (player.getEquippedItem() instanceof CraftingItem)
                //placeItem(dx, dy);
                placeItem(0, -1);

        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new CheatCodeTerminal(new CheatCodeTerminalController(), GameAssetManager.getGameAssetManager().getSkin()));
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new InventoryView(new InventoryController(),
                GameAssetManager.getGameAssetManager().getSkin(),
                game.getCurrentPlayingPlayer()));
        }
        //TODO handle input key
    }


    public void placeItem(int dx, int dy) {
        player = App.getCurrentGame().getCurrentPlayingPlayer();
        CraftingItemType craftingItemType = (CraftingItemType) player.getEquippedItem().getType();

        int x = player.getX() / worldController.getTileWidth() + dx;
        int y = player.getY() / worldController.getTileHeight() + dy;
        Tile tile = Tile.getTile(x, y);

        if (tile.getPlaceable() != null) {
            //TODO: maybe graphical error
            return;
            //return new Result(false, "tile is full");
        }

        App.getCurrentGame().getCurrentPlayingPlayer().getBackPack().useItem(craftingItemType);
        CraftingItem craftingItem = new CraftingItem(craftingItemType, player);
        craftingItem.setStart_x(x);
        craftingItem.setStart_y(y);
        craftingItem.addCraftingItemBound();

        tile.setPlaceable(craftingItem);
        tile.setWalkAble(false);

        switch (craftingItemType) {
            case CherryBomb -> {
                int range = 3;
                for (int i = -range; i < range + 1; i++) {
                    for (int j = -range; j < range + 1; j++) {

                        Tile target = Tile.getTile(tile.getX() + i, tile.getY() + j);
                        if (target != null) {
                            target.setPlaceable(null);
                        }
                    }
                }
            }

            case Bomb -> {
                int range = 5;
                for (int i = -range; i < range + 1; i++) {
                    for (int j = -range; j < range + 1; j++) {

                        Tile target = Tile.getTile(tile.getX() + i, tile.getY() + j);
                        if (target != null) {
                            target.setPlaceable(null);
                        }
                    }
                }
            }

            case MegaBomb -> {
                int range = 7;
                for (int i = -range; i < range + 1; i++) {
                    for (int j = -range; j < range + 1; j++) {

                        Tile target = Tile.getTile(tile.getX() + i, tile.getY() + j);
                        if (target != null) {
                            target.setPlaceable(null);
                        }
                    }
                }
            }

            case Sprinkler -> {
                int[] dx2 = {0, 1, 0, -1};
                int[] dy2 = {1, 0, -1, 0};
                for (int i = 0; i < 4; i++) {
                    Tile target = Tile.getTile(tile.getX() + dx2[i], tile.getY() + dy2[i]);
                    if (target != null && target.getPlaceable() instanceof Plant plant) {
                        plant.wateringPlant();
                    }
                }
            }

            case QualitySprinkler -> {
                int range = 1;
                for (int i = -range; i < range + 1; i++) {
                    for (int j = -range; j < range + 1; j++) {

                        Tile target = Tile.getTile(tile.getX() + i, tile.getY() + j);
                        if (target != null && target.getPlaceable() instanceof Plant plant) {
                            plant.wateringPlant();
                        }
                    }
                }
            }

            case IridiumSprinkler -> {
                int range = 2;
                for (int i = -range; i < range + 1; i++) {
                    for (int j = -range; j < range + 1; j++) {

                        Tile target = Tile.getTile(tile.getX() + i, tile.getY() + j);
                        if (target != null && target.getPlaceable() instanceof Plant plant) {
                            plant.wateringPlant();
                        }
                    }
                }
            }

            case Scarecrow -> {
                int range = 8;
                for (int i = -range; i < range + 1; i++) {
                    for (int j = -range; j < range + 1; j++) {

                        Tile target = Tile.getTile(tile.getX() + i, tile.getY() + j);
                        if (target != null) {
                            tile.setCrowImmunity(true);
                        }
                    }
                }
            }

            case DeluxeScarecrow -> {
                int range = 12;
                for (int i = -range; i < range + 1; i++) {
                    for (int j = -range; j < range + 1; j++) {

                        Tile target = Tile.getTile(tile.getX() + i, tile.getY() + j);
                        if (target != null) {
                            tile.setCrowImmunity(true);
                        }
                    }
                }
            }

            case BeeHouse -> {

            }

            case CheesePress -> {

            }

            case Keg -> {

            }

            case Loom -> {

            }

            case MayonnaiseMachine -> {

            }

            case OilMaker -> {

            }

            case PreservesJar -> {

            }

            case Dehydrator -> {

            }

            case FishSmoker -> {

            }

            case MysticTreeSeed -> {

            }
        }
    }

    public WorldController getWorldController() {
        return worldController;
    }

    public ToolController getToolController() {
        return toolController;
    }


}
