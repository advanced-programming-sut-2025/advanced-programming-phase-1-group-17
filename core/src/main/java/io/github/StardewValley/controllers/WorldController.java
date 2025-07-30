package io.github.StardewValley.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import io.github.StardewValley.GameAssetManager;
import io.github.StardewValley.Main;
import io.github.StardewValley.models.*;
import io.github.StardewValley.models.NPCS.NPC;
import io.github.StardewValley.models.artisan.ArtisanProduct;
import io.github.StardewValley.models.crafting.CraftingItem;
import io.github.StardewValley.models.enums.Season;
import io.github.StardewValley.models.map.*;
import io.github.StardewValley.models.market.Store;
import io.github.StardewValley.models.market.StoreType;
import io.github.StardewValley.models.plant.*;

import java.util.Arrays;
import java.util.HashMap;

public class WorldController {
    private final OrthographicCamera camera;

    private transient Texture backgroundTexture;
    private transient Texture backgroundTexture2;
    private int tileWidth;
    private int tileHeight;
    private HashMap<Tree, float[]> treesInThisFrame = new HashMap<>();
    private HashMap<Crop, float[]> giantCropsInThisFrame = new HashMap<>();

    public WorldController(OrthographicCamera camera) {
        this.camera = camera;
    }

    public void initTransients() {
        this.backgroundTexture = GameAssetManager.getGameAssetManager().getBackgroundTexture1();
        this.backgroundTexture2 = GameAssetManager.getGameAssetManager().getBackgroundTexture2();
        this.tileWidth = backgroundTexture.getWidth();
        this.tileHeight = backgroundTexture.getHeight();
        System.out.println(tileHeight + " " + tileWidth);
    }

    public void update() {
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
        for (int x = minTileX - 1; x < maxTileX; x++) {
            for (int y = minTileY - 1; y < maxTileY; y++) {
                if (x < -2 || y < -2 || x > 300 || y > 300)
                    continue;
                Tile tile = Tile.getTile(x + 1, y + 1);
                if (tile == null) continue;
                if (tile.getPlaceable() instanceof Store)
                    continue;

                if ((tile.getX() + tile.getY()) % 2 == 0)
                    Main.getBatch().draw(backgroundTexture2, tile.getX() * tileWidth, tile.getY() * tileHeight);
                else
                    Main.getBatch().draw(backgroundTexture, tile.getX() * tileWidth, tile.getY() * tileHeight);

                if (tile.isPlowed())
                    Main.getBatch().draw(GameAssetManager.getGameAssetManager().getPlowedTexture(), tile.getX() * tileWidth, tile.getY() * tileHeight);
                if (tile.getPlaceable() == null)
                    continue;
                    //TODO: will be deleted
//                if (tile.getPlaceable().getTexture() == null)
//                    continue;
                printTileTexture(tile);
            }
        }

        drawCraftingItemsProgressBars();
        drawBigTextures();
        drawStores();
        drawTrees();
        drawGiantCrops();
        Main.getBatch().draw(TreeAssetManager.getTreeAssetManager().getFullyGrownTexture(TreeType.ApricotTree, Season.Spring), 0, 0);
    }

    private void drawGiantCrops() {
        giantCropsInThisFrame.forEach((crop, coordinates) -> {
            Main.getBatch().draw(
                CropAssetManager.getCropAssetManager().getGiantTexture(crop.getType()),
                coordinates[0],
                coordinates[1],
                tileWidth * 2 - 10,
                tileHeight * 2 - 10
            );
        });
    }

    private void drawTrees() {
        treesInThisFrame.forEach((tree, coordinates) -> {
            Texture texture = tree.getTexture();
            if (texture != null)
                Main.getBatch().draw(texture, coordinates[0], coordinates[1]);
            else
                Main.getBatch().draw(
                    TreeAssetManager.getTreeAssetManager().getFullyGrownTexture(tree.getType(), App.getCurrentGame().getDate().getSeason()),
                    coordinates[0],
                    coordinates[1]
                );
        });
    }

    private void printTileTexture(Tile tile) {
        Texture texture = null;
        float printX = 0, printY = 0;
        if (tile.getPlaceable().getTexture() != null) {
            texture = tile.getPlaceable().getTexture();
            printX = (tile.getX() * tileWidth) +
                ((tileWidth - texture.getWidth()) / 2f);
            printY = (tile.getY() * tileHeight) + 10;
        }

        if (tile.getPlaceable() instanceof Crop crop) {
            if (crop.isGiant()) {
                if (crop.isLeftBottomTileOfGiant())
                    giantCropsInThisFrame.put(crop, new float[]{printX, printY});
                else
                    return;
            }
        } else if (tile.getPlaceable() instanceof Tree tree) {
            if (texture == null) {
                TextureRegion textureRegion = TreeAssetManager.getTreeAssetManager().getFullyGrownTexture(tree.getType(), App.getCurrentGame().getDate().getSeason());
                printX = (tile.getX() * tileWidth) +
                    ((tileWidth - textureRegion.getRegionWidth()) / 2f);
                printY = (tile.getY() * tileHeight) + 10;
            }
            treesInThisFrame.put(tree, new float[]{printX, printY});
            return;
        }
        switch (tile.getPlaceable()) {
            case Fence fence -> Main.getBatch().draw(texture, printX, printY, 80, 80);
            case Hut hut -> {
                return;
            }
            case NPC npc when !npc.isNPC() -> {
                return;
            }
            case NPC npc when npc.isNPC() ->
                Main.getBatch().draw(texture, printX, printY, (float) backgroundTexture.getWidth() / 1.5f, (float) backgroundTexture.getHeight() / 1.5f);
            case null, default -> Main.getBatch().draw(texture, printX, printY);
        }
    }

    private void drawCraftingItemsProgressBars() {
        for (CraftingItem craftingItem : CraftingItem.getAllCraftingItems()) {
            ProgressBar bar = craftingItem.getProgressBar();
            ArtisanProduct artisanProduct = craftingItem.getArtisanProductInProgress();
            if (bar != null) {
                bar.setPosition(craftingItem.getStart_x() * tileWidth, craftingItem.getStart_y() * tileHeight + craftingItem.getHeight() + 5);
                bar.setWidth(craftingItem.getWidth());
                bar.setHeight(50f);
                bar.act(Gdx.graphics.getDeltaTime());
                bar.draw(Main.getBatch(), 1f);
            } else if (artisanProduct != null && artisanProduct.isReady()) {
                Main.getBatch().draw(
                    artisanProduct.getType().getInventoryTexture(),
                    craftingItem.getStart_x() * tileWidth, craftingItem.getStart_y() * tileHeight + craftingItem.getHeight() + 5,
                    (float) 0.5 * tileWidth, (float) 0.5 * tileHeight
                    );
            }
        }
    }

    private void drawBigTextures() {
        for (int i = 0 ; i < 4 ; i++) {
            Main.getBatch().draw(App.getCurrentGame().getPlayers().get(i).getPlayerMap().getHut().getTexture()
                ,App.getCurrentGame().getPlayers().get(i).getPlayerMap().getHut().getX() * tileWidth,
                App.getCurrentGame().getPlayers().get(i).getPlayerMap().getHut().getY() * tileHeight, 400 , 400);
        }
        for (int i = 0 ; i < 5 ; i++) {
            Main.getBatch().draw(App.getCurrentGame().getNPCHuts().get(i).getTexture(),
                App.getCurrentGame().getNPCHuts().get(i).x_start * tileWidth, App.getCurrentGame().getNPCHuts().get(i).y_start * tileHeight, 500 , 500);
        }
        for (GreenHouse greenHouse : App.getCurrentGame().getGreenHouses()) {
            Main.getBatch().draw(
                GameAssetManager.getGameAssetManager().getGreenHouseTexture(),
                greenHouse.getStarting_x() * tileWidth, greenHouse.getStarting_y() * tileHeight,
                greenHouse.getWidth() * tileWidth, greenHouse.getHeight() * tileHeight);
        }
    }

    private void drawStores() {
        GameAssetManager assets = GameAssetManager.getGameAssetManager();
        Season season = App.getCurrentGame().getDate().getSeason();

        Store store = App.getCurrentGame().getStoreManager().getStore(StoreType.Blacksmith);
        Main.getBatch().draw(assets.getStoreTexture(season, StoreType.Blacksmith),
            store.getStart_x() * tileWidth, store.getStart_y() *  tileHeight,
            store.getWidth() * tileWidth, store.getHeight() * tileHeight);

        store = App.getCurrentGame().getStoreManager().getStore(StoreType.JojaMart);
        Main.getBatch().draw(assets.getStoreTexture(season, StoreType.JojaMart),
            store.getStart_x() * tileWidth, store.getStart_y() *  tileHeight,
            store.getWidth() * tileWidth, store.getHeight() * tileHeight);

        store = App.getCurrentGame().getStoreManager().getStore(StoreType.PierresGeneralStore);
        Main.getBatch().draw(assets.getStoreTexture(season, StoreType.PierresGeneralStore),
            store.getStart_x() * tileWidth, store.getStart_y() *  tileHeight,
            store.getWidth() * tileWidth, store.getHeight() * tileHeight);

        store = App.getCurrentGame().getStoreManager().getStore(StoreType.CarpentersShop);
        Main.getBatch().draw(assets.getStoreTexture(season, StoreType.CarpentersShop),
            store.getStart_x() * tileWidth, store.getStart_y() *  tileHeight,
            store.getWidth() * tileWidth, store.getHeight() * tileHeight);

        store = App.getCurrentGame().getStoreManager().getStore(StoreType.FishShop);
        Main.getBatch().draw(assets.getStoreTexture(season, StoreType.FishShop),
            store.getStart_x() * tileWidth, store.getStart_y() *  tileHeight,
            store.getWidth() * tileWidth, store.getHeight() * tileHeight);

        store = App.getCurrentGame().getStoreManager().getStore(StoreType.Ranch);
        Main.getBatch().draw(assets.getStoreTexture(season, StoreType.Ranch),
            store.getStart_x() * tileWidth, store.getStart_y() *  tileHeight,
            store.getWidth() * tileWidth, store.getHeight() * tileHeight);

        store = App.getCurrentGame().getStoreManager().getStore(StoreType.StardropSaloon);
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
}
