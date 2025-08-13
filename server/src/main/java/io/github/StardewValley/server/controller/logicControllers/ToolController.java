package io.github.StardewValley.server.controller.logicControllers;

import io.github.StardewValley.server.AppServer;
import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.shared.dto.AnimalDTO;
import io.github.StardewValley.shared.dto.AnimalProductDTO;
import io.github.StardewValley.shared.dto.HandleWorldClickResponse;
import io.github.StardewValley.shared.models.game.Game;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.TimeAndDate;
import io.github.StardewValley.shared.models.animal.AnimalType;
import io.github.StardewValley.shared.models.backpack.BackPackable;
import io.github.StardewValley.shared.models.backpack.NormalItem;
import io.github.StardewValley.shared.models.backpack.NormalItemType;
import io.github.StardewValley.shared.models.cooking.BuffType;
import io.github.StardewValley.shared.models.crafting.CraftingItem;
import io.github.StardewValley.shared.models.crafting.CraftingItemType;
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
import io.github.StardewValley.shared.models.tools.ToolType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

public class ToolController {
    private Tool tool;
    private double leverage;
    private Tile tile;

    public HandleWorldClickResponse toolUse(int dx, int dy, Player player, Game game) {
        leverage = game.getDate().getTodayWeatherType().getEnergyConsume();
        int x = player.getTileX() + dx;
        int y = player.getTileY() + dy;

        tool = player.getCurrentTool();
        tile = AppServer.getCurrentGame().getTile(x, y);

        if (tile == null) {
            return new HandleWorldClickResponse(false, "Invalid Tile", HandleWorldClickResponse.ActionType.SHOW_NOTIFICATION);
        }
        HandleWorldClickResponse result = null;

        if (tool.getToolType().equals(ToolType.Hoe)) {
            result = useHoe(player);
        } else if (tool.getToolType().equals(ToolType.Pickaxe)) {
            result = usePickAxe(player);
        } else if (tool.getToolType().equals(ToolType.Axe)) {
            result = useAxe(player);
        } else if (tool.getToolType().equals(ToolType.WateringCan)) {
            result = useWateringCan(player);
        } else if (tool.getToolType().equals(ToolType.Scythe)) {
            result = useScythe(player);
        } else if (tool.getToolType().equals(ToolType.MilkPail)) {
            result = useMilkPail(player);
        } else if (tool.getToolType().equals(ToolType.Shear)) {
            result = useShear(player);
        } else if (tool.getToolType().equals(ToolType.FishingPole)) {
            result =  useFishingPole(player, game);
        }
        return result;
    }


    private HandleWorldClickResponse useHoe(Player player) {
        System.out.println("Using Hoe");
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
            return new HandleWorldClickResponse(true, true, "Plowed successfully", HandleWorldClickResponse.ActionType.NONE);
        }
        player.setEnergy(player.getEnergy() - energy * leverage);
        return new HandleWorldClickResponse(true, false, "Hoe used but incorrectly", HandleWorldClickResponse.ActionType.SHOW_NOTIFICATION);
    }


    private HandleWorldClickResponse usePickAxe(Player player) {
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
                return new HandleWorldClickResponse(false, "This type of Pickaxe (%s) cannot break this mineral (%s)".formatted(
                    tool.getMaterial(),
                    mineral.getType().name()
                ), HandleWorldClickResponse.ActionType.SHOW_NOTIFICATION);
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
                return new HandleWorldClickResponse(true, true, "stone broke successfully and you also got 1 more because of mining level",
                HandleWorldClickResponse.ActionType.SHOW_NOTIFICATION);
            }

        } else if (tile.isPlowed()) {
            tile.setPlowed(false);
            energy = Math.max(energy, 0);
            player.setEnergy(player.getEnergy() - energy * leverage);
            return new HandleWorldClickResponse(true, true, "unplowed successfully", HandleWorldClickResponse.ActionType.NONE);
        } else if (tile.getPlaceable() instanceof BackPackable item) {
            tile.setPlaceable(null);
            tile.setWalkAble(true);
            energy = Math.max(energy, 0);
            player.setEnergy(player.getEnergy() - energy * leverage);
            return new HandleWorldClickResponse(true, item.getName() + " destroyed successfully", HandleWorldClickResponse.ActionType.NONE);
        }
        energy = Math.max(energy - 1, 0);
        player.setEnergy(player.getEnergy() - energy * leverage);
        return new HandleWorldClickResponse(true, true, "you used pickaxe but incorrectly", HandleWorldClickResponse.ActionType.SHOW_NOTIFICATION);
    }


    private HandleWorldClickResponse useAxe(Player player) {
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
            return new HandleWorldClickResponse(true, true, "you broke tree successfully", HandleWorldClickResponse.ActionType.NONE);
        }
        if (tile.getPlaceable() instanceof NormalItem normalItem) {
            if (normalItem.getType().equals(NormalItemType.Wood)) {
                tile.setPlaceable(null);
                player.getAbilities().increaseForagingAbility();
                player.setEnergy(player.getEnergy() - energy * leverage);
                tile.setWalkAble(true);
                return new HandleWorldClickResponse(true, false, "You destroyed Wood", HandleWorldClickResponse.ActionType.SHOW_NOTIFICATION);
            }
        }
        energy--;
        energy = Math.max(energy, 0);
        player.setEnergy(player.getEnergy() - energy * leverage);
        return new HandleWorldClickResponse(true, true, "You used Axe but incorrectly", HandleWorldClickResponse.ActionType.SHOW_NOTIFICATION);
    }


    private HandleWorldClickResponse useWateringCan(Player player) {
        System.out.println("Using watering Can.");
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
                return new HandleWorldClickResponse(true, true, "plant watered sucessfully",
                    HandleWorldClickResponse.ActionType.SHOW_NOTIFICATION);
            }
        } else if (tile.isWater()) {
            player.setEnergy(player.getEnergy() - energy * leverage);
            if (tool.isWateringCanFull())
                return new HandleWorldClickResponse(true,
                    "watering can is already full", HandleWorldClickResponse.ActionType.SHOW_NOTIFICATION);
            tool.handleWateringCanStorage();
        }
        return new HandleWorldClickResponse(true,
            "watering can is now full of water", HandleWorldClickResponse.ActionType.SHOW_NOTIFICATION);
    }


    private HandleWorldClickResponse useScythe(Player player) {
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
                    return new HandleWorldClickResponse(true,
                        "Tree (%s) is not Fully Grown Yet\n(Days till Fully Growth: %d)"
                        .formatted(tree.getType().name(), tree.getDaysTillFullGrowth()),
                        HandleWorldClickResponse.ActionType.SHOW_NOTIFICATION);
                }
                else if (!tree.hasFruit()) {
                    return new HandleWorldClickResponse(true, "Tree (%s) is Fully Grown but doesn't have fruit today\n(Days till next harvest time: %d)"
                        .formatted(tree.getType().name(), tree.getDaysTillNextHarvest()),
                        HandleWorldClickResponse.ActionType.SHOW_NOTIFICATION);
                }
                tree.harvest(player);
                Fruit fruit = new Fruit(tree.getType().getFruitType());
                fruit.setItemQuality();
                player.getBackPack().addItemToInventory(fruit);
                if (tree.isForaging())
                    player.getAbilities().increaseForagingAbility();
            } else if (plant instanceof Crop crop) {
                if (!crop.isFullyGrown()) {
                    return new HandleWorldClickResponse(true, "Crop (%s) is not Fully Grown Yet\n(Days till Fully Growth: %d)"
                        .formatted(crop.getName(), crop.getDaysTillFullGrowth()),
                        HandleWorldClickResponse.ActionType.SHOW_NOTIFICATION);
                }
                else if (!crop.hasFruit()) {
                    return new HandleWorldClickResponse(true, "Crop (%s) is Fully Grown but doesn't have fruit today\n(Days till next harvest time: %d)"
                        .formatted(crop.getName(), crop.getDaysTillNextHarvest()),
                        HandleWorldClickResponse.ActionType.SHOW_NOTIFICATION);
                }
                crop.harvest(player);
            }
        }
        return new HandleWorldClickResponse(true, true, "", HandleWorldClickResponse.ActionType.NONE);
    }


    private HandleWorldClickResponse useMilkPail(Player player) {
        player.setEnergy(player.getEnergy() - 4 * leverage);
        if (tile.getPlaceable() instanceof AnimalDTO animal) {
            if (animal.getAnimalType().equals(AnimalType.Cow)) {
                ArrayList<AnimalProductDTO> toRemoved = new ArrayList<>();
                for (AnimalProductDTO animalProduct : animal.getAnimalProductDTOS()) {
                    player.getBackPack().addItemToInventory(animalProduct);
                    toRemoved.add(animalProduct);
                    if (player.getBackPack().isBackPackFull()) {
                        animal.getAnimalProductDTOS().removeAll(toRemoved);
                        StringBuilder sb = new StringBuilder();
                        for (Map.Entry<AnimalProductDTO, Integer> entry : AnimalLogicService.getMapListOfAnimalProducts(toRemoved).entrySet()) {
                            sb.append(entry.getKey().getType().name()).append(" : ")
                                .append(entry.getValue()).append("\n");
                        }
                        return new HandleWorldClickResponse(false, "backpack gets full , you collect these -> \n"
                            + sb.toString(), HandleWorldClickResponse.ActionType.SHOW_NOTIFICATION);
                    }
                }
                StringBuilder sb = new StringBuilder();
                for (Map.Entry<AnimalProductDTO, Integer> entry : AnimalLogicService.getMapListOfAnimalProducts(toRemoved).entrySet()) {
                    sb.append(entry.getKey().getType().name()).append(" : ")
                        .append(entry.getValue()).append("\n");
                }
                animal.getAnimalProductDTOS().removeAll(toRemoved);
                return new HandleWorldClickResponse(true, "you collected all product -> \n " +
                    sb.toString(), HandleWorldClickResponse.ActionType.SHOW_NOTIFICATION);
            }
        }
        return new HandleWorldClickResponse(true, true, "", HandleWorldClickResponse.ActionType.NONE);
    }


    private HandleWorldClickResponse useShear(Player player) {
        player.setEnergy(player.getEnergy() - 4 * leverage);
        if (tile.getPlaceable() instanceof AnimalDTO animal) {
            if (animal.getAnimalType().equals(AnimalType.Sheep)) {
                if (animal.getAnimalProductDTOS().isEmpty()) {
                    return new HandleWorldClickResponse(false, "this sheep has no product"
                    , HandleWorldClickResponse.ActionType.SHOW_NOTIFICATION);
                }
                ArrayList<AnimalProductDTO> toRemoved = new ArrayList<>();
                for (AnimalProductDTO animalProduct : animal.getAnimalProductDTOS()) {
                    player.getBackPack().addItemToInventory(animalProduct);
                    toRemoved.add(animalProduct);
                    if (player.getBackPack().isBackPackFull()) {
                        animal.getAnimalProductDTOS().removeAll(toRemoved);
                        return new HandleWorldClickResponse(false, "back pack gets full , you collected these -> \n" +
                            animalProduct.getType().name() + " -> " + toRemoved.size(),
                            HandleWorldClickResponse.ActionType.SHOW_NOTIFICATION);
                    }
                }
                animal.getAnimalProductDTOS().removeAll(toRemoved);
                return new HandleWorldClickResponse(true, true, "you collected all " + toRemoved.size() + " wools of " + animal.getName(),
                    HandleWorldClickResponse.ActionType.SHOW_NOTIFICATION);
            }
        }
        return new HandleWorldClickResponse(true, true, "", HandleWorldClickResponse.ActionType.NONE);
    }


    private HandleWorldClickResponse useFishingPole(Player player, Game game) {
        if (!tile.isWater()) {
            return new HandleWorldClickResponse(false, "you should catch fish near water and lakes , here is not water",
                HandleWorldClickResponse.ActionType.SHOW_NOTIFICATION);
        }
        double energy = 2;
        switch (tool.getFishingPoleType()) {
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
        return fishing(tool.getFishingPoleType().name(), player, game);
    }


    public HandleWorldClickResponse fishing(String fishingPole, Player player, Game game) {
//        if (!Animal.areWeNearWater(player.getTileX(), player.getTileY())) {
//            return new HandleWorldClickResponse(false, "first go near water",
//                HandleWorldClickResponse.ActionType.SHOW_NOTIFICATION);
//        }
        if (player.getBackPack().isBackPackFull()) {
            return new HandleWorldClickResponse(false, "your backpack is full",
                HandleWorldClickResponse.ActionType.SHOW_NOTIFICATION);
        }
        FishingPoleType fishingPoleType;
        try {
            fishingPoleType = FishingPoleType.valueOf(fishingPole);
        } catch (Exception e) {
            return new HandleWorldClickResponse(false, "invalid fishing pole",
                HandleWorldClickResponse.ActionType.SHOW_NOTIFICATION);
        }
        if (!player.getBackPack().getBackPackItems().containsKey(fishingPoleType)) {
            return new HandleWorldClickResponse(false, "you dont have this fishing pole in your backpack",
                HandleWorldClickResponse.ActionType.SHOW_NOTIFICATION);
        }


        double R = Math.random();
        double M = 1;
        TimeAndDate date = game.getDate();
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
        return new HandleWorldClickResponse(true, true, count + " " + fish.getFishType().getName() + " got caught successfully",
            HandleWorldClickResponse.ActionType.SHOW_NOTIFICATION);
    }


    public HandleWorldClickResponse placeCraftingItem(int dx, int dy, Player player, Game game) {
        CraftingItemType craftingItemType = (CraftingItemType) player.getEquippedItem().getType();

        int x = player.getX() / GameAssetManager.getGameAssetManager().getTileWidth() + dx;
        int y = player.getY() / GameAssetManager.getGameAssetManager().getTileHeight() + dy;
        Tile tile = game.getTile(x, y);

        if (tile.getPlaceable() != null) {
            return new HandleWorldClickResponse(false, "tile is full", HandleWorldClickResponse.ActionType.SHOW_NOTIFICATION);
        }

        player.getBackPack().useItem(craftingItemType);
        CraftingItem craftingItem = new CraftingItem(craftingItemType, player, game);
        craftingItem.setStart_x(x);
        craftingItem.setStart_y(y);
        game.addCraftingItem(craftingItem);

        tile.setPlaceable(craftingItem);
        tile.setWalkAble(false);

        switch (craftingItemType) {
            case CherryBomb -> {
                int range = 3;
                for (int i = -range; i < range + 1; i++) {
                    for (int j = -range; j < range + 1; j++) {

                        Tile target = Tile.getTile(tile.getX() + i, tile.getY() + j);
                        if (target != null) {
                            target.setPlaceable(null);
                        }
                    }
                }
            }

            case Bomb -> {
                int range = 5;
                for (int i = -range; i < range + 1; i++) {
                    for (int j = -range; j < range + 1; j++) {

                        Tile target = Tile.getTile(tile.getX() + i, tile.getY() + j);
                        if (target != null) {
                            target.setPlaceable(null);
                        }
                    }
                }
            }

            case MegaBomb -> {
                int range = 7;
                for (int i = -range; i < range + 1; i++) {
                    for (int j = -range; j < range + 1; j++) {

                        Tile target = Tile.getTile(tile.getX() + i, tile.getY() + j);
                        if (target != null) {
                            target.setPlaceable(null);
                        }
                    }
                }
            }

            case Sprinkler -> {
                int[] dx2 = {0, 1, 0, -1};
                int[] dy2 = {1, 0, -1, 0};
                for (int i = 0; i < 4; i++) {
                    Tile target = Tile.getTile(tile.getX() + dx2[i], tile.getY() + dy2[i]);
                    if (target != null && target.getPlaceable() instanceof Plant plant) {
                        plant.wateringPlant();
                    }
                }
            }

            case QualitySprinkler -> {
                int range = 1;
                for (int i = -range; i < range + 1; i++) {
                    for (int j = -range; j < range + 1; j++) {

                        Tile target = Tile.getTile(tile.getX() + i, tile.getY() + j);
                        if (target != null && target.getPlaceable() instanceof Plant plant) {
                            plant.wateringPlant();
                        }
                    }
                }
            }

            case IridiumSprinkler -> {
                int range = 2;
                for (int i = -range; i < range + 1; i++) {
                    for (int j = -range; j < range + 1; j++) {

                        Tile target = Tile.getTile(tile.getX() + i, tile.getY() + j);
                        if (target != null && target.getPlaceable() instanceof Plant plant) {
                            plant.wateringPlant();
                        }
                    }
                }
            }

            case Scarecrow -> {
                int range = 8;
                for (int i = -range; i < range + 1; i++) {
                    for (int j = -range; j < range + 1; j++) {

                        Tile target = Tile.getTile(tile.getX() + i, tile.getY() + j);
                        if (target != null) {
                            tile.setCrowImmunity(true);
                        }
                    }
                }
            }

            case DeluxeScarecrow -> {
                int range = 12;
                for (int i = -range; i < range + 1; i++) {
                    for (int j = -range; j < range + 1; j++) {

                        Tile target = Tile.getTile(tile.getX() + i, tile.getY() + j);
                        if (target != null) {
                            tile.setCrowImmunity(true);
                        }
                    }
                }
            }

            case BeeHouse -> {

            }

            case CheesePress -> {

            }

            case Keg -> {

            }

            case Loom -> {

            }

            case MayonnaiseMachine -> {

            }

            case OilMaker -> {

            }

            case PreservesJar -> {

            }

            case Dehydrator -> {

            }

            case FishSmoker -> {

            }

            case MysticTreeSeed -> {

            }
        }
        return new HandleWorldClickResponse(true, "Item placed Successfully.",
            HandleWorldClickResponse.ActionType.NONE);
    }

    public void handleRefund(BackPackable backPackable, Player player) {
        double refundPercentage = player.getTrashCan().getTrashCanRefundPercentage() / 100.0;
        double refund = backPackable.getType().getPrice() * refundPercentage;
        player.addCoin(refund);
    }

}
