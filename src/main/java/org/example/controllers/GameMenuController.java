package org.example.controllers;

import org.example.controllers.helperControllers.ArtisanController;
import org.example.controllers.helperControllers.FarmingController;
import org.example.controllers.helperControllers.MarketsController;
import org.example.display;
import org.example.models.*;
import org.example.models.animal.*;
import org.example.models.NPCS.NPC;
import org.example.models.NPCS.Quest;
import org.example.models.artisan.ArtisanProductType;
import org.example.models.cooking.*;
import org.example.models.crafting.CraftingItem;
import org.example.models.crafting.CraftingItemType;
import org.example.models.crafting.CraftingRecipe;
import org.example.models.enums.*;
import org.example.models.foraging.ForagingController;
import org.example.models.foraging.Mineral;
import org.example.models.animal.AnimalPlace;
import org.example.models.map.GreenHouse;
import org.example.models.map.Hut;
import org.example.models.map.Tile;
import org.example.models.plant.*;
import org.example.models.tools.*;
import org.example.models.Trade;
import org.example.models.market.*;

import java.util.*;
import java.util.regex.Matcher;

public class GameMenuController {
    private final ArtisanController artisanController = new ArtisanController();
    private final FarmingController farmingController = new FarmingController();
    private final MarketsController marketsController = new MarketsController();

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
        tile.lightningStrike();
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
        if (player.getBackPack().getCoin() < 1000)
            return new Result(false, "You only have %.2f coin. (not enough)".formatted(
                    player.getBackPack().getCoin()
            ));
        int woodCount = player.getBackPack().getInventorySize(NormalItemType.Wood.getName());
        if (woodCount < 500)
            return new Result(false, "You only have %d wood(not enough wood)".formatted(woodCount));

        player.getBackPack().addCoin(-1000);

        for (int i = 0; i < 500; i++)
            player.getBackPack().useItem(NormalItemType.Wood);

        player.getPlayerMap().getGreenHouse().setActive(true);
        return new Result(true, "GreenHouse created Successfully");
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
            if (item instanceof FishingPoleType toolType) {
                Tool tool = (Tool) backPack.getBackPackItems().get(toolType).get(0);
                if (tool.getType().getName().equalsIgnoreCase(toolName)) {
                    App.getCurrentGame().getCurrentPlayingPlayer().setCurrentTool(tool);
                    return new Result(true, "You are now using " + tool.getType().getName() + ".");
                }
            }
        }

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
            if (backPackableType instanceof ToolType toolType ) {
                Tool tool = (Tool) backPack.getBackPackItems().get(toolType).get(0);
                if (tool != null) {
                    sb.append(tool.getType().getName()).append("\n");

                }

            }
            if (backPackableType instanceof FishingPoleType toolType) {
                Tool tool = (Tool) backPack.getBackPackItems().get(toolType).get(0);
                sb.append(tool.getType().getName()).append("\n");
            }

        }
        if (sb.isEmpty()) {
            return new Result(false, "You dont have any tool");
        }
        return new Result(true, sb.toString());
    }

    public Result toolUpgrade(String toolName) {
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        Tool tool = Tool.findToolByName(toolName);
        if (tool == null) {
            return new Result(false, "Tool with this name doesn't exist in your backpack.");
        }
        Tile tile = Tile.getTile(player.getX(), player.getY());
        if(!(tile.getPlaceable() instanceof Store)){
            return new Result(false, "You should go to blacksmith to upgrade tools");
        }
        if(tile.getPlaceable() instanceof Store store){
            if(store.getType() != StoreType.Blacksmith){
                return new Result(false,"you are in wrong store , go to blacksmith to upgrade tools");
            }
        }
        if (tool.getToolType().equals(ToolType.FishingPole)) {
//            if(tool.getLevel()==3){
//                return new Result(true, "Your Fishing Pole is at max level");
//            }
//            tool.setLevel(tool.getLevel()+1);
//            return new Result(true,"your fishing pole is now "
//                    + FishingPoleType.values()[tool.getLevel()].name());
            return new Result(false, "you should buy better fishing pole from shop");
        }

        if (tool.getLevel() == 4) {
            return new Result(false, toolName + " is already at max level");
        }
        ArtisanProductType type = ArtisanProductType.CopperBar;
        double price=2000;
        switch (tool.getLevel() +1){
            case 1 : type = ArtisanProductType.CopperBar;
            price = 2000;
            break;
            case 2 : type = ArtisanProductType.IronBar;
            price = 5000;
            break;
            case 3 : type = ArtisanProductType.GoldBar;
            price = 10000;
            break;
            case 4 : type = ArtisanProductType.IridiumBar;
            price = 25000;
            break;
            default:type = ArtisanProductType.CopperBar;
        }
        if(toolName.equals("TrashCan")){
            price = price/2;
        }
        if(player.getBackPack().getCoin()<price){
            return new Result(false,"not enough coin");
        }
        if(player.getBackPack().getBackPackItems().containsKey(type)){
            if(player.getBackPack().getBackPackItems().get(type).size()>=5){
                for(int i=0;i<5;i++){
                    player.getBackPack().useItem(type);
                }
                tool.setLevel(tool.getLevel() + 1);
                return new Result(true, toolName + " upgraded to " + tool.getLevelMaterial());
            }
        }

        return new Result(false,"not enough " + type.name());

    }

    public Result toolUse(String direction) {
        double leverage = App.getCurrentGame().getDate().getTodayWeatherType().getEnergyConsume();
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        int x = player.getX() + App.handleDirection(Integer.parseInt(direction))[0];
        int y = player.getY() + App.handleDirection(Integer.parseInt(direction))[1];

        Tool tool = App.getCurrentGame().getCurrentPlayingPlayer().getCurrentTool();
        Tile tile = Tile.getTile(x, y);

        if (tile == null) {
            return new Result(false, "invalid tile");
        }

        if (tool.getToolType().equals(ToolType.Hoe)) {
            double energy = ToolType.Hoe.getEnergyCosts()[tool.getLevel()];
            if (player.getAbilities().getFarmingLevel() == 4) {
                energy--;
            }
            if(player.getBuff().getBuffType().equals(BuffType.Farming)){
                energy--;
            }
            energy = Math.max(energy, 0);
            if (tile.getPlaceable() == null || tile.getPlaceable() instanceof GreenHouse) {
                tile.setPlowed(true);
                player.setEnergy(player.getEnergy() - energy * leverage);
                player.getAbilities().increaseFarmingAbility();
                return new Result(true, "plowed successfully");
            }
            player.setEnergy(player.getEnergy() - energy * leverage);
            return new Result(true, "Hoe used but incorrectly");
        } else if (tool.getToolType().equals(ToolType.Pickaxe)) {
            double energy = ToolType.Pickaxe.getEnergyCosts()[tool.getLevel()];
            if (player.getAbilities().getMiningLevel() == 4) {
                energy--;
            }
            if(player.getBuff().getBuffType().equals(BuffType.Mining)){
                energy--;
            }
            if (tile.getPlaceable() instanceof Mineral mineral) {

                if (!ForagingController.canBreakMineral(player.getCurrentTool().getMaterial(),
                        mineral.getType())) {
                    energy--;
                    energy = Math.max(energy, 0);
                    player.setEnergy(player.getEnergy() - energy * leverage);
                    return new Result(false, "this type of pickaxe cannot break this mineral");
                }
                player.getAbilities().increaseMiningAbility();
                if (mineral.isForaging())
                    player.getAbilities().increaseForagingAbility();
                energy = Math.max(energy, 0);
                player.setEnergy(player.getEnergy() - energy * leverage);
                player.getBackPack().addItemToInventory(mineral);
                tile.setPlaceable(null);
                if (player.getAbilities().getMiningLevel() < 2) {
                    return new Result(true, "stone broke successfully");
                }
                if (player.getAbilities().getMiningLevel() >= 2) {
                    player.getBackPack().addItemToInventory(mineral);
                    return new Result(true, "stone broke successfully and you also got 1 more because of mining level");
                }
                return new Result(true, "stone broke successfully");

            } else if (tile.isPlowed()) {
                tile.setPlowed(false);
                energy = Math.max(energy, 0);
                player.setEnergy(player.getEnergy() - energy * leverage);
                return new Result(true, "unplowed successfully");
            } else if (tile.getPlaceable() instanceof BackPackable item) {
                tile.setPlaceable(null);
                energy = Math.max(energy, 0);
                player.setEnergy(player.getEnergy() - energy * leverage);
                return new Result(true, item.getName() + " destroyed successfully");
            }
            energy = Math.max(energy - 1, 0);
            player.setEnergy(player.getEnergy() - energy * leverage);
            return new Result(true, "you used pickaxe but incorrectly");
        } else if (tool.getToolType().equals(ToolType.Axe)) {
            double energy = ToolType.Axe.getEnergyCosts()[tool.getLevel()];
            if (player.getAbilities().getForagingLevel() == 4) {
                energy--;
            }
            if(player.getBuff().getBuffType().equals(BuffType.Foraging)){
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
                    return new Result(true, "you destroyed wood");
                }
            }
            energy--;
            energy = Math.max(energy, 0);
            player.setEnergy(player.getEnergy() - energy * leverage);
            return new Result(true, "you used axe but incorrectly");
        } else if (tool.getToolType().equals(ToolType.WateringCan)) {
            double energy = ToolType.WateringCan.getEnergyCosts()[tool.getLevel()];
            if (player.getAbilities().getForagingLevel() == 4) {
                energy--;
            }
            if(player.getBuff().getBuffType().equals(BuffType.Farming)){
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
                if (tool.isWateringCanFull()) {
                    return new Result(true, "watering can is already full");
                }
                tool.handleWateringCanStorage();
                return new Result(true, "watering can is now full of water");
            }
        } else if (tool.getToolType().equals(ToolType.Scythe)) {
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
                    //fruit.setItemQuality();
                    player.getBackPack().addItemToInventory(
                            fruit);
                    if (tree.isForaging())
                        player.getAbilities().increaseForagingAbility();
                } else if (plant instanceof Crop crop) {
                    crop.harvest();
                }
            }
        } else if (tool.getToolType().equals(ToolType.MilkPail)) {
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
        } else if (tool.getToolType().equals(ToolType.Shear)) {
            player.setEnergy(player.getEnergy() - 4 * leverage);
            if (tile.getPlaceable() instanceof Animal animal) {
                if (animal.getAnimalType().equals(AnimalType.Sheep)) {
                    if(animal.getAnimalProducts().isEmpty()){
                        return new Result(false,"this sheep has no product");
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
        } else if (tool.getToolType().equals(ToolType.FishingPole)) {
            if(!tile.isWater()){
                return new Result(false,"you should catch fish near water and lakes , here is not water");
            }
            double energy =2;
            switch (tool.getFishingPoleMaterial()) {
                case TrainingFishingPole -> energy = 8;
                case BambooFishingPole -> energy = 6;
                case FiberglassFishingPole -> energy = 4;
                case IridiumFishingPole -> energy = 2;
            }
            if (player.getAbilities().getFishingLevel() == 4) {
                energy--;
            }
            if(player.getBuff().getBuffType().equals(BuffType.Fishing)){
                energy--;
            }
            player.setEnergy(player.getEnergy() - energy * leverage);
            System.out.println(fishing(tool.getFishingPoleMaterial().name()));

        }
        return new Result(true, "Tool used.");
    }


    public Result craftInfo(String name) {
        return farmingController.craftInfo(name);
    }

    public Result plantSeed(String source, String direction) {
        return farmingController.plantSeed(source, direction);
    }

    public Result showPlant(String x, String y) {
        return farmingController.showPlant(x, y);
    }

    public Result fertilize(String fertilizer, String direction) {
        return farmingController.fertilize(fertilizer, direction);
    }

    public Result howMuchWater() {
        return farmingController.howMuchWater();
    }

    public Result craftingShowRecipes() {
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        Tile tile  = Tile.getTile(player.getX(), player.getY());
        if(tile == null){
            return new Result(false, "Tile not found");
        }
        if(!(tile.getPlaceable() instanceof Hut)){
            return new Result(false,"you should be in Hut");
        }
        if (App.getCurrentGame().getCurrentPlayingPlayer().getCraftingRecipes().isEmpty()) {
            return new Result(false, "No crafting recipes found");
        }
        StringBuilder sb = new StringBuilder();
        for (CraftingRecipe craftingRecipe : App.getCurrentGame().getCurrentPlayingPlayer().getCraftingRecipes()) {
            sb.append(craftingRecipe.getTargetItem().name()).append(" :\n     ");
            for (Map.Entry<BackPackableType, Integer> entry : craftingRecipe.getTargetItem().getIngredients().entrySet()) {
                sb.append(entry.getKey()).append(" -> ").append(entry.getValue()).append("\n     ");
            }
            sb.append("\n");
        }
        return new Result(true, sb.toString());
    }

    public Result craftingCraft(String itemName) {
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        Tile tile  = Tile.getTile(player.getX(), player.getY());
        if(tile == null){
            return new Result(false, "Tile not found");
        }
        if(!(tile.getPlaceable() instanceof Hut)){
            return new Result(false,"you should be in Hut");
        }
        CraftingRecipe recipe = CraftingRecipe.findRecipe(itemName);
        if(tile == null){
            return new Result(false, "Tile not found");
        }
        if(!(tile.getPlaceable() instanceof Hut)){
            return new Result(false,"for crafting item you should be in Hut");
        }
        if (recipe == null) {
            return new Result(false, "No crafting recipe found");
        }

        if (App.getCurrentGame().getCurrentPlayingPlayer().getBackPack().isBackPackFull()) {
            return new Result(false, "no free space in inventory");
        }
        BackPack backPack = App.getCurrentGame().getCurrentPlayingPlayer().getBackPack();
        for (Map.Entry<BackPackableType, Integer> entry : recipe.getTargetItem().getIngredients().entrySet()) {
            if (!(player.getBackPack().getBackPackItems().containsKey(entry.getKey())
                    && player.getBackPack().getBackPackItems().get(entry.getKey()).size() >= entry.getValue())) {
                return new Result(false, "not enough ingredient");
            }
        }
        for (Map.Entry<BackPackableType, Integer> entry : recipe.getTargetItem().getIngredients().entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                player.getBackPack().useItem(entry.getKey());
            }
        }
        CraftingItem craftingItem = new CraftingItem(recipe.getTargetItem());
        backPack.addItemToInventory(craftingItem);
        return new Result(true, itemName + " crafted successfully");
    }

    public Result placeItem(String itemName, String direction) {
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        CraftingItemType craftingItemType;
        try {
            craftingItemType = CraftingItemType.valueOf(itemName);
        } catch (Exception e) {
            return new Result(false, "Invalid item name");
        }
        if (!player.getBackPack().getBackPackItems().containsKey(craftingItemType)) {
            return new Result(false, " you dont have " + craftingItemType.name());
        }
        int[] direction1 = App.handleDirection(Integer.parseInt(direction));
        Tile tile = App.getCurrentGame().getTileByIndex(player.getX() + direction1[0],
                player.getY() + direction1[1]);
        if (tile.getPlaceable() != null) {
            return new Result(false, "tile is full");
        }

        App.getCurrentGame().getCurrentPlayingPlayer().getBackPack().useItem(craftingItemType);
        tile.setPlaceable(new CraftingItem(craftingItemType));
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
                int[] dx = {0, 1, 0, -1};
                int[] dy = {1, 0, -1, 0};
                for (int i = 0; i < 4; i++) {
                    Tile target = Tile.getTile(tile.getX() + dx[i], tile.getY() + dy[i]);
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


        return new Result(true, "Item placed Successfully.");
    }

    public Result addItem(String itemName, String countStr) {
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        int count;
        try {
            count = Integer.parseInt(countStr);
        } catch (NumberFormatException e) {
            return new Result(false, "Invalid number format for count.");
        }

        ArrayList<Object> result = marketsController.addItem(itemName);

        BackPackableType type = (BackPackableType) result.get(0);
        BackPackable sampleItem = (BackPackable) result.get(1);

        if (type == null && sampleItem == null)
            return new Result(false, "Invalid item name");


        for (int i = 0; i < count; i++) {
            player.getBackPack().addItemToInventory(sampleItem);
        }

        return new Result(true, count + " x " + itemName + " added to backpack.");
    }


    public Result cookingRefrigerator(String mode, String itemName) {
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        Tile tile  = Tile.getTile(player.getX(), player.getY());
        if(tile == null){
            return new Result(false, "Tile not found");
        }
        if(!(tile.getPlaceable() instanceof Hut)){
            return new Result(false,"you should be in Hut");
        }
        BackPack backPack = App.getCurrentGame().getCurrentPlayingPlayer().getBackPack();
        if (mode.equals("put")) {

            for (BackPackableType backPackableType : backPack.getBackPackItems().keySet()) {
                if (backPackableType instanceof FoodType foodType) {
                    Food food = (Food) backPack.getBackPackItems().get(foodType).get(0);
                    if (food.getFoodtype().getName().equals(itemName)) {
                        player.getPlayerMap().getHut().getRefrigerator().getFoods().add(food);
                        player.getBackPack().useItem(food.getType());
                    }
                }
            }
        } else if (mode.equals("pick")) {
            for (Food food : player.getPlayerMap().getHut().getRefrigerator().getFoods()) {
                if (food.getFoodtype().getName().equals(itemName)) {
                    player.getPlayerMap().getHut().getRefrigerator().getFoods().remove(food);
                    player.getBackPack().addItemToInventory(food);
                }
            }
        }
        return new Result(false, "just put or pick");
    }

    public Result cookingShowRecipes() {
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        Tile tile  = Tile.getTile(player.getX(), player.getY());
        if(tile == null){
            return new Result(false, "Tile not found");
        }
        if(!(tile.getPlaceable() instanceof Hut)){
            return new Result(false,"you should be in Hut");
        }
        if (App.getCurrentGame().getCurrentPlayingPlayer().getRecipes().isEmpty()) {
            return new Result(false, "you dont have any recipes");
        }
        StringBuilder sb = new StringBuilder();
        for (Recipe recipe : App.getCurrentGame().getCurrentPlayingPlayer().getRecipes()) {
            sb.append(recipe.getFoodToBeCooked().name()).append(" : ").append("\n     ");
            for (Map.Entry<BackPackableType, Integer> entry : recipe.getFoodToBeCooked().getIngredients().entrySet()) {
                sb.append(entry.getKey()).append(" -> ").append(entry.getValue()).append("\n     ");
            }
            sb.append("\n");
        }
        return new Result(true, sb.toString());
    }

    public Result cookingPrepare(String recipeName) {
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        Tile tile  = Tile.getTile(player.getX(), player.getY());
        if(tile == null){
            return new Result(false, "Tile not found");
        }
        if(!(tile.getPlaceable() instanceof Hut)){
            return new Result(false,"you should be in Hut");
        }
        Recipe recipe = Recipe.findRecipe(recipeName);
        if (recipe == null) {
            return new Result(false, "Recipe not found");
        }
        if (player.getBackPack().isBackPackFull()) {
            return new Result(false, "Your back pack is full");
        }
        if (recipe.getFoodToBeCooked().equals(FoodType.MakiRoll)) {
            if (player.getBackPack().getBackPackItems().containsKey(NormalItemType.Fiber) &&
                    player.getBackPack().getBackPackItems().containsKey(NormalItemType.Rice)) {

                FishType selectedFish = null;
                for (FishType fish : FishType.values()) {
                    if (player.getBackPack().getBackPackItems().containsKey(fish)) {
                        selectedFish = fish;
                        break;
                    }
                }

                if (selectedFish != null) {
                    BackPack backPack = player.getBackPack();
                    backPack.useItem(NormalItemType.Fiber);
                    backPack.useItem(NormalItemType.Rice);
                    backPack.useItem(selectedFish);
                } else {
                    return new Result(false, "no fish found for MakiRoll");
                }
            } else {
                return new Result(false, "not enough ingredients");
            }
        } else {
            for (Map.Entry<BackPackableType, Integer> entry : recipe.getFoodToBeCooked().getIngredients().entrySet()) {
                if (!(player.getBackPack().getBackPackItems().containsKey(entry.getKey())
                        && player.getBackPack().getBackPackItems().get(entry.getKey()).size() >= entry.getValue())) {
                    return new Result(false, "not enough ingredient");
                }
            }
            for (Map.Entry<BackPackableType, Integer> entry : recipe.getFoodToBeCooked().getIngredients().entrySet()) {
                for (int i = 0; i < entry.getValue(); i++) {
                    player.getBackPack().useItem(entry.getKey());
                }
            }
        }
        Food newFood = new Food(null);
        newFood.setFoodtype(recipe.getFoodToBeCooked());
        newFood.setRecipe(recipe);
        player.getBackPack().addItemToInventory(newFood);
        App.getCurrentGame().getCurrentPlayingPlayer().setEnergy(App.getCurrentGame().getCurrentPlayingPlayer().getEnergy() - 3);
        return new Result(true, recipe.getFoodToBeCooked().name() + " cooked");
    }

    public Result eat(String foodName) {
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        Tile tile  = Tile.getTile(player.getX(), player.getY());
        if(tile == null){
            return new Result(false, "Tile not found");
        }
        if(!(tile.getPlaceable() instanceof Hut)){
            return new Result(false,"you should be in Hut");
        }
        try {
            FoodType food = FoodType.valueOf(foodName);
            if (!player.getBackPack().getBackPackItems().containsKey(food)) {
                StringBuilder sb = new StringBuilder();
                sb.append("you dont have ").append(foodName).append(" in your backpack ").append("\n")
                        .append("you can cook it with : ").append("\n");
                for (Map.Entry<BackPackableType, Integer> entry : food.getIngredients().entrySet()) {
                    sb.append(" x").append(entry.getValue()).append(" ").append(entry.getKey()).append("\n");
                }
                return new Result(false, sb.toString());
            }
            player.getBackPack().useItem(food);
            player.setEnergy(player.getEnergy() + food.getEnergy());
            switch (food){
                case TripleShotEspresso -> player.applyTemporaryMaxEnergyBoost(100,5);
                case RedPlate -> player.applyTemporaryMaxEnergyBoost(50,3);
                case HashBrowns, FarmersLunch -> {
                    player.setBuff(new Buff(BuffType.Farming, 5));
                    player.applyTemporaryMaxEnergyBoost(0,0);
                }
                case Pancakes -> {
                    player.setBuff(new Buff(BuffType.Foraging, 11));
                    player.applyTemporaryMaxEnergyBoost(0,0);
                }
                case SurvivalBurger -> {
                    player.setBuff(new Buff(BuffType.Foraging, 5));
                    player.applyTemporaryMaxEnergyBoost(0,0);
                }
                case DishOTheSea -> {
                    player.setBuff(new Buff(BuffType.Fishing, 5));
                    player.applyTemporaryMaxEnergyBoost(0,0);
                }
                case SeafoamPudding -> {
                    player.setBuff(new Buff(BuffType.Fishing, 10));
                    player.applyTemporaryMaxEnergyBoost(0,0);
                }
                case MinersTreat -> {
                    player.setBuff(new Buff(BuffType.Mining, 5));
                    player.applyTemporaryMaxEnergyBoost(0,0);
                }
            }
            return new Result(true, "you ate " + foodName + " successfully , " +
                    food.getEnergy() + " energy added");
        } catch (Exception e) {
            return new Result(false, "invalid food");
        }
    }

    public Result build(String name, String x, String y) {
        AnimalPlaceType animalPlaceType;
        try {
            animalPlaceType = AnimalPlaceType.valueOf(name);
        } catch (Exception e) {
            return new Result(false, "Invalid place");
        }
        AnimalPlace animalPlace = new AnimalPlace(animalPlaceType);
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        double money = player.getBackPack().getCoin();
        if (money < animalPlaceType.getPrice()) {
            return new Result(false, "you dont have enough money");
        }
        player.getBackPack().addcoin(-animalPlaceType.getPrice());
        int xint = Integer.parseInt(x);
        int yint = Integer.parseInt(y);
        for (int i = -2; i < 2; i++) {
            for (int j = -2; j < 2; j++) {
                Tile tile = Tile.getTile(xint + i, yint + j);
                if (tile == null) {
                    return new Result(false, "Tile not found");
                }
                if (tile.getPlaceable() != null) {
                    return new Result(false, "this area is not empty for building ");
                }
            }
        }
        for (int i = -2; i < 2; i++) {
            for (int j = -2; j < 2; j++) {
                Tile tile = Tile.getTile(xint + i, yint + j);
                tile.setPlaceable(animalPlace);
            }
        }
        App.getCurrentGame().getCurrentPlayingPlayer().getPlayerMap().getFarm().getAnimalPlaces().add(animalPlace);
        return new Result(true, "build successfully");
    }

    public Result buyAnimal(String animal, String name) {
        AnimalType animalType;
        try {
            animalType = AnimalType.valueOf(animal);
        } catch (Exception e) {
            return new Result(false, "Invalid animal");
        }

        Animal newAnimal = new Animal(name, animalType);
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        double money = player.getBackPack().getCoin();
        if (money < animalType.getPrice()) {
            return new Result(false, "you dont have enough money");
        }
        player.getBackPack().addcoin(-animalType.getPrice());

        List<AnimalPlace> animalPlaces = player.getPlayerMap().getFarm().getAnimalPlaces();
        for (int i = 0; i < animalPlaces.size(); i++) {
            AnimalPlace place = animalPlaces.get(i);

            if (!animalType.getAnimalPlaceTypes().contains(place.getAnimalPlaceType())) {
                continue;
            }

            if (place.getAnimals().size() >= place.getAnimalPlaceType().getCapacity()) {
                if (i == animalPlaces.size() - 1) {
                    return new Result(false, "No valid AnimalPlace with enough space");
                }
                continue;
            }

            // افزودن حیوان
            place.addAnimal(newAnimal);
            newAnimal.setAnimalPlace(place); // اگر خواستی مکان رو هم ثبت کن
            player.getPlayerMap().getFarm().getAnimals().add(newAnimal);

            return new Result(true, name + " added to your animals successfully");
        }

        return new Result(false, "No suitable AnimalPlace for " + name);
    }


    public Result pet(String name) {
        Animal animal = Animal.findAnimalByName(name);
        if (animal == null) {
            return new Result(false, "no animal with name : " + name);
        }

        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        Tile tile = Tile.getTile(player.getX(), player.getY());
        if (animal.isPettedToday()) {
            return new Result(false, animal.getName() + " is a already petted today");
        }


        if (animal.isOutside() && !Tile.findAround(animal)) {
            return new Result(false, "you should stand next to " + name + " to pet it");
        }
        if (tile.getPlaceable() instanceof AnimalPlace animalPlace && animalPlace.getAnimals().contains(animal)) {
            animal.setFriendship(animal.getFriendship() + 15);
            animal.setPettedToday(true);
            return new Result(true, name + " petted successfully :)");
        }
        animal.setFriendship(animal.getFriendship() + 15);
        animal.setPettedToday(true);
        return new Result(true, name + " petted successfully");

    }

    public Result setFriendship(String animalName, String amount) {
        Animal animal = Animal.findAnimalByName(animalName);
        if (animal == null) {
            return new Result(false, "animal not found");
        }

        int amountInt = Integer.parseInt(amount);
        animal.cheatSetFriendship(amountInt);
        return new Result(true, "friendship is now " + animal.getFriendship());
    }

    public Result animals() {
        StringBuilder sb = new StringBuilder();
        for (Animal animal : App.getCurrentGame().getCurrentPlayingPlayer().getPlayerMap().getFarm().getAnimals()) {
            sb.append(animal.getName()).append(" (").append(animal.getAnimalType()).append(") ").append("\n")
                    .append("friendship : ").append(animal.getFriendship()).append("\n")
                    .append(animal.isPettedToday() ? "petted today" : "not petted today").append("\n")
                    .append(animal.isFedToday() ? "feded today" : "not fed today").append("\n\n");
            if(animal.getTile() != null){
                sb.append(animal.getTile().getX()).append(" ").append(animal.getTile().getY()).append("\n");
            }
        }
        return new Result(true, sb.toString());
    }


    public Result shepherdAnimal(String animalName, String x, String y) {
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        Animal animal = Animal.findAnimalByName(animalName);
        if (animal == null) {
            return new Result(false, "animal not found with name : " + animalName);
        }
        int xInt = Integer.parseInt(x);
        int yInt = Integer.parseInt(y);
        AnimalPlace animalPlace = animal.getAnimalPlace();
        Tile tile = Tile.getTile(xInt, yInt);
        if (tile == null) {
            return new Result(false, "tile not found");
        }
        if (!animal.isOutside()) {
            if (tile.getPlaceable() instanceof AnimalPlace) {
                return new Result(false, "animal is already in a animalPlace");
            }
            if (tile.getPlaceable() == null) {
                animalPlace.getAnimals().remove(animal);
                tile.setPlaceable(animal);
                animal.setTile(tile);
                animal.setOutside(true);
                animal.setFedOutside(true);
                return new Result(true, "animal is outside now");
            }
        } else {
            if (tile.getPlaceable() instanceof AnimalPlace animalPlace1) {
                if (!animal.getAnimalType().getAnimalPlaceTypes().contains(animalPlace1.getAnimalPlaceType())) {
                    return new Result(false, "you can't put " + animal.getAnimalType().name() + " in "
                            + animalPlace1.getAnimalPlaceType().name());
                }
                if (animalPlace1.isFull()) {
                    return new Result(false, "this " + animalPlace1.getAnimalPlaceType().name() + " is full");
                }
                animal.getTile().setPlaceable(null);
                animal.setTile(null);
                animal.setOutside(false);
                animalPlace1.getAnimals().add(animal);
                return new Result(true, animalName + " went to " + animalPlace1.getAnimalPlaceType().name());

            }
            if (tile.getPlaceable() == null) {
                return new Result(false, animalName + " is already outside");
            }
            if (tile.getPlaceable() != null) {
                return new Result(false, "you can't put animals here");
            }
        }
        return new Result(true, "DONE");
    }

    public Result feedHay(String animalName) {
        Animal animal = Animal.findAnimalByName(animalName);
        if (animal == null) {
            return new Result(false, "no animal with name : " + animalName);
        }
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        if (!player.getBackPack().getBackPackItems().containsKey(NormalItemType.Hay)) {
            return new Result(false, "not enough hay");
        }
        if (animal.isFedToday()) {
            return new Result(false, "already fed today");
        }
        animal.setFedToday(true);
        animal.setFriendship(animal.getFriendship() + 15);
        return new Result(true, animal.getName() + " feded seccessfully");
    }

    public Result produces() {
        StringBuilder sb = new StringBuilder();
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        for (Animal animal : player.getPlayerMap().getFarm().getAnimals()) {
            if (!animal.getAnimalProducts().isEmpty()) {
                sb.append(animal.getName()).append("\n");
                for (AnimalProduct animalProduct : animal.getAnimalProducts()) {
                    sb.append(animalProduct.getAnimalProductType().name()).append("\n")
                            .append("quality : ").append(animalProduct.getQuality().name()).append("\n");
                }
            }
        }
        return new Result(true, sb.toString());
    }

    public Result collectProduct(String name) {
        Animal animal = Animal.findAnimalByName(name);
        if (animal == null) {
            return new Result(false, "animal not found");
        }

        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        if (player.getBackPack().isBackPackFull()) {
            return new Result(false, "your backpack is full");
        }

        ArrayList<AnimalProduct> currentProducts = animal.getAnimalProducts();
        ArrayList<AnimalProduct> toRemove = new ArrayList<>();

        for (AnimalProduct product : currentProducts) {
            if (player.getBackPack().isBackPackFull()) {
                break;
            }
            player.getBackPack().addItemToInventory(product);
            toRemove.add(product);
        }

        currentProducts.removeAll(toRemove);
        return new Result(true, "products collected successfully");
    }


    public Result sellAnimal(String name) {
        Animal animal = Animal.findAnimalByName(name);
        if (animal == null) {
            return new Result(false, "animal not found");
        }

        animal.sell();
        return new Result(true, "animal sold successfully");
    }

    public Result fishing(String fishingPole) {
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        if(!Animal.areWeNearWater(player.getX(),player.getY())) {
            return new Result(false,"first go near water");
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

    public Result artisanUse(String artisanName, String itemNames) {
        return artisanController.artisanUse(artisanName, itemNames, marketsController);
    }


    public Result artisanGet(String artisanName) {
        return artisanController.artisanGet(artisanName);
    }


    public Result showAllProducts() {
        return marketsController.showAllProducts();
    }


    public Result showAllAvailableProducts() {
        return marketsController.showAllAvailableProducts();
    }


    public Result purchase(String productName, String count) {
        return marketsController.purchase(productName, count);
    }


    public Result cheatAddDollars(String count) {
        return marketsController.cheatAddDollars(count);
    }

    public Result sellProduct(String productName, String count) {
        return marketsController.sellProduct(productName, count);
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
                if (quest.getLevel() <= currentPlayer.getFriendShipsWithNPCs().get(npc) / 200
                        && quest.isActive()) {
                    String item = quest.getItem();
                    int amount = quest.getAmount();
                    if (currentPlayer.getBackPack().getInventorySize(item) >= amount) {
                        for (int j = 0; j < amount; j++) {
                            currentPlayer.getBackPack().useItem(item);
                        }
                        if (2 < currentPlayer.getFriendShipsWithNPCs().get(npc) / 200) {
                            npc.giveReward(currentPlayer, Integer.parseInt(index));
                            npc.giveReward(currentPlayer, Integer.parseInt(index));
                        } else {
                            npc.giveReward(currentPlayer, Integer.parseInt(index));
                        }
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
