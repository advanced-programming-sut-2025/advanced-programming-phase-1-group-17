package io.github.StardewValley.models.plant;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public class TreeAssetManager {
    private static TreeAssetManager treeAssetManager = null;

    private final HashMap<FruitType, Texture> fruitTypeTextureHashMap = new HashMap<>();

    private TreeAssetManager() {
        loadFruitTexture();
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

    public Texture getInventoryTexture(SaplingType saplingType) {
        return null;
    }
}
