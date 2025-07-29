package io.github.StardewValley.controllers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.StardewValley.Main;
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


    public void toolUse(int dx, int dy) {
        leverage = App.getCurrentGame().getDate().getTodayWeatherType().getEnergyConsume();
        player = App.getCurrentGame().getCurrentPlayingPlayer();
        //TODO: direction
        int x = player.getTileX() + dx;
        int y = player.getTileY() + dy;

        tool = App.getCurrentGame().getCurrentPlayingPlayer().getCurrentTool();
        tile = Tile.getTile(x, y);

        if (tile == null) {
            //TODO: maybe graphical error
            return;
        }

        if (tool.getToolType().equals(ToolType.Hoe)) {
            useHoe();
        } else if (tool.getToolType().equals(ToolType.Pickaxe)) {
            usePickAxe();
        } else if (tool.getToolType().equals(ToolType.Axe)) {
            useAxe();
        } else if (tool.getToolType().equals(ToolType.WateringCan)) {
            useWateringCan();
        } else if (tool.getToolType().equals(ToolType.Scythe)) {
            useScythe();
        } else if (tool.getToolType().equals(ToolType.MilkPail)) {
            useMilkPail();
        } else if (tool.getToolType().equals(ToolType.Shear)) {
            useShear();
        } else if (tool.getToolType().equals(ToolType.FishingPole)) {
            useFishingPole();
        }
        startToolAnimation();
    }


    private void useHoe() {
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
        }
        player.setEnergy(player.getEnergy() - energy * leverage);
    }


    private void usePickAxe() {
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
                return;
                //TODO: maybe graphical error
                //return new Result(false, "this type of pickaxe cannot break this mineral");
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
                //TODO: maybe graphical message
                //return new Result(true, "stone broke successfully and you also got 1 more because of mining level");
            }

        } else if (tile.isPlowed()) {
            tile.setPlowed(false);
            energy = Math.max(energy, 0);
            player.setEnergy(player.getEnergy() - energy * leverage);
        } else if (tile.getPlaceable() instanceof BackPackable item) {
            tile.setPlaceable(null);
            energy = Math.max(energy, 0);
            player.setEnergy(player.getEnergy() - energy * leverage);
            //TODO: maybe graphical message
            //return new Result(true, item.getName() + " destroyed successfully");
        }
        energy = Math.max(energy - 1, 0);
        player.setEnergy(player.getEnergy() - energy * leverage);
    }


    private void useAxe() {
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
            return;
            //TODO: maybe graphical message
            //return new Result(true, "you broke tree successfully");
        }
        if (tile.getPlaceable() instanceof NormalItem normalItem) {
            if (normalItem.getType().equals(NormalItemType.Wood)) {
                tile.setPlaceable(null);
                player.getAbilities().increaseForagingAbility();
                player.setEnergy(player.getEnergy() - energy * leverage);
                return;
                //TODO: maybe graphical message
                //return new Result(true, "you destroyed wood");
            }
        }
        energy--;
        energy = Math.max(energy, 0);
        player.setEnergy(player.getEnergy() - energy * leverage);
    }


    private void useWateringCan() {
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
            }
        } else if (tile.isWater()) {
            player.setEnergy(player.getEnergy() - energy * leverage);
            tool.handleWateringCanStorage();
        }
    }


    private void useScythe() {
        player.setEnergy(player.getEnergy() - 2 * leverage);
        if (tile.getPlaceable() instanceof NormalItem normalItem) {
            if (normalItem.getType().equals(NormalItemType.Grass))
                tile.setPlaceable(null);
            else if (normalItem.getType().equals(NormalItemType.Fiber)) {
                tile.setPlaceable(null);
                player.getBackPack().addItemToInventory(new NormalItem(NormalItemType.Fiber));
            }
        } else if (tile.getPlaceable() instanceof Plant plant) {
            player.getAbilities().increaseFarmingAbility();
            if (plant instanceof Tree tree) {
                tree.harvest();
                Fruit fruit = new Fruit(tree.getType().getFruitType());
                fruit.setItemQuality();
                player.getBackPack().addItemToInventory(
                    fruit);
                if (tree.isForaging())
                    player.getAbilities().increaseForagingAbility();
            } else if (plant instanceof Crop crop) {
                crop.harvest();
            }
        }
    }


    private void useMilkPail() {
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
                        return;
                        //TODO: maybe graphical message
//                        return new Result(false, "backpack gets full , you collect these -> \n"
//                            + sb.toString());
                    }
                }
                StringBuilder sb = new StringBuilder();
                for (Map.Entry<AnimalProduct, Integer> entry : Animal.getMapListOfAnimalProducts(toRemoved).entrySet()) {
                    sb.append(entry.getKey().getAnimalProductType().name()).append(" : ")
                        .append(entry.getValue()).append("\n");
                }
                animal.getAnimalProducts().removeAll(toRemoved);
//TODO: maybe graphical message
                //                return new Result(true, "you collected all product -> \n " +
//                    sb.toString());
            }
        }
    }


    private void useShear() {
        player.setEnergy(player.getEnergy() - 4 * leverage);
        if (tile.getPlaceable() instanceof Animal animal) {
            if (animal.getAnimalType().equals(AnimalType.Sheep)) {
                if (animal.getAnimalProducts().isEmpty()) {
                    return;
                    //TODO: maybe graphical message
                    //return new Result(false, "this sheep has no product");
                }
                ArrayList<AnimalProduct> toRemoved = new ArrayList<>();
                for (AnimalProduct animalProduct : animal.getAnimalProducts()) {
                    player.getBackPack().addItemToInventory(animalProduct);
                    toRemoved.add(animalProduct);
                    if (player.getBackPack().isBackPackFull()) {
                        animal.getAnimalProducts().removeAll(toRemoved);
                        return;
                        //TODO: maybe graphical message
//                        return new Result(false, "back pack gets full , you collected these -> \n" +
//                            animalProduct.getAnimalProductType().name() + " -> " + toRemoved.size());
                    }
                }
                animal.getAnimalProducts().removeAll(toRemoved);
                return;
                //TODO: maybe graphical message
                //return new Result(true, "you collected all " + toRemoved.size() + " wools of " + animal.getName());
            }
        }
    }


    private void useFishingPole() {
        if (!tile.isWater()) {
            return;
            //TODO: maybe graphical error
            //return new Result(false, "you should catch fish near water and lakes , here is not water");
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
        //System.out.println(fishing(tool.getFishingPoleMaterial().name()));
        fishing(tool.getFishingPoleMaterial().name());
    }


    public void fishing(String fishingPole) {
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();

        if (!Animal.areWeNearWater(player.getTileX(), player.getTileY())) {
            //TODO: maybe graphical error
            return;
            //return new Result(false, "first go near water");
        }
        if (player.getBackPack().isBackPackFull()) {
            //TODO: maybe graphical error
            return;
            //return new Result(false, "your backpack is full");
        }
        FishingPoleType fishingPoleType;
        try {
            fishingPoleType = FishingPoleType.valueOf(fishingPole);
        } catch (Exception e) {
            //TODO: maybe graphical error
            return;
            //return new Result(false, "invalid fishing pole");
        }
        if (!player.getBackPack().getBackPackItems().containsKey(fishingPoleType)) {
            //TODO: maybe graphical error
            return;
            //return new Result(false, "you dont have this fishing pole in your backpack");
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
        //TODO: maybe graphical error
        //return new Result(true, count + " " + fish.getFishType().getName() + " got caught successfully");
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
            Texture texture =new Texture(player.getCurrentTool().getType().getInventoryTexture());
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
