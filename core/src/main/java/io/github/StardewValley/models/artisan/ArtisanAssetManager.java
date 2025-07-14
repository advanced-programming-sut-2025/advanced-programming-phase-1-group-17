package io.github.StardewValley.models.artisan;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public class ArtisanAssetManager {
    private static ArtisanAssetManager artisanAssetManager = null;
    private final HashMap<ArtisanProductType, Texture> artisanProductTypeTextureHashMap = new HashMap<>();

    private ArtisanAssetManager() {
        for (ArtisanProductType type : ArtisanProductType.values()) {
            artisanProductTypeTextureHashMap.put(type, new Texture("Artisan_good/" + type.name() + ".png"));
        }
    }

    public static ArtisanAssetManager getArtisanAssetManager() {
        if (artisanAssetManager == null)
            artisanAssetManager = new ArtisanAssetManager();
        return artisanAssetManager;
    }

     public Texture getTexture(ArtisanProductType type) {
        return artisanProductTypeTextureHashMap.get(type);
     }
}
