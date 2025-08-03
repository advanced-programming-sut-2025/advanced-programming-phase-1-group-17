package io.github.StardewValley.shared.models.plant;

import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;
import java.util.HashMap;

public class CropAssetManager {
    private static CropAssetManager cropAssetManager = null;

    private final HashMap<CropType, ArrayList<String>> stageTextures = new HashMap<>();
    private final HashMap<CropType, String> inventoryTextures = new HashMap<>();
    private final HashMap<SeedType, String> seedTextures = new HashMap<>();
    private final HashMap<CropType, String> giantTextures = new HashMap<>();

    private final HashMap<FertilizerType, String> fertilizerTextures = new HashMap<>() {{
        put(FertilizerType.SpeedGro, "Fertilizer/Speed-Gro.png");
        put(FertilizerType.BasicRetainingSoil,"Fertilizer/Basic_Retaining_Soil.png");
        put(FertilizerType.DeluxeRetainingSoil, "Fertilizer/Deluxe_Retaining_Soil.png");
        put(FertilizerType.QualityRetainingSoil, "Fertilizer/Quality_Retaining_Soil.png");
    }};

    private CropAssetManager() {
        loadStageTextures();
        loadInventoryTextures();
        loadSeedTextures();
        loadGiantTextures();
    }

    private void loadGiantTextures() {
        giantTextures.put(CropType.Cauliflower,("Crops/Giant_Cauliflower.png"));
        giantTextures.put(CropType.Melon, ("Crops/Giant_Melon.png"));
        giantTextures.put(CropType.Powdermelon, ("Crops/Giant_Powdermelon.png"));
        giantTextures.put(CropType.Pumpkin, ("Crops/Giant_Pumpkin.png"));
    }

    private void loadSeedTextures() {
        for (SeedType type : SeedType.values()) {
            seedTextures.put(type, (type.getTexturePath()));
        }
    }

    private void loadStageTextures() {
        for (CropType type : CropType.values()) {
            stageTextures.put(type, new ArrayList<>());
            for (String path : type.getStageTexturePaths()) {
                if (path != null && !path.isEmpty()) {
                    stageTextures.get(type).add(path);
                }
            }
        }
    }

    private void loadInventoryTextures() {
        for (CropType type : CropType.values()) {
            String inventoryPath = type.getInventoryTexturePath();
            if (inventoryPath != null && !inventoryPath.isEmpty()) {
                inventoryTextures.put(type, inventoryPath);
            }
        }
    }

    public static CropAssetManager getCropAssetManager() {
        if (cropAssetManager == null)
            cropAssetManager = new CropAssetManager();
        return cropAssetManager;
    }

    public String  getStageTexture(int currentStageIndex, CropType type) {
        return stageTextures.get(type).get(currentStageIndex);
    }

    public String getInventoryTexture(CropType cropType) {
        return inventoryTextures.get(cropType);
    }

    public String getSeedTexture(SeedType seedType) {
        return seedTextures.get(seedType);
    }

    public String getFertilizerTexture(FertilizerType fertilizerType) {
        return fertilizerTextures.get(fertilizerType);
    }

    public String getGiantTexture(CropType type) {
        return giantTextures.get(type);
    }
}
