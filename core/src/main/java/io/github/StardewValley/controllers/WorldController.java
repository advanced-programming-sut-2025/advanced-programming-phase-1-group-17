package io.github.StardewValley.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.github.StardewValley.GameAssetManagerClient;
import io.github.StardewValley.GameClient;
import io.github.StardewValley.controllers.UIControllers.LightningRenderController;
import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.dto.CraftingItemDTO;
import io.github.StardewValley.shared.dto.GetGameStateResponse;
import io.github.StardewValley.shared.models.App;
import io.github.StardewValley.shared.models.Fence;
import io.github.StardewValley.shared.models.NPCS.NPC;
import io.github.StardewValley.shared.models.TileDTO;
import io.github.StardewValley.shared.models.backpack.NormalItem;
import io.github.StardewValley.shared.models.backpack.NormalItemType;
import io.github.StardewValley.shared.models.enums.Season;
import io.github.StardewValley.shared.models.greenhouse.GreenHouse;
import io.github.StardewValley.shared.models.map.Lake;
import io.github.StardewValley.shared.models.market.Store;
import io.github.StardewValley.shared.models.market.StoreType;
import io.github.StardewValley.shared.models.plant.Crop;
import io.github.StardewValley.shared.models.plant.CropAssetManager;
import io.github.StardewValley.shared.models.plant.Tree;
import io.github.StardewValley.shared.models.map.Hut;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WorldController {
    private final OrthographicCamera camera;

    private transient Texture backgroundTexture;
    private transient Texture backgroundTexture2;
    private int tileWidth;
    private int tileHeight;
    private final int printPad = 30;
    private HashMap<Tree, float[]> treesInThisFrame = new HashMap<>();
    private HashMap<Crop, float[]> giantCropsInThisFrame = new HashMap<>();

    private List<CraftingItemDTO> craftingItems = new ArrayList<>();
    private final HashMap<String, ProgressBar> progressBarMap = new HashMap<>();
    private LightningRenderController lightningRenderController;

    public WorldController(OrthographicCamera camera) {
        this.lightningRenderController = LightningRenderController.getLightningController();
        this.camera = camera;
    }

    public void initTransients() {
        this.backgroundTexture =new Texture(GameAssetManager.getGameAssetManager().getBackgroundTexture1());
        this.backgroundTexture2 = new Texture(GameAssetManager.getGameAssetManager().getBackgroundTexture2());
        this.tileWidth = backgroundTexture.getWidth();
        this.tileHeight = backgroundTexture.getHeight();
        System.out.println(tileHeight + " " + tileWidth);
    }

    public void update(float delta) throws Exception {
        float camLeft = camera.position.x - camera.viewportWidth / 2 * camera.zoom;
        float camRight = camera.position.x + camera.viewportWidth / 2 * camera.zoom;
        float camBottom = camera.position.y - camera.viewportHeight / 2 * camera.zoom;
        float camTop = camera.position.y + camera.viewportHeight / 2 * camera.zoom;

        int minTileX = Math.max((int) (camLeft / tileWidth), 0);
        int maxTileX = Math.min((int) (camRight / tileWidth) + 1, 302);
        int minTileY = Math.max((int) (camBottom / tileHeight), 0);
        int maxTileY = Math.min((int) (camTop / tileHeight) + 1, 302);

        treesInThisFrame.clear();
        giantCropsInThisFrame.clear();

        GetGameStateResponse gameState = GameClient.getGameStateApiClient().getGameState(minTileX, maxTileX, minTileY, maxTileY);
        List<TileDTO> tiles = gameState.getTiles();
        //tiles = GameClient.getGameStateApiClient().getMapTilesAroundPlayer(minTileX,maxTileX,minTileY,maxTileY);
        craftingItems = gameState.getCraftingItems();

        lightningRenderController.applyLightningState(gameState.getLightningStateDTO());
        lightningRenderController.renderLightning(Main.getBatch(), GameClient.getPlayer());

        for (int x = minTileX - 1; x < maxTileX; x++) {
            for (int y = minTileY - 1; y < maxTileY; y++) {
                if (x < -2 || y < -2 || x > 300 || y > 300)
                    continue;
                TileDTO tile = null;
                for (TileDTO tileDTO : tiles) {
                    if (tileDTO.getX() == x && tileDTO.getY() == y){
                        tile = tileDTO;
                        break;
                    }
                }

                if (tile == null) continue;
                if (tile.getPlaceableType().equals("Store"))
                    continue;

                if ((tile.getX() + tile.getY()) % 2 == 0)
                    Main.getBatch().draw(backgroundTexture2, tile.getX() * tileWidth, tile.getY() * tileHeight);
                else
                    Main.getBatch().draw(backgroundTexture, tile.getX() * tileWidth, tile.getY() * tileHeight);

                if (tile.isPlowed())
                    Main.getBatch().draw(GameAssetManagerClient.getGameAssetManager().getPlowedTexture(), tile.getX() * tileWidth, tile.getY() * tileHeight);
                if (tile.getPlaceableType() == null)
                    continue;


                printTileTexture(tile);
            }
        }

        drawCraftingItemsProgressBars();
        drawBigTextures();
        drawStores();
        drawTrees();
        drawGiantCrops();
    }

    private void drawGiantCrops() {
        giantCropsInThisFrame.forEach((crop, coordinates) -> {
            Main.getBatch().draw(
                GameAssetManagerClient.getGameAssetManager().getTexture(CropAssetManager.getCropAssetManager().getGiantTexture(crop.getType())),
                coordinates[0],
                coordinates[1],
                tileWidth * 2 - 10,
                tileHeight * 2 - 10
            );
        });
    }

    private void drawTrees() {
        treesInThisFrame.forEach((tree, coordinates) -> {
            Texture texture = GameAssetManagerClient.getGameAssetManager().getTexture((tree.getTexture()));
            if (texture != null)
                Main.getBatch().draw(texture, coordinates[0], coordinates[1]);
            //TODO handle app.get..
//            else
//                Main.getBatch().draw(
//                    TreeAssetManager.getTreeAssetManager().getFullyGrownTexture(tree.getType(), App.getCurrentGame().getDate().getSeason()),
//                    coordinates[0],
//                    coordinates[1]
//                );
        });
    }

    private void printTileTexture(TileDTO tile) {
        Texture texture = GameAssetManagerClient.getGameAssetManager().getTexture((tile.getTexture()));
        float printX = tile.getX() * tileWidth + printPad, printY = tile.getY() * tileHeight + 20;
        float printWidth = tileWidth - 2 * printPad, printHeight = tileHeight - 2 * printPad;

//        if (tile.getPlaceable() instanceof Crop crop) {
//            if (crop.isGiant()) {
//                if (crop.isLeftBottomTileOfGiant())
//                    giantCropsInThisFrame.put(crop, new float[]{printX, printY});
//                else
//                    return;
//            }
//        }
//        else if (tile.getPlaceable() instanceof Tree tree) {
//            if (texture == null) {
//                TextureRegion textureRegion = TreeAssetManager.getTreeAssetManager().getFullyGrownTexture(tree.getType(), App.getCurrentGame().getDate().getSeason());
//                printX = (tile.getX() * tileWidth) +
//                    ((tileWidth - textureRegion.getRegionWidth()) / 2f);
//                printY = (tile.getY() * tileHeight) + 10;
//            }
//            treesInThisFrame.put(tree, new float[]{printX, printY});
//            return;
//        }
        switch (tile.getPlaceableType()) {
            case "Fence" -> Main.getBatch().draw(texture, printX, printY, 80, 80);
            case "Hut" -> {
                return;
            }
            //TODo
//            case NPC npc when !npc.isNPC() -> {
//                return;
//            }
//            case NPC npc when npc.isNPC() ->
//                Main.getBatch().draw(texture, printX, printY, (float) backgroundTexture.getWidth() / 1.5f, (float) backgroundTexture.getHeight() / 1.5f);
//            case NormalItem normalItem when normalItem.getType().equals(NormalItemType.Grass) ->
//                Main.getBatch().draw(normalItem.getGrassTextureRegion(),
//                    printX, printY,
//                    printWidth, printHeight);
            case "Lake" -> Main.getBatch().draw(texture, tile.getX() * tileWidth, tile.getY() * tileHeight);
            case null, default -> Main.getBatch().draw(texture,
                printX,  printY,
                printWidth, printHeight);
        }
    }

    private void drawCraftingItemsProgressBars() {
        for (CraftingItemDTO craftingItem : craftingItems) {
            String key = craftingItem.getTileX() + ":" + craftingItem.getTileY();
            ProgressBar bar = progressBarMap.get(key);

            if (bar == null && craftingItem.isInProgress() && !craftingItem.isArtisanProductReady()) {
                bar = createProgressBar(); // or however you create it
                progressBarMap.put(key, bar);
            }

            if (bar != null) {
                if (craftingItem.isInProgress() && !craftingItem.isArtisanProductReady()) {
                    float value = craftingItem.getProgress(); // e.g., returns 0.0 to 1.0
                    bar.setValue(value);
                    bar.setPosition(craftingItem.getTileX() * tileWidth,
                        craftingItem.getTileY() * tileHeight + GameAssetManager.getGameAssetManager().getTileHeight() + 5);
                    bar.setWidth(GameAssetManager.getGameAssetManager().getTileWidth());
                    bar.setHeight(50f);
                    bar.act(Gdx.graphics.getDeltaTime());
                    bar.draw(Main.getBatch(), 1f);
                } else {
                    progressBarMap.remove(key);
                }
            } else if (craftingItem.isInProgress() && craftingItem.isArtisanProductReady()) {
                Main.getBatch().draw(
                    GameAssetManagerClient.getGameAssetManager().getTexture(
                        craftingItem.getArtisanProductTexturePath()
                    ),
                    craftingItem.getTileX() * tileWidth,
                    craftingItem.getTileY() * tileHeight + GameAssetManager.getGameAssetManager().getTileHeight() + 5,
                    0.5f * tileWidth,
                    0.5f * tileHeight
                );
            }
        }
    }

    private void drawBigTextures() {
        for (int i = 0 ; i < 4 ; i++) {
            Main.getBatch().draw(GameAssetManagerClient.getGameAssetManager().getTexture(App.getCurrentGame().getPlayers().get(i).getPlayerMap().getHut().getTexture())
                ,App.getCurrentGame().getPlayers().get(i).getPlayerMap().getHut().getX() * tileWidth,
                App.getCurrentGame().getPlayers().get(i).getPlayerMap().getHut().getY() * tileHeight, 400 , 400);
        }
        for (int i = 0 ; i < 5 ; i++) {
            Main.getBatch().draw(GameAssetManagerClient.getGameAssetManager().getTexture(App.getCurrentGame().getNPCHuts().get(i).getTexture()),
                App.getCurrentGame().getNPCHuts().get(i).x_start * tileWidth, App.getCurrentGame().getNPCHuts().get(i).y_start * tileHeight, 500 , 500);
        }
        for (GreenHouse greenHouse : GreenHouse.getGreenHouseBounds().keySet()) {
            Main.getBatch().draw(
                GameAssetManagerClient.getGameAssetManager().getTexture(GameAssetManager.getGameAssetManager().getGreenHouseTexture()),
                greenHouse.getStarting_x() * tileWidth, greenHouse.getStarting_y() * tileHeight,
                greenHouse.getWidth() * tileWidth, greenHouse.getHeight() * tileHeight);
        }
    }

    private void drawStores() {
        GameAssetManager assets = GameAssetManager.getGameAssetManager();
        Season season = App.getCurrentGame().getDate().getSeason();

        Store store = App.getCurrentGame().getMarketsController().getStore(StoreType.Blacksmith);
        Main.getBatch().draw(assets.getStoreTexture(season, StoreType.Blacksmith),
            store.getStart_x() * tileWidth, store.getStart_y() *  tileHeight,
            store.getWidth() * tileWidth, store.getHeight() * tileHeight);

        store = App.getCurrentGame().getMarketsController().getStore(StoreType.JojaMart);
        Main.getBatch().draw(assets.getStoreTexture(season, StoreType.JojaMart),
            store.getStart_x() * tileWidth, store.getStart_y() *  tileHeight,
            store.getWidth() * tileWidth, store.getHeight() * tileHeight);

        store = App.getCurrentGame().getMarketsController().getStore(StoreType.PierresGeneralStore);
        Main.getBatch().draw(assets.getStoreTexture(season, StoreType.PierresGeneralStore),
            store.getStart_x() * tileWidth, store.getStart_y() *  tileHeight,
            store.getWidth() * tileWidth, store.getHeight() * tileHeight);

        store = App.getCurrentGame().getMarketsController().getStore(StoreType.CarpentersShop);
        Main.getBatch().draw(assets.getStoreTexture(season, StoreType.CarpentersShop),
            store.getStart_x() * tileWidth, store.getStart_y() *  tileHeight,
            store.getWidth() * tileWidth, store.getHeight() * tileHeight);

        store = App.getCurrentGame().getMarketsController().getStore(StoreType.FishShop);
        Main.getBatch().draw(assets.getStoreTexture(season, StoreType.FishShop),
            store.getStart_x() * tileWidth, store.getStart_y() *  tileHeight,
            store.getWidth() * tileWidth, store.getHeight() * tileHeight);

        store = App.getCurrentGame().getMarketsController().getStore(StoreType.Ranch);
        Main.getBatch().draw(assets.getStoreTexture(season, StoreType.Ranch),
            store.getStart_x() * tileWidth, store.getStart_y() *  tileHeight,
            store.getWidth() * tileWidth, store.getHeight() * tileHeight);

        store = App.getCurrentGame().getMarketsController().getStore(StoreType.StardropSaloon);
        Main.getBatch().draw(assets.getStoreTexture(season, StoreType.StardropSaloon),
            store.getStart_x() * tileWidth, store.getStart_y() *  tileHeight,
            store.getWidth() * tileWidth, store.getHeight() * tileHeight);
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public void setTileWidth(int tileWidth) {
        this.tileWidth = tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public void setTileHeight(int tileHeight) {
        this.tileHeight = tileHeight;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    private ProgressBar createProgressBar() {
        Skin skin = GameAssetManagerClient.getGameAssetManager().getSkin();
//        ProgressBar.ProgressBarStyle style = new ProgressBar.ProgressBarStyle();
//        style.background = skin.newDrawable("white", Color.DARK_GRAY);  // Replace "white" with a texture name in your atlas
//        style.knob = skin.newDrawable("white", Color.GRAY); // or whatever color
//        style.knobBefore = skin.newDrawable("white", Color.GREEN);      // Replace with fill texture
//        style.background.setMinHeight(15); // adjust height
//        style.knob.setMinHeight(15);
//        style.knobBefore.setMinHeight(15);
//
//        ProgressBar progressBar = new ProgressBar(0f, 1f, 0.01f, false, style);
//        progressBar.setValue(0.01f);
//        progressBar.setAnimateDuration(0.25f);
        return new ProgressBar(0f, 1f, 0.01f, false, skin, "default-horizontal");
    }
}
