package models.tools;

import models.enums.ToolMaterial;
import models.enums.ToolName;

public class Tool {
    private ToolName name;
    private ToolMaterial material;

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
}
