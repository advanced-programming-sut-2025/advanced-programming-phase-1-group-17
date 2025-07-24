package io.github.StardewValley.shared.models.plant;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.HashMap;

public class TreeAssetManager {
    private static TreeAssetManager treeAssetManager = null;

    private final HashMap<FruitType, Texture> fruitTypeTextureHashMap = new HashMap<>();
    private final HashMap<TreeType, ArrayList<Texture>> stageTextures = new HashMap<>();
    private final HashMap<TreeType, Texture> hasFruitTexture = new HashMap<>();

    private TreeAssetManager() {
        loadFruitTexture();
        loadStageTextures();
        loadHasFruitTextures();
    }

    private void loadHasFruitTextures() {
        for (TreeType treeType : TreeType.values()) {
            hasFruitTexture.put(treeType, new Texture(treeType.getHasFruitTexturePath()));
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

    public Texture getHasFruitTexture(TreeType treeType) {
        return hasFruitTexture.get(treeType);
    }

    public Texture getInventoryTexture(SaplingType saplingType) {
        //TODO
        return null;
    }
}
