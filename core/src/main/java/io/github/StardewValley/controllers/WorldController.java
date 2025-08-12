package io.github.StardewValley.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.github.StardewValley.GameAssetManagerClient;
import io.github.StardewValley.GameClient;
import io.github.StardewValley.controllers.UIControllers.LightningRenderController;
import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.dto.CraftingItemDTO;
import io.github.StardewValley.shared.models.game.GameState;
import io.github.StardewValley.shared.models.TileDTO;
import io.github.StardewValley.shared.models.enums.Season;
import io.github.StardewValley.shared.models.game.VotingSession;
import io.github.StardewValley.shared.models.market.Store;
import io.github.StardewValley.shared.models.market.StoreType;
import io.github.StardewValley.shared.models.plant.Crop;
import io.github.StardewValley.shared.models.plant.Tree;
import io.github.StardewValley.views.ForceTerminateMenu;
import io.github.StardewValley.views.VotingMenu;
import kotlin.Pair;

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
    private final List<Pair<Texture, float[]>> treesInThisFrame = new ArrayList<>();
    private final List<Pair<TextureRegion, float[]>> treeTextureRegions = new ArrayList<>();
    private final List<Pair<String, float[]>> giantCropsInThisFrame = new ArrayList<>();

    private GameState gameState;

    private List<CraftingItemDTO> craftingItems = new ArrayList<>();
    private final HashMap<String, ProgressBar> progressBarMap = new HashMap<>();
    private final LightningRenderController lightningRenderController;

    public WorldController(OrthographicCamera camera) {
        this.lightningRenderController = LightningRenderController.getLightningController();
        this.camera = camera;
    }

    public void initTransients() {
        this.backgroundTexture = new Texture(GameAssetManager.getGameAssetManager().getBackgroundTexture1());
        this.backgroundTexture2 = new Texture(GameAssetManager.getGameAssetManager().getBackgroundTexture2());
        this.tileWidth = backgroundTexture.getWidth();
        this.tileHeight = backgroundTexture.getHeight();
    }

    public void update() throws Exception {
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
        treeTextureRegions.clear();

        gameState = GameClient.getGameStateApiClient().getGameState(minTileX, maxTileX, minTileY, maxTileY);
        if (gameState.isPaused()) {
            if (gameState.getType().equals(VotingSession.VotingType.FORCE_TERMINATE)) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new ForceTerminateMenu(
                    new ForceTerminateController(),
                    GameAssetManagerClient.getGameAssetManager().getSkin()
                ));
            } else if(gameState.getType().equals(VotingSession.VotingType.KICK_PLAYER)) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new VotingMenu(
                    new VotingMenuController(),
                    GameAssetManagerClient.getGameAssetManager().getSkin(),
                    gameState.getTargetUsername()
                    ));
            }
            return;
        }

        List<TileDTO> tiles = gameState.getTiles();
        craftingItems = gameState.getCraftingItems();

        lightningRenderController.applyLightningState(gameState.getLightningStateDTO());
        lightningRenderController.renderLightning(Main.getBatch(), GameClient.getPlayer());

        for (int x = minTileX - 1; x < maxTileX; x++) {
            for (int y = minTileY - 1; y < maxTileY; y++) {
                if (x < -2 || y < -2 || x > 300 || y > 300)
                    continue;
                TileDTO tile = null;
                for (TileDTO tileDTO : tiles) {
                    if (tileDTO.getX() == x && tileDTO.getY() == y) {
                        tile = tileDTO;
                        break;
                    }
                }

                if (tile == null) continue;

                if (tile.isPlowed())
                    Main.getBatch().draw(GameAssetManagerClient.getGameAssetManager().getPlowedTexture(), tile.getX() * tileWidth, tile.getY() * tileHeight);
                else if ((tile.getX() + tile.getY()) % 2 == 0)
                    Main.getBatch().draw(backgroundTexture2, tile.getX() * tileWidth, tile.getY() * tileHeight);
                else
                    Main.getBatch().draw(backgroundTexture, tile.getX() * tileWidth, tile.getY() * tileHeight);
                if (tile.getPlaceableType() == null)
                    continue;
                if (tile.getPlaceableType().equals(Store.class.getSimpleName()))
                    continue;

                printTileTexture(tile, gameState.getTimeAndDateDTO().getSeason());
            }
        }

        drawCraftingItemsProgressBars();
        drawBigTextures();
        drawStores();
        drawTrees();
        drawGiantCrops();
    }

    private void drawGiantCrops() {
        for (var entry : giantCropsInThisFrame) {
            Main.getBatch().draw(
                GameAssetManagerClient.getGameAssetManager().getTexture(entry.getFirst()),
                entry.getSecond()[0],
                entry.getSecond()[1],
                tileWidth * 2 - 10,
                tileHeight * 2 - 10
            );
        }
    }

    private void drawTrees() {
        for (var entry : treesInThisFrame) {
            Main.getBatch().draw(entry.getFirst(), entry.getSecond()[0], entry.getSecond()[1]);
        }
        for (var entry : treeTextureRegions) {
            Main.getBatch().draw(entry.getFirst(), entry.getSecond()[0], entry.getSecond()[1]);
        }
    }

    private void printTileTexture(TileDTO tile, Season season) {
        Texture texture = GameAssetManagerClient.getGameAssetManager().getTexture((tile.getTexturePath()));
        float printX = tile.getX() * tileWidth + printPad, printY = tile.getY() * tileHeight + 20;
        float printWidth = tileWidth - 2 * printPad, printHeight = tileHeight - 2 * printPad;

        if (tile.getPlaceableType().equals(Crop.class.getSimpleName())) {
            if (tile.isCropGiant()) {
                if (tile.isLeftBottomCornerOfGiantCrop())
                    giantCropsInThisFrame.add(new Pair<>(tile.getTexturePath(), new float[]{printX, printY}));
                else
                    return;
            }
        }
        else if (tile.getPlaceableType().equals(Tree.class.getSimpleName())) {
            if (texture == null) {
                TextureRegion textureRegion = GameAssetManagerClient.getGameAssetManager().
                    getFullyGrownTexture(tile.getTreeType(), season);
                printX = (tile.getX() * tileWidth) +
                    ((tileWidth - textureRegion.getRegionWidth()) / 2f);
                printY = (tile.getY() * tileHeight) + 10;
                treeTextureRegions.add(new Pair<>(textureRegion, new float[]{printX, printY}));
            } else
                treesInThisFrame.add(new Pair<>(texture, new float[]{printX, printY}));
            return;
        }
        switch (tile.getPlaceableType()) {
            case "Fence" -> Main.getBatch().draw(texture, printX, printY, 80, 80);
            case "Hut" -> {
                return;
            }
            case "GreenHouse" -> {
                if (tile.isPlowed())
                    Main.getBatch().draw(GameAssetManagerClient.getGameAssetManager().getPlowedTexture(), printX, printY);
            }
            case "Sebastian", "Robin", "Lia", "Harvey", "Abigail" -> {
                if (!tile.getTexturePath().startsWith("hut")) {
                    Main.getBatch().draw(texture, printX, printY, (float) backgroundTexture.getWidth() / 1.5f, (float) backgroundTexture.getHeight() / 1.5f);
                }
            }
            case "normalItem" -> {
                if (tile.getGrassTextureID() != 0)
                    Main.getBatch().draw(GameAssetManager.getGameAssetManager().getGrassTextures().get(tile.getGrassTextureID()),
                        printX, printY,
                        printWidth, printHeight);
                else
                    Main.getBatch().draw(texture,
                        printX, printY,
                        printWidth, printHeight);
            }
            case "Lake" -> Main.getBatch().draw(texture, tile.getX() * tileWidth, tile.getY() * tileHeight);
            case null, default -> {
                if (texture == null)
                    System.out.println(tile.getPlaceableType());
                else
                    Main.getBatch().draw(texture,
                    printX, printY,
                    printWidth, printHeight);
            }
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
                    float value = GameClient.getArtisanProductionProgress(craftingItem); // e.g., returns 0.0 to 1.0
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
        for (int x : GameClient.getPlayersHutLocations().keySet()) {
            Main.getBatch().draw(GameAssetManagerClient.getGameAssetManager().getTexture("hut.png")
                , GameClient.getPlayersHutLocations().get(x).get(0) * tileWidth,
                GameClient.getPlayersHutLocations().get(x).get(1) * tileHeight, 400, 400);
        }
        for (int x : GameClient.getNPCsHutsLocations().keySet()) {
            Main.getBatch().draw(GameAssetManagerClient.getGameAssetManager().getTexture("hut2.png"),
                GameClient.getNPCsHutsLocations().get(x).get(0) * tileWidth, GameClient.getNPCsHutsLocations().get(x).get(1) * tileHeight, 600, 600);
        }
        for (int x : GameClient.getGreenHouseLocations().keySet()) {
            List<Integer> values = GameClient.getGreenHouseLocations().get(x);
            Main.getBatch().draw(
                GameAssetManagerClient.getGameAssetManager().getTexture(GameAssetManager.getGameAssetManager().getGreenHouseTexture()),
                values.get(0) * tileWidth, values.get(1) * tileHeight,
                values.get(2) * tileWidth, values.get(3) * tileHeight);
        }
    }

    private void drawStores() {
        GameAssetManagerClient assets = GameAssetManagerClient.getGameAssetManager();
        Season season = gameState.getTimeAndDateDTO().getSeason();

        for (StoreType storeType : StoreType.values()) {
            Main.getBatch().draw(assets.getStoreTexture(season, storeType),
                storeType.getStart_x() * tileWidth, storeType.getStart_y() * tileHeight,
                storeType.getWidth() * tileWidth, storeType.getHeight() * tileHeight);
        }
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
