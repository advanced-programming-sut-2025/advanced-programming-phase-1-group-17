package io.github.StardewValley.shared.models.foraging;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public class MineralAssetManager {
    private static MineralAssetManager mineralAssetManager = null;

    private final HashMap<MineralType, String> mineralTextures = new HashMap<>();

    private MineralAssetManager() {
        for (MineralType type : MineralType.values()) {
            mineralTextures.put(type, type.getTexturePath());
        }
    }

    public static MineralAssetManager getMineralAssetManager() {
        if (mineralAssetManager == null)
            mineralAssetManager = new MineralAssetManager();
        return mineralAssetManager;
    }

    public String getTexture(MineralType mineralType) {
        return mineralTextures.get(mineralType);
    }
}
