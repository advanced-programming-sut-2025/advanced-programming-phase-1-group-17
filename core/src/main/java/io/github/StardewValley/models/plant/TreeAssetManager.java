package io.github.StardewValley.models.plant;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.StardewValley.models.enums.Season;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TreeAssetManager {
    private static TreeAssetManager treeAssetManager = null;

    private final HashMap<FruitType, Texture> fruitTypeTextureHashMap = new HashMap<>();
    private final HashMap<TreeType, ArrayList<Texture>> stageTextures = new HashMap<>();
    private final HashMap<TreeType, HashMap<Season, TextureRegion>> fullyGrownTextures = new HashMap<>();
    private final HashMap<TreeType, Texture> hasFruitTexture = new HashMap<>();
    private final HashMap<SaplingType, Texture> saplingTextures = new HashMap<>();

    private TreeAssetManager() {
        loadFruitTexture();
        loadStageTextures();
        loadFullyGrownTextures();
        loadHasFruitTextures();
        loadSaplingTextures();
    }

    private void loadSaplingTextures() {
        for (SaplingType saplingType : SaplingType.values()) {
            saplingTextures.put(saplingType, new Texture(saplingType.getTexturePath()));
        }
    }

    private void loadHasFruitTextures() {
        for (TreeType treeType : TreeType.values()) {
            hasFruitTexture.put(treeType, new Texture(treeType.getHasFruitTexturePath()));
        }
    }


    private void loadFullyGrownTextures() {
        List<Season> seasons = Arrays.asList(Season.Spring, Season.Summer, Season.Fall, Season.Winter);
        for (TreeType treeType : TreeType.values()) {
            if (treeType.equals(TreeType.MushroomTree))
                continue;
            HashMap<Season, TextureRegion> seasonalTextures = new HashMap<>();
            Texture texture = stageTextures.get(treeType).getLast();

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
                stageTextures.get(treeType).add(new Texture(stageTexturePath));
            }
        }
    }

    private void loadFruitTexture() {
        for (FruitType fruitType : FruitType.values()) {
            fruitTypeTextureHashMap.put(fruitType, new Texture("Trees/%s.png".formatted(fruitType)));
        }
    }

    public static TreeAssetManager getTreeAssetManager() {
        if (treeAssetManager == null)
            treeAssetManager = new TreeAssetManager();
        return treeAssetManager;
    }

    public Texture getFruitTexture(FruitType fruitType) {
        return fruitTypeTextureHashMap.get(fruitType);
    }

    public Texture getStageTexture(TreeType treeType, int stageIndex) {
        return stageTextures.get(treeType).get(stageIndex);
    }

    public TextureRegion getFullyGrownTexture(TreeType treeType, Season season) {
        return fullyGrownTextures.get(treeType).get(season);
    }

    public Texture getHasFruitTexture(TreeType treeType) {
        return hasFruitTexture.get(treeType);
    }

    public Texture getSaplingTexture(SaplingType saplingType) {
        return saplingTextures.get(saplingType);
    }
}
