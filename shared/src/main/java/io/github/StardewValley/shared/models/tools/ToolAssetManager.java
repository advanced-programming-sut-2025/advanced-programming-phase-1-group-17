package io.github.StardewValley.shared.models.tools;

import java.util.HashMap;

public class ToolAssetManager {
    private static ToolAssetManager toolAssetManager = null;
    private final HashMap<ToolType, HashMap<ToolMaterial, String>> toolTexturePaths = new HashMap<>();
    private final HashMap<FishingPoleType, String> fishingPoleTexturePaths = new HashMap<>();

    private ToolAssetManager() {
        for (ToolType type : ToolType.values()) {
            if (type.equals(ToolType.FishingPole))
                continue;
            toolTexturePaths.put(type, new HashMap<>());
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
            toolTexturePaths.get(ToolType.Hoe).put(material, "Hoe/%sHoe.png".formatted(materialName));
        }
    }

    private void loadWateringCanTextures() {
        for (ToolMaterial material : ToolMaterial.values()) {
            String materialName = (material.equals(ToolMaterial.Basic)) ? "" : "%s_".formatted(material.name());
            toolTexturePaths.get(ToolType.WateringCan).put(material, "Watering_Can/%sWatering_Can.png".formatted(materialName));
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
    }

    private void loadShearTextures() {
        toolTexturePaths.get(ToolType.Shear).put(null, "Tools/Shears.png");
    }

    private void loadTrashCanTextures() {
        for (ToolMaterial material : ToolMaterial.values()) {
            String materialName = (material.equals(ToolMaterial.Basic)) ? "_Steel" : "_%s".formatted(material.name());
            toolTexturePaths.get(ToolType.TrashCan).put(material, "Tools/Trash_Can%s.png".formatted(materialName));
        }
    }

    private void loadScytheTextures() {
        toolTexturePaths.get(ToolType.Scythe).put(null, "Tools/Scythe.png");
    }

    private void loadAxeTextures() {
        for (ToolMaterial material : ToolMaterial.values()) {
            String materialName = (material.equals(ToolMaterial.Basic)) ? "" : "%s_".formatted(material.name());
            toolTexturePaths.get(ToolType.Axe).put(material, "Tools/Axe/%sAxe.png".formatted(materialName));
        }
    }

    private void loadPickaxeTextures() {
        for (ToolMaterial material : ToolMaterial.values()) {
            String materialName = (material.equals(ToolMaterial.Basic)) ? "" : "%s_".formatted(material.name());
            toolTexturePaths.get(ToolType.Pickaxe).put(material, "Tools/Pickaxe/%sPickaxe.png".formatted(materialName));
        }
    }

    public static ToolAssetManager getToolAssetManager() {
        if (toolAssetManager == null)
            toolAssetManager = new ToolAssetManager();
        return toolAssetManager;
    }

    public String getToolTexturePath(ToolType toolType, ToolMaterial toolMaterial, FishingPoleType fishingPoleType) {
        if (toolType.equals(ToolType.FishingPole)) {
            System.out.println(fishingPoleType);
            return fishingPoleTexturePaths.get(fishingPoleType);
        }
        System.out.println(toolMaterial);
        return toolTexturePaths.get(toolType).get(toolMaterial);
    }
}
