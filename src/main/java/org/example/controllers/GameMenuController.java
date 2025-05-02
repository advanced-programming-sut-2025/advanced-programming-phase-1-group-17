package org.example.controllers;

import org.example.display;
import org.example.models.*;
import org.example.models.artisan.ArtisanProduct;
import org.example.models.artisan.ArtisanProductType;
import org.example.models.artisan.IngredientGroup;
import org.example.models.cooking.Food;
import org.example.models.cooking.FoodType;
import org.example.models.cooking.Recipe;
import org.example.models.crafting.CraftingItem;
import org.example.models.crafting.CraftingItemType;
import org.example.models.enums.*;
import org.example.models.map.GreenHouse;
import org.example.models.map.Stone;
import org.example.models.map.Tile;
import org.example.models.plant.*;
import org.example.models.tools.BackPack;
import org.example.models.tools.Tool;

import java.util.*;

public class GameMenuController {

    public Result newGame(String username1, String username2, String username3, String rest, Scanner scanner) {
        if (rest != null && !rest.trim().isEmpty())
            return new Result(false, "you can't give more than 3 names");

        User user1, user2, user3;
        if (username1 == null) {
            return new Result(false, "you must give at least 1 username");
        }
        user1 = App.getUserWithUsername(username1);
        if (user1 == null)
            return new Result(false, "no user exists with username %s".formatted(username1));
        if (user1.getActiveGame() != null)
            return new Result(false, "user with username %s has an active game".formatted(username1));

        if (username2 == null) {
            if (App.getUserWithUsername("guest1") != null) {
                App.getUsers().remove(App.getUserWithUsername("guest1"));
            }
            user2 = new User();
            user2.setUsername("guest1");
            App.getUsers().add(user2);
        } else {
            user2 = App.getUserWithUsername(username2);
            if (user2 == null)
                return new Result(false, "no user exists with username %s".formatted(username2));
            if (user2.getActiveGame() != null)
                return new Result(false, "user with username %s has an active game".formatted(username2));
        }
        if (username3 == null) {
            if (App.getUserWithUsername("guest2") != null) {
                App.getUsers().remove(App.getUserWithUsername("guest2"));
            }
            user3 = new User();
            user3.setUsername("guest2");
            App.getUsers().add(user3);
        } else {
            user3 = App.getUserWithUsername(username3);
            if (user3 == null)
                return new Result(false, "no user exists with username %s".formatted(username3));
            if (user3.getActiveGame() != null)
                return new Result(false, "user with username %s has an active game".formatted(username3));
        }
        Game game = new Game(user1, user2, user3);
        App.setCurrentGame(game);
        App.getGames().add(game);
        gameMap(scanner);
        return new Result(true, "new game created Successfully");
    }

    public void gameMap(Scanner scanner) {
        boolean done = false;
        int playerChoice = 0;
        System.out.println("Enter the number of the gameMapType you would like to play (1 or 2)");
        while (!done) {
            String input = scanner.nextLine();
            if (GameMenuCommands.ChooseGameMap.getMatcher(input) == null) {
                System.out.println("Invalid input");
            } else if (GameMenuCommands.Int.getMatcher
                    (GameMenuCommands.ChooseGameMap.getMatcher(input).
                            group("mapNumber")) == null) {
                System.out.println("Invalid number");
            } else if (Integer.parseInt(GameMenuCommands.ChooseGameMap.getMatcher(input).group("mapNumber")) != 1
                    && Integer.parseInt(GameMenuCommands.ChooseGameMap.getMatcher(input).group("mapNumber")) != 2) {
                System.out.println("Invalid number");
            } else {
                App.getCurrentGame().getPlayers().get(playerChoice).getPlayerMap().setMapType
                        (Integer.parseInt(GameMenuCommands.ChooseGameMap.getMatcher(input).group("mapNumber")));
                playerChoice++;
                if (playerChoice == 4) {
                    done = true;
                    System.out.println("Let's go");
                }
            }
        }
    }


    public Result loadGame() {
        return new Result(false, "t");
    }


    public Result exitGame() {
        if (!App.getCurrentGame().getCurrentPlayingPlayer().equals(App.getCurrentGame()))
            return new Result(false, "Only the game creator can exit the game.");
        //TODO: saving the game
        App.setCurrentMenu(null);
        return new Result(true, "Game saved successfully.");
    }

    public Result removeCurrentGame() {
        return new Result(false, "t");
    }

    public Result nextTurn() {
        App.getCurrentGame().switchPlayer();
        return new Result(true, "Switched to %s".formatted(App.getCurrentGame().
                getCurrentPlayingPlayer().getUser().getUsername()));
    }

    public Result getTime() {
        return new Result(true, App.getCurrentGame().getDate().getHour() + " : " +
                App.getCurrentGame().getDate().getMinute());
    }


    public Result getDate() {
        return new Result(true, App.getCurrentGame().getDate().getDay() + "/" +
                App.getCurrentGame().getDate().getMonth() + "/" + App.getCurrentGame().getDate().getYear());
    }

    public Result getDateTime() {
        StringBuilder sb = new StringBuilder();
        sb.append(getTime()).append("\n")
                .append(getDate());
        return new Result(true, sb.toString());
    }

    public Result getDayOfTheWeek() {
        return new Result(true, App.getCurrentGame().getDate().getDayOfTheWeek().name());
    }

    public Result getSeason() {
        return new Result(true, App.getCurrentGame().getDate().getSeason().name());
    }

    public Result changeTime(String hour) {
        int amount = Integer.parseInt(hour);
        for (int i = 0; i < amount; i++)
            App.getCurrentGame().getDate().increaseHour();
        return new Result(true, "added successfully");
    }

    public Result changeDate(String day) {
        int amount = Integer.parseInt(day);
        for (int i = 0; i < amount; i++) {
            App.getCurrentGame().getDate().goToNextDay();
        }
        return new Result(true, amount + " days added successfully");
    }

    public Result cheatThor(int x, int y) {
        Tile tile = App.getCurrentGame().getTileByIndex(x, y);
        if (tile == null)
            return new Result(false, "tile not found");
        tile.setLightninged(true);
        return new Result(true, "Successfully lightninged.");
    }

    public Result getWeather() {
        return new Result(true, App.getCurrentGame()
                .getDate().getTodayWeatherType().name());
    }

    public Result weatherForeCast() {
        return new Result(true, App.getCurrentGame().getDate().getTomorrowWeather().name());
    }

    public Result changeWeather(String input) {
        try {
            App.getCurrentGame().getDate().setTomorrowWeather(WeatherType.valueOf(input));
            return new Result(true, "tomorrow weather changed to "
                    + App.getCurrentGame().getDate().getTomorrowWeather().name() + " successfully");
        } catch (Exception e) {
            return new Result(false, "valid options : Sunny,Rainy,Storm,Snow");
        }
    }

    public Result buildGreenHouse() {
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        if (player.getCoin() >= 1000 && player.getWood() >= 500) {
            player.addCoin(-1000);
            player.addWood(-500);
            player.getPlayerMap().setGreenHouse(new GreenHouse(player));
            return new Result(true, "GreenHouse created Successfully");
        } else {
            return new Result(false, "you don't have enough coin and wood");
        }
    }

    public Result walk(int x, int y, Scanner scanner) {
        List<Tile> result;
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        Tile destination = Tile.getTile(x, y);
        if (destination.getOwner() != player) {
            return new Result(false, "you can't walk to this tile because this tile is not for you.");
        } else if (!destination.isWalkAble()) {
            return new Result(false, "you can't walk to this tile because this tile is not walkable.");
        } else if ((result = bfs(player.getX(), player.getY(), x, y, player)) == null) {
            return new Result(false, "you can't walk to this tile because there is not path to this tile");
        } else {
            //TODO
            float energy_needed = (float) (result.size() - 1) / 20;
            System.out.println("your energy : " + player.getEnergy());
            System.out.printf("energy needed : %.2f\n", energy_needed);
            System.out.println("do you want to go to the destination? press y or n and press enter");
            String input = scanner.nextLine();
            if (input.equals("y")) {
                player.setEnergy(player.getEnergy() - energy_needed);
                if (player.getEnergy() <= 0) {
                    player.setHasPassedOutToday(true);
                    player.setEnergy(0);
                    return new Result(false, "you fainted");
                } else {
                    //TODO Walkin the path
//                    for (Tile tile : result) {
//                        System.out.println(tile.getX() + "__" + tile.getY());
//                    }
                    Tile.getTile(player.getX(), player.getY()).setWhoIsHere(null);
                    Tile.getTile(x, y).setWhoIsHere(player);
                    player.setX(x);
                    player.setY(y);
                    return new Result(true, "you are in the destination now");
                }
            } else {
                return new Result(true, "cancellation...");
            }
        }
    }

    public boolean isValid(int x, int y, int rows, int cols) {
        return x >= 0 && x < rows && y >= 0 && y < cols && Tile.getTile(x, y) != null && Tile.getTile(x, y).isWalkAble();
    }

    public List<Tile> bfs(int startX, int startY, int endX, int endY, Player player) {
        int[][] directions = {
                {0, 1},
                {1, 0},
                {0, -1},
                {-1, 0},
                {1, 1},
                {1, -1},
                {-1, 1},
                {-1, -1}
        };

        int rows = 51 + player.getPlayerMap().getRow();
        int cols = 51 + player.getPlayerMap().getCol();

        boolean[][] visited = new boolean[rows][cols];
        Map<Tile, Tile> parent = new HashMap<>();
        Queue<Tile> queue = new LinkedList<>();

        Tile start = Tile.getTile(startX, startY);
        Tile end = Tile.getTile(endX, endY);
        if (start == null || end == null || !start.isWalkAble() || !end.isWalkAble()) {
            return null;
        }

        queue.offer(start);
        visited[startX][startY] = true;

        while (!queue.isEmpty()) {
            Tile current = queue.poll();
            if (current.getX() == endX && current.getY() == endY) {
                return buildPath(parent, start, end);
            }

            for (int[] dir : directions) {
                int newX = current.getX() + dir[0];
                int newY = current.getY() + dir[1];

                if (isValid(newX, newY, rows, cols) && !visited[newX][newY]) {
                    Tile neighbor = Tile.getTile(newX, newY);
                    visited[newX][newY] = true;
                    parent.put(neighbor, current);
                    queue.offer(neighbor);
                }
            }
        }

        return null;
    }

    public List<Tile> buildPath(Map<Tile, Tile> parent, Tile start, Tile end) {
        List<Tile> path = new ArrayList<>();
        Tile current = end;

        while (current != null && !current.equals(start)) {
            path.add(current);
            current = parent.get(current);
        }

        if (current == null) return null;

        path.add(start);
        Collections.reverse(path);
        return path;
    }

    public void printMap(int x, int y, int size) {
        display.run(x, y, size);
    }

    public void helpReadingMap() {
        display.helpReadingMap();
    }

    public Result energyShow() {
        return new Result(true, "%f".formatted(
                App.getCurrentGame().getCurrentPlayingPlayer().getEnergy()));
    }

    public Result energySet(String value) {
        double energy;
        try {
            energy = Integer.parseInt(value);
        } catch (Exception e) {
            return new Result(false, "You must enter a number");
        }
        App.getCurrentGame().getCurrentPlayingPlayer().setEnergy(energy);
        return new Result(true, "Energy successfully set to %f".formatted(energy));
    }

    public Result energyUnlimited() {
        App.getCurrentGame().getCurrentPlayingPlayer().setEnergy(Double.POSITIVE_INFINITY);
        return new Result(true, "Energy successfully set to infinity");
    }

    public Result inventoryShow() {
        BackPack backPack = App.getCurrentGame().getCurrentPlayingPlayer().getBackPack();
        StringBuilder result = new StringBuilder();

        for (BackPackableType backPackableType : backPack.getBackPackItems().keySet()) {
            result.append("%s: %d\n".formatted(backPackableType.getName(), backPack.getBackPackItems().get(backPackableType).size()));
        }

        return new Result(true, result.toString().trim());
    }

    public Result inventoryTrash(String itemName, String number) {
        itemName = itemName.trim().toLowerCase();
        BackPack backPack = App.getCurrentGame().getCurrentPlayingPlayer().getBackPack();
        double refundPercentage = App.getCurrentGame().getCurrentPlayingPlayer().getTrashCan().getTrashCanRefundPercentage() / 100.0;

        for (Map.Entry<BackPackableType, ArrayList<BackPackable>> entry : backPack.getBackPackItems().entrySet()) {
            BackPackableType type = entry.getKey();
            List<BackPackable> items = entry.getValue();

            if (type.getName().equalsIgnoreCase(itemName)) {
                int numberToRemove = (number == null) ? items.size() : Math.min(Integer.parseInt(number), items.size());
                double refund = numberToRemove * type.getPrice() * refundPercentage;

                items.subList(0, numberToRemove).clear(); // remove items

                if (items.isEmpty()) {
                    backPack.getBackPackItems().remove(type);
                }

                App.getCurrentGame().getCurrentPlayingPlayer().addCoin(refund);
                return new Result(true, String.format("Deleted %d of %s from inventory. Got %.2f coins.",
                        numberToRemove, type.getName(), refund));
            }
        }

        return new Result(false, "Item with this name doesn't exist in your backpack.");
    }


    public Result toolEquip(String toolName) {
        BackPack backPack = App.getCurrentGame().getCurrentPlayingPlayer().getBackPack();
        for (BackPackableType item : backPack.getBackPackItems().keySet()) {
            if (item instanceof ToolType toolType) {
                Tool tool = (Tool) backPack.getBackPackItems().get(toolType).get(0);
                if (tool.getToolType().getName().equalsIgnoreCase(toolName)) {
                    App.getCurrentGame().getCurrentPlayingPlayer().setCurrentTool(tool);
                    return new Result(true, "You are now using " + tool.getToolType().getName() + ".");
                }
            }
        }

        App.getCurrentGame().getCurrentPlayingPlayer().setCurrentTool(null);
        return new Result(false, "Tool with name '" + toolName + "' doesn't exist in your backpack.");
    }


    public Result currentToolShow() {
        if (App.getCurrentGame().getCurrentPlayingPlayer().getCurrentTool() == null) {
            return new Result(false, "You are not using any tool right now");
        }
        return new Result(true, "your current tool is " +
                App.getCurrentGame().getCurrentPlayingPlayer().getCurrentTool().getToolType().name());
    }

    public Result toolsShowAvailable() {
        StringBuilder sb = new StringBuilder();
        BackPack backPack = App.getCurrentGame().getCurrentPlayingPlayer().getBackPack();

        for (BackPackableType backPackableType : backPack.getBackPackItems().keySet()) {
            if (backPackableType instanceof ToolType toolType) {
                Tool tool = (Tool) backPack.getBackPackItems().get(toolType).get(0);
                if (tool != null) {
                    sb.append(tool.getToolType().name()).append("\n");
                }
            }
        }
        if (sb.isEmpty()) {
            return new Result(false, "You dont have any tool");
        }
        return new Result(true, sb.toString());
    }

    public Result toolUpgrade(String toolName) {
        if (Tool.findToolByName(toolName) == null) {
            return new Result(false, "Tool with this name doesn't exist in your backpack.");
        }
        Tool tool = Tool.findToolByName(toolName);
        if (tool.getLevel() == 4) {
            return new Result(false, toolName + "is alredy at max level");
        }
        //TODO money needed for Upgrade
        tool.setLevel(tool.getLevel() + 1);
        return new Result(true, toolName + "upgraded to " + tool.getLevel());

    }

    public Result toolUse(String direction) {
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        int x = player.getX() + App.handleDirection(Integer.parseInt(direction))[0];
        int y = player.getY() + App.handleDirection(Integer.parseInt(direction))[1];

        Tool tool = App.getCurrentGame().getCurrentPlayingPlayer().getCurrentTool();
        Tile tile = Tile.getTile(x, y);

        if (tile == null) {
            return new Result(false, "unknown error");
        }

        if (tool.getToolType().equals(ToolType.Hoe)) {
            if (tile.getPlaceable() == null) {
                tile.setPlowed(true);
                return new Result(true, "plowed successfully");
            }
        } else if (tool.getToolType().equals(ToolType.Pickaxe)) {
            if (tile.getPlaceable() instanceof Stone) {
                tile.setPlaceable(null);
                return new Result(true, "stone breaked successfully");
            } else if (tile.isPlowed()) {
                tile.setPlowed(false);
            }

        } else if (tool.getToolType().equals(ToolType.Axe)) {
            if (tile.getPlaceable() instanceof Tree) {
                tile.setPlaceable(null);
            }
        } else if (tool.getToolType().equals(ToolType.WateringCan)) {
            if (tile.getPlaceable() instanceof Plant plant) {
                if (tool.getWateringCanStorage() > 0) {
                    plant.wateringPlant();
                    tool.setWateringCanStorage(tool.getWateringCanStorage() - 1);
                }
            } else if (tile.isWater()) {
                tool.handleWateringCanStorage();
            }
        } else if (tool.getToolType().equals(ToolType.Scythe)) {
            //TODO: Alaf
            player.setEnergy(player.getEnergy() - 2);
            if (tile.getPlaceable() instanceof Plant plant) {
                player.getAbilities().increaseFarmingAbility(5);
                if (plant instanceof Tree tree) {
                    if (tree.getTile().isLightninged()) {
                        //TODO: gives Coal instead of fruit
                    }
                    tree.setFullyGrown(false);
                    player.getBackPack().addItemToInventory(
                            new Fruit(tree.getType().getFruitType())
                    );
                    tree.setFullyGrown(false);
                } else if (plant instanceof Crop crop) {
                    if (crop.isFullyGrown()) {
                        player.getBackPack().addItemToInventory(crop);
                        crop.getTile().setPlaceable(null);
                    }
                }
            }
        } else if (tool.getToolType().equals(ToolType.MilkPail)) {
            if (tile.getPlaceable() instanceof Animal) {
                Animal animal = (Animal) tile.getPlaceable();
            }
        }
        return new Result(true, "Tool used.");
    }

    public Result craftInfo(String name) {
        StringBuilder result = new StringBuilder();

        for (CropType cropType : CropType.values()) {
            if (!cropType.name().equals(name)) {
                continue;
            }
            result.append("""
                    Name: %s
                    Source: %s""".formatted(cropType.name(), cropType.getSource().name()));
            result.append("\nStages: ");
            int counter = 1;
            for (Integer stage : cropType.getStages()) {
                result.append("%s".formatted(stage));
                if (counter != cropType.getStages().size())
                    result.append("-");
                counter++;
            }
            result.append("\n");
            result.append("""
                    Total Harvest Time: %d
                    One Time: %s
                    Regrowth Time: %s
                    Base Sell Price: %.0f
                    Is Edible: %s
                    Base Energy: %d
                    Base Health:
                    Season: """.formatted(
                    cropType.getTotalGrowthTime(), cropType.isOneTime(),
                    (cropType.getRegrowthTime() == -1) ? "" : cropType.getRegrowthTime(),
                    cropType.getBaseSellPrice(), cropType.isEdible(), cropType.getEnergy()
            ));

            counter = 1;
            for (Season season : cropType.getSeasons()) {
                result.append("%s".formatted(season));
                if (counter != cropType.getSeasons().size())
                    result.append("-");
                counter++;
            }

            result.append("\nCan Become Giant: %s".formatted(cropType.isCanBecomeGiant()));
            return new Result(true, result.toString());
        }


        for (FruitType fruitType : FruitType.values()) {
            TreeType tree = fruitType.getSourceTreeType();
            if (fruitType.name().equals(name)) {
                break;
            }
            result.append("""
                    Name: %s
                    Source: %s""".formatted(fruitType.name(), tree.getSource().name()));
            result.append("\nStages: ");
            int counter = 1;
            for (Integer stage : tree.getStages()) {
                result.append("%s".formatted(stage));
                if (counter != tree.getStages().size())
                    result.append("-");
                counter++;
            }
            result.append("\n");
            result.append("""
                    Total Harvest Time: %d
                    One Time: False
                    Regrowth Time: %s
                    Base Sell Price: %.0f
                    Is Edible: %s
                    Base Energy: %d
                    Base Health:
                    Season: """.formatted(
                    tree.getTotalGrowthTime(),
                    (tree.getFruitHarvestCycle() == -1) ? "" : tree.getFruitHarvestCycle(),
                    fruitType.getBaseSellPrice(), fruitType.isEdible(), fruitType.getEnergy()
            ));

            counter = 1;
            for (Season season : tree.getSeasons()) {
                result.append("%s".formatted(season));
                if (counter != tree.getSeasons().size())
                    result.append("-");
                counter++;
            }

            result.append("Can Become Giant: False");
            return new Result(true, result.toString());
        }

        return new Result(false, "No Crop or Fruit Found with this Name");
    }

    public Result plantSeed(String seed, String direction) {
        int[] directions = App.handleDirection(Integer.parseInt(direction));
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        SeedType seedType = App.getSeedType(seed);

        int newX = player.getX() + directions[0];
        int newY = player.getY() + directions[1];

        Tile tile = App.getCurrentGame().getTileByIndex(newX, newY);

        if (!tile.isPlowed())
            return new Result(false, "The Specified tile is not Plowed");

        else if (seedType == null)
            return new Result(false, "No SeedType with this Name");

        player.getBackPack().useItem(seedType);
        tile.setPlaceable(new Crop(false, seedType.getCropType(), false, tile));

        return new Result(true,
                "Successfully planted a plant of type %s in (%d,%d)".formatted(
                        seedType.name(), newX, newY
                ));
    }

    public Result showPlant(String x, String y) {
        int x1 = Integer.parseInt(x);
        int y1 = Integer.parseInt(y);

        Tile tile = App.getCurrentGame().getTileByIndex(x1, y1);
        if (tile.getPlaceable() instanceof Tree tree) {
            return new Result(true, """
                    Name: %s
                    Days Left Till Full Growth: %d
                    Current Stage: %d
                    Quality:
                    Fertilizer:""".formatted(tree.getType().name(), tree.getDaysTillFullGrowth(), tree.getCurrentStageIndex() + 1)); //TODO: Plant Quality?
        } else if (tile.getPlaceable() instanceof Crop crop) {
            return new Result(true, """
                    Name: %s
                    Days Left Till Full Growth: %d
                    Current Stage: %d
                    Quality:
                    Fertilizer:""".formatted(crop.getName(), crop.getDaysTillFullGrowth(), crop.getCurrentStageIndex() + 1));
        }
        return new Result(false, "There is no plant in this tile");
    }

    public Result fertilize(String fertilizer, String direction) {
        //TODO: Add different kinds of fertilizers

        int[] directions = App.handleDirection(Integer.parseInt(direction));
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();

        int newX = player.getX() + directions[0];
        int newY = player.getY() + directions[1];
        Tile tile = App.getCurrentGame().getTileByIndex(newX, newY);

        //TODO: if we don't have any fertilizer
        //TODO: delete fertilizer from backPack

        if (tile.getPlaceable() instanceof Tree tree) {
            tree.setFertilized(true);
            return new Result(true, "Fertilized successfully");
        } else if (tile.getPlaceable() instanceof Crop crop) {
            crop.setFertilized(true);
            return new Result(true, "Fertilized successfully");
        }

        return new Result(false, "No plant in this tile");
    }

    public Result howMuchWater() {
        //TODO: Harvesting with scythe
        //TODO: Watering Plant when using 'use tool'
        Tool tool = App.getCurrentGame().getCurrentPlayingPlayer().getCurrentTool();
        if (tool.getToolType().equals(ToolType.WateringCan)) {
            return new Result(true, "%d".formatted(tool.getWateringCanStorage()));
        }
        //TODO: it must always return how much water is left
        return new Result(false, "");
    }

    public Result craftingShowRecipes() {
        if (App.getCurrentGame().getCurrentPlayingPlayer().getCraftingRecipes().isEmpty()) {
            return new Result(false, "No crafting recipes found");
        }
        StringBuilder sb = new StringBuilder();
        for (CraftingItem recipe : App.getCurrentGame().getCurrentPlayingPlayer().getCraftingRecipes()) {
            sb.append(recipe.getTargetItem().getName()).append(" -> ").append("\n");
            for (Map.Entry<CraftingItemType, Integer> entry : recipe.getCraftIngredients().entrySet()) {
                CraftingItemType item = entry.getKey();
                int quantity = entry.getValue();
                sb.append(item).append(": ").append(quantity).append("\n");
            }
        }
        return new Result(true, sb.toString());
    }

    public Result craftingCraft(String itemName) {
        if (CraftingItem.findCraftingItemTypeByName(itemName) == null) {
            return new Result(false, "No crafting recipe found");
        }
        CraftingItem item = CraftingItem.findCraftingItemTypeByName(itemName);
        if (App.getCurrentGame().getCurrentPlayingPlayer().getBackPack().isBackPackFull()) {
            return new Result(false, "no free space in inventory");
        }
        BackPack backPack = App.getCurrentGame().getCurrentPlayingPlayer().getBackPack();
        for (BackPackableType backPackableType : backPack.getBackPackItems().keySet()) {
            //TODO enough item or not
        }
        backPack.addItemToInventory(item);
        return new Result(true, itemName + "crafted successfully");

    }

    public Result placeItem(String itemName, String direction) {
        if (CraftingItem.findCraftingItemTypeByName(itemName) == null) {
            return new Result(false, "No item found");
        }
        int[] direction1 = App.handleDirection(Integer.parseInt(direction));
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        Tile tile = App.getCurrentGame().getTileByIndex(player.getX() + direction1[0],
                player.getY() + direction1[1]);
        if (tile.getPlaceable() != null) {
            return new Result(false, "tile is full");
        }

        CraftingItem item = CraftingItem.findCraftingItemTypeByName(itemName);
        App.getCurrentGame().getCurrentPlayingPlayer().getBackPack().useItem(item.getType());
        tile.setPlaceable(item);
        return new Result(true, "Item placed Successfully.");
    }

    public Result addItem(String itemName, String number) {
        return new Result(false, "t");
    }

    public Result cookingRefrigerator(String mode, String itemName) {
        if (mode.equals("put")) {
            BackPack backPack = App.getCurrentGame().getCurrentPlayingPlayer().getBackPack();

            for (BackPackableType backPackableType : backPack.getBackPackItems().keySet()) {
                if (backPackableType instanceof FoodType foodType) {
                    Food food = (Food) backPack.getBackPackItems().get(foodType).get(0);
                    if (food.getFoodtype().getName().equals(itemName)) {

                    }
                }
            }
        }
        return new Result(false, "ttt");
    }

    public Result cookingShowRecipes() {
        if (App.getCurrentGame().getCurrentPlayingPlayer().getRecipes().isEmpty()) {
            return new Result(false, "you dont have any resipes");
        }
        StringBuilder sb = new StringBuilder();
        for (Recipe recipe : App.getCurrentGame().getCurrentPlayingPlayer().getRecipes()) {
            sb.append(recipe.getFoodToBeCooked().getName()).append(" : ");
            for (Food food : recipe.getIngredients()) {
                sb.append(food.getName()).append(", ");
            }
            sb.append("\n");
        }
        return new Result(true, sb.toString());
    }

    public Result cookingPrepare(String recipeName) {
        if (Recipe.findRecipe(recipeName) == null) {
            return new Result(false, "Recipe not found");
        }
        Recipe recipe = Recipe.findRecipe(recipeName);
        //TODO if player have ingridiant food should be cooked
        App.getCurrentGame().getCurrentPlayingPlayer().setEnergy(App.getCurrentGame().getCurrentPlayingPlayer().getEnergy() - 3);
        return new Result(true, "t");
    }

    public Result eat(String foodName) {
        return new Result(false, "t");
    }

    public Result build(String name, String x, String y) {
        return new Result(false, "t");
    }

    public Result buyAnimal(String animal, String name) {
        return new Result(false, "t");
    }

    public Result pet(String name) {
        return new Result(false, "t");
    }

    public Result setFriendship(String animalName, String amount) {
        return new Result(false, "t");
    }

    public Result animals() {
        return new Result(false, "t");
    }

    public Result shepherdAnimal(String animalName, String x, String y) {
        return new Result(false, "t");
    }

    public Result feedHay(String animalName) {
        return new Result(false, "t");
    }

    public Result produce() {
        return new Result(false, "t");
    }

    public Result collectProduct(String name) {
        return new Result(false, "t");
    }

    public Result sellAnimal(String name) {
        return new Result(false, "t");
    }

    public Result fishing(String fishingPole) {
        return new Result(false, "t");
    }

    public Result artisanUse(String artisanName, String itemNames) {
        // 1. Find artisan tool by name
        CraftingItemType artisan = CraftingItemType.getCraftingItemTypeByName(artisanName);

        if (artisan == null) {
            return new Result(false, "No artisan found with name '%s'".formatted(artisanName));
        }

        // 2. Check ownership of artisan tool
        BackPack playerBackPack = App.getCurrentGame().getCurrentPlayingPlayer().getBackPack();
        if (!playerBackPack.getBackPackItems().containsKey(artisan)) {
            return new Result(false, "You do not own the artisan tool '%s'.".formatted(artisan.getName()));
        }

        // 3. Parse input ingredient names into BackPackableTypes
        String[] tokens = itemNames.trim().split("\\s+");
        Map<BackPackableType, Integer> provided = new HashMap<>();
        for (String token : tokens) {
            Optional<BackPackableType> maybeIngredient = parseBackPackable(token);
            if (maybeIngredient.isEmpty()) {
                return new Result(false, "Ingredient '%s' not recognized.".formatted(token));
            }
            BackPackableType type = maybeIngredient.get();
            provided.put(type, provided.getOrDefault(type, 0) + 1);
        }

        // 4. Try to match an ArtisanProductType with given artisan and ingredients
        for (ArtisanProductType product : ArtisanProductType.values()) {
            if (!product.getArtisan().equals(artisan)) continue;

            Map<Object, Integer> requiredIngredients = product.getIngredients();

            // Check if all required ingredients are present in the provided map
            boolean match = true;
            for (Map.Entry<Object, Integer> entry : requiredIngredients.entrySet()) {
                Object key = entry.getKey();
                int requiredAmount = entry.getValue();
                int providedAmount = 0;

                if (key instanceof BackPackableType type) {
                    providedAmount = provided.getOrDefault(type, 0);
                } else if (key instanceof IngredientGroup group) {
                    for (BackPackableType type : group.getMembers()) {
                        providedAmount += provided.getOrDefault(type, 0);
                    }
                }

                if (providedAmount < requiredAmount) {
                    match = false;
                    break;
                }
            }

            if (!match) continue;

            // 5. Check if player owns enough of each required ingredient
            //TODO: check if is already artisan is producing something
            for (Map.Entry<Object, Integer> entry : requiredIngredients.entrySet()) {
                int required = entry.getValue();

                if (entry.getKey() instanceof BackPackableType type) {
                    int owned = playerBackPack.getBackPackItems()
                            .getOrDefault(type, new ArrayList<>()).size();
                    if (owned < required) {
                        return new Result(false, "Not enough of '%s'.".formatted(type.getName()));
                    }
                } else if (entry.getKey() instanceof IngredientGroup group) {
                    int available = group.countInBackPack(playerBackPack);
                    if (available < required) {
                        return new Result(false, "Not enough items from group '%s'.".formatted(group.name()));
                    }
                }
            }

            // 6. Remove ingredients from backpack
            for (Map.Entry<Object, Integer> entry : requiredIngredients.entrySet()) {
                int amount = entry.getValue();
                if (entry.getKey() instanceof BackPackableType type) {
                    for (int i = 0; i < amount; i++) {
                        playerBackPack.useItem(type);
                    }
                } else if (entry.getKey() instanceof IngredientGroup group) {
                    group.removeFromBackPack(amount, playerBackPack);
                }
            }

            // 7. Start artisan production
            App.getCurrentGame().getCurrentPlayingPlayer()
                    .getArtisanProductsInProgress().add(new ArtisanProduct(product));

            return new Result(true, "'%s' is now being produced.".formatted(product.getName()));
        }

        return new Result(false, "No matching artisan product found for '%s' with given ingredients.".formatted(artisanName));
    }


    private Optional<BackPackableType> parseBackPackable(String name) {
        List<Class<? extends Enum<?>>> enumClasses = List.of(
                FruitType.class,
                FishType.class,
                AnimalProductType.class,
                CraftingItemType.class,
                SeedType.class,
                CropType.class
                //TODO: Add more if needed
        );

        for (Class<? extends Enum<?>> enumClass : enumClasses) {
            for (Enum<?> constant : enumClass.getEnumConstants()) {
                if (constant.name().equalsIgnoreCase(name)) {
                    return Optional.of((BackPackableType) constant);
                }
            }
        }
        return Optional.empty();
    }


    public Result artisanGet(String artisanName) {
        CraftingItemType type = CraftingItemType.getCraftingItemTypeByName(artisanName);
        if (type == null) {
            return new Result(false, "there is no artisan with name %s".formatted(artisanName));
        }

        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        for (ArtisanProduct artisanProduct : player.getArtisanProductsInProgress()) {
            if (artisanProduct.getType().getArtisan().equals(type)) {
                if (artisanProduct.isReady()) {
                    player.getBackPack().addItemToInventory(artisanProduct);
                    return new Result(true, "artisan product added to backpack");
                } else {
                    return new Result(false, "artisan product is not ready.");
                }
            }
        }
        return new Result(false, "artisan with name %s is not producing anything".formatted(artisanName));
    }

    public Result showAllProducts() {
        return new Result(false, "t");
    }

    public Result showAllAvailableProducts() {
        return new Result(false, "t");
    }

    public Result purchase(String productName, String count) {
        return new Result(false, "t");
    }

    public Result addDollars(String count) {
        return new Result(false, "t");
    }

    public Result sellProduct(String productName, String count) {
        return new Result(false, "t");
    }

    public Result friendship() {
        Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
        String result = "";
        for (Player player : currentPlayer.getFriendShips().keySet()) {
            result += "your friendship amount with " + player.getUser().getUsername() + " : " +
                    currentPlayer.getFriendShips().get(player) + "\n" + "your friendship level : "
                    + String.valueOf((int) Math.floor(currentPlayer.getFriendShips().get(player) / 100)) + "\n";
        }
        return new Result(true, result);
    }

    public boolean sideBySide(Player currentPlayer, Player player) {
        int x = currentPlayer.getX();
        int y = currentPlayer.getY();
        int x1 = player.getX();
        int y1 = player.getY();
        if ((x == x1 && y == y1)
                || (x == x1 + 1 && y == y1)
                || (x == x1 - 1 && y == y1)
                || (x == x1 && y == y1 + 1)
                || (x == x1 - 1 && y == y1 + 1)
                || (x == x1 + 1 && y == y1 + 1)
                || (x == x1 && y == y1 - 1)
                || (x == x1 + 1 && y == y1 - 1)
                || (x == x1 - 1 && y == y1 - 1)) {
            return true;
        } else return false;

    }

    public Result talk(String username, String massage) {
        Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
        for (Player player : App.getCurrentGame().getPlayers()) {
            if (player.getUser().getUsername().equals(username)) {
                if (currentPlayer.getTalk().get(player) != null) {
                    if (sideBySide(currentPlayer, player)) {
                        currentPlayer.getTalk().get(player).addTalk("you" + " : " + massage + "\n");
                        player.getTalk().get(currentPlayer).addTalk(currentPlayer.getUser().getUsername()
                                + " : " + massage + "\n");
                        player.addFriendShips(currentPlayer, player.getFriendShips().get(currentPlayer) + 20);
                        currentPlayer.addFriendShips(player, currentPlayer.getFriendShips().get(player) + 20);
                        return new Result(true, "your message sent to " + player.getUser().getUsername());
                    } else {
                        return new Result(false, "you can't talk from this distance");
                    }
                } else {
                    return new Result(false, "there isn't player in this game with this username");
                }
            }
        }
        return new Result(false, "there isn't player in this game with this username");
    }

    public Result talkHistory(String username) {
        if (App.getUserWithUsername(username) == null) {
            return new Result(false, "there isn't player in this game with this username");
        }
        for (Player player : App.getCurrentGame().getPlayers()) {
            if (player.getUser().getUsername().equals(username)) {
                if (App.getCurrentGame().getCurrentPlayingPlayer().getTalk().get(player) != null) {
                    return new Result(true,
                            App.getCurrentGame().getCurrentPlayingPlayer().getTalk().get(player).getTalk());
                }
            }
        }
        return new Result(false, "there isn't player in this game with this username");
    }

    public Result gift(String username, String item, String amount) {
        int Amount = Integer.parseInt(amount);
        Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
        if (App.getUserWithUsername(username) == null) {
            return new Result(false, "there isn't player in this game with this username");
        }
        for (Player player : App.getCurrentGame().getPlayers()) {
            if (player.getUser().getUsername().equals(username)) {
                if (currentPlayer.getFriendShips().get(player) != null) {
                    if (currentPlayer.getFriendShips().get(player) < 100) {
                        return new Result(false, "your level is less than 1");
                    } else {
                        //TODO
                    }
                }
            }
        }
        return new Result(false, "there isn't player in this game with this username");
    }

    public Result giftList() {
        return new Result(false, "t");
    }

    public Result giftRate(String giftNumber, String rate) {
        return new Result(false, "t");
    }

    public Result giftHistory(String username) {
        return new Result(false, "t");
    }

    public Result hug(String username) {
        if (App.getUserWithUsername(username) == null) {
            return new Result(false, "there isn't player in this game with this username");
        }
        if (App.getCurrentGame().getCurrentPlayingPlayer().getUser().getUsername().equals(username)) {
            return new Result(false, "you can't hug yourself");
        }
        for (Player player : App.getCurrentGame().getPlayers()) {
            if (player.getUser().getUsername().equals(username)) {
                if (sideBySide(player, App.getCurrentGame().getCurrentPlayingPlayer())) {
                    App.getCurrentGame().getCurrentPlayingPlayer().getFriendShips().put(
                            player, (App.getCurrentGame().getCurrentPlayingPlayer().getFriendShips().get(player) + 60));
                    player.getFriendShips().put(App.getCurrentGame().getCurrentPlayingPlayer(),
                            App.getCurrentGame().getCurrentPlayingPlayer().getFriendShips().get(player));
                    return new Result(true, "you hug your friend ^^");
                } else {
                    return new Result(false, "you can't hug your friend from this distance");
                }
            }
        }
        return new Result(false, "t");
    }

    public Result flower(String username) {
        return new Result(false, "t");
    }

    public Result askMarriage(String username, String marriage) {
        return new Result(false, "t");
    }

    public Result respond(String username) {
        return new Result(false, "t");
    }

    public Result startTrade() {
        return new Result(false, "t");
    }

    public Result trade(String username, String type, String amount, String price, String targetItem) {
        return new Result(false, "t");
    }

    public Result tradeResponse(String acceptance, String id) {
        return new Result(false, "t");
    }

    public Result tradeHistory() {
        return new Result(false, "t");
    }

    public Result tradeList() {
        return new Result(false, "t");
    }

    public Result meetNPC(String npcName) {
        return new Result(false, "t");
    }

    public Result giftNPC(String NPCName, String item) {
        return new Result(false, "t");
    }

    public Result friendshipNPCList() {
        return new Result(false, "t");
    }

    public Result questsList() {
        return new Result(false, "t");
    }

    public Result questFinish(String index) {
        return new Result(false, "t");
    }


}
