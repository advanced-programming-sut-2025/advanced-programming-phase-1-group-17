package org.example.models.tools;

import org.example.models.App;
import org.example.models.enums.ToolMaterial;
import org.example.models.enums.ToolName;

public class Tool {
    private ToolName name;
    private ToolMaterial material;
    private int level=0;
    public Tool(ToolName name, ToolMaterial material) {
        this.name = name;
        this.material = material;
    }

    public ToolName getName() {
        return name;
    }

    public void setName(ToolName name) {
        this.name = name;
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

    public void toolUse( Tool tool) {
        switch(tool.getName()) {
            case Pichaxe:
        }
    }
    public static Tool findToolByName(String toolName) {
        for(Tool tool : App.getCurrentPlayer().getBackPack().getTools().keySet()){
            if(tool.getName().name().equals(toolName)){
                return tool;
            }
        }
        return null;
    }
}
