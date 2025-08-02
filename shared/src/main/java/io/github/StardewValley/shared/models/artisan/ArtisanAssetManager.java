package io.github.StardewValley.shared.models.artisan;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public class ArtisanAssetManager {
    private static ArtisanAssetManager artisanAssetManager = null;
    private final HashMap<ArtisanProductType, Texture> artisanProductTypeTextureHashMap = new HashMap<>();

    private ArtisanAssetManager() {
        for (ArtisanProductType type : ArtisanProductType.values()) {
            if (type.equals(ArtisanProductType.Coal))
                artisanProductTypeTextureHashMap.put(type, new Texture("Resource/Coal.png"));
            else if (type.equals(ArtisanProductType.Oil))
                artisanProductTypeTextureHashMap.put(type, new Texture("Ingredient/Oil.png"));
            else if (type.equals(ArtisanProductType.SmokedFish))
                artisanProductTypeTextureHashMap.put(type, new Texture("Fish/Smoked_Fish.png"));
            else if (type.equals(ArtisanProductType.IridiumBar))
                artisanProductTypeTextureHashMap.put(type, new Texture("Crafting/Iridium_Bar.png"));
            else if (type.equals(ArtisanProductType.GoldBar))
                artisanProductTypeTextureHashMap.put(type, new Texture("Crafting/Gold_Bar.png"));
            else if (type.equals(ArtisanProductType.IronBar))
                artisanProductTypeTextureHashMap.put(type, new Texture("Crafting/Iron_Bar.png"));
            else if (type.equals(ArtisanProductType.CopperBar))
                artisanProductTypeTextureHashMap.put(type, new Texture("Crafting/Copper_Bar.png"));
            else if (type.equals(ArtisanProductType.Vinegar))
                artisanProductTypeTextureHashMap.put(type, new Texture("Ingredient/Vinegar.png"));
            else
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
