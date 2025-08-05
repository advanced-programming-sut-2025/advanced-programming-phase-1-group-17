package io.github.StardewValley.shared.models.tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.StardewValley.shared.models.Player;

import java.util.HashMap;

public class ToolAssetManager {
    private static ToolAssetManager toolAssetManager = null;
    private final HashMap<ToolType, HashMap<ToolMaterial, String>> toolTexturePaths = new HashMap<>();
    private final HashMap<FishingPoleType, String> fishingPoleTexturePaths = new HashMap<>();

    private final HashMap<ToolType, HashMap<ToolMaterial, Sprite>> toolSprites = new HashMap<>();

    private ToolAssetManager() {
        for (ToolType type : ToolType.values()) {
            if (type.equals(ToolType.FishingPole))
                continue;
            toolTexturePaths.put(type, new HashMap<>());
            toolSprites.put(type, new HashMap<>());
        }
        loadPickaxeTextures();
        loadScytheTextures();
        loadHoeTextures();
        loadAxeTextures();
        loadWateringCanTextures();
        loadFishingPoleTextures();
        loadMilkPailTextures();
        loadShearTextures();
        loadTrashCanTextures();
    }

    private void loadHoeTextures() {
        for (ToolMaterial material : ToolMaterial.values()) {
            String materialName = (material.equals(ToolMaterial.Basic)) ? "" : "%s_".formatted(material.name());
            Texture texture = new Texture("Hoe/%sHoe.png".formatted(materialName));
            toolTexturePaths.get(ToolType.Hoe).put(material, "Hoe/%sHoe.png".formatted(materialName));
            toolSprites.get(ToolType.Hoe).put(material, new Sprite(texture));
        }
    }

    private void loadWateringCanTextures() {
        for (ToolMaterial material : ToolMaterial.values()) {
            String materialName = (material.equals(ToolMaterial.Basic)) ? "" : "%s_".formatted(material.name());
            Texture texture = new Texture("Watering_Can/%sWatering_Can.png".formatted(materialName));
            toolTexturePaths.get(ToolType.WateringCan).put(material, "Watering_Can/%sWatering_Can.png".formatted(materialName));
            toolSprites.get(ToolType.WateringCan).put(material, new Sprite(texture));
        }
    }

    private void loadFishingPoleTextures() {
        fishingPoleTexturePaths.put(FishingPoleType.IridiumFishingPole, "Fishing_Pole/Iridium_Rod.png");
        fishingPoleTexturePaths.put(FishingPoleType.BambooFishingPole,   "Fishing_Pole/Bamboo_Pole.png");
        fishingPoleTexturePaths.put(FishingPoleType.TrainingFishingPole,   "Fishing_Pole/Training_Rod.png");
        fishingPoleTexturePaths.put(FishingPoleType.FiberglassFishingPole,   "Fishing_Pole/Fiberglass_Rod.png");
    }

    private void loadMilkPailTextures() {
        toolTexturePaths.get(ToolType.MilkPail).put(null, "Tools/Milk_Pail.png");
        toolSprites.get(ToolType.MilkPail).put(null, new Sprite(new Texture("Tools/Milk_Pail.png")));
    }

    private void loadShearTextures() {
        Texture texture =   new Texture ("Tools/Shears.png");
        toolTexturePaths.get(ToolType.Shear).put(null, "Tools/Shears.png");
        toolSprites.get(ToolType.Shear).put(null, new Sprite(texture));
    }

    private void loadTrashCanTextures() {
        for (ToolMaterial material : ToolMaterial.values()) {
            String materialName = (material.equals(ToolMaterial.Basic)) ? "_Steel" : "_%s".formatted(material.name());
            Texture texture =   new Texture ("Tools/Trash_Can%s.png".formatted(materialName));
            toolTexturePaths.get(ToolType.Axe).put(material, "Tools/Trash_Can%s.png".formatted(materialName));
            toolSprites.get(ToolType.Axe).put(material, new Sprite(texture));
        }
    }

    private void loadScytheTextures() {
        Texture texture =   new Texture ("Tools/Scythe.png");
        toolTexturePaths.get(ToolType.Scythe).put(null, "Tools/Scythe.png");
        toolSprites.get(ToolType.Scythe).put(null, new Sprite(texture));
    }

    private void loadAxeTextures() {
        for (ToolMaterial material : ToolMaterial.values()) {
            String materialName = (material.equals(ToolMaterial.Basic)) ? "" : "%s_".formatted(material.name());
            Texture texture =   new Texture ("Tools/Axe/%sAxe.png".formatted(materialName));
            toolTexturePaths.get(ToolType.Axe).put(material, "Tools/Axe/%sAxe.png".formatted(materialName));
            toolSprites.get(ToolType.Axe).put(material, new Sprite(texture));
        }
    }

    private void loadPickaxeTextures() {
        for (ToolMaterial material : ToolMaterial.values()) {
            String materialName = (material.equals(ToolMaterial.Basic)) ? "" : "%s_".formatted(material.name());
            Texture texture =   new Texture ("Tools/Pickaxe/%sPickaxe.png".formatted(materialName));
            toolTexturePaths.get(ToolType.Pickaxe).put(material, "Tools/Pickaxe/%sPickaxe.png".formatted(materialName));
            toolSprites.get(ToolType.Pickaxe).put(material, new Sprite(texture));
        }
    }

    public static ToolAssetManager getToolAssetManager() {
        if (toolAssetManager == null)
            toolAssetManager = new ToolAssetManager();
        return toolAssetManager;
    }

    public String getToolTexturePath(ToolType toolType, Player player) {
        if (toolType.equals(ToolType.FishingPole)) {
            FishingPoleType material = ((Tool)player.getBackPack().getBackPackItems().get(toolType).get(0)).getFishingPoleMaterial();
            return fishingPoleTexturePaths.get(material);
        }
        ToolMaterial material = ((Tool)player.getBackPack().getBackPackItems().get(toolType).get(0)).getMaterial();
        return toolTexturePaths.get(toolType).get(material);
    }

    public Sprite getToolSprite(ToolType toolType, Player player) {
//TODO
        //        if (toolType.equals(ToolType.FishingPole)) {
//            FishingPoleType material = ((Tool)player.getBackPack().getBackPackItems().get(toolType).getFirst()).getFishingPoleMaterial();
//            return fishingPoleTextures.get(material);
//        }
        ToolMaterial material = ((Tool)player.getBackPack().getBackPackItems().get(toolType).get(0)).getMaterial();
        return toolSprites.get(toolType).get(material);
    }
}
