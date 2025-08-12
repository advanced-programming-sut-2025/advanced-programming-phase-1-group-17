package io.github.StardewValley.shared.models.artisan;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public class ArtisanAssetManager {
    private static ArtisanAssetManager artisanAssetManager = null;
    private final HashMap<ArtisanProductType, String> artisanProductTypeTextureHashMap = new HashMap<>();

    private ArtisanAssetManager() {
        for (ArtisanProductType type : ArtisanProductType.values()) {
//            if (type.equals(ArtisanProductType.Coal))
//                artisanProductTypeTextureHashMap.put(type, "Resource/Coal.png");
            if (type.equals(ArtisanProductType.Oil))
                artisanProductTypeTextureHashMap.put(type, "Ingredient/Oil.png");
            else if (type.equals(ArtisanProductType.SmokedFish))
                artisanProductTypeTextureHashMap.put(type, "Fish/Smoked_Fish.png");
            else if (type.equals(ArtisanProductType.IridiumBar))
                artisanProductTypeTextureHashMap.put(type, "Crafting/Iridium_Bar.png");
            else if (type.equals(ArtisanProductType.GoldBar))
                artisanProductTypeTextureHashMap.put(type, "Crafting/Gold_Bar.png");
            else if (type.equals(ArtisanProductType.IronBar))
                artisanProductTypeTextureHashMap.put(type, "Crafting/Iron_Bar.png");
            else if (type.equals(ArtisanProductType.CopperBar))
                artisanProductTypeTextureHashMap.put(type, "Crafting/Copper_Bar.png");
            else if (type.equals(ArtisanProductType.Vinegar))
                artisanProductTypeTextureHashMap.put(type, "Ingredient/Vinegar.png");
            else
                artisanProductTypeTextureHashMap.put(type, "Artisan_good/" + type.name() + ".png");
        }
    }

    public static ArtisanAssetManager getArtisanAssetManager() {
        if (artisanAssetManager == null)
            artisanAssetManager = new ArtisanAssetManager();
        return artisanAssetManager;
    }

    public String getTexture(ArtisanProductType type) {
        return artisanProductTypeTextureHashMap.get(type);
    }
}
