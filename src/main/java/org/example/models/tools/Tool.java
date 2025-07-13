package org.example.models.tools;

import org.example.models.App;
import org.example.models.BackPackable;
import org.example.models.BackPackableType;

public class Tool implements BackPackable {
    private ToolType type;
    private ToolMaterial material;
    private int level = 0;
    private double price;
    private int wateringCanStorage = 0;
    private boolean isWateringCanFull = true;
    private FishingPoleType fishingPoleMaterial;


    public Tool(ToolType type, ToolMaterial material,FishingPoleType fishingPoleMaterial) {
        this.type = type;
        this.material = material;
        handleWateringCanStorage();
        this.fishingPoleMaterial = fishingPoleMaterial;
    }

    public void handleWateringCanStorage() {
        if (type.equals(ToolType.WateringCan)) {
            if (material.equals(ToolMaterial.Basic))
                wateringCanStorage = 40;
            else if (material.equals(ToolMaterial.Copper))
                wateringCanStorage = 55;
            else if (material.equals(ToolMaterial.Iron))
                wateringCanStorage = 70;
            else if (material.equals(ToolMaterial.Gold))
                wateringCanStorage = 85;
            else
                wateringCanStorage = 100; //Iridium
        }
        isWateringCanFull = true;
    }

    public ToolType getToolType() {
        return type;
    }

    public String getName() {
        return type.name();
    }

    public void setType(ToolType type) {
        this.type = type;
    }

    public ToolMaterial getMaterial() {
        return material;
    }

    public int getLevel() {
        return level;
    }
    public ToolMaterial getLevelMaterial(){
        return ToolMaterial.values()[this.level];
    }

    public void setLevel(int level) {
        this.level = level;
    }


    public static Tool findToolByName(String toolName) {
        BackPack backPack = App.getCurrentGame().getCurrentPlayingPlayer().getBackPack();

        for (BackPackableType backPackableType : backPack.getBackPackItems().keySet()) {
            if (backPackableType instanceof ToolType toolType) {
                Tool tool = (Tool) backPack.getBackPackItems().get(toolType).get(0);
                if (tool.getToolType().name().equals(toolName)) {
                    return tool;
                }
            }
        }
        return null;
    }

    public int getTrashCanRefundPercentage() {
        if (type.equals(ToolType.TrashCan)) {
            if (material.equals(ToolMaterial.Basic))
                return 0;
            else if (material.equals(ToolMaterial.Copper))
                return 15;
            else if (material.equals(ToolMaterial.Iron))
                return 30;
            else if (material.equals(ToolMaterial.Gold))
                return 45;
            return 60; //Iridium
        }
        return 0;
    }


    @Override
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    @Override
    public BackPackableType getType() {
        if (type == ToolType.FishingPole && fishingPoleMaterial != null) {
            return fishingPoleMaterial;
        }
        return type;
    }

    public int getWateringCanStorage() {
        return wateringCanStorage;
    }

    public void setWateringCanStorage(int wateringCanStorage) {
        this.wateringCanStorage = wateringCanStorage;
        this.isWateringCanFull=false;
    }

    public boolean isWateringCanFull() {
        return isWateringCanFull;
    }

    public void setWateringCanFull(boolean wateringCanFull) {
        isWateringCanFull = wateringCanFull;
    }

    public FishingPoleType getFishingPoleMaterial() {
        return fishingPoleMaterial;
    }

    public void setFishingPoleMaterial(FishingPoleType fishingPoleMaterial) {
        this.fishingPoleMaterial = fishingPoleMaterial;
    }
}
