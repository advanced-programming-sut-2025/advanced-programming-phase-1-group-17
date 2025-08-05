package io.github.StardewValley.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.StardewValley.GameAssetManagerClient;
import io.github.StardewValley.GameClient;
import io.github.StardewValley.Main;
import io.github.StardewValley.controllers.UIControllers.*;
import io.github.StardewValley.controllers.UIControllers.LightningController;
import io.github.StardewValley.shared.dto.HandleWorldClickResponse;
import io.github.StardewValley.shared.models.*;
import io.github.StardewValley.shared.models.crafting.CraftingItem;
import io.github.StardewValley.shared.models.map.Lake;
import io.github.StardewValley.shared.models.market.ShippingBin;
import io.github.StardewValley.views.*;
import io.github.StardewValley.shared.models.map.Tile;
import io.github.StardewValley.shared.models.market.StoreType;
import io.github.StardewValley.views.GameView;
import io.github.StardewValley.views.MapView;
import io.github.StardewValley.models.plant.CrowAttackEffect;

public class GameController {
    public GameView view;
    private OrthographicCamera camera;
    int mapWidthInPixels;
    int mapHeightInPixels;
    private static GameClient gameClient = new GameClient();

    private  WorldController worldController;
    private ToolRenderController toolRenderController;
    private  LightningController lightningController;
    private  CrowAttackEffect crowAttackEffect;

    private PlayerDto player;

    {
        create();
    }


    public GameController() {
        try {
            PlayerClient playerClient = new PlayerClient(GameClient.getGameStateApiClient().getUserWithUserDTO());
            GameClient.setPlayer(playerClient);
            PlayerDto player = GameClient.getGameStateApiClient().updateStateOfPlayer(0.0001f, upPressed, downPressed, leftPressed, rightPressed);
            playerUpdate(player);
            this.camera.position.set(player.getX() , player.getY(), 0);

            this.worldController = new WorldController(this.camera);
            this.worldController.initTransients();
            this.toolRenderController = new ToolRenderController(playerClient);
            this.lightningController = LightningController.getLightningController();

            this.mapWidthInPixels = worldController.getTileWidth();
            this.mapHeightInPixels = worldController.getTileHeight();
            this.crowAttackEffect = new CrowAttackEffect();
            //TODO
//            initializeStoreRectangles();

            App.setCamera(this.camera);
            GameClient.setCamera(this.camera);
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
                PlayerDto pd = GameClient.getGameStateApiClient().updateStateOfPlayer(delta, upPressed, downPressed, leftPressed, rightPressed);
                playerUpdate(pd);
                updateCamera(GameClient.getPlayer());
                worldController.update();
            } catch (Exception e) {
                e.printStackTrace();
            }

            GameClient.getPlayer().draw(Main.getBatch());

            //TODO handle connection to server
//            toolController.update(delta, player);

            //TODO
            lightningController.updateLightning(delta);
            lightningController.renderLightning(Main.getBatch(), player);

            crowAttackEffect.update(delta);
            crowAttackEffect.render(Main.getBatch());
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
        playerClient.setNewMessage(player.isNewMessage());
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

    public void handlePlayerInput() {
        //player = App.getCurrentGame().getCurrentPlayingPlayer();
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
            HandleWorldClickResponse result = null;
            try {
                result = GameClient.getGameStateApiClient().handleWorldClick(dx, dy, Input.Buttons.LEFT);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (result != null)
                handleClickAction(result);

        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new CheatCodeTerminal(new CheatCodeTerminalController(),   GameAssetManagerClient.getGameAssetManager().getSkin()));
        }
        //TODO handle player
        else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new InventoryView(new InventoryController(),
                  GameAssetManagerClient.getGameAssetManager().getSkin()));
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new Journal(new JournalController(), GameAssetManagerClient.getGameAssetManager().getSkin()));
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
            try {
                GameClient.getGameStateApiClient().pickForaging(dx, dy);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(Gdx.input.isTouched()) {
            Vector3 vector3 = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            App.getCamera().unproject(vector3);
            Tile tile = Tile.getTileByClick((int)vector3.x,(int)vector3.y);
            if(tile != null && tile.getPlaceable() != null && tile.getPlaceable() instanceof Lake) {
                Main.getMain().getScreen().dispose();
                //TODO
                //Main.getMain().setScreen(new FishingView(new FishingController(),GameAssetManagerClient.getGameAssetManager().getSkin()));
            }
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            Main.getMain().getScreen().dispose();
            //TODO
            //Main.getMain().setScreen(new FishingView(new FishingController(),GameAssetManagerClient.getGameAssetManager().getSkin()));
        }
    }

    public WorldController getWorldController() {
        return worldController;
    }

    public CrowAttackEffect getCrowAttackEffect() {
        return crowAttackEffect;
    }

    public boolean handleClickAction(HandleWorldClickResponse result) {
        if (result.getActionType().equals(HandleWorldClickResponse.ActionType.NONE))
            return false;

        switch (result.getActionType()) {
            case SHOW_NOTIFICATION -> view.showNotification(result.getMessage());
            case OPEN_STORE -> {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new StoreMenu(
                    new StoreMenuController(),
                    GameAssetManagerClient.getGameAssetManager().getSkin(),
                    (StoreType) result.getPayload()
                ));
            }
            case OPEN_ARTISAN_CRAFT_MENU -> {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new ArtisanCraftMenu(
                    new ArtisanCraftMenuController(),
                    GameAssetManagerClient.getGameAssetManager().getSkin(),
                    (CraftingItem) result.getPayload()
                ));
            }
            case OPEN_GREENHOUSE_BUILD -> {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new GreenHouseBuildScreen(
                    new GreenHouseBuildController(),
                    GameAssetManagerClient.getGameAssetManager().getSkin()
                ));
            }
            case OPEN_SHIPPING_BIN_MENU -> {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new ShippingBinScreen(
                    (ShippingBin) result.getPayload(),
                    new ShippingBinScreenController(),
                    GameAssetManagerClient.getGameAssetManager().getSkin()
                ));
            }
            case OPEN_ARTISAN_INFO_MENU -> {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new ArtisanInfoMenu(
                    new ArtisanInfoMenuController(),
                    GameAssetManagerClient.getGameAssetManager().getSkin(),
                    (CraftingItem) result.getPayload()
                ));
            }
            default -> {
            }
        }
        return true;
    }
}
