package org.example.models.tools;

import org.example.models.App;
import org.example.models.BackPackable;
import org.example.models.BackPackableType;
import org.example.models.enums.ToolMaterial;
import org.example.models.enums.ToolType;

public class Tool implements BackPackable {
    private ToolType type;
    private ToolMaterial material;
    private int level = 0;
    private double price;
    private int wateringCanStorage = 0;


    public Tool(ToolType type, ToolMaterial material) {
        this.type = type;
        this.material = material;
        handleWateringCanStorage();
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

    public void setMaterial(ToolMaterial material) {
        this.material = material;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void toolUse(Tool tool) {
        switch (tool.getToolType()) {
            case Pickaxe:
        }
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
        if (type.equals(ToolType.TrashCan))
            return 0;
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


    @Override
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    @Override
    public ToolType getType() {
        return type;
    }

    public int getWateringCanStorage() {
        return wateringCanStorage;
    }

    public void setWateringCanStorage(int wateringCanStorage) {
        this.wateringCanStorage = wateringCanStorage;
    }
}
