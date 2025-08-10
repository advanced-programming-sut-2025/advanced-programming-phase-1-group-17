package io.github.StardewValley;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.shared.models.backpack.NormalItemType;
import io.github.StardewValley.shared.models.enums.Season;
import io.github.StardewValley.shared.models.market.StoreType;
import io.github.StardewValley.shared.models.plant.TreeType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GameAssetManagerClient {
    private static GameAssetManagerClient gameAssetManager;
    private final Texture plowedTexture = new Texture("Flooring/Flooring_57.png");
    private final HashMap<String, Texture> textures = new HashMap<>();
    private Skin skin = new Skin(Gdx.files.internal("Skin/StardewSkin.json"));
    private final String  backgroundTexture1 = "Flooring/Flooring_44.png";
    private final String backgroundTexture2 = "Flooring/Flooring_50.png";
    private final int tileWidth = 120;
    private final int tileHeight = 120;

    private final String  farmTexture ="Flooring/Flooring_14.png";

    //For GreenHouse
    private final String  lakeTexture = "lake.png";
    private final String  greenHouseFenceTexture = "Fence/Hardwood_Fence.png";
    private final String  greenHouseTexture = "Greenhouse/greenhouse.png";

    //For backpack and Tools
    private final String backPackTexture = "Tools/36_Backpack.png";

    private final String  shippingBinTexture =("Chest/ChestOrange.png");

    private final HashMap<NormalItemType, String> normalItemTextures = new HashMap<>();
    private final ArrayList<TextureRegion> grassTextures = new ArrayList<>();

    private final HashMap<String, Texture> abilityTextures = new HashMap<>() {{
        put("Farming", new Texture("Skill/Farming_Skill_Icon.png"));
        put("Fishing", new Texture("Skill/Fishing_Skill_Icon.png"));
        put("Foraging", new Texture("Skill/Foraging_Skill_Icon.png"));
        put("Mining", new Texture("Skill/Mining_Skill_Icon.png"));
    }};
    private final HashMap<Season, Texture > seasonalMapTextures = new HashMap<>();
    private final HashMap<StoreType, HashMap<Season, TextureRegion>> storeTextures = new HashMap<>();

    private final HashMap<TreeType, HashMap<Season, TextureRegion>> fullyGrownTextures = new HashMap<>();
    private final HashMap<TreeType, ArrayList<String>> stageTextures = new HashMap<>();

    private GameAssetManagerClient() {
        loadStoreTextures();
        loadNormalItemTextures();
        loadGrassTextures();
        //loadFullyGrownTextures();
        //loadStageTextures();
    }

    public static GameAssetManagerClient getGameAssetManager() {
        if (gameAssetManager == null) {
            gameAssetManager = new GameAssetManagerClient();
        }
        return gameAssetManager;
    }


    public Skin getSkin() {
        return skin;
    }

    public void setSkin(Skin skin) {
        this.skin = skin;
    }

    public Texture getPlowedTexture() {
        return plowedTexture;
    }

    public Texture getTexture(String key) {
        if (textures.containsKey(key)) return textures.get(key);
        else {
            try {
                if (key == null)
                    System.out.println("key is null");
                Texture texture = new Texture(key);
                if (texture == null) {
                    System.out.println("Texture is null");
                    System.out.println(key);
                }
                textures.put(key, texture);
                return texture;
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    public Texture getAbilityTextures(String abilityName) {
        return abilityTextures.get(abilityName);
    }


    private void loadStoreTextures() {
        // 1. Load each season’s map texture only once:
        seasonalMapTextures.put(Season.Spring, new Texture("sprites/Pelican Town Spring.png"));
        seasonalMapTextures.put(Season.Summer, new Texture("sprites/Pelican Town Summer.png"));
        seasonalMapTextures.put(Season.Fall, new Texture("sprites/Pelican Town Fall.png"));
        seasonalMapTextures.put(Season.Winter, new Texture("sprites/Pelican Town Winter.png"));

        for (StoreType storeType : StoreType.values()) {
            storeTextures.put(storeType, new HashMap<>());
        }
        // 2. Build regions based on those persistent textures
        seasonalMapTextures.forEach((season, texture) -> {
            storeTextures.get(StoreType.PierresGeneralStore).put(season, new TextureRegion(texture, 240, 175, 106, 145));
            storeTextures.get(StoreType.PierresGeneralStore).put(season, new TextureRegion(texture, 83, 177, 157, 145));
            storeTextures.get(StoreType.StardropSaloon).put(season, new TextureRegion(texture, 240, 177, 106, 145));

            storeTextures.get(StoreType.JojaMart).put(season, new TextureRegion(texture, 0, 800, 320, 187));
            storeTextures.get(StoreType.Blacksmith).put(season, new TextureRegion(texture, 400, 0, 112, 135));
            storeTextures.get(StoreType.FishShop).put(season, new TextureRegion(texture, 256, 0, 144, 175));
            storeTextures.get(StoreType.CarpentersShop).put(season, new TextureRegion(texture, 0, 0, 125, 175));
            storeTextures.get(StoreType.Ranch).put(season, new TextureRegion(texture, 125, 0, 131, 175));
        });
    }

    public TextureRegion getStoreTexture(Season season, StoreType type) {
        return storeTextures.get(type).get(season);
    }


    private void loadGrassTextures() {
        Texture texture = new Texture("sprites/Grass.png");
        int grassHeight = texture.getHeight() / 12;
        for (int row = 0; row < 12; row++) {
            if (row == 7)
                continue;
            for (int column = 0; column < 3; column++) {
                grassTextures.add(new TextureRegion(
                    texture,
                    12 * column,
                    grassHeight * row,
                    12,
                    grassHeight
                ));
            }
        }
    }

    private void loadNormalItemTextures() {
        for (NormalItemType normalItemType : NormalItemType.values()) {
            if (normalItemType.equals(NormalItemType.Well)) {
                continue;
            }
            normalItemTextures.put(normalItemType,normalItemType.getTexturePath());
        }
    }


    private void loadFullyGrownTextures() {
        List<Season> seasons = Arrays.asList(Season.Spring, Season.Summer, Season.Fall, Season.Winter);
        for (TreeType treeType : TreeType.values()) {
            if (treeType.equals(TreeType.MushroomTree))
                continue;
            HashMap<Season, TextureRegion> seasonalTextures = new HashMap<>();
            Texture texture = new Texture(stageTextures.get(treeType).getLast());

            for (int i = 0; i < 4; i++) {
                seasonalTextures.put(seasons.get(i), new TextureRegion(
                    texture,
                    texture.getWidth() / 4 * i,
                    0,
                    texture.getWidth() / 4,
                    texture.getHeight()
                ));
            }
            fullyGrownTextures.put(treeType, seasonalTextures);
        }
    }


    private void loadStageTextures() {
        for (TreeType treeType : TreeType.values()) {
            stageTextures.put(treeType, new ArrayList<>());
            for (String stageTexturePath : treeType.getStageTexturePaths()) {
                stageTextures.get(treeType).add(stageTexturePath);
            }
        }
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

    public TextureRegion getFullyGrownTexture(TreeType treeType, Season season) {
        HashMap<Season, TextureRegion> textures = fullyGrownTextures.get(treeType);
        if (textures == null) {
            List<Season> seasons = Arrays.asList(Season.Spring, Season.Summer, Season.Fall, Season.Winter);
            if (treeType.equals(TreeType.MushroomTree)) {
                String path = TreeType.MushroomTree.getStageTexturePaths()[TreeType.MushroomTree.getStageTexturePaths().length - 1];
                TextureRegion textureRegion = new TextureRegion(new Texture(path));
                HashMap<Season, TextureRegion> seasonalTextures = new HashMap<>();
                for (Season s : seasons) {
                    seasonalTextures.put(s, textureRegion);
                }
                fullyGrownTextures.put(treeType, seasonalTextures);
            }
            else {
                stageTextures.put(treeType, new ArrayList<>());
                for (String stageTexturePath : treeType.getStageTexturePaths()) {
                    stageTextures.get(treeType).add(stageTexturePath);
                }

                HashMap<Season, TextureRegion> seasonalTextures = new HashMap<>();
                Texture texture = new Texture(stageTextures.get(treeType).getLast());

                for (int i = 0; i < 4; i++) {
                    seasonalTextures.put(seasons.get(i), new TextureRegion(
                        texture,
                        texture.getWidth() / 4 * i,
                        0,
                        texture.getWidth() / 4,
                        texture.getHeight()
                    ));
                }
                fullyGrownTextures.put(treeType, seasonalTextures);
            }
        }
        return fullyGrownTextures.get(treeType).get(season);
    }
}
