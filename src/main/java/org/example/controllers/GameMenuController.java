package org.example.controllers;

import org.example.display;
import org.example.models.*;
import org.example.models.artisan.ArtisanProduct;
import org.example.models.artisan.ArtisanProductType;
import org.example.models.artisan.IngredientGroup;
import org.example.models.NPCS.NPC;
import org.example.models.NPCS.Quest;
import org.example.models.crafting.CraftingItem;
import org.example.models.crafting.CraftingItemType;
import org.example.models.cooking.Food;
import org.example.models.cooking.FoodType;
import org.example.models.cooking.Recipe;
import org.example.models.enums.*;
import org.example.models.foraging.ForagingController;
import org.example.models.foraging.Mineral;
import org.example.models.foraging.MineralType;
import org.example.models.map.AnimalPlace;
import org.example.models.map.GreenHouse;
import org.example.models.map.Tile;
import org.example.models.plant.*;
import org.example.models.tools.BackPack;
import org.example.models.tools.FishingPoleType;
import org.example.models.tools.Tool;
import org.example.models.Trade;
import org.example.models.tools.ToolType;
import org.example.models.market.*;
import org.example.models.trade.Fish;
import org.example.models.trade.Trade;

import java.util.*;
import java.util.regex.Matcher;

import static org.example.models.enums.WeatherType.*;

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
        Tile.getTiles().clear();
        NPC.setFatherPlayer(null);
        NPC.setFatherUser(null);
        GreenHouse.getGreenHouse().clear();
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
        if (player.getBackPack().getCoin() >= 1000 && player.getBackPack().getBackPackItems().get(NormalItemType.Wood).size() >= 500) {
            player.getBackPack().addCoin(-1000);

            for (int i = 0; i < 500; i++)
                player.getBackPack().useItem(NormalItemType.Wood);

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
        if (!(destination.getOwner().equals(player.getPartner())
                || destination.getOwner().equals(player)
                || destination.getOwner().equals(NPC.getFatherPlayer()))) {
            return new Result(false, "you can't walk to this tile because this tile is not for you.");
        } else if (!destination.isWalkAble()) {
            return new Result(false, "you can't walk to this tile because this tile is not walkable.");
        } else if ((result = aStar(player.getX(), player.getY(), x, y, player)) == null) {
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
                    player.passOut();
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
    public List<Tile> aStar(int startX, int startY, int endX, int endY, Player player) {
        int[][] directions = {
                {0, 1}, {1, 0}, {0, -1}, {-1, 0},
                {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
        };

        Tile start = Tile.getTile(startX, startY);
        Tile end = Tile.getTile(endX, endY);

        if (start == null || end == null || !start.isWalkAble() || !end.isWalkAble()) {
            return null;
        }

        Map<Tile, Tile> parent = new HashMap<>();
        Map<Tile, Integer> gScore = new HashMap<>();
        PriorityQueue<Tile> openSet = new PriorityQueue<>(Comparator.comparingInt(tile -> gScore.get(tile) + heuristic(tile, end)));
        boolean[][] visited = new boolean[200][200];

        gScore.put(start, 0);
        openSet.add(start);

        while (!openSet.isEmpty()) {
            Tile current = openSet.poll();

            if (current.getX() == endX && current.getY() == endY) {
                return buildPath(parent, start, end);
            }

            visited[current.getX()][current.getY()] = true;

            for (int[] dir : directions) {
                int newX = current.getX() + dir[0];
                int newY = current.getY() + dir[1];

                if (newX <= 0 || newY <= 0 || newX > 200 || newY > 200) continue;
                Tile neighbor = Tile.getTile(newX, newY);

                if (neighbor == null || visited[newX][newY] || !isValid(neighbor, player)) continue;

                int tentativeG = gScore.get(current) + 1;

                if (!gScore.containsKey(neighbor) || tentativeG < gScore.get(neighbor)) {
                    gScore.put(neighbor, tentativeG);
                    parent.put(neighbor, current);
                    openSet.add(neighbor);
                }
            }
        }

        return null;
    }
    private boolean isValid(Tile tile, Player player) {
        return tile != null && tile.isWalkAble() &&
                (tile.getOwner().equals(player) ||
                        tile.getOwner().equals(player.getPartner()) ||
                        tile.getOwner().equals(NPC.getFatherPlayer()));
    }

    private List<Tile> buildPath(Map<Tile, Tile> parent, Tile start, Tile end) {
        List<Tile> path = new LinkedList<>();
        Tile current = end;
        while (current != null && !current.equals(start)) {
            path.add(0, current);
            current = parent.get(current);
        }
        if (current != null) path.add(0, start);
        return path;
    }

    private int heuristic(Tile a, Tile b) {
        int dx = Math.abs(a.getX() - b.getX());
        int dy = Math.abs(a.getY() - b.getY());
        return dx + dy;
    }

    public void printMap(int x, int y, int size) {
        display.run(x, y, size);
    }

    public void helpReadingMap() {
        display.helpReadingMap();
    }

    public Result energyShow() {
        return new Result(true, "%.2f".formatted(
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
        return new Result(true, "Energy successfully set to %.2f".formatted(energy));
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

                App.getCurrentGame().getCurrentPlayingPlayer().getBackPack().addCoin(refund);
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
            if (tile.getPlaceable() instanceof Mineral mineral) {
                if (!ForagingController.canBreakMineral(player.getCurrentTool().getMaterial(),
                        mineral.getType()))
                    return new Result(false, "this type of pichaxe cannot break this mineral");

                player.getAbilities().increaseMiningAbility(10);
                if (mineral.isForaging())
                    player.getAbilities().increaseForagingAbility(10);

                player.getBackPack().addItemToInventory(mineral);
                tile.setPlaceable(null);
                return new Result(true, "stone broke successfully");
                if (player.getAbilities().getMiningLevel() >= 2) {
                    player.getBackPack().addItemToInventory(mineral);
                    return new Result(true, "stone broke successfully and you also got 1 more because of mining level");
                }
                return new Result(true, "stone breaked successfully");

            } else if (tile.isPlowed()) {
                tile.setPlowed(false);
            } //TODO: destroying items of player on the floor

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
                        player.getBackPack().addItemToInventory(new Mineral(MineralType.Coal, false));
                    }
                    tree.setFullyGrown(false);
                    player.getBackPack().addItemToInventory(
                            new Fruit(tree.getType().getFruitType())
                    );
                    tree.setFullyGrown(false);
                    if (tree.isForaging())
                        player.getAbilities().increaseForagingAbility(5);
                } else if (plant instanceof Crop crop) {
                    if (crop.isFullyGrown()) {
                        //TODO: chandbar bardasht
                        player.getBackPack().addItemToInventory(crop);
                        crop.getTile().setPlaceable(null);
                    }
                }
            }
        } else if (tool.getToolType().equals(ToolType.MilkPail)) {
            if (tile.getPlaceable() instanceof Animal) {
                Animal animal = (Animal) tile.getPlaceable();
                if(animal.getAnimalType().equals(AnimalType.Cow)){
                    for(AnimalProduct animalProduct : animal.getAnimalProducts()){
                        player.getBackPack().addItemToInventory(animalProduct);
                    }
                }
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

    public Result plantSeed(String source, String direction) {
        int[] directions = App.handleDirection(Integer.parseInt(direction));
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        SeedType seedType = SeedType.getSeedTypeByName(source);
        SaplingType saplingType = SaplingType.getTypeByName(source);

        int newX = player.getX() + directions[0];
        int newY = player.getY() + directions[1];

        Tile tile = App.getCurrentGame().getTileByIndex(newX, newY);
        if (tile.getPlaceable() != null && !(tile.getPlaceable() instanceof GreenHouse))
            return new Result(false, "Specified tile is already occupied");

        if (!tile.isPlowed())
            return new Result(false, "The Specified tile is not Plowed");

        else if (seedType == null && saplingType == null)
            return new Result(false, "No SeedType with this Name");

        if (seedType != null) {
            if (player.getBackPack().getBackPackItems().get(seedType) == null ||
                    player.getBackPack().getBackPackItems().get(seedType).isEmpty())
                return new Result(false, "You do not have any seed of this type");

            player.getBackPack().useItem(seedType);
            if (tile.getPlaceable() instanceof GreenHouse)
                tile.setPlaceable(new Crop(false, seedType.getCropType(), false, tile, true));
            else if (tile.getPlaceable() == null)
                tile.setPlaceable(new Crop(false, seedType.getCropType(), false, tile, false));

            return new Result(true,
                    "Successfully planted a plant of type %s in (%d,%d)".formatted(
                            seedType.name(), newX, newY
                    ));
        }
        //if saplingType != null
        if (player.getBackPack().getBackPackItems().get(saplingType) == null ||
                player.getBackPack().getBackPackItems().get(saplingType).isEmpty())
            return new Result(false, "You do not have any sapling of this type");

        player.getBackPack().useItem(saplingType);
        if (tile.getPlaceable() instanceof GreenHouse)
            tile.setPlaceable(new Tree(false, saplingType.getTreeType(), false, tile, true));
        else if (tile.getPlaceable() == null)
            tile.setPlaceable(new Tree(false, saplingType.getTreeType(), false, tile, false));

        return new Result(true,
                "Successfully planted a plant of type %s in (%d,%d)".formatted(
                        saplingType.name(), newX, newY
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
                    Fertilizer:""".formatted(tree.getType().name(), tree.getDaysTillFullGrowth(), tree.getCurrentStageIndex() + 1));
        } else if (tile.getPlaceable() instanceof Crop crop) {
            return new Result(true, """
                    Name: %s
                    Days Left Till Full Growth: %d
                    Current Stage: %d
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
        App.getCurrentGame().getCurrentPlayingPlayer().getBackPack().useItem(item.getType().getName());
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

        try{
            App.getCurrentGame().getCurrentPlayingPlayer().getBackPack().useItem(foodName);
            return new Result(true, "you ate " + foodName + " successfully");
        }catch (Exception e){
            return new Result(false,"invalid food");
        }
    }

    public Result build(String name, String x, String y) {
        AnimalPlaceType animalPlaceType;
        try{
            animalPlaceType = AnimalPlaceType.valueOf(name);
        }catch(Exception e){
            return new Result(false, "Invalid place");
        }
        AnimalPlace animalPlace = new AnimalPlace(animalPlaceType);
        int xint=Integer.parseInt(x);
        int yint=Integer.parseInt(y);
        for(int i=-2;i<2;i++){
            for(int j=-2;j<2;j++){
                Tile tile = Tile.getTile(xint + i,yint+j);
                if(tile==null){
                    return new Result(false,"Tile not found");
                }
                if(tile.getPlaceable() != null){
                    return new Result(false,"this area is not empty for building ");
                }
            }
        }
        for(int i=-2;i<2;i++){
            for(int j=-2;j<2;j++){
                Tile tile = Tile.getTile(xint + i,yint+j);
                tile.setPlaceable(animalPlace);
            }
        }
        return new Result(true,"build successfully");
    }

    public Result buyAnimal(String animal, String name) {
        AnimalType animalType;
        try{
            animalType=AnimalType.valueOf(animal);
        }catch (Exception e){
            return new Result(false, "Invalid animal");
        }
        Animal animal1=new Animal(name,animalType);

        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        for(AnimalPlace animalPlace : player.getPlayerMap().getFarm().getAnimalPlaces()){
            if(animal1.getAnimalType().getAnimalPlaceTypes().contains(animalPlace.getAnimalPlaceType())){
                if(animalPlace.getCapacity()==0){
                    List<AnimalPlace> list = player.getPlayerMap().getFarm().getAnimalPlaces();
                    if(animalPlace.equals(player.getPlayerMap().getFarm().getAnimalPlaces().get(list.size()-1))){
                        return new Result(false,"no valid AnimalPlace with enough space");
                    }
                    continue;
                }
                animalPlace.addAnimal(animal1);
                player.getPlayerMap().getFarm().getAnimals().add(animal1);
                return new Result(true,name + " added to your animal successfully");
            }
        }
        return new Result(false,"no suitable AnimalPlace for " + name);
    }

    public Result pet(String name) {
        if(Animal.findAnimalByName(name) == null){
            return new Result(false,"no animal with name : " + name);
        }
        Animal animal = Animal.findAnimalByName(name);
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        Tile tile = Tile.getTile(player.getX(),player.getY());
        if(Tile.findAround(animal) ==null){
            return new Result(false,"you should stand next to " + name + " to pet it");
        }
        animal.setFriendship(animal.getFriendship()+15);
        return new Result(true,name + " petted successfully");

    }

    public Result setFriendship(String animalName, String amount) {
        if(Animal.findAnimalByName(animalName) == null){
            return new Result(false,"animal not found");
        }
        Animal animal = Animal.findAnimalByName(animalName);
        int amountInt = Integer.parseInt(amount);
        animal.setFriendship(animal.getFriendship()+amountInt);
        return new Result(true,"friendship is now " + animal.getFriendship());
    }

    public Result animals() {
    StringBuilder sb = new StringBuilder();
    for(Animal animal : App.getCurrentGame().getCurrentPlayingPlayer().getPlayerMap().getFarm().getAnimals()){
        sb.append(animal.getName()).append(" (").append(animal.getAnimalType()).append(") ").append("\n")
                .append("friendship : ").append(animal.getFriendship()).append("\n")
                .append(animal.isPettedToday()?"petted today" : "not petted today").append("\n")
                .append(animal.isFedToday()?"feded today" : "not fed today");
    }
    return new Result(true,sb.toString());
    }

    public Result shepherdAnimal(String animalName, String x, String y) {
        return new Result(false, "t");
    }

    public Result feedHay(String animalName) {
        if(Animal.findAnimalByName(animalName) == null){
            return new Result(false,"no animal with name : " + animalName);
        }
        Animal animal = Animal.findAnimalByName(animalName);
        //TODO enough hay?
        if(animal.isFedToday()){
            return new Result(false,"already fed today");
        }
        animal.setFedToday(true);
        return new Result(true,animal.getName() + " feded seccessfully");
    }

    public Result produce() {
        StringBuilder sb = new StringBuilder();
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        for(Animal animal :player.getPlayerMap().getFarm().getAnimals()){
            if(!animal.getAnimalProducts().isEmpty()){
                sb.append(animal.getName()).append("\n");
                for(AnimalProduct animalProduct : animal.getAnimalProducts()){
                    sb.append(animalProduct.getAnimalProductType()).append("\n")
                            .append("quantity : ").append(animalProduct.getCount()).append("\n")
                            .append("quality : ").append(animalProduct.getShippingBinType().name()).append("\n");
                }
            }
        }
        return new Result(true,sb.toString());
    }

    public Result collectProduct(String name) {
        if(Animal.findAnimalByName(name) == null){
            return new Result(false,"animal not found");
        }
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        if(player.getBackPack().isBackPackFull()){
            return new Result(false,"your backpack is full");
        }
        //TODO  im confused
    }

    public Result sellAnimal(String name) {
        if(Animal.findAnimalByName(name) == null){
            return new Result(false,"animal not found");
        }
        Animal animal = Animal.findAnimalByName(name);
        animal.sell();
        return new Result(true,"animal sold successfully");
    }

    public Result fishing(String fishingPole) {
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        FishingPoleType fishingPoleType;
        try{
             fishingPoleType = FishingPoleType.valueOf(fishingPole);
        }catch (Exception e){
            return new Result(false,"invalid fishing pole");
        }
        if(!player.getBackPack().getBackPackItems().containsKey(fishingPoleType)){
            return new Result(false,"you dont have this fishing pole in your backpack");
        }

        double R = Math.random();
        double M=1;
        TimeAndDate date = App.getCurrentGame().getDate();
        switch (date.getTodayWeatherType()){
            case Sunny -> M=1.5;
            case Rainy -> M=1.2;
            case Storm -> M=0.5;
            default -> M=0.1;
        }
        int level=player.getAbilities().getFishingLevel();
        int count = (int) Math.ceil(R * M * (level + 2));
        count = Math.min(6,count);
        double pole = fishingPoleType.getPole();
        Fish fish=new Fish();
        ArrayList<FishType> fishes = new ArrayList<>();
        for(FishType fishType : FishType.values()){
            if(fishType.getSeason().equals(date.getSeason())){
                fishes.add(fishType);
            }
        }
        Random rand = new Random();
        FishType randomElement = fishes.get(rand.nextInt(fishes.size()));
        if(player.getBackPack().isBackPackFull()){
            return new Result(false,"your backpack is full");
        }
        fish.setFishType(randomElement);
        player.getBackPack().addItemToInventory(fish);
        return new Result(true,"fish got cought successfully");
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
                CropType.class,
                NormalItemType.class
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
        //check is the player in store
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        Tile tile = App.getCurrentGame().getTileByIndex(player.getX(), player.getY());
        if (tile.getPlaceable() instanceof Store store)
            return new Result(true, App.getCurrentGame().getStoreManager().showAllProducts(store));
        return new Result(false, "You are not in a store");
    }


    public Result showAllAvailableProducts() {
        //check is the player in store
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        Tile tile = App.getCurrentGame().getTileByIndex(player.getX(), player.getY());
        if (tile.getPlaceable() instanceof Store store)
            return new Result(true, App.getCurrentGame().getStoreManager().showAllAvailableProducts(store));
        return new Result(false, "You are not in a store");
    }


    public Result purchase(String productName, String count) {
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        StoreType storeType;
        if (App.getCurrentGame().getTileByIndex(player.getX(), player.getY()).getPlaceable() instanceof Store store) {
            storeType = store.getType();
        } else
            return new Result(false, "The Player is not in a store");

        ShopItem product = null;
        for (ShopItem shopItem : App.getCurrentGame().getStoreManager().getInventory(storeType).getItems()) {
            if (shopItem.getName().equals(productName)) {
                product = shopItem;
            }
        }
        if (product == null)
            return new Result(false, "no product with name %s in store %s".formatted(
                    productName, storeType.name()
            ));

        int count1;

        if (count == null)
            count1 = 1;
        else
            count1 = Integer.parseInt(count);

        int availableCount = product.getDailyLimit() - product.getSoldToday();
        if (availableCount < count1)
            return new Result(false, "only %d left today".formatted(availableCount));

        double price = product.getPrice() * count1;
        if (player.getBackPack().getCoin() < price)
            return new Result(false, "you have only %.2f dollars left(not enough money)".formatted(
                    player.getBackPack().getCoin()
            ));

        //purchasing
        product.setSoldToday(product.getSoldToday() + count1);
        player.getBackPack().addcoin(-price);
        for (int i = 0; i < count1; i++)
            player.getBackPack().addItemToInventory(product);

        return new Result(false, "purhcased successfully");
    }


    public Result cheatAddDollars(String count) {
        double amount;
        try {
            amount = Double.parseDouble(count);
        } catch (Exception e) {
            return new Result(false, "Amount must be number.");
        }

        App.getCurrentGame().getCurrentPlayingPlayer().getBackPack().addCoin(amount);
        return new Result(false, "Your new Balance: %.1f".formatted(
                App.getCurrentGame().getCurrentPlayingPlayer().getBackPack().getCoin()
        ));
    }

    public Result sellProduct(String productName, String count) {
        //TODO: find out what does the first error in the document means
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();

        int[] dx = {0, 1, 1, 1, 0, -1, -1, -1};
        int[] dy = {1, 1, 0, -1, -1, -1, 0, 1};

        ShippingBin bin = null;
        for (int i = 0; i < 8; i++) {
            if (App.getCurrentGame().getTileByIndex((player.getX() + dx[i]), (player.getY() + dy[i])).getPlaceable()
                    instanceof ShippingBin shippingBin) {
                if (shippingBin.getTodayItemOwner() != null) {
                    if (!shippingBin.getTodayItemOwner().equals(player))
                        return new Result(false, "this shipping bin is already used by %s today.".formatted(
                                shippingBin.getTodayItemOwner().getUser().getUsername()) +
                                "you may be able to use it tomorrow.");
                }
                bin = shippingBin;
            }
        }
        if (bin == null)
            return new Result(false, "you are not near a shipping bin");

        Optional<BackPackableType> productType = parseBackPackable(productName);
        if (productType.isEmpty())
            return new Result(false, "no product type found with name %s".formatted(productName));

        int count1;
        if (count == null)
            count1 = 1;
        else
            count1 = Integer.parseInt(count);

        if (player.getBackPack().getBackPackItems().get(productType) == null)
            return new Result(false, "you do not have item of type %s".formatted(productType.get().getName()));

        int availableCount = player.getBackPack().getBackPackItems().get(productType).size();
        if (availableCount < count1)
            return new Result(false, "not enough count: you only have %d of this item".formatted(availableCount));

        bin.addItems(productType.get(), count1, player);
        for (int i = 0; i < count1; i++) {
            player.getBackPack().useItem(productType.get());
        }

        return new Result(true, "sold successfully");
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
                        message message = new message(currentPlayer, massage);
                        player.addMessage(message);
                        if (player.getPartner().equals(currentPlayer) && !player.isInteractionWithPartner()) {
                            player.setEnergy(player.getEnergy() + 50);
                            currentPlayer.setEnergy(currentPlayer.getEnergy() + 50);
                        }
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
                    if (sideBySide(currentPlayer, player)) {
                        if (currentPlayer.getFriendShips().get(player) < 100) {
                            return new Result(false, "your level is less than 1");
                        } else {
                            if (currentPlayer.getBackPack().getInventorySize(item) < Amount) {
                                return new Result(false, "insufficient inventory");
                            } else {
                                for (int i = 0; i < Amount; i++) {
                                    BackPackable backPackable = currentPlayer.getBackPack().useItem(item);
                                    player.getBackPack().addItemToInventory(backPackable);
                                }
                                Gift gift = new Gift(currentPlayer, player, item, Amount);
                                currentPlayer.getGifts().get(player).add(gift);
                                player.getGifts().get(currentPlayer).add(gift);
                                if (player.getPartner().equals(currentPlayer) && !player.isInteractionWithPartner()) {
                                    player.setEnergy(player.getEnergy() + 50);
                                    currentPlayer.setEnergy(currentPlayer.getEnergy() + 50);
                                }
                                return new Result(true, "your gift was received by " + player.getUser().getUsername()
                                        + "\n" + player.getUser().getUsername() + ", you have received a gift from " + currentPlayer.getUser().getUsername()
                                        + "\n" + "your gift : " + item + "\n" + "your gift amount : " + amount + "\n"
                                        + "please rate this gift between one and five Whenever you have time ");
                            }
                        }
                    } else {
                        return new Result(false, "you can't gift from this distance");
                    }
                }
            }
        }
        return new Result(false, "there isn't player in this game with this username");
    }

    public Result giftList() {
        Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
        String result = "";
        for (Player player : currentPlayer.getGifts().keySet()) {
            result += player.getUser().getUsername() + "\n";
            for (Gift gift : currentPlayer.getGifts().get(player)) {
                if (currentPlayer.equals(gift.getPlayerWhoGetGift())) {
                    result += gift.getItem() + " : " + gift.getAmount() + " ---> " + gift.getGiftNumber() + "\n";
                }
            }
        }
        return new Result(true, result);
    }

    public Result giftRate(String giftNumber, String rate) {
        if (GameMenuCommands.Int.getMatcher(rate) == null || GameMenuCommands.Int.getMatcher(giftNumber) == null) {
            return new Result(true, "your rate or giftNumber is not valid");
        } else if (Integer.parseInt(rate) > 5 || Integer.parseInt(rate) < 1) {
            return new Result(true, "your rate is not valid");
        } else {
            Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
            for (Player player : currentPlayer.getGifts().keySet()) {
                for (Gift gift : currentPlayer.getGifts().get(player)) {
                    if (gift.getGiftNumber() == Integer.parseInt(giftNumber) && gift.getPlayerWhoGetGift() == currentPlayer) {
                        if (!gift.getRateGiven()) {
                            gift.setRateGiven(true);
                            currentPlayer.getFriendShips().put(player, currentPlayer.getFriendShips().get(player) + (Integer.parseInt(rate) - 3) * 30 + 15);
                            player.getFriendShips().put(currentPlayer, currentPlayer.getFriendShips().get(player));
                            return new Result(true, "your rate was recorded as " + Integer.parseInt(rate));
                        } else {
                            return new Result(true, "you rate to this gift previously");
                        }
                    }
                }
            }


        }
        return new Result(false, "you have not received a gift with this giftNumber");
    }

    public Result giftHistory(String username) {
        Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
        for (Player player : App.getCurrentGame().getPlayers()) {
            if (player.getUser().getUsername().equals(username)) {
                String result = "";
                result += player.getUser().getUsername() + "\n";
                for (Gift gift : currentPlayer.getGifts().get(player)) {
                    result += "whoGetGift : " + gift.getPlayerWhoGetGift() + "\n" + gift.getItem() + " : " + gift.getAmount() + " ---> " + gift.getGiftNumber() + "\n";
                }
                return new Result(true, result);
            }
        }
        return new Result(false, "this username there is not in this game");

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
                    if (player.getPartner().equals(App.getCurrentGame().getCurrentPlayingPlayer()) && !player.isInteractionWithPartner()) {
                        player.setEnergy(player.getEnergy() + 50);
                        App.getCurrentGame().getCurrentPlayingPlayer().setEnergy(App.getCurrentGame()
                                .getCurrentPlayingPlayer().getEnergy() + 50);
                    }
                    return new Result(true, "you hug your friend ^^");
                } else {
                    return new Result(false, "you can't hug your friend from this distance");
                }
            }
        }
        return new Result(false, "this username does not exist in this game");
    }

    public Result flower(String username) {
        Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
        for (Player player : App.getCurrentGame().getPlayers()) {
            if (player.getUser().getUsername().equals(username)) {
                if (currentPlayer.getFriendShips().containsKey(player)) {
                    if (currentPlayer.getFriendShips().get(player) < 200) {
                        return new Result(false, "your friendship level is less than 2");
                    } else {
                        if (currentPlayer.getBackPack().getInventorySize("FLOWER") > 0) {
                            if (currentPlayer.getFriendShips().get(player) < 300) {
                                currentPlayer.getFriendShips().put(player, 300);
                                player.getFriendShips().put(currentPlayer, 300);
                                BackPackable b = currentPlayer.getBackPack().useItem("FLOWER");
                                player.getBackPack().addItemToInventory(b);
                                return new Result(true, "Flower were presented to " + player.getUser().getUsername());
                            } else {
                                BackPackable b = currentPlayer.getBackPack().useItem("FLOWER");
                                player.getBackPack().addItemToInventory(b);
                                if (player.getPartner().equals(currentPlayer) && !player.isInteractionWithPartner()) {
                                    player.setEnergy(player.getEnergy() + 50);
                                    currentPlayer.setEnergy(currentPlayer.getEnergy() + 50);
                                }
                                return new Result(true, "Flower were presented to " + player.getUser().getUsername());
                            }
                        } else {
                            return new Result(false, "insufficient inventory");
                        }

                    }
                }
                return new Result(false, "you can't give flower to your self");
            }
        }
        return new Result(false, "this username does not exist in this game");
    }

    public Result askMarriage(String username, String ring) {
        Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
        for (Player player : App.getCurrentGame().getPlayers()) {
            if (player.getUser().getUsername().equals(username)) {
                if (sideBySide(player, currentPlayer)) {
                    if (currentPlayer.getFriendShips().containsKey(player)) {
                        if (currentPlayer.getFriendShips().get(player) < 300) {
                            return new Result(false, "your friendship level is less than 3");
                        } else {
                            if (currentPlayer.getUser().getGender().equals(Gender.Female)) {
                                return new Result(false, "you can't ask marriage");
                            } else if (player.getUser().getGender() == currentPlayer.getUser().getGender()) {
                                return new Result(false, "khejalat bekesh dadash (abjy)");
                            } else if (currentPlayer.getBackPack().getInventorySize(ring) < 1) {
                                return new Result(false, "you have not this Ring for ask marriage");
                            } else {
                                message message = new message(currentPlayer, "ask for marriage with " + ring);
                                return new Result(true, "your marriage request has been sent");
                            }
                        }
                    } else {
                        return new Result(false, "you can't ask marriage to your self");
                    }
                } else {
                    return new Result(false, "you can't ask marriage from this distance");
                }
            }
        }
        return new Result(false, "this username does not exist in this game");
    }

    public Result respond(String accept, String username) {
        Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
        for (message m : currentPlayer.getMessage()) {
            if (m.getMessage().startsWith("ask for marriage with ")) {
                for (Player player : App.getCurrentGame().getPlayers()) {
                    if (player.getUser().getUsername().equals(username)) {
                        if (m.getSender().equals(player)) {
                            if (accept.trim().equals("accept")) {
                                BackPackable b = player.getBackPack().useItem(m.getMessage().substring(22));
                                currentPlayer.getBackPack().addItemToInventory(b);
                                ArrayList<message> temp = new ArrayList<message>();
                                for (message message : player.getMessage()) {
                                    if (m.getMessage().startsWith("ask for marriage with ")) {
                                        temp.add(message);
                                    }
                                }
                                for (message message : temp) {
                                    player.getMessage().remove(message);
                                }
                                if (player.getFriendShips().get(currentPlayer) < 400) {
                                    player.getFriendShips().put(currentPlayer, 400);
                                    currentPlayer.getFriendShips().put(player, 400);
                                }
                                player.getBackPack().addCoin(currentPlayer.getBackPack().getCoin());
                                currentPlayer.getBackPack().addCoin(player.getBackPack().getCoin());
                                player.setPartner(currentPlayer);
                                currentPlayer.setPartner(player);
                                return new Result(true, "Congratulations, you got married");
                            } else {
                                player.setIsbrokenUp(7);
                                player.getFriendShips().put(currentPlayer, 0);
                                currentPlayer.getFriendShips().put(player, 0);
                                ArrayList<message> temp = new ArrayList<message>();
                                for (message message : player.getMessage()) {
                                    if (m.getMessage().startsWith("ask for marriage with ")) {
                                        temp.add(message);
                                    }
                                }
                                for (message message : temp) {
                                    player.getMessage().remove(message);
                                }
                                return new Result(true, "request was rejected");
                            }
                        } else {
                            return new Result(false, "this username did not request marriage to you");
                        }
                    }
                }
                return new Result(false, "this username does not exist in this game");
            }
        }

        return new Result(false, "this username does not exist in this game");
    }

    public Result startTrade() {
        String result = "";
        Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
        for (message m : currentPlayer.getMessage()) {
            if (m.getMessage().startsWith("you have a trade")) {
                result += (m.getMessage() + "\n");
            }
        }
        return new Result(false, "you are now in trade menu \nlist of players : \n"
                + App.getCurrentGame().getPlayers().get(1).getUser().getUsername() + "\n"
                + App.getCurrentGame().getPlayers().get(2).getUser().getUsername() + "\n"
                + App.getCurrentGame().getPlayers().get(3).getUser().getUsername() + "\nnew trade request or offer : \n"
                + result);
    }

    public Result tradeByMoney(Matcher matcher, String type, int enable) {
        Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
        if (type.trim().equals("offer")) {
            for (Player player : App.getCurrentGame().getPlayers()) {
                if (player.getUser().getUsername().equals(matcher.group("username"))) {
                    String item = matcher.group("item");
                    int amount = Integer.parseInt(matcher.group("amount"));
                    double price = Float.parseFloat(matcher.group("price"));
                    if (currentPlayer.getBackPack().getInventorySize(item) >= amount) {
                        if (enable == 0) {
                            Trade trade = new Trade(currentPlayer, type, item, amount, price, "", 0, "byMoney", matcher);
                            message message = new message(currentPlayer, "you have a trade offer from "
                                    + currentPlayer.getUser().getUsername());
                            player.addMessage(message);
                            player.addTrades(trade);
                            currentPlayer.addTrades(trade);
                            return new Result(true, "yor offer for trade has been registered");
                        } else if (enable == 1) {
                            player.getBackPack().addcoin(-1 * price);
                            currentPlayer.getBackPack().addcoin(price);
                            for (int i = 0; i < amount; i++) {
                                BackPackable b = currentPlayer.getBackPack().useItem(item);
                                player.getBackPack().addItemToInventory(b);
                            }
                        }
                    } else {
                        return new Result(false, "you have not enough items in your inventory");
                    }
                }
            }
            return new Result(false, "this username does not exist in this game");
        } else {
            for (Player player : App.getCurrentGame().getPlayers()) {
                if (player.getUser().getUsername().equals(matcher.group("username"))) {
                    String item = matcher.group("item");
                    int amount = Integer.parseInt(matcher.group("amount"));
                    double price = Float.parseFloat(matcher.group("price"));
                    if (currentPlayer.getBackPack().getCoin() < price) {
                        return new Result(false, "you have not enough coins");
                    }
                    if (player.getBackPack().getInventorySize(item) >= amount) {
                        if (enable == 0) {
                            Trade trade = new Trade(currentPlayer, type, item, amount, price, "", 0, "byMoney", matcher);
                            message message = new message(currentPlayer, "you have a trade offer from "
                                    + currentPlayer.getUser().getUsername());
                            player.addMessage(message);
                            player.addTrades(trade);
                            currentPlayer.addTrades(trade);
                            return new Result(true, "yor request for trade has been registered");
                        } else if (enable == 1) {
                            player.getBackPack().addcoin(price);
                            currentPlayer.getBackPack().addcoin(-1 * price);
                            for (int i = 0; i < amount; i++) {
                                BackPackable b = player.getBackPack().useItem(item);
                                currentPlayer.getBackPack().addItemToInventory(b);
                            }

                        }
                    } else {
                        return new Result(false, "this player have not enough items in her/his inventory");
                    }
                }
            }
            return new Result(false, "this username does not exist in this game");
        }

    }

    public Result tradeByItem(Matcher matcher, String type, int enable) {
        Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
        if (type.trim().equals("offer")) {
            for (Player player : App.getCurrentGame().getPlayers()) {
                if (player.getUser().getUsername().equals(matcher.group("username"))) {
                    String item = matcher.group("item");
                    int amount = Integer.parseInt(matcher.group("amount"));
                    String targetItem = matcher.group("targetItem");
                    int targetAmount = Integer.parseInt(matcher.group("targetAmount"));
                    if (player.getBackPack().getInventorySize(targetItem) >= targetAmount) {
                        if (currentPlayer.getBackPack().getInventorySize(item) >= amount) {
                            if (enable == 0) {
                                Trade trade = new Trade(currentPlayer, type, item, amount, 0, targetItem, targetAmount, "byItem", matcher);
                                message message = new message(currentPlayer, "you have a trade offer from "
                                        + currentPlayer.getUser().getUsername());
                                player.addMessage(message);
                                player.addTrades(trade);
                                currentPlayer.addTrades(trade);
                                return new Result(true, "yor offer for trade has been registered");
                            } else if (enable == 1) {
                                for (int i = 0; i < amount; i++) {
                                    BackPackable b = currentPlayer.getBackPack().useItem(item);
                                    player.getBackPack().addItemToInventory(b);
                                }
                                for (int i = 0; i < targetAmount; i++) {
                                    BackPackable b = player.getBackPack().useItem(targetItem);
                                    currentPlayer.getBackPack().addItemToInventory(b);
                                }
                            }
                        } else {
                            return new Result(false, "you have not enough item in your inventory");
                        }
                    } else {
                        return new Result(false, "this player have not enough targetItems in her/his inventory");
                    }
                }
            }
        } else {
            for (Player player : App.getCurrentGame().getPlayers()) {
                if (player.getUser().getUsername().equals(matcher.group("username"))) {
                    String item = matcher.group("item");
                    int amount = Integer.parseInt(matcher.group("amount"));
                    String targetItem = matcher.group("targetItem");
                    int targetAmount = Integer.parseInt(matcher.group("targetAmount"));
                    if (player.getBackPack().getInventorySize(item) >= amount) {
                        if (currentPlayer.getBackPack().getInventorySize(targetItem) >= targetAmount) {
                            if (enable == 0) {
                                Trade trade = new Trade(currentPlayer, type, item, amount, 0, targetItem, targetAmount, "byItem", matcher);
                                message message = new message(currentPlayer, "you have a trade request from "
                                        + currentPlayer.getUser().getUsername());
                                player.addMessage(message);
                                player.addTrades(trade);
                                currentPlayer.addTrades(trade);
                                return new Result(true, "yor request for trade has been registered");
                            } else if (enable == 1) {
                                for (int i = 0; i < amount; i++) {
                                    BackPackable b = player.getBackPack().useItem(item);
                                    currentPlayer.getBackPack().addItemToInventory(b);
                                }
                                for (int i = 0; i < targetAmount; i++) {
                                    BackPackable b = currentPlayer.getBackPack().useItem(targetItem);
                                    player.getBackPack().addItemToInventory(b);
                                }
                            }
                        } else {
                            return new Result(false, "this player have not enough item in your inventory");
                        }
                    } else {
                        return new Result(false, "this player have not enough targetItems in her/his inventory");
                    }
                }
            }
        }
        return new Result(false, "this username does not exist in this game");
    }

    public Result tradeResponse(Matcher matcher) {
        String accept = matcher.group("accept");
        int id = Integer.parseInt(matcher.group("id"));
        Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
        Trade trade = null;
        for (Trade trade1 : currentPlayer.getTrades()) {
            if (trade1.getId() == id && !trade1.getSender().equals(currentPlayer)) {
                trade = trade1;
            }
        }
        if (trade == null) {
            return new Result(false, "invalid id");
        } else if (accept.equals("accept")) {
            if (trade.getTradeType().equals("byMoney")) {
                tradeByMoney(trade.getMatcher(), trade.getMatcher().group("type"), 1);
                trade.getSender().addTradeHistory(trade);
                currentPlayer.addTradeHistory(trade);
                trade.getSender().getTrades().remove(trade);
                currentPlayer.getTrades().remove(trade);
                return new Result(true, "the operation was successful");
            } else {
                tradeByItem(trade.getMatcher(), trade.getMatcher().group("type"), 1);
                trade.getSender().addTradeHistory(trade);
                currentPlayer.addTradeHistory(trade);
                trade.getSender().getTrades().remove(trade);
                currentPlayer.getTrades().remove(trade);
                return new Result(true, "the operation was successful");
            }
        } else {
            trade.getSender().getTrades().remove(trade);
            currentPlayer.getTrades().remove(trade);
            return new Result(false, "the operation was successful");
        }
    }

    public Result tradeHistory() {
        Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
        String result = "on going trades : \n";
        for (Trade trade : currentPlayer.getTrades()) {
            if (trade.getTradeType().equals("byMoney")) {
                result += (trade.getType() + " : " + "\n"
                        + "tradeId : " + trade.getId() + "\n"
                        + "Sender : " + trade.getSender().getUser().getUsername() + "\n"
                        + "tradeType : " + "byMoney" + "\n"
                        + "tradeItem : " + trade.getItem() + "\n"
                        + "amount : " + trade.getAmount() + "\n"
                        + "price : " + trade.getPrice() + "\n"
                        + "--------------------------------------------------\n");
            } else {
                result += (trade.getType() + " : " + "\n"
                        + "tradeId : " + trade.getId() + "\n"
                        + "Sender : " + trade.getSender().getUser().getUsername() + "\n"
                        + "tradeType : " + "byItem" + "\n"
                        + "item : " + trade.getItem() + "\n"
                        + "amount : " + trade.getAmount() + "\n"
                        + "targetItem : " + trade.getTargetItem() + "\n"
                        + "targetAmount : " + trade.getTargetAmount() + "\n"
                        + "--------------------------------------------------\n");
            }
        }
        result += "previous trades (accepted) \n";
        for (Trade trade : currentPlayer.getTradeHistory()) {
            if (trade.getTradeType().equals("byMoney")) {
                result += (trade.getType() + " : " + "\n"
                        + "tradeId : " + trade.getId() + "\n"
                        + "Sender : " + trade.getSender().getUser().getUsername() + "\n"
                        + "tradeType : " + "byMoney" + "\n"
                        + "tradeItem : " + trade.getItem() + "\n"
                        + "amount : " + trade.getAmount() + "\n"
                        + "price : " + trade.getPrice() + "\n"
                        + "--------------------------------------------------\n");
            } else {
                result += (trade.getType() + " : " + "\n"
                        + "tradeId : " + trade.getId() + "\n"
                        + "Sender : " + trade.getSender().getUser().getUsername() + "\n"
                        + "tradeType : " + "byItem" + "\n"
                        + "item : " + trade.getItem() + "\n"
                        + "amount : " + trade.getAmount() + "\n"
                        + "targetItem : " + trade.getTargetItem() + "\n"
                        + "targetAmount : " + trade.getTargetAmount() + "\n"
                        + "--------------------------------------------------\n");
            }
        }
        return new Result(true, result);
    }

    public Result tradeList() {
        Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
        if (currentPlayer.getTrades() == null) {
            return new Result(false, "there are nothing trade for you");
        } else {
            String result = "";
            for (Trade trade : currentPlayer.getTrades()) {
                if (!trade.getSender().equals(currentPlayer)) {
                    if (trade.getTradeType().equals("byMoney")) {
                        result += (trade.getType() + " : " + "\n"
                                + "tradeId : " + trade.getId() + "\n"
                                + "Sender : " + trade.getSender().getUser().getUsername() + "\n"
                                + "tradeType : " + "byMoney" + "\n"
                                + "tradeItem : " + trade.getItem() + "\n"
                                + "amount : " + trade.getAmount() + "\n"
                                + "price : " + trade.getPrice() + "\n"
                                + "--------------------------------------------------\n");
                    } else {
                        result += (trade.getType() + " : " + "\n"
                                + "tradeId : " + trade.getId() + "\n"
                                + "Sender : " + trade.getSender().getUser().getUsername() + "\n"
                                + "tradeType : " + "byItem" + "\n"
                                + "item : " + trade.getItem() + "\n"
                                + "amount : " + trade.getAmount() + "\n"
                                + "targetItem : " + trade.getTargetItem() + "\n"
                                + "targetAmount : " + trade.getTargetAmount() + "\n"
                                + "--------------------------------------------------\n");
                    }
                }
            }
            if (result.isEmpty()) {
                return new Result(false, "there are nothing trade for you");
            } else {
                return new Result(true, result);
            }
        }
    }

    public boolean sideBySide(Player currentPlayer, NPC npc) {
        int x = currentPlayer.getX();
        int y = currentPlayer.getY();
        int x1 = npc.getX();
        int y1 = npc.getY();
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

    public Result meetNPC(String npcName, Scanner scanner) {
        if (!(npcName.equals("Abigail")
                || npcName.equals("Harvey")
                || npcName.equals("Lia")
                || npcName.equals("Robin")
                || npcName.equals("Sebastian"))) {
            return new Result(false, "there isn't this NPC");
        } else {
            Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
            NPC npc = null;
            for (NPC n : App.getCurrentGame().getNPCs()) {
                if (n.getName().equals(npcName)) {
                    npc = n;
                }
            }
            if (npc != null) {
                if (sideBySide(currentPlayer, npc)) {
                    String input;
                    do {
                        input = scanner.nextLine();
                        if (npc.getDialogue().get(input) != null) {
                            System.out.println(npc.getDialogue().get(input));
                        } else {
                            System.out.println("Please enter a valid dialogue");
                        }
                    } while (!input.equals("goodbye"));
                    if (!currentPlayer.getTalkedNPCToday().get(npc)) {
                        currentPlayer.getFriendShipsWithNPCs().put(npc, Math.min(799, currentPlayer.getFriendShipsWithNPCs().get(npc) + 20));
                        currentPlayer.getTalkedNPCToday().put(npc, true);
                        return new Result(true, "your friendship level with " + npcName + " increased by 20 points.");
                    } else {
                        return new Result(true, "you talked with " + npcName);
                    }

                } else {
                    return new Result(false, "you can not talk NPC from this distance");
                }
            } else {
                return new Result(false, "there isn't this NPC");
            }

        }
    }

    public Result giftNPC(Matcher matcher) {
        String NPCName = matcher.group("npcName");
        String item = matcher.group("item");
        Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
        if (App.getCurrentGame().getNPC(NPCName) != null) {
            NPC npc = App.getCurrentGame().getNPC(NPCName);
            if (currentPlayer.getBackPack().getInventorySize(item) == 0) {
                return new Result(false, "your inventory is empty");
            } else {
                currentPlayer.getBackPack().useItem(item);
                if (!currentPlayer.getGiftNPCToday().get(npc)) {
                    if (npc.getFavorites().contains(item)) {
                        currentPlayer.getFriendShipsWithNPCs().put(npc, Math.min(799, currentPlayer.getFriendShipsWithNPCs().get(npc) + 200));
                        return new Result(true, "your beautiful gift was received by + " + npc.getName());
                    } else {
                        currentPlayer.getFriendShipsWithNPCs().put(npc, Math.min(799, currentPlayer.getFriendShipsWithNPCs().get(npc) + 50));
                        return new Result(true, "your gift was received by + " + npc.getName());
                    }
                } else {
                    return new Result(true, "your gift was received by + " + npc.getName());
                }

            }
        }
        return new Result(false, "this NPC doesn't exist");
    }

    public Result friendshipNPCList() {
        Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
        String result = "";
        for (NPC npc : currentPlayer.getFriendShipsWithNPCs().keySet()) {
            result += ("friendship score with " + npc.getName()
                    + " : " + currentPlayer.getFriendShipsWithNPCs().get(npc)
                    + "\n" + "friendship level with " + npc.getName() + " : "
                    + currentPlayer.getFriendShipsWithNPCs().get(npc) / 200 + "\n" + "-------------" + "\n");
        }
        return new Result(true, result);
    }

    public Result questsList() {
        String result = "";
        Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
        result += "Only missions with your level are active for you.\n";
        for (NPC npc : currentPlayer.getFriendShipsWithNPCs().keySet()) {
            int temp = currentPlayer.getFriendShipsWithNPCs().get(npc) / 200;
            result += (npc.getName() + "   your friendship level: " + temp + "\n"
                    + "1- questLeve: " + npc.getRequests().get(0).getLevel() + "\n quest explanation: "
                    + npc.getRequests().get(0).getQuestExplanation() + "\n" +
                    (npc.getRequests().get(0).isCompleted() ? " is completed" : " not completed")
                    + "\n2- questLeve: "
                    + npc.getRequests().get(1).getLevel()
                    + "\n quest explanation: " + npc.getRequests().get(1).getQuestExplanation() + "\n" +
                    (npc.getRequests().get(1).isCompleted() ? " is completed" : " not completed")
                    + "\n3- questLeve: " + npc.getRequests().get(2).getLevel()
                    + "\n quest explanation: " + npc.getRequests().get(2).getQuestExplanation() + "\n" +
                    (npc.getRequests().get(2).isCompleted() ? " is completed" : " not completed")
                    + "\n" + "------------------------------------" + "\n");
        }
        return new Result(false, result);
    }

    public Result questFinish(String index) {
        int i = Integer.parseInt(index);
        if (i < 1 || i > 3) {
            return new Result(false, "invalid index");
        }
        Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
        NPC npc = null;
        for (NPC npc2 : App.getCurrentGame().getNPCs()) {
            if (sideBySide(currentPlayer, npc2)) {
                npc = npc2;
                break;
            }
        }
        if (npc == null) {
            return new Result(false, "npc not found");
        } else {
            Quest quest = npc.getRequests().get(i);
            if (quest.isCompleted()) {
                return new Result(false, "quest already completed");
            } else {
                if (quest.getLevel() <= currentPlayer.getFriendShipsWithNPCs().get(npc) / 200) {
                    String item = quest.getItem();
                    int amount = quest.getAmount();
                    if (currentPlayer.getBackPack().getInventorySize(item) >= amount) {
                        for (int j = 0; j < amount; j++) {
                            currentPlayer.getBackPack().useItem(item);
                        }
                        //TODO reward
                        return new Result(true, "the mission was successfully completed." +
                                "your reward has been added to your backpack");
                    } else {
                        return new Result(false, "you can't finish quest because you do not have a the required item");
                    }
                } else {
                    return new Result(false, "you can't finish quest because you do not have a the required level");
                }
            }
        }
    }

    public Result showMessage() {
        return new Result(true, App.getCurrentGame().getCurrentPlayingPlayer().getStringMessage());
    }

    public Result deleteMessage(int index) {
        if (index >= App.getCurrentGame().getCurrentPlayingPlayer().getMessage().size()) {
            return new Result(false, "there are no messages with this index");
        } else {
            message message = App.getCurrentGame().getCurrentPlayingPlayer().getMessage().get(index);
            App.getCurrentGame().getCurrentPlayingPlayer().getMessage().remove(message);
            return new Result(true, "message delete successfully");
        }
    }

}
