package io.github.StardewValley.models.tools;

import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.models.App;
import io.github.StardewValley.models.Player;

import java.util.HashMap;

public class ToolAssetManager {
    private static ToolAssetManager toolAssetManager = null;
    private final HashMap<ToolType, HashMap<ToolMaterial, Texture>> toolTextures = new HashMap<>();
    private final HashMap<FishingPoleType, Texture> fishingPoleTextures = new HashMap<>();

    private ToolAssetManager() {
        for (ToolType type : ToolType.values()) {
            if (type.equals(ToolType.FishingPole))
                continue;
            toolTextures.put(type, new HashMap<>());
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
            toolTextures.get(ToolType.Hoe).put(material, new Texture("Hoe/%sHoe.png".formatted(materialName)));
        }
    }

    private void loadWateringCanTextures() {
        for (ToolMaterial material : ToolMaterial.values()) {
            String materialName = (material.equals(ToolMaterial.Basic)) ? "" : "%s_".formatted(material.name());
            toolTextures.get(ToolType.WateringCan).put(material, new Texture("Watering_Can/%sWatering_Can.png".formatted(materialName)));
        }
    }

    private void loadFishingPoleTextures() {
        fishingPoleTextures.put(FishingPoleType.IridiumFishingPole, new Texture("Fishing_Pole/Iridium_Rod.png"));
        fishingPoleTextures.put(FishingPoleType.BambooFishingPole, new Texture("Fishing_Pole/Bamboo_Pole.png"));
        fishingPoleTextures.put(FishingPoleType.TrainingFishingPole, new Texture("Fishing_Pole/Training_Rod.png"));
        fishingPoleTextures.put(FishingPoleType.FiberglassFishingPole, new Texture("Fishing_Pole/Fiberglass_Rod.png"));;
    }

    private void loadMilkPailTextures() {
        toolTextures.get(ToolType.MilkPail).put(null, new Texture("Tools/Milk_Pail.png"));
    }

    private void loadShearTextures() {
        toolTextures.get(ToolType.Shear).put(null, new Texture("Tools/Shears.png"));
    }

    private void loadTrashCanTextures() {
        for (ToolMaterial material : ToolMaterial.values()) {
            String materialName = (material.equals(ToolMaterial.Basic)) ? "_Steel" : "_%s".formatted(material.name());
            toolTextures.get(ToolType.Axe).put(material, new Texture("Tools/Trash_Can%s.png".formatted(materialName)));
        }
    }

    private void loadScytheTextures() {
         toolTextures.get(ToolType.Scythe).put(null, new Texture("Tools/Scythe.png"));
    }

    private void loadAxeTextures() {
        for (ToolMaterial material : ToolMaterial.values()) {
            String materialName = (material.equals(ToolMaterial.Basic)) ? "" : "%s_".formatted(material.name());
            toolTextures.get(ToolType.Axe).put(material, new Texture("Tools/Axe/%sAxe.png".formatted(materialName)));
        }
    }

    private void loadPickaxeTextures() {
        for (ToolMaterial material : ToolMaterial.values()) {
            String materialName = (material.equals(ToolMaterial.Basic)) ? "" : "%s_".formatted(material.name());
            toolTextures.get(ToolType.Pickaxe).put(material, new Texture("Tools/Pickaxe/%sPickaxe.png".formatted(materialName)));
        }
    }

    public static ToolAssetManager getToolAssetManager() {
        if (toolAssetManager == null)
            toolAssetManager = new ToolAssetManager();
        return toolAssetManager;
    }

    public Texture getToolTexture(ToolType toolType) {
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        if (toolType.equals(ToolType.FishingPole)) {
            FishingPoleType material = ((Tool)player.getBackPack().getBackPackItems().get(toolType).getFirst()).getFishingPoleMaterial();
            return fishingPoleTextures.get(material);
        }
        ToolMaterial material = ((Tool)player.getBackPack().getBackPackItems().get(toolType).getFirst()).getMaterial();
        return toolTextures.get(toolType).get(material);
    }
}
