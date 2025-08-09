package io.github.StardewValley.shared.models.plant;

import java.util.ArrayList;
import java.util.HashMap;

public class TreeAssetManager {
    private static TreeAssetManager treeAssetManager = null;

    private final HashMap<FruitType, String> fruitTypeTextureHashMap = new HashMap<>();
    private final HashMap<TreeType, ArrayList<String>> stageTextures = new HashMap<>();
    private final HashMap<TreeType, String > hasFruitTexture = new HashMap<>();

    private final HashMap<SaplingType, String > saplingTextures = new HashMap<>();

    private TreeAssetManager() {
        loadFruitTexture();
        loadStageTextures();
        loadHasFruitTextures();
        loadSaplingTextures();
    }

    private void loadSaplingTextures() {
        for (SaplingType saplingType : SaplingType.values()) {
            saplingTextures.put(saplingType, (saplingType.getTexturePath()));
        }
    }

    private void loadHasFruitTextures() {
        for (TreeType treeType : TreeType.values()) {
            hasFruitTexture.put(treeType, treeType.getHasFruitTexturePath());
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

    private void loadFruitTexture() {
        for (FruitType fruitType : FruitType.values()) {
            fruitTypeTextureHashMap.put(fruitType, "Trees/%s.png".formatted(fruitType));
        }
    }

    public static TreeAssetManager getTreeAssetManager() {
        if (treeAssetManager == null)
            treeAssetManager = new TreeAssetManager();
        return treeAssetManager;
    }

    public String getFruitTexture(FruitType fruitType) {
        return fruitTypeTextureHashMap.get(fruitType);
    }

    public String getStageTexture(TreeType treeType, int stageIndex) {
        return stageTextures.get(treeType).get(stageIndex);
    }

    public String  getHasFruitTexture(TreeType treeType) {
        return hasFruitTexture.get(treeType);
    }

    public String  getSaplingTexture(SaplingType saplingType) {
        return saplingTextures.get(saplingType);
    }
}
