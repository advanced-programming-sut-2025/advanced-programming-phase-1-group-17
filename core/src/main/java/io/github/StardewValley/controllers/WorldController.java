package io.github.StardewValley.controllers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.GameAssetManager;
import io.github.StardewValley.Main;
import io.github.StardewValley.models.*;
import io.github.StardewValley.models.NPCS.NPC;
import io.github.StardewValley.models.enums.Season;
import io.github.StardewValley.models.map.*;
import io.github.StardewValley.models.market.Store;
import io.github.StardewValley.models.market.StoreType;
import io.github.StardewValley.models.plant.Tree;

public class WorldController {
    private final OrthographicCamera camera;

    private transient Texture backgroundTexture;
    private transient Texture backgroundTexture2;
    private int tileWidth;
    private int tileHeight;

    public WorldController(OrthographicCamera camera) {
        this.camera = camera;
    }

    public void initTransients() {
        this.backgroundTexture = GameAssetManager.getGameAssetManager().getBackgroundTexture1();
        this.backgroundTexture2 = GameAssetManager.getGameAssetManager().getBackgroundTexture2();
        this.tileWidth = backgroundTexture.getWidth();
        this.tileHeight = backgroundTexture.getHeight();
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
                    else if (tile.getPlaceable() == null)
                        continue;
                        //TODO: will be deleted
                    else if (tile.getPlaceable().getTexture() == null)
                        continue;
                    else if (tile.getPlaceable() instanceof Fence)
                        Main.getBatch().draw(tile.getPlaceable().getTexture(), tile.getX() * tileWidth, tile.getY() * tileHeight, 80, 80);
                    else if (tile.getPlaceable() instanceof Hut)
                        continue;
                    else if (tile.getPlaceable() instanceof NPC && !((NPC) tile.getPlaceable()).isNPC())
                        continue;
                    else if (tile.getPlaceable() instanceof Tree)
                        continue;
                    else if (tile.getPlaceable() instanceof NPC && ((NPC) tile.getPlaceable()).isNPC())
                        Main.getBatch().draw(tile.getPlaceable().getTexture(), tile.getX() * tileWidth, tile.getY() * tileHeight, (float) backgroundTexture.getWidth() / 1.5f, (float) backgroundTexture.getHeight() / 1.5f);
                    else
                        Main.getBatch().draw(tile.getPlaceable().getTexture(), tile.getX() * tileWidth, tile.getY() * tileHeight);

            }
        }
        drawBigTextures();
    }

    private void drawBigTextures() {
        for (Tile tile : Tile.getTreeTile()) {
            Main.getBatch().draw(tile.getPlaceable().getTexture(), tile.getX() * tileWidth, tile.getY() * tileHeight);
        }
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
        drawStores();
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


//        for (Tile tile : tiles) {
//            if (tile == null) {
//                System.out.print("null");
//            } else if (tile.getPlaceable() instanceof Animal animal) {
//                if (animal.getAnimalType().equals(AnimalType.Chicken)) {
//
////                    System.out.print(BOLD + YELLOW + "C" + RESET);
//                } else if (animal.getAnimalType().equals(AnimalType.Duck)) {
////                    System.out.print(BOLD + WHITE + "d" + RESET);
//                } else if (animal.getAnimalType().equals(AnimalType.Rabbit)) {
////                    System.out.print(BOLD + WHITE + "R" + RESET);
//                } else if (animal.getAnimalType().equals(AnimalType.Dinosaur)) {
////                    System.out.print(BOLD + RED + "D" + RESET);
//                } else if (animal.getAnimalType().equals(AnimalType.Cow)) {
////                    System.out.print(BOLD + CHOCOLATE + "C" + RESET);
//                } else if (animal.getAnimalType().equals(AnimalType.Goat)) {
////                    System.out.print(BOLD + WHITE + "G" + RESET);
//                } else if (animal.getAnimalType().equals(AnimalType.Sheep)) {
////                    System.out.print(BOLD + WHITE + "S" + RESET);
//                } else if (animal.getAnimalType().equals(AnimalType.Pig)) {
////                    System.out.print(BOLD + CHOCOLATE + "P" + RESET);
//                }
//            } else if (tile.getPlaceable() instanceof AnimalPlace) {
////                System.out.print(BOLD + ORANGE + "A" + RESET);
//            } else if (tile.getPlaceable() instanceof NPC) {
//                if (tile.getNpcIsHere() == null) {
//                    if (tile.getPlaceable() instanceof Abigail) {
////                        System.out.print(BOLD + BLUE + "A" + RESET);
//                    } else if (tile.getPlaceable() instanceof Harvey) {
////                        System.out.print(BOLD + GREEN + "H" + RESET);
//                    } else if (tile.getPlaceable() instanceof Lia) {
////                        System.out.print(BOLD + CHOCOLATE + "L" + RESET);
//                    } else if (tile.getPlaceable() instanceof Robin) {
////                        System.out.print(BOLD + RED + "R" + RESET);
//                    } else if (tile.getPlaceable() instanceof Sebastian) {
////                        System.out.print(BOLD + YELLOW + "S" + RESET);
//                    }
//                } else {
//                    if (tile.getPlaceable() instanceof Abigail) {
////                        System.out.print(BOLD + WHITE + "A" + RESET);
//                    } else if (tile.getPlaceable() instanceof Harvey) {
////                        System.out.print(BOLD + WHITE + "H" + RESET);
//                    } else if (tile.getPlaceable() instanceof Lia) {
////                        System.out.print(BOLD + WHITE + "L" + RESET);
//                    } else if (tile.getPlaceable() instanceof Robin) {
////                        System.out.print(BOLD + WHITE + "R" + RESET);
//                    } else if (tile.getPlaceable() instanceof Sebastian) {
////                        System.out.print(BOLD + WHITE + "S" + RESET);
//                    }
//                }
//            } else if (tile.getWhoIsHere() != null) {
//                if (tile.getWhoIsHere().equals(game.getPlayers().get(0))) {
////                    System.out.print(BOLD + WHITE + "P" + RESET);
//                } else if (tile.getWhoIsHere().equals(game.getPlayers().get(1))) {
////                    System.out.print(BOLD + RED + "P" + RESET);
//                } else if (tile.getWhoIsHere().equals(game.getPlayers().get(2))) {
////                    System.out.print(BOLD + BLUE + "P" + RESET);
//                } else if (tile.getWhoIsHere().equals(game.getPlayers().get(3))) {
////                    System.out.print(BOLD + YELLOW + "P" + RESET);
//                }
//            } else if (tile.getPlaceable() instanceof Quarry) {
////                System.out.print(BOLD + CYAN + "Q" + RESET);
//            } else if (tile.getPlaceable() instanceof Lake) {
////                System.out.print(BOLD + BLUE + "L" + RESET);
//            } else if (tile.getPlaceable() instanceof Mineral) {
////                System.out.print(BOLD + CHOCOLATE + "M" + RESET);
//            } else if (tile.getPlaceable() instanceof Hut) {
////                System.out.print(BOLD + YELLOW + "H" + RESET);
//            } else if (tile.getPlaceable() instanceof GreenHouse greenHouse) {
//                if (greenHouse.isActive())
////                    System.out.print(BOLD + GREEN + "G" + RESET);
//                else
////                    System.out.print(BOLD + PURPLE + "G" + RESET);
//            } else if (tile.getPlaceable() instanceof CraftingItem || (tile.getPlaceable() instanceof ShopItem shopItem
//                && shopItem.getType().getClass().equals(CraftingItemType.class))) {
////                System.out.print(BOLD + RED + "A" + RESET);
//            } else if (tile.getPlaceable() instanceof Tree) {
////                System.out.print(BOLD + GREEN + "T" + RESET);
//            } else if (tile.getPlaceable() instanceof Crop crop) {
//                if (crop.isGiant())
////                    System.out.print(BOLD + RED + "G" + RESET);
//                else
////                    System.out.print(BOLD + RED + "C" + RESET);
//            } else if (tile.getPlaceable() instanceof NormalItem normalItem) {
//                if (normalItem.getType().equals(NormalItemType.Wood))
////                    System.out.print(BOLD + CHOCOLATE + "W" + RESET);
//                else if (normalItem.getType().equals(NormalItemType.Grass))
////                    System.out.print(BOLD + WHITE + "G" + RESET);
//                else if (normalItem.getType().equals(NormalItemType.Fiber))
////                    System.out.print(BOLD + GREEN + "F" + RESET);
//            } else if ((tile.getPlaceable() instanceof ShopItem shopItem
//                && shopItem.getType().getClass().equals(NormalItemType.class))) {
//                NormalItem normalItem = new NormalItem((NormalItemType) shopItem.getType());
//                if (normalItem.getType().equals(NormalItemType.Wood))
////                    System.out.print(BOLD + CHOCOLATE + "W" + RESET);
//                else if (normalItem.getType().equals(NormalItemType.Grass))
////                    System.out.print(BOLD + WHITE + "G" + RESET);
//                else if (normalItem.getType().equals(NormalItemType.Fiber))
////                    System.out.print(BOLD + GREEN + "F" + RESET);
//            } else if (tile.getPlaceable() instanceof Abigail) {
////                System.out.print(BOLD + BLUE + "A" + RESET);
//            } else if (tile.getPlaceable() instanceof Harvey) {
////                System.out.print(BOLD + GREEN + "H" + RESET);
//            } else if (tile.getPlaceable() instanceof Lia) {
////                System.out.print(BOLD + WHITE + "L" + RESET);
//            } else if (tile.getPlaceable() instanceof Robin) {
////                System.out.print(BOLD + RED + "R" + RESET);
//            } else if (tile.getPlaceable() instanceof Sebastian) {
////                System.out.print(BOLD + YELLOW + "S" + RESET);
//            } else if (tile.getPlaceable() instanceof Seed) {
////                System.out.printf(BOLD + GREEN + "S" + RESET);
//            } else if (tile.getPlaceable() instanceof GreenHouseFence) {
////                System.out.printf(BOLD + CHOCOLATE + "F" + RESET);
//            } else if (tile.getPlaceable() instanceof ShippingBin) {
////                System.out.print(BOLD + RED + "X" + RESET);
//            } else if (tile.getPlaceable() instanceof Store store) {
//                if (store.getType().equals(StoreType.Blacksmith))
////                    System.out.print(BOLD + YELLOW + "B" + RESET);
//                else if (store.getType().equals(StoreType.Ranch))
////                    System.out.print(BOLD + YELLOW + "R" + RESET);
//                else if (store.getType().equals(StoreType.StardropSaloon))
////                    System.out.print(BOLD + BLUE + "S" + RESET);
//                else if (store.getType().equals(StoreType.CarpentersShop))
////                    System.out.print(BOLD + YELLOW + "C" + RESET);
//                else if (store.getType().equals(StoreType.JojaMart))
////                    System.out.print(BOLD + YELLOW + "J" + RESET);
//                else if (store.getType().equals(StoreType.PierresGeneralStore))
////                    System.out.print(BOLD + YELLOW + "P" + RESET);
//                else if (store.getType().equals(StoreType.FishShop))
////                    System.out.print(BOLD + YELLOW + "F" + RESET);
//            } else {
//                if (tile.getPlaceable() instanceof Seed) {

    /// /                    System.out.print(BOLD + GREEN + "S" + RESET);
//                } else {
//                    System.out.print(" ");
//                }
//            }
//        }

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
}
