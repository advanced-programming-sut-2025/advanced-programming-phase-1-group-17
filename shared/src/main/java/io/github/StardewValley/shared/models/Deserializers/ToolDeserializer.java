package io.github.StardewValley.shared.models.Deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.JsonNode;
import io.github.StardewValley.shared.models.tools.FishingPoleType;
import io.github.StardewValley.shared.models.tools.Tool;
import io.github.StardewValley.shared.models.tools.ToolMaterial;
import io.github.StardewValley.shared.models.tools.ToolType;

import java.io.IOException;

public class ToolDeserializer extends StdDeserializer<Tool> {

    public ToolDeserializer() {
        super(Tool.class);
    }

    @Override
    public Tool deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectNode node = p.getCodec().readTree(p);

        // Extract type string and parse ToolType
        String typeStr = node.get("type").asText();
        ToolType toolType = ToolType.valueOf(typeStr);

        ToolMaterial material = null;
        FishingPoleType fishingPoleType = null;

        if (toolType == ToolType.FishingPole) {
            // For FishingPole, get fishingPoleType string and parse enum
            JsonNode fishingPoleNode = node.get("fishingPoleType");
            if (fishingPoleNode != null && !fishingPoleNode.isNull()) {
                String fishingPoleStr = fishingPoleNode.asText();
                fishingPoleType = FishingPoleType.valueOf(fishingPoleStr);
            }
            // material must be null for FishingPole
        } else {
            // For other tool types, deserialize material
            JsonNode materialNode = node.get("material");
            if (materialNode != null && !materialNode.isNull()) {
                material = ToolMaterial.valueOf(materialNode.asText());
            }
            // fishingPoleType must be null for non-FishingPole
        }

        // Deserialize other fields manually (level, price, wateringCanStorage, isWateringCanFull)
        int level = node.has("level") ? node.get("level").asInt() : 0;
        double price = node.has("price") ? node.get("price").asDouble() : 0.0;
        int wateringCanStorage = node.has("wateringCanStorage") ? node.get("wateringCanStorage").asInt() : 0;
        boolean isWateringCanFull = node.has("isWateringCanFull") ? node.get("isWateringCanFull").asBoolean() : true;

        // Create Tool instance with gathered data
        Tool tool = new Tool();
        tool.setType(toolType);
        tool.setMaterial(material);
        tool.setFishingPoleType(fishingPoleType);
        tool.setLevel(level);
        tool.setPrice(price);
        tool.setWateringCanStorage(wateringCanStorage);
        tool.setWateringCanFull(isWateringCanFull);

        return tool;
    }
}
