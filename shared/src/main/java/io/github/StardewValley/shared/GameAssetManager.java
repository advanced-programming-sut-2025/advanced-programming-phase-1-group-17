package io.github.StardewValley.shared;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

    private final HashMap<StoreType, HashMap<Season, TextureRegion>> storeTextures = new HashMap<>();
    private final String  shippingBinTexture =("Chest/ChestOrange.png");

    private final HashMap<NormalItemType, String> normalItemTextures = new HashMap<>();
    private final ArrayList<TextureRegion> grassTextures = new ArrayList<>();

    private GameAssetManager() {
        loadNormalItemTextures();
    }

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

    private Texture createFilteredTexture(String path) {
        Texture texture = new Texture(path);
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        return texture;
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
