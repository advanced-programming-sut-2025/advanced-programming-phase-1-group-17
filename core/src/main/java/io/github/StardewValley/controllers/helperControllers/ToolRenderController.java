package io.github.StardewValley.controllers.helperControllers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.models.*;
import io.github.StardewValley.shared.models.tools.*;

import java.util.HashMap;

public class ToolRenderController {
    private boolean isToolAnimating = false;
    private float toolRotation = 0;
    private float toolAnimationTimer = 0;
    private final float TOOL_ANIMATION_DURATION = 0.4f;

    private PlayerDto player;

    private Sprite toolSprite;
    private final HashMap<ToolType, HashMap<ToolMaterial, Sprite>> toolSprites = new HashMap<>();
    private final HashMap<FishingPoleType, Sprite> fishingPoleSprites = new HashMap<>();

    public ToolRenderController() {
        for (ToolType type : ToolType.values()) {
            if (type.equals(ToolType.FishingPole))
                continue;
            toolSprites.put(type, new HashMap<>());
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


    public void startToolAnimation() {
        isToolAnimating = true;
        toolRotation = 90f;
        toolAnimationTimer = 0;
    }

    public void updateToolAnimation(float delta) {
        if (!isToolAnimating) return;
        toolSprite = getToolSprite(player.getToolType(), player.getToolMaterial(), player.getFishingPoleType());

        toolAnimationTimer += delta;
        float progress = toolAnimationTimer / TOOL_ANIMATION_DURATION;

        toolRotation = progress * -90f; // You can tweak this arc

        if (toolAnimationTimer >= TOOL_ANIMATION_DURATION) {
            isToolAnimating = false;
        }

        if (toolSprite != null) {
            toolSprite.setOrigin(toolSprite.getWidth() / 2f, toolSprite.getHeight() / 2f);
            toolSprite.setRotation(toolRotation);
        }
    }


    public boolean isToolAnimating() {
        return isToolAnimating;
    }

    public float getToolRotation() {
        return toolRotation;
    }

    public void update(float delta, PlayerDto player) {
        if (player.getToolType() == null)
            return;
        this.player = player;
        updateToolAnimation(delta);
        drawTool();
    }

    private void drawTool() {
        if (!isToolAnimating()) {
            toolSprite = getToolSprite(player.getToolType(), player.getToolMaterial(), player.getFishingPoleType());
            Main.getBatch().draw(toolSprite, player.getX(), player.getY());
        } else {
            if (toolSprite == null) return;
            // Position tool sprite relative to player position
            float playerX = player.getX();
            float playerY = player.getY();

            toolSprite.setPosition(playerX, playerY);

            toolSprite.draw(Main.getBatch());
        }
    }

    private void loadHoeTextures() {
        for (ToolMaterial material : ToolMaterial.values()) {
            String materialName = (material.equals(ToolMaterial.Basic)) ? "" : "%s_".formatted(material.name());
            Texture texture = new Texture("Hoe/%sHoe.png".formatted(materialName));
            toolSprites.get(ToolType.Hoe).put(material, new Sprite(texture));
        }
    }

    private void loadWateringCanTextures() {
        for (ToolMaterial material : ToolMaterial.values()) {
            String materialName = (material.equals(ToolMaterial.Basic)) ? "" : "%s_".formatted(material.name());
            Texture texture = new Texture("Watering_Can/%sWatering_Can.png".formatted(materialName));
            toolSprites.get(ToolType.WateringCan).put(material, new Sprite(texture));
        }
    }

    private void loadFishingPoleTextures() {
        fishingPoleSprites.put(FishingPoleType.IridiumFishingPole, new Sprite(new Texture("Fishing_Pole/Iridium_Rod.png")));
        fishingPoleSprites.put(FishingPoleType.BambooFishingPole,  new Sprite(new Texture( "Fishing_Pole/Bamboo_Pole.png")));
        fishingPoleSprites.put(FishingPoleType.TrainingFishingPole,   new Sprite(new Texture("Fishing_Pole/Training_Rod.png")));
        fishingPoleSprites.put(FishingPoleType.FiberglassFishingPole,   new Sprite(new Texture("Fishing_Pole/Fiberglass_Rod.png")));
    }

    private void loadMilkPailTextures() {
        toolSprites.get(ToolType.MilkPail).put(null, new Sprite(new Texture("Tools/Milk_Pail.png")));
    }

    private void loadShearTextures() {
        Texture texture =   new Texture ("Tools/Shears.png");
        toolSprites.get(ToolType.Shear).put(null, new Sprite(texture));
    }

    private void loadTrashCanTextures() {
        for (ToolMaterial material : ToolMaterial.values()) {
            String materialName = (material.equals(ToolMaterial.Basic)) ? "_Steel" : "_%s".formatted(material.name());
            Texture texture =   new Texture ("Tools/Trash_Can%s.png".formatted(materialName));
            toolSprites.get(ToolType.TrashCan).put(material, new Sprite(texture));
        }
    }

    private void loadScytheTextures() {
        Texture texture =   new Texture ("Tools/Scythe.png");
        toolSprites.get(ToolType.Scythe).put(null, new Sprite(texture));
    }

    private void loadAxeTextures() {
        for (ToolMaterial material : ToolMaterial.values()) {
            String materialName = (material.equals(ToolMaterial.Basic)) ? "" : "%s_".formatted(material.name());
            Texture texture =   new Texture ("Tools/Axe/%sAxe.png".formatted(materialName));
            toolSprites.get(ToolType.Axe).put(material, new Sprite(texture));
        }
    }

    private void loadPickaxeTextures() {
        for (ToolMaterial material : ToolMaterial.values()) {
            String materialName = (material.equals(ToolMaterial.Basic)) ? "" : "%s_".formatted(material.name());
            Texture texture =   new Texture ("Tools/Pickaxe/%sPickaxe.png".formatted(materialName));
            toolSprites.get(ToolType.Pickaxe).put(material, new Sprite(texture));
        }
    }

    public Sprite getToolSprite(ToolType toolType, ToolMaterial toolMaterial, FishingPoleType fishingPoleType) {
        if (toolType.equals(ToolType.FishingPole)) {
            return fishingPoleSprites.get(fishingPoleType);
        }
        return toolSprites.get(toolType).get(toolMaterial);
    }
}
