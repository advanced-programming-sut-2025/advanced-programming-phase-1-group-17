package org.example.models.tools;

import org.example.models.App;
import org.example.models.BackPackable;
import org.example.models.enums.ToolMaterial;
import org.example.models.enums.ToolType;

public class Tool implements BackPackable {
    private ToolType type;
    private ToolMaterial material;
    private int level = 0;
    private double price;


    public Tool(ToolType type, ToolMaterial material) {
        this.type = type;
        this.material = material;
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
            case Pichaxe:
        }
    }

    public static Tool findToolByName(String toolName) {
        for (BackPackable backPackItem : App.getCurrentPlayer().getBackPack().getBackPackItems().keySet()) {
            if (backPackItem instanceof Tool tool) {
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
}
