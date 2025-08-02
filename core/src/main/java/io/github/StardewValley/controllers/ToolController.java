package io.github.StardewValley.controllers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.models.App;
import io.github.StardewValley.shared.models.*;
import io.github.StardewValley.shared.models.animal.Animal;
import io.github.StardewValley.shared.models.animal.AnimalProduct;
import io.github.StardewValley.shared.models.animal.AnimalType;
import io.github.StardewValley.shared.models.backpack.BackPackable;
import io.github.StardewValley.shared.models.backpack.NormalItem;
import io.github.StardewValley.shared.models.backpack.NormalItemType;
import io.github.StardewValley.shared.models.cooking.BuffType;
import io.github.StardewValley.shared.models.enums.FishType;
import io.github.StardewValley.shared.models.foraging.ForagingController;
import io.github.StardewValley.shared.models.foraging.Mineral;
import io.github.StardewValley.shared.models.greenhouse.GreenHouse;
import io.github.StardewValley.shared.models.map.Tile;
import io.github.StardewValley.shared.models.market.Fish;
import io.github.StardewValley.shared.models.market.ItemQuality;
import io.github.StardewValley.shared.models.plant.Crop;
import io.github.StardewValley.shared.models.plant.Fruit;
import io.github.StardewValley.shared.models.plant.Plant;
import io.github.StardewValley.shared.models.plant.Tree;
import io.github.StardewValley.shared.models.tools.FishingPoleType;
import io.github.StardewValley.shared.models.tools.Tool;
import io.github.StardewValley.shared.models.tools.ToolAssetManager;
import io.github.StardewValley.shared.models.tools.ToolType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

public class ToolController {
    private boolean isToolAnimating = false;
    private float toolRotation = 0;
    private float toolAnimationTimer = 0;
    private final float TOOL_ANIMATION_DURATION = 0.4f;

    private Player player;
    private Tool tool;
    private double leverage;
    private Tile tile;

    private Sprite toolSprite;

    public ToolController(Player player) {
        this.player = player;
        this.toolSprite = ToolAssetManager.getToolAssetManager().getToolSprite(player.getCurrentTool().getToolType());
        toolSprite.setOriginCenter(); // Rotation around center — adjust if needed
    }


    public void startToolAnimation() {
        isToolAnimating = true;
        toolRotation = 0;
        toolAnimationTimer = 0;
    }

    public void updateToolAnimation(float delta) {
        if (!isToolAnimating) return;
        toolSprite = ToolAssetManager.getToolAssetManager().getToolSprite(player.getCurrentTool().getToolType());

        toolAnimationTimer += delta;
        float progress = toolAnimationTimer / TOOL_ANIMATION_DURATION;

        toolRotation = progress * -90f; // You can tweak this arc

        if (toolAnimationTimer >= TOOL_ANIMATION_DURATION) {
            isToolAnimating = false;
        }

        if (toolSprite != null) {
            toolSprite.setRotation(toolRotation);
        }
    }


    public Result toolUse(int dx, int dy) {
        leverage = App.getCurrentGame().getDate().getTodayWeatherType().getEnergyConsume();
        player = App.getCurrentGame().getCurrentPlayingPlayer();
        int x = player.getTileX() + dx;
        int y = player.getTileY() + dy;

        tool = App.getCurrentGame().getCurrentPlayingPlayer().getCurrentTool();
        tile = Tile.getTile(x, y);

        if (tile == null) {
            return new Result(false, "Invalid Tile");
        }
        Result result = null;

        if (tool.getToolType().equals(ToolType.Hoe)) {
            result = useHoe();
        } else if (tool.getToolType().equals(ToolType.Pickaxe)) {
            result = usePickAxe();
        } else if (tool.getToolType().equals(ToolType.Axe)) {
            result = useAxe();
        } else if (tool.getToolType().equals(ToolType.WateringCan)) {
            result = useWateringCan();
        } else if (tool.getToolType().equals(ToolType.Scythe)) {
            result = useScythe();
        } else if (tool.getToolType().equals(ToolType.MilkPail)) {
            result = useMilkPail();
        } else if (tool.getToolType().equals(ToolType.Shear)) {
            result = useShear();
        } else if (tool.getToolType().equals(ToolType.FishingPole)) {
            result =  useFishingPole();
        }
        startToolAnimation();
        return result;
    }


    private Result useHoe() {
        double energy = ToolType.Hoe.getEnergyCosts()[tool.getLevel()];
        if (player.getAbilities().getFarmingLevel() == 4) {
            energy--;
        }
        if (player.getBuff().getBuffType().equals(BuffType.Farming)) {
            energy--;
        }
        energy = Math.max(energy, 0);
        if (tile.getPlaceable() == null || tile.getPlaceable() instanceof GreenHouse) {
            tile.setPlowed(true);
            player.setEnergy(player.getEnergy() - energy * leverage);
            player.getAbilities().increaseFarmingAbility();
            return new Result(true, "Plowed successfully");
        }
        player.setEnergy(player.getEnergy() - energy * leverage);
        return new Result(false, "Hoe used but incorrectly");
    }


    private Result usePickAxe() {
        double energy = ToolType.Pickaxe.getEnergyCosts()[tool.getLevel()];
        if (player.getAbilities().getMiningLevel() == 4) {
            energy--;
        }
        if (player.getBuff().getBuffType().equals(BuffType.Mining)) {
            energy--;
        }
        if (tile.getPlaceable() instanceof Mineral mineral) {
            if (!ForagingController.canBreakMineral(player.getCurrentTool().getMaterial(),
                mineral.getType())) {
                energy--;
                energy = Math.max(energy, 0);
                player.setEnergy(player.getEnergy() - energy * leverage);
                return new Result(false, "This type of Pickaxe (%s) cannot break this mineral (%s)".formatted(
                    tool.getMaterial(),
                    mineral.getType().name()
                    ));
            }
            player.getAbilities().increaseMiningAbility();

            if (mineral.isForaging())
                player.getAbilities().increaseForagingAbility();

            energy = Math.max(energy, 0);
            player.setEnergy(player.getEnergy() - energy * leverage);
            player.getBackPack().addItemToInventory(mineral);
            tile.setPlaceable(null);

            if (player.getAbilities().getMiningLevel() >= 2) {
                player.getBackPack().addItemToInventory(mineral);
                tile.setWalkAble(true);
                return new Result(false, "stone broke successfully and you also got 1 more because of mining level");
            }

        } else if (tile.isPlowed()) {
            tile.setPlowed(false);
            energy = Math.max(energy, 0);
            player.setEnergy(player.getEnergy() - energy * leverage);
            return new Result(true, "unplowed successfully");
        } else if (tile.getPlaceable() instanceof BackPackable item) {
            tile.setPlaceable(null);
            tile.setWalkAble(true);
            energy = Math.max(energy, 0);
            player.setEnergy(player.getEnergy() - energy * leverage);
            return new Result(true, item.getName() + " destroyed successfully");
        }
        energy = Math.max(energy - 1, 0);
        player.setEnergy(player.getEnergy() - energy * leverage);
        return new Result(true, "you used pickaxe but incorrectly");
    }


    private Result useAxe() {
        double energy = ToolType.Axe.getEnergyCosts()[tool.getLevel()];
        if (player.getAbilities().getForagingLevel() == 4) {
            energy--;
        }
        if (player.getBuff().getBuffType().equals(BuffType.Foraging)) {
            energy--;
        }
        if (tile.getPlaceable() instanceof Tree) {
            player.getAbilities().increaseForagingAbility();
            tile.setPlaceable(new NormalItem(NormalItemType.Wood));
            player.setEnergy(player.getEnergy() - energy * leverage);
            return new Result(true, "you broke tree successfully");
        }
        if (tile.getPlaceable() instanceof NormalItem normalItem) {
            if (normalItem.getType().equals(NormalItemType.Wood)) {
                tile.setPlaceable(null);
                player.getAbilities().increaseForagingAbility();
                player.setEnergy(player.getEnergy() - energy * leverage);
                tile.setWalkAble(true);
                return new Result(false, "You destroyed Wood");
            }
        }
        energy--;
        energy = Math.max(energy, 0);
        player.setEnergy(player.getEnergy() - energy * leverage);
        return new Result(false, "You used Axe but incorrectly");
    }


    private Result useWateringCan() {
        double energy = ToolType.WateringCan.getEnergyCosts()[tool.getLevel()];
        if (player.getAbilities().getForagingLevel() == 4) {
            energy--;
        }
        if (player.getBuff().getBuffType().equals(BuffType.Farming)) {
            energy--;
        }
        if (tile.getPlaceable() instanceof Plant plant) {
            if (tool.getWateringCanStorage() > 0) {
                plant.wateringPlant();
                tool.setWateringCanStorage(tool.getWateringCanStorage() - 1);
                player.getAbilities().increaseForagingAbility();
                return new Result(true, "plant watered sucessfully");
            }
        } else if (tile.isWater()) {
            player.setEnergy(player.getEnergy() - energy * leverage);
            if (tool.isWateringCanFull())
                return new Result(true, "watering can is already full");
            tool.handleWateringCanStorage();
        }
        return new Result(true, "watering can is now full of water");
    }


    private Result useScythe() {
        player.setEnergy(player.getEnergy() - 2 * leverage);
        if (tile.getPlaceable() instanceof NormalItem normalItem) {
            if (normalItem.getType().equals(NormalItemType.Grass)) {
                tile.setPlaceable(null);
                tile.setWalkAble(true);
            }
            else if (normalItem.getType().equals(NormalItemType.Fiber)) {
                tile.setPlaceable(null);
                tile.setWalkAble(true);
                player.getBackPack().addItemToInventory(normalItem);
            }
        } else if (tile.getPlaceable() instanceof Plant plant) {
            player.getAbilities().increaseFarmingAbility();
            if (plant instanceof Tree tree) {
                if (!tree.isFullyGrown()) {
                    return new Result(false, "Tree (%s) is not Fully Grown Yet\n(Days till Fully Growth: %d)"
                        .formatted(tree.getType().name(), tree.getDaysTillFullGrowth()));
                }
                else if (!tree.hasFruit()) {
                    return new Result(false, "Tree (%s) is Fully Grown but doesn't have fruit today\n(Days till next harvest time: %d)"
                        .formatted(tree.getType().name(), tree.getDaysTillNextHarvest()));
                }
                tree.harvest();
                Fruit fruit = new Fruit(tree.getType().getFruitType());
                fruit.setItemQuality();
                player.getBackPack().addItemToInventory(fruit);
                if (tree.isForaging())
                    player.getAbilities().increaseForagingAbility();
            } else if (plant instanceof Crop crop) {
                if (!crop.isFullyGrown()) {
                    return new Result(false, "Crop (%s) is not Fully Grown Yet\n(Days till Fully Growth: %d)"
                        .formatted(crop.getName(), crop.getDaysTillFullGrowth()));
                }
                else if (!crop.hasFruit()) {
                    return new Result(false, "Crop (%s) is Fully Grown but doesn't have fruit today\n(Days till next harvest time: %d)"
                        .formatted(crop.getName(), crop.getDaysTillNextHarvest()));
                }
                crop.harvest();
            }
        }
        return new Result(true, "");
    }


    private Result useMilkPail() {
        player.setEnergy(player.getEnergy() - 4 * leverage);
        if (tile.getPlaceable() instanceof Animal animal) {
            if (animal.getAnimalType().equals(AnimalType.Cow)) {
                ArrayList<AnimalProduct> toRemoved = new ArrayList<>();
                for (AnimalProduct animalProduct : animal.getAnimalProducts()) {
                    player.getBackPack().addItemToInventory(animalProduct);
                    toRemoved.add(animalProduct);
                    if (player.getBackPack().isBackPackFull()) {
                        animal.getAnimalProducts().removeAll(toRemoved);
                        StringBuilder sb = new StringBuilder();
                        for (Map.Entry<AnimalProduct, Integer> entry : Animal.getMapListOfAnimalProducts(toRemoved).entrySet()) {
                            sb.append(entry.getKey().getAnimalProductType().name()).append(" : ")
                                .append(entry.getValue()).append("\n");
                        }
                        return new Result(false, "backpack gets full , you collect these -> \n"
                            + sb.toString());
                    }
                }
                StringBuilder sb = new StringBuilder();
                for (Map.Entry<AnimalProduct, Integer> entry : Animal.getMapListOfAnimalProducts(toRemoved).entrySet()) {
                    sb.append(entry.getKey().getAnimalProductType().name()).append(" : ")
                        .append(entry.getValue()).append("\n");
                }
                animal.getAnimalProducts().removeAll(toRemoved);
                return new Result(true, "you collected all product -> \n " +
                    sb.toString());
            }
        }
        return new Result(true, "");
    }


    private Result useShear() {
        player.setEnergy(player.getEnergy() - 4 * leverage);
        if (tile.getPlaceable() instanceof Animal animal) {
            if (animal.getAnimalType().equals(AnimalType.Sheep)) {
                if (animal.getAnimalProducts().isEmpty()) {
                    return new Result(false, "this sheep has no product");
                }
                ArrayList<AnimalProduct> toRemoved = new ArrayList<>();
                for (AnimalProduct animalProduct : animal.getAnimalProducts()) {
                    player.getBackPack().addItemToInventory(animalProduct);
                    toRemoved.add(animalProduct);
                    if (player.getBackPack().isBackPackFull()) {
                        animal.getAnimalProducts().removeAll(toRemoved);
                        return new Result(false, "back pack gets full , you collected these -> \n" +
                            animalProduct.getAnimalProductType().name() + " -> " + toRemoved.size());
                    }
                }
                animal.getAnimalProducts().removeAll(toRemoved);
                return new Result(true, "you collected all " + toRemoved.size() + " wools of " + animal.getName());
            }
        }
        return new Result(true, "");
    }


    private Result useFishingPole() {
        if (!tile.isWater()) {
            return new Result(false, "you should catch fish near water and lakes , here is not water");
        }
        double energy = 2;
        switch (tool.getFishingPoleMaterial()) {
            case TrainingFishingPole -> energy = 8;
            case BambooFishingPole -> energy = 6;
            case FiberglassFishingPole -> energy = 4;
            case IridiumFishingPole -> energy = 2;
        }
        if (player.getAbilities().getFishingLevel() == 4) {
            energy--;
        }
        if (player.getBuff().getBuffType().equals(BuffType.Fishing)) {
            energy--;
        }
        player.setEnergy(player.getEnergy() - energy * leverage);
        return fishing(tool.getFishingPoleMaterial().name());
    }


    public Result fishing(String fishingPole) {
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();

        if (!Animal.areWeNearWater(player.getTileX(), player.getTileY())) {
            return new Result(false, "first go near water");
        }
        if (player.getBackPack().isBackPackFull()) {
            return new Result(false, "your backpack is full");
        }
        FishingPoleType fishingPoleType;
        try {
            fishingPoleType = FishingPoleType.valueOf(fishingPole);
        } catch (Exception e) {
            return new Result(false, "invalid fishing pole");
        }
        if (!player.getBackPack().getBackPackItems().containsKey(fishingPoleType)) {
            return new Result(false, "you dont have this fishing pole in your backpack");
        }


        double R = Math.random();
        double M = 1;
        TimeAndDate date = App.getCurrentGame().getDate();
        switch (date.getTodayWeatherType()) {
            case Sunny -> M = 1.5;
            case Rainy -> M = 1.2;
            case Storm -> M = 0.5;
            default -> M = 1;
        }
        int level = player.getAbilities().getFishingLevel();
        int count = (int) Math.ceil(R * M * (level + 2));
        count = Math.min(6, count);
        double pole = fishingPoleType.getPole();
        double qualityInt = ((R * (level + 2) * pole) / (7 - M));
        ItemQuality quality;
        if (qualityInt < 0.5) {
            quality = ItemQuality.Regular;
        } else if (qualityInt < 0.7) {
            quality = ItemQuality.Silver;
        } else if (qualityInt < 0.9) {
            quality = ItemQuality.Gold;
        } else {
            quality = ItemQuality.Iridium;
        }
        Fish fish = new Fish(null, null);
        ArrayList<FishType> fishes = new ArrayList<>();
        if (fishingPoleType.equals(FishingPoleType.TrainingFishingPole)) {
            fishes.addAll(new ArrayList<>(Arrays.asList
                (FishType.Sardine, FishType.Perch, FishType.Herring, FishType.SunFish)));
        } else {
            for (FishType fishType : FishType.values()) {
                if (fishType.getSeason().equals(date.getSeason())) {
                    fishes.add(fishType);
                }
            }
        }
        if (player.getAbilities().getFishingLevel() != 4) {
            ArrayList<FishType> fishesToRemove = new ArrayList<>();
            for (FishType fishType : fishes) {
                if (fishType.isLegendary()) {
                    fishesToRemove.add(fishType);
                }
            }
            fishes.removeAll(fishesToRemove);
        }
        Random rand = new Random();
        FishType randomElement = fishes.get(rand.nextInt(fishes.size()));
        fish.setFishType(randomElement);
        fish.setQuality(quality);
        for (int i = 0; i < count; i++) {
            player.getBackPack().addItemToInventory(fish);
        }
        player.getAbilities().increaseFishingAbility();
        return new Result(true, count + " " + fish.getFishType().getName() + " got caught successfully");
    }


    public boolean isToolAnimating() {
        return isToolAnimating;
    }

    public float getToolRotation() {
        return toolRotation;
    }

    public void update(float delta, Player player) {
        this.player = player;
        updateToolAnimation(delta);
        if (player.getCurrentTool() != null)
            drawTool();
    }

    private void drawTool() {
        if (!isToolAnimating()) {
            Texture texture = player.getCurrentTool().getType().getInventoryTexture();
            Main.getBatch().draw(texture, player.getX(), player.getY());
        } else {
            if (toolSprite == null) return;
            // Position tool sprite relative to player position
            float playerX = player.getX();
            float playerY = player.getY();

            toolSprite.setPosition(playerX, playerY);

            toolSprite.draw(Main.getBatch());
        }
    }
}
