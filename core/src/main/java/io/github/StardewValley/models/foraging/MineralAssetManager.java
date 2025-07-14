package io.github.StardewValley.models.foraging;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public class MineralAssetManager {
    private static MineralAssetManager mineralAssetManager = null;

    private final HashMap<MineralType, Texture> mineralTextures = new HashMap<>();

    private MineralAssetManager() {
        for (MineralType type : MineralType.values()) {
            mineralTextures.put(type, new Texture(type.getTexturePath()));
        }
    }

    public static MineralAssetManager getMineralAssetManager() {
        if (mineralAssetManager == null)
            mineralAssetManager = new MineralAssetManager();
        return mineralAssetManager;
    }

    public Texture getTexture(MineralType mineralType) {
        return mineralTextures.get(mineralType);
    }
}
