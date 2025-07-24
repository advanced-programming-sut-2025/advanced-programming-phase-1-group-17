package io.github.StardewValley.shared.models.plant;

import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;
import java.util.HashMap;

public class CropAssetManager {
    private static CropAssetManager cropAssetManager = null;

    private final HashMap<CropType, ArrayList<Texture>> stageTextures = new HashMap<>();
    private final HashMap<CropType, Texture> inventoryTextures = new HashMap<>();
    private final HashMap<SeedType, Texture> seedTextures = new HashMap<>();

    private final HashMap<FertilizerType, Texture> fertilizerTextures = new HashMap<>() {{
        put(FertilizerType.SpeedGro, new Texture("Fertilizer/Speed-Gro.png"));
        put(FertilizerType.BasicRetainingSoil, new Texture("Fertilizer/Basic_Retaining_Soil.png"));
        put(FertilizerType.DeluxeRetainingSoil, new Texture("Fertilizer/Deluxe_Retaining_Soil.png"));
        put(FertilizerType.QualityRetainingSoil, new Texture("Fertilizer/Quality_Retaining_Soil.png"));
    }};

    private CropAssetManager() {
        loadStageTextures();
        loadInventoryTextures();
        loadSeedTextures();
    }

    private void loadSeedTextures() {
        for (SeedType type : SeedType.values()) {
            seedTextures.put(type, new Texture(type.getTexturePath()));
        }
    }

    private void loadStageTextures() {
        for (CropType type : CropType.values()) {
            stageTextures.put(type, new ArrayList<>());
            for (String path : type.getStageTexturePaths()) {
                if (path != null && !path.isEmpty()) {
                    stageTextures.get(type).add(new Texture(path));
                }
            }
        }
    }

    private void loadInventoryTextures() {
        for (CropType type : CropType.values()) {
            String inventoryPath = type.getInventoryTexturePath();
            if (inventoryPath != null && !inventoryPath.isEmpty()) {
                inventoryTextures.put(type, new Texture(inventoryPath));
            }
        }
    }

    public static CropAssetManager getCropAssetManager() {
        if (cropAssetManager == null)
            cropAssetManager = new CropAssetManager();
        return cropAssetManager;
    }

    public Texture getStageTexture(int currentStageIndex, CropType type) {
        return stageTextures.get(type).get(currentStageIndex);
    }

    public Texture getInventoryTexture(CropType cropType) {
        return inventoryTextures.get(cropType);
    }

    public Texture getSeedTexture(SeedType seedType) {
        return seedTextures.get(seedType);
    }

    public Texture getFertilizerTexture(FertilizerType fertilizerType) {
        return fertilizerTextures.get(fertilizerType);
    }
}
