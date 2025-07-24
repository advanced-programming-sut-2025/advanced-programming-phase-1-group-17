package io.github.StardewValley.shared;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.github.StardewValley.shared.models.enums.Season;
import io.github.StardewValley.shared.models.market.StoreType;

import java.util.HashMap;


public class GameAssetManager {
    private static GameAssetManager gameAssetManager;
    private final Skin skin = new Skin(Gdx.files.internal("skin/star-soldier-ui.json"));

    private final Texture backgroundTexture1 = new Texture("Flooring/Flooring_44.png");
    private final Texture backgroundTexture2 = new Texture("Flooring/Flooring_50.png");
    private final float tileWidth = backgroundTexture1.getWidth();
    private final float tileHeight = backgroundTexture1.getHeight();

    private final Texture plowedTexture = new Texture("Flooring/Flooring_57.png");
    private final Texture farmTexture = new Texture("Flooring/Flooring_14.png");

    //For GreenHouse
    private final Texture lakeTexture = new Texture("lake.png");
    private final Texture greenHouseFenceTexture = new Texture("Fence/Hardwood_Fence.png");
    private final Texture greenHouseTexture = new Texture("Greenhouse/greenhouse.png");

    //For backpack and Tools
    private final Texture backPackTexture = new Texture("Tools/36_Backpack.png");

    private final HashMap<Season, Texture> seasonalMapTextures = new HashMap<>();
    private final HashMap<StoreType, HashMap<Season, TextureRegion>> storeTextures = new HashMap<>();

    private GameAssetManager() {
        loadStoreTextures();
    }

    public static GameAssetManager getGameAssetManager() {
        if (gameAssetManager == null) {
            gameAssetManager = new GameAssetManager();
        }
        return gameAssetManager;
    }

    public Skin getSkin() {
        return skin;
    }

    public Texture getBackgroundTexture1() {
        return backgroundTexture1;
    }

    public Texture getBackgroundTexture2() {
        return backgroundTexture2;
    }

    public float getTileWidth() {
        return tileWidth;
    }

    public float getTileHeight() {
        return tileHeight;
    }

    public Texture getPlowedTexture() {
        return plowedTexture;
    }

    public Texture getGreenHouseTexture() {
        return greenHouseTexture;
    }


    public Texture getGreenHouseFenceTexture() {
        return greenHouseFenceTexture;
    }
    public String getFarmTexture() {
        return "Flooring/Flooring_44.png";
    }

    public Texture getLakeTexture() {
        return lakeTexture;
    }

    public Texture getBackPackTexture() {
        return backPackTexture;
    }
    public String getFenceTexture() {
        return "Fence/Gate.png";
    }

    public String getFenceTexture2() {
        return "Fence/Hardwood_Fence.png";
    }




    private void loadStoreTextures() {
        // 1. Load each season’s map texture only once:
        seasonalMapTextures.put(Season.Spring, createFilteredTexture("sprites/Pelican Town Spring.png"));
        seasonalMapTextures.put(Season.Summer, createFilteredTexture("sprites/Pelican Town Summer.png"));
        seasonalMapTextures.put(Season.Fall, createFilteredTexture("sprites/Pelican Town Fall.png"));
        seasonalMapTextures.put(Season.Winter, createFilteredTexture("sprites/Pelican Town Winter.png"));

        for (StoreType storeType : StoreType.values()) {
            storeTextures.put(storeType, new HashMap<>());
        }

        // 2. Build regions based on those persistent textures:
        seasonalMapTextures.forEach((season, texture) -> {
            //storeTextures.get(StoreType.PierresGeneralStore).put(season, new TextureRegion(texture, 240, 175, 106, 145));
            storeTextures.get(StoreType.PierresGeneralStore).put(season, new TextureRegion(texture, 83, 177, 157, 145));
            storeTextures.get(StoreType.StardropSaloon).put(season, new TextureRegion(texture, 240, 177, 106, 145));

            storeTextures.get(StoreType.JojaMart).put(season, new TextureRegion(texture, 0, 800, 320, 187));
            storeTextures.get(StoreType.Blacksmith).put(season, new TextureRegion(texture, 400, 0, 112, 135));
            storeTextures.get(StoreType.FishShop).put(season, new TextureRegion(texture, 256, 0, 144, 175));
            storeTextures.get(StoreType.CarpentersShop).put(season, new TextureRegion(texture, 0, 0, 125, 175));
            storeTextures.get(StoreType.Ranch).put(season, new TextureRegion(texture, 125, 0, 131, 175));
        });
    }

    private Texture createFilteredTexture(String path) {
        Texture texture = new Texture(path);
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        return texture;
    }

    public TextureRegion getStoreTexture(Season season, StoreType type) {
        return storeTextures.get(type).get(season);
    }
}
