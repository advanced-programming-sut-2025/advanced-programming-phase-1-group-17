package io.github.StardewValley.shared;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.github.StardewValley.shared.models.backpack.NormalItemType;
import io.github.StardewValley.shared.models.enums.Season;
import io.github.StardewValley.shared.models.market.StoreType;

import java.util.ArrayList;
import java.util.HashMap;


public class GameAssetManager {
    private static GameAssetManager gameAssetManager;
    private final String skin = "Skin/StardewSkin.json";

    private final String  backgroundTexture1 = "Flooring/Flooring_44.png";
    private final String backgroundTexture2 = "Flooring/Flooring_50.png";
    private final int tileWidth = 120;
    private final int tileHeight = 120;

    private final String  plowedTexture ="Flooring/Flooring_57.png";
    private final String  farmTexture ="Flooring/Flooring_14.png";

    //For GreenHouse
    private final String  lakeTexture = "lake.png";
    private final String  greenHouseFenceTexture = "Fence/Hardwood_Fence.png";
    private final String  greenHouseTexture = "Greenhouse/greenhouse.png";

    //For backpack and Tools
    private final String backPackTexture = "Tools/36_Backpack.png";

    private final HashMap<Season, String > seasonalMapTextures = new HashMap<>();
    private final HashMap<StoreType, HashMap<Season, TextureRegion>> storeTextures = new HashMap<>();
    private final String  shippingBinTexture =("Chest/ChestOrange.png");

    private final HashMap<String, String> abilityTextures = new HashMap<>() {{
        put("Farming", ("Skill/Farming_Skill_Icon.png"));
        put("Fishing", ("Skill/Fishing_Skill_Icon.png"));
        put("Foraging",("Skill/Foraging_Skill_Icon.png"));
        put("Mining", ("Skill/Mining_Skill_Icon.png"));
    }};

    private final HashMap<NormalItemType, String> normalItemTextures = new HashMap<>();
    private final ArrayList<TextureRegion> grassTextures = new ArrayList<>();

    private GameAssetManager() {
        loadStoreTextures();
        loadNormalItemTextures();
//        loadGrassTextures();
    }

//    private void loadGrassTextures() {
//        Texture texture = new Texture("sprites/Grass.png");
//        int grassHeight = texture.getHeight() / 12;
//        for (int row = 0; row < 12; row++) {
//            if (row == 7)
//                continue;
//            for (int column = 0; column < 3; column++) {
//                grassTextures.add(new TextureRegion(
//                    texture,
//                    12 * column,
//                    grassHeight * row,
//                    12,
//                    grassHeight
//                ));
//            }
//        }
//    }

    public static GameAssetManager getGameAssetManager() {
        if (gameAssetManager == null) {
            gameAssetManager = new GameAssetManager();
        }
        return gameAssetManager;
    }

    public String getSkin() {
        return skin;
    }

    public String getBackgroundTexture1() {
        return backgroundTexture1;
    }

    public String getBackgroundTexture2() {
        return backgroundTexture2;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public String getPlowedTexture() {
        return plowedTexture;
    }

    public String getGreenHouseTexture() {
        return greenHouseTexture;
    }


    public String getGreenHouseFenceTexture() {
        return greenHouseFenceTexture;
    }
    public String getFarmTexture() {
        return "Flooring/Flooring_44.png";
    }

    public String  getLakeTexture() {
        return lakeTexture;
    }

    public String getBackPackTexture() {
        return "Tools/36_Backpack.png";
    }
    public String getFenceTexture() {
        return "Fence/Gate.png";
    }

    public String getFenceTexture2() {
        return "Fence/Hardwood_Fence.png";
    }


    private void loadNormalItemTextures() {
        for (NormalItemType normalItemType : NormalItemType.values()) {
            if (normalItemType.equals(NormalItemType.Well)) {
                continue;
            }
            normalItemTextures.put(normalItemType,normalItemType.getTexturePath());
        }
    }


    private void loadStoreTextures() {
        // 1. Load each season’s map texture only once:
        //TODO -- createFilteredTexture() --
        seasonalMapTextures.put(Season.Spring,"sprites/Pelican Town Spring.png");
        seasonalMapTextures.put(Season.Summer, "sprites/Pelican Town Summer.png");
        seasonalMapTextures.put(Season.Fall, "sprites/Pelican Town Fall.png");
        seasonalMapTextures.put(Season.Winter, "sprites/Pelican Town Winter.png");

        for (StoreType storeType : StoreType.values()) {
            storeTextures.put(storeType, new HashMap<>());
        }
        //TODO
        // 2. Build regions based on those persistent textures
//        seasonalMapTextures.forEach((season, texture) -> {
//            //storeTextures.get(StoreType.PierresGeneralStore).put(season, new TextureRegion(texture, 240, 175, 106, 145));
//            storeTextures.get(StoreType.PierresGeneralStore).put(season, new TextureRegion(texture, 83, 177, 157, 145));
//            storeTextures.get(StoreType.StardropSaloon).put(season, new TextureRegion(texture, 240, 177, 106, 145));
//
//            storeTextures.get(StoreType.JojaMart).put(season, new TextureRegion(texture, 0, 800, 320, 187));
//            storeTextures.get(StoreType.Blacksmith).put(season, new TextureRegion(texture, 400, 0, 112, 135));
//            storeTextures.get(StoreType.FishShop).put(season, new TextureRegion(texture, 256, 0, 144, 175));
//            storeTextures.get(StoreType.CarpentersShop).put(season, new TextureRegion(texture, 0, 0, 125, 175));
//            storeTextures.get(StoreType.Ranch).put(season, new TextureRegion(texture, 125, 0, 131, 175));
//        });
    }

    private Texture createFilteredTexture(String path) {
        Texture texture = new Texture(path);
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        return texture;
    }

    public TextureRegion getStoreTexture(Season season, StoreType type) {
        return storeTextures.get(type).get(season);
    }

    public String getAbilityTextures(String abilityName) {
        return abilityTextures.get(abilityName);
    }

    public String getNormalItemTexture(NormalItemType normalItemType) {
        return normalItemTextures.get(normalItemType);
    }

    public ArrayList<TextureRegion> getGrassTextures() {
        return grassTextures;
    }

    public String getShippingBinTexture() {
        return shippingBinTexture;
    }
}
