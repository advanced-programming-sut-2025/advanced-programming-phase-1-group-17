package io.github.StardewValley.controllers;

import io.github.StardewValley.GameAssetManagerClient;
import io.github.StardewValley.GameClient;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.models.App;
import io.github.StardewValley.shared.controller.LightningController;
import io.github.StardewValley.shared.models.NPCS.Gift;
import io.github.StardewValley.shared.models.backpack.BackPack;
import io.github.StardewValley.shared.models.backpack.BackPackable;
import io.github.StardewValley.shared.models.backpack.BackPackableType;
import io.github.StardewValley.shared.models.backpack.NormalItemType;
import io.github.StardewValley.shared.models.enums.Gender;
import io.github.StardewValley.shared.models.*;
import io.github.StardewValley.shared.models.NPCS.NPC;
import io.github.StardewValley.shared.models.NPCS.Quest;
import io.github.StardewValley.shared.models.animal.*;
import io.github.StardewValley.shared.models.artisan.ArtisanProductType;
import io.github.StardewValley.shared.models.cooking.*;
import io.github.StardewValley.shared.models.crafting.CraftingItem;
import io.github.StardewValley.shared.models.crafting.CraftingRecipe;
import io.github.StardewValley.shared.models.enums.CheatCodeCommands;
import io.github.StardewValley.shared.models.enums.FishType;
import io.github.StardewValley.shared.models.enums.Season;
import io.github.StardewValley.shared.models.enums.WeatherType;
import io.github.StardewValley.shared.models.map.Hut;
import io.github.StardewValley.shared.models.map.Tile;
import io.github.StardewValley.shared.models.market.MarketsController;
import io.github.StardewValley.shared.models.market.Store;
import io.github.StardewValley.shared.models.market.StoreType;
import io.github.StardewValley.shared.models.tools.*;
import io.github.StardewValley.views.GameMenu;
import io.github.StardewValley.views.MainMenu;

import java.util.*;
import java.util.regex.Matcher;

public class GameMenuController {
    private GameMenu view;

    public void setView(GameMenu view) {
        this.view = view;
        setupButtonListener();
    }

    private void setupButtonListener() {
//        view.getBackButton().addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                view.setError("Entering to MainMenu...");
//                com.badlogic.gdx.utils.Timer.schedule(new com.badlogic.gdx.utils.Timer.Task() {
//                    @Override
//                    public void run() {
//                        Main.getMain().getScreen().dispose();
//                        Main.getMain().setScreen(
//                            new MainMenu(
//                                new MainMenuController(),
//                                GameAssetManager.getGameAssetManager().getSkin()
//                            )
//                        );
//                    }
//                }, 2);
//            }
//        });
//        view.getAddUser().addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                view.setError("");
//                String username = view.getUserName().getText().trim();
//                if (username.isEmpty()) {
//                    view.setError("Please enter a username");
//                    return;
//                }
//                if (!(view.getUser1().getText().toString().equals("-") || view.getUser2().getLabel().getText().toString().equals("-")
//                    || view.getUser3().getLabel().getText().toString().equals("-") || view.getUser4().getLabel().getText().toString().equals("-"))) {
//                    view.setError("you can only add a maximum of 4 players to the game!");
//                    return;
//                }
//
//                if (username.equals(view.getUser1().getLabel().getText().toString()) ||
//                    username.equals(view.getUser2().getLabel().getText().toString()) ||
//                    username.equals(view.getUser3().getLabel().getText().toString()) ||
//                    username.equals(view.getUser4().getLabel().getText().toString())) {
//                    view.setError("you cannot add repetitive player!");
//                    return;
//                }
//                if (App.getUserWithUsername(username) == null) {
//                    view.setError("no user exists with this username");
//                    return;
//                }
//                if (App.getUserWithUsername(username).getActiveGame() != null) {
//                    view.setError("user with this username has an active game");
//                    return;
//                }
//                if (view.getUser2().getLabel().getText().toString().equals("-")) {
//                    view.getUser2().getLabel().setText(view.getUserName().getText().trim());
//                    return;
//                }
//                if (view.getUser3().getLabel().getText().toString().equals("-")) {
//                    view.getUser3().getLabel().setText(view.getUserName().getText().trim());
//                    return;
//                }
//                if (view.getUser4().getLabel().getText().toString().equals("-")) {
//                    view.getUser4().getLabel().setText(view.getUserName().getText().trim());
//                    return;
//                }
//
//            }
//        });
//        view.getStartGame().addListener(new ClickListener() {
//            public void clicked(InputEvent event, float x, float y) {
//                view.setError("");
//
//                String username1 = view.getUser1().getLabel().getText().toString().trim();
//                String username2 = view.getUser2().getLabel().getText().toString().trim();
//                String username3 = view.getUser3().getLabel().getText().toString().trim();
//                String username4 = view.getUser4().getLabel().getText().toString().trim();
//                User user1 = App.getUserWithUsername(username1);
//                User user2 = App.getUserWithUsername(username2);
//                User user3 = App.getUserWithUsername(username3);
//                User user4 = App.getUserWithUsername(username4);
//
//                if (username1.equals("-")) {
//                    view.setError("you must give at least 1 username");
//                    return;
//                }
//                if (username2.equals("-")) {
//                    if (App.getUserWithUsername("guest1") != null) {
//                        App.getUsers().remove(App.getUserWithUsername("guest1"));
//                    }
//                    user2 = new User();
//                    user2.setUsername("guest1");
//                    App.getUsers().add(user2);
//                } else {
//                    user2 = App.getUserWithUsername(username2);
//                }
//                if (username3.equals("-")) {
//                    if (App.getUserWithUsername("guest2") != null) {
//                        App.getUsers().remove(App.getUserWithUsername("guest2"));
//                    }
//                    user3 = new User();
//                    user3.setUsername("guest2");
//                    App.getUsers().add(user3);
//                } else {
//                    user3 = App.getUserWithUsername(username3);
//                }
//                if (username4.equals("-")) {
//                    if (App.getUserWithUsername("guest3") != null) {
//                        App.getUsers().remove(App.getUserWithUsername("guest3"));
//                    }
//                    user4 = new User();
//                    user4.setUsername("guest3");
//                    App.getUsers().add(user4);
//                } else {
//                    user4 = App.getUserWithUsername(username4);
//                }
//                Tile.getTiles().clear();
//
//                NPC.setFatherPlayer(null);
//                NPC.setFatherUser(null);
//                Game game = new Game(user2, user3, user4);
//                App.setCurrentGame(game);
//                App.getGames().add(game);
//                view.setError("new game created Successfully");
//
//                com.badlogic.gdx.utils.Timer.schedule(new com.badlogic.gdx.utils.Timer.Task() {
//                    @Override
//                    public void run() {
//                        Main.getMain().getScreen().dispose();
//                        Main.getMain().setScreen(new chooseMap(new ChooseMapController(), GameAssetManager.getGameAssetManager().getSkin()));
//                    }
//                }, 2);
//
//
//            }
//        });
//        view.getDeleteUser1().addListener(new ClickListener() {
//            public void clicked(InputEvent event, float x, float y) {
//                view.setError("");
//                String username1 = view.getUser1().getLabel().getText().toString().trim();
//                view.setError("you can not delete the loggedIn user (you)");
//                return;
//            }
//        });
//        view.getDeleteUser2().addListener(new ClickListener() {
//            public void clicked(InputEvent event, float x, float y) {
//                view.setError("");
//                view.getUser2().getLabel().setText("-");
//                view.setError("delete user");
//                return;
//            }
//        });
//        view.getDeleteUser3().addListener(new ClickListener() {
//            public void clicked(InputEvent event, float x, float y) {
//                view.setError("");
//                view.getUser3().getLabel().setText("-");
//                view.setError("delete user");
//                return;
//            }
//        });
//        view.getDeleteUser4().addListener(new ClickListener() {
//            public void clicked(InputEvent event, float x, float y) {
//                view.setError("");
//                view.getUser4().getLabel().setText("-");
//                view.setError("delete user");
//                return;
//            }
//        });
//        view.getLoadGame().addListener(new ClickListener() {
//            public void clicked(InputEvent event, float x, float y) {
//                User user = App.getLoggedInUser();
//                Player currentPlayer = null;
//                if (user.getLastGame() == null) {
//                    view.setError("you have no game to load");
//                } else {
//                    Game game = user.getLastGame();
//                    for (Player player : game.getPlayers()) {
//                        if (!(player.getUser().getLastGame() != null && player.getUser().getLastGame().equals(game))) {
//                            view.setError("your friends have another active game");
//                            return;
//                        }
//                        if (player.getUser().equals(user)) {
//                            currentPlayer = player;
//                        }
//                    }
//                    App.setCurrentGame(game);
//                    App.getCurrentGame().setCurrentPlayingPlayer(currentPlayer);
//                    App.getCurrentGame().setCreator(currentPlayer);
//                    for (PlayerMap pm : game.getGameMap().getPlayerMaps()) {
//                        for (Tile tile : pm.getTiles()) {
//                            Tile.getTiles().add(tile);
//                        }
//                    }
//                    NPC.setFatherPlayer(game.getPlayers().get(4));
//                    NPC.setFatherUser(game.getPlayers().get(4).getUser());
//
//                    com.badlogic.gdx.utils.Timer.schedule(new com.badlogic.gdx.utils.Timer.Task() {
//                        @Override
//                        public void run() {
//                            view.setError("you are in Game now");
//                            Main.getMain().getScreen().dispose();
//                            Main.getMain().setScreen(
//                                new GameView(new GameController(App.getCurrentGame()),new GameMenuController())
//                            );
//                        }
//                    }, 2);
//
//                }
//            }
//        });

    }


    private final FarmingController farmingController = new FarmingController();
    private final MarketsController marketsController = new MarketsController();


//    public void gameMap(Scanner scanner) {
//        boolean done = false;
//        int playerChoice = 0;
//        System.out.println("Enter the number of the gameMapType you would like to play (1 or 2)");
//        while (!done) {
//            String input = scanner.nextLine();
//            if (GameMenuCommands.ChooseGameMap.getMatcher(input) == null) {
//                System.out.println("Invalid input");
//            } else if (GameMenuCommands.Int.getMatcher
//                (GameMenuCommands.ChooseGameMap.getMatcher(input).
//                    group("mapNumber")) == null) {
//                System.out.println("Invalid number");
//            } else if (Integer.parseInt(GameMenuCommands.ChooseGameMap.getMatcher(input).group("mapNumber")) != 1
//                && Integer.parseInt(GameMenuCommands.ChooseGameMap.getMatcher(input).group("mapNumber")) != 2) {
//                System.out.println("Invalid number");
//            } else {
//                App.getCurrentGame().getPlayers().get(playerChoice).getPlayerMap().setMapType
//                    (Integer.parseInt(GameMenuCommands.ChooseGameMap.getMatcher(input).group("mapNumber")));
//                playerChoice++;
//                if (playerChoice == 4) {
//                    done = true;
//                    System.out.println("Let's go");
//                }
//            }
//        }
//    }


//    public Result deleteAndExitThisGame(Scanner scanner) {
//        System.out.println("enter your comment about deleting this game");
//        for (Player player : App.getCurrentGame().getPlayers()) {
//            if (player.equals(App.getCurrentGame().getCurrentPlayingPlayer())) continue;
//            if (player.getUser().getUsername().equals("NPC") || player.isGuest()) continue;
//            System.out.println(player.getUser().getUsername() + " please enter your comment (y/n)");
//            String input = scanner.nextLine();
//            if (input.equals("n")) {
//                return new Result(false, "Not all players agree, so the game will not be deleted. You are in gameMenu now.");
//            }
//        }
//        App.setLoggedInUser(App.getCurrentGame().getCreator().getUser());
//
//        for (Player player : App.getCurrentGame().getPlayers()) {
//            player.getUser().setLastGame(null);
//            player.getUser().setActiveGame(null);
//        }
//
//        App.getGames().remove(App.getCurrentGame());
//        App.setCurrentGame(null);
//        //App.setCurrentMenu(Menu.MainMenu);
//        return new Result(true, "game deleted, you are in MainMenu now.");
//
//    }
    public Result exitGame() {
        try {
            if (!GameClient.gameStateApiClient.exitGame())
                return new Result(false, "Only the game creator can exit the game.");
            else{
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new MainMenu(new MainMenuController(), GameAssetManagerClient.getGameAssetManager().getSkin()));
                return new Result(true, "Exit the game.");
            }

        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, e.getMessage());
        }
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

//    public Result changeDate(String day) {
//        int amount = Integer.parseInt(day);
//        for (int i = 0; i < amount; i++) {
//            App.getCurrentGame().getDate().goToNextDay();
//            for (ArtisanProduct artisanItemsInProgress : CraftingItem.getAllArtisanProductsInProgress()) {
//                artisanItemsInProgress.goToNextDay(24);
//            }
//        }
//        return new Result(true, amount + " days added successfully");
//    }

    public Result cheatThor(int x, int y) {
        Tile tile = Tile.getTile(x, y);
        if (tile == null)
            return new Result(false, "tile not found");
        LightningController.getLightningController().triggerLightning();
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

    public int countTurns(List<Tile> result) {
        if (result.size() < 3) {
            return 0;
        }
        int turns = 0;
        for (int i = 1; i < result.size() - 1; i++) {
            int dx1 = normalize(result.get(i).getX() - result.get(i - 1).getX());
            int dy1 = normalize(result.get(i).getY() - result.get(i - 1).getX());
            int dx2 = normalize(result.get(i + 1).getX() - result.get(i).getX());
            int dy2 = normalize(result.get(i + 1).getY() - result.get(i).getX());

            if (dx1 != dx2 || dy1 != dy2) {
                turns++;
            }
        }
        return turns;
    }

    private int normalize(int delta) {
        if (delta > 0) return 1;
        if (delta < 0) return -1;
        return 0;
    }

//    public Result walk(int x, int y, Scanner scanner) {
//        List<Tile> result;
//        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
//        Tile destination = Tile.getTile(x, y);
//        if (!(destination.getOwner().equals(player.getPartner())
//            || destination.getOwner().equals(player)
//            || destination.getOwner().equals(NPC.getFatherPlayer()))) {
//            return new Result(false, "you can't walk to this tile because this tile is not for you.");
//        } else if (!destination.isWalkAble()) {
//            return new Result(false, "you can't walk to this tile because this tile is not walkable.");
//        } else if ((result = aStar(player.getX(), player.getY(), x, y, player)) == null) {
//            return new Result(false, "you can't walk to this tile because there is not path to this tile");
//        } else {
//            float energy_needed = (float) (((result.size() - 1) + (10 * countTurns(result))) / 20);
//            System.out.println("your energy : " + player.getEnergy());
//            System.out.printf("energy needed : %.2f\n", energy_needed);
//            System.out.println("do you want to go to the destination? press y or n and press enter");
//            String input = scanner.nextLine();
//            if (input.equals("y")) {
//                double energy = player.getEnergy();
//                player.setEnergy(player.getEnergy() - energy_needed);
//                if (player.getEnergy() <= 0) {
//                    int temp = 0;
//                    for (int i = 0; i < result.size(); i++) {
//                        if ((i + 1) * energy_needed / result.size() >= energy) {
//                            temp = i + 1;
//                            break;
//                        }
//                    }
//                    player.passOut();
//                    player.setEnergy(0);
//                    try {
//                        Tile tile = result.get(temp);
//                        player.setX(tile.getX());
//                        player.setY(tile.getY());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    return new Result(false, "you fainted");
//                } else {
////                    for (Tile tile : result) {
////                        System.out.println(tile.getX() + " " + tile.getY());
////                    }
//                    player.setX(x);
//                    player.setY(y);
//                    return new Result(true, "you are in the destination now");
//                }
//            } else {
//                return new Result(true, "cancellation...");
//            }
//        }
//    }

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
            if (backPackableType == null) {
                continue;
            }
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
            if (backPackableType instanceof ToolType toolType) {
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
        if (Tile.getTile(player.getTileX(), player.getTileY()).getPlaceable() instanceof Store store) {
            if (!store.getType().equals(StoreType.Blacksmith))
                return new Result(false, "The Player is not in Blacksmith");
        } else
            return new Result(false, "The Player is not in a store");

        if (toolName.equalsIgnoreCase("TrashCan")) {
            if (player.getTrashCan().getMaterial().equals(ToolMaterial.Iridium)) {
                return new Result(false, toolName + " is already at max level");
            }
            player.upgradeTrashCan();
            Tool t = player.getTrashCan();
            return new Result(true, toolName + " upgraded to " + player.getTrashCan().getMaterial().name());
        }

        Tool tool = Tool.findToolByName(toolName);
        if (tool == null) {
            return new Result(false, "Tool with this name doesn't exist in your backpack.");
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
        double price = 2000;
        switch (tool.getLevel() + 1) {
            case 1:
                type = ArtisanProductType.CopperBar;
                price = 2000;
                break;
            case 2:
                type = ArtisanProductType.IronBar;
                price = 5000;
                break;
            case 3:
                type = ArtisanProductType.GoldBar;
                price = 10000;
                break;
            case 4:
                type = ArtisanProductType.IridiumBar;
                price = 25000;
                break;
            default:
                type = ArtisanProductType.CopperBar;
        }
        if (toolName.equals("TrashCan")) {
            price = price / 2;
        }
        if (player.getBackPack().getCoin() < price) {
            return new Result(false, "not enough coin");
        }
        if (player.getBackPack().getBackPackItems().containsKey(type)) {
            if (player.getBackPack().getBackPackItems().get(type).size() >= 5) {
                for (int i = 0; i < 5; i++) {
                    player.getBackPack().useItem(type);
                }
                tool.setLevel(tool.getLevel() + 1);
                return new Result(true, toolName + " upgraded to " + tool.getLevelMaterial());
            }
        }

        return new Result(false, "not enough " + type.name());

    }

//    public Result toolUse(String direction) {
//        double leverage = App.getCurrentGame().getDate().getTodayWeatherType().getEnergyConsume();
//        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
//        int x = player.getTileX() + App.handleDirection(Integer.parseInt(direction))[0];
//        int y = player.getTileY() + App.handleDirection(Integer.parseInt(direction))[1];
//
//        Tool tool = App.getCurrentGame().getCurrentPlayingPlayer().getCurrentTool();
//        Tile tile = Tile.getTile(x, y);
//
//        if (tile == null) {
//            return new Result(false, "invalid tile");
//        }
//
//        if (tool.getToolType().equals(ToolType.Hoe)) {
//            double energy = ToolType.Hoe.getEnergyCosts()[tool.getLevel()];
//            if (player.getAbilities().getFarmingLevel() == 4) {
//                energy--;
//            }
//            if (player.getBuff().getBuffType().equals(BuffType.Farming)) {
//                energy--;
//            }
//            energy = Math.max(energy, 0);
//            if (tile.getPlaceable() == null || tile.getPlaceable() instanceof GreenHouse) {
//                tile.setPlowed(true);
//                player.setEnergy(player.getEnergy() - energy * leverage);
//                player.getAbilities().increaseFarmingAbility();
//                return new Result(true, "plowed successfully");
//            }
//            player.setEnergy(player.getEnergy() - energy * leverage);
//            return new Result(true, "Hoe used but incorrectly");
//        } else if (tool.getToolType().equals(ToolType.Pickaxe)) {
//            double energy = ToolType.Pickaxe.getEnergyCosts()[tool.getLevel()];
//            if (player.getAbilities().getMiningLevel() == 4) {
//                energy--;
//            }
//            if (player.getBuff().getBuffType().equals(BuffType.Mining)) {
//                energy--;
//            }
//            if (tile.getPlaceable() instanceof Mineral mineral) {
//
//                if (!ForagingController.canBreakMineral(player.getCurrentTool().getMaterial(),
//                    mineral.getType())) {
//                    energy--;
//                    energy = Math.max(energy, 0);
//                    player.setEnergy(player.getEnergy() - energy * leverage);
//                    return new Result(false, "this type of pickaxe cannot break this mineral");
//                }
//                player.getAbilities().increaseMiningAbility();
//                if (mineral.isForaging())
//                    player.getAbilities().increaseForagingAbility();
//                energy = Math.max(energy, 0);
//                player.setEnergy(player.getEnergy() - energy * leverage);
//                player.getBackPack().addItemToInventory(mineral);
//                tile.setPlaceable(null);
//                if (player.getAbilities().getMiningLevel() < 2) {
//                    return new Result(true, "stone broke successfully");
//                }
//                if (player.getAbilities().getMiningLevel() >= 2) {
//                    player.getBackPack().addItemToInventory(mineral);
//                    return new Result(true, "stone broke successfully and you also got 1 more because of mining level");
//                }
//                return new Result(true, "stone broke successfully");
//
//            } else if (tile.isPlowed()) {
//                tile.setPlowed(false);
//                energy = Math.max(energy, 0);
//                player.setEnergy(player.getEnergy() - energy * leverage);
//                return new Result(true, "unplowed successfully");
//            } else if (tile.getPlaceable() instanceof BackPackable item) {
//                tile.setPlaceable(null);
//                energy = Math.max(energy, 0);
//                player.setEnergy(player.getEnergy() - energy * leverage);
//                return new Result(true, item.getName() + " destroyed successfully");
//            }
//            energy = Math.max(energy - 1, 0);
//            player.setEnergy(player.getEnergy() - energy * leverage);
//            return new Result(true, "you used pickaxe but incorrectly");
//        } else if (tool.getToolType().equals(ToolType.Axe)) {
//            double energy = ToolType.Axe.getEnergyCosts()[tool.getLevel()];
//            if (player.getAbilities().getForagingLevel() == 4) {
//                energy--;
//            }
//            if (player.getBuff().getBuffType().equals(BuffType.Foraging)) {
//                energy--;
//            }
//            if (tile.getPlaceable() instanceof Tree) {
//                player.getAbilities().increaseForagingAbility();
//                tile.setPlaceable(new NormalItem(NormalItemType.Wood));
//                player.setEnergy(player.getEnergy() - energy * leverage);
//                return new Result(true, "you broke tree successfully");
//            }
//            if (tile.getPlaceable() instanceof NormalItem normalItem) {
//                if (normalItem.getType().equals(NormalItemType.Wood)) {
//                    tile.setPlaceable(null);
//                    player.getAbilities().increaseForagingAbility();
//                    player.setEnergy(player.getEnergy() - energy * leverage);
//                    return new Result(true, "you destroyed wood");
//                }
//            }
//            energy--;
//            energy = Math.max(energy, 0);
//            player.setEnergy(player.getEnergy() - energy * leverage);
//            return new Result(true, "you used axe but incorrectly");
//        } else if (tool.getToolType().equals(ToolType.WateringCan)) {
//            double energy = ToolType.WateringCan.getEnergyCosts()[tool.getLevel()];
//            if (player.getAbilities().getForagingLevel() == 4) {
//                energy--;
//            }
//            if (player.getBuff().getBuffType().equals(BuffType.Farming)) {
//                energy--;
//            }
//            if (tile.getPlaceable() instanceof Plant plant) {
//                if (tool.getWateringCanStorage() > 0) {
//                    plant.wateringPlant();
//                    tool.setWateringCanStorage(tool.getWateringCanStorage() - 1);
//                    player.getAbilities().increaseForagingAbility();
//                    return new Result(true, "plant watered sucessfully");
//                }
//            } else if (tile.isWater()) {
//                player.setEnergy(player.getEnergy() - energy * leverage);
//                if (tool.isWateringCanFull()) {
//                    return new Result(true, "watering can is already full");
//                }
//                tool.handleWateringCanStorage();
//                return new Result(true, "watering can is now full of water");
//            }
//        } else if (tool.getToolType().equals(ToolType.Scythe)) {
//            player.setEnergy(player.getEnergy() - 2 * leverage);
//            if (tile.getPlaceable() instanceof NormalItem normalItem) {
//                if (normalItem.getType().equals(NormalItemType.Grass))
//                    tile.setPlaceable(null);
//                else if (normalItem.getType().equals(NormalItemType.Fiber)) {
//                    tile.setPlaceable(null);
//                    player.getBackPack().addItemToInventory(new NormalItem(NormalItemType.Fiber));
//                }
//            } else if (tile.getPlaceable() instanceof Plant plant) {
//                player.getAbilities().increaseFarmingAbility();
//                if (plant instanceof Tree tree) {
//                    tree.harvest();
//                    Fruit fruit = new Fruit(tree.getType().getFruitType());
//                    fruit.setItemQuality();
//                    player.getBackPack().addItemToInventory(
//                        fruit);
//                    if (tree.isForaging())
//                        player.getAbilities().increaseForagingAbility();
//                } else if (plant instanceof Crop crop) {
//                    crop.harvest();
//                }
//            }
//        } else if (tool.getToolType().equals(ToolType.MilkPail)) {
//            player.setEnergy(player.getEnergy() - 4 * leverage);
//            if (tile.getPlaceable() instanceof Animal animal) {
//                if (animal.getAnimalType().equals(AnimalType.Cow)) {
//                    ArrayList<AnimalProduct> toRemoved = new ArrayList<>();
//                    for (AnimalProduct animalProduct : animal.getAnimalProducts()) {
//                        player.getBackPack().addItemToInventory(animalProduct);
//                        toRemoved.add(animalProduct);
//                        if (player.getBackPack().isBackPackFull()) {
//                            animal.getAnimalProducts().removeAll(toRemoved);
//                            StringBuilder sb = new StringBuilder();
//                            for (Map.Entry<AnimalProduct, Integer> entry : Animal.getMapListOfAnimalProducts(toRemoved).entrySet()) {
//                                sb.append(entry.getKey().getAnimalProductType().name()).append(" : ")
//                                    .append(entry.getValue()).append("\n");
//                            }
//                            return new Result(false, "backpack gets full , you collect these -> \n"
//                                + sb.toString());
//                        }
//                    }
//                    StringBuilder sb = new StringBuilder();
//                    for (Map.Entry<AnimalProduct, Integer> entry : Animal.getMapListOfAnimalProducts(toRemoved).entrySet()) {
//                        sb.append(entry.getKey().getAnimalProductType().name()).append(" : ")
//                            .append(entry.getValue()).append("\n");
//                    }
//                    animal.getAnimalProducts().removeAll(toRemoved);
//                    return new Result(true, "you collected all product -> \n " +
//                        sb.toString());
//                }
//            }
//        } else if (tool.getToolType().equals(ToolType.Shear)) {
//            player.setEnergy(player.getEnergy() - 4 * leverage);
//            if (tile.getPlaceable() instanceof Animal animal) {
//                if (animal.getAnimalType().equals(AnimalType.Sheep)) {
//                    if (animal.getAnimalProducts().isEmpty()) {
//                        return new Result(false, "this sheep has no product");
//                    }
//                    ArrayList<AnimalProduct> toRemoved = new ArrayList<>();
//                    for (AnimalProduct animalProduct : animal.getAnimalProducts()) {
//                        player.getBackPack().addItemToInventory(animalProduct);
//                        toRemoved.add(animalProduct);
//                        if (player.getBackPack().isBackPackFull()) {
//                            animal.getAnimalProducts().removeAll(toRemoved);
//                            return new Result(false, "back pack gets full , you collected these -> \n" +
//                                animalProduct.getAnimalProductType().name() + " -> " + toRemoved.size());
//                        }
//                    }
//                    animal.getAnimalProducts().removeAll(toRemoved);
//                    return new Result(true, "you collected all " + toRemoved.size() + " wools of " + animal.getName());
//                }
//            }
//        } else if (tool.getToolType().equals(ToolType.FishingPole)) {
//            if (!tile.isWater()) {
//                return new Result(false, "you should catch fish near water and lakes , here is not water");
//            }
//            double energy = 2;
//            switch (tool.getFishingPoleMaterial()) {
//                case TrainingFishingPole -> energy = 8;
//                case BambooFishingPole -> energy = 6;
//                case FiberglassFishingPole -> energy = 4;
//                case IridiumFishingPole -> energy = 2;
//            }
//            if (player.getAbilities().getFishingLevel() == 4) {
//                energy--;
//            }
//            if (player.getBuff().getBuffType().equals(BuffType.Fishing)) {
//                energy--;
//            }
//            player.setEnergy(player.getEnergy() - energy * leverage);
//            System.out.println(fishing(tool.getFishingPoleMaterial().name()));
//
//        }
//        return new Result(true, "Tool used.");
//    }

    public Result craftingShowRecipes() {
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        Tile tile = Tile.getTile(player.getTileX(), player.getTileY());
        if (tile == null) {
            return new Result(false, "Tile not found");
        }
        if (!(tile.getPlaceable() instanceof Hut)) {
            return new Result(false, "you should be in Hut");
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
        Tile tile = Tile.getTile(player.getTileX(), player.getTileY());
        if (tile == null) {
            return new Result(false, "Tile not found");
        }
        if (!(tile.getPlaceable() instanceof Hut)) {
            return new Result(false, "you should be in Hut");
        }
        CraftingRecipe recipe = CraftingRecipe.findRecipe(itemName);
        if (tile == null) {
            return new Result(false, "Tile not found");
        }
        if (!(tile.getPlaceable() instanceof Hut)) {
            return new Result(false, "for crafting item you should be in Hut");
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
        CraftingItem craftingItem = new CraftingItem(recipe.getTargetItem(), player);
        backPack.addItemToInventory(craftingItem);
        return new Result(true, itemName + " crafted successfully");
    }

//    public Result placeItem(String itemName, String direction) {
//        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
//        CraftingItemType craftingItemType;
//        try {
//            craftingItemType = CraftingItemType.valueOf(itemName);
//        } catch (Exception e) {
//            return new Result(false, "Invalid item name");
//        }
//        if (!player.getBackPack().getBackPackItems().containsKey(craftingItemType)) {
//            return new Result(false, " you dont have " + craftingItemType.name());
//        }
//        int[] direction1 = App.handleDirection(Integer.parseInt(direction));
//        Tile tile = Tile.getTile(player.getTileX() + direction1[0],
//            player.getY() + direction1[1]);
//        if (tile.getPlaceable() != null) {
//            return new Result(false, "tile is full");
//        }
//
//        App.getCurrentGame().getCurrentPlayingPlayer().getBackPack().useItem(craftingItemType);
//        tile.setPlaceable(new CraftingItem(craftingItemType));
//        switch (craftingItemType) {
//            case CherryBomb -> {
//                int range = 3;
//                for (int i = -range; i < range + 1; i++) {
//                    for (int j = -range; j < range + 1; j++) {
//
//                        Tile target = Tile.getTile(tile.getX() + i, tile.getY() + j);
//                        if (target != null) {
//                            target.setPlaceable(null);
//                        }
//                    }
//                }
//            }
//
//            case Bomb -> {
//                int range = 5;
//                for (int i = -range; i < range + 1; i++) {
//                    for (int j = -range; j < range + 1; j++) {
//
//                        Tile target = Tile.getTile(tile.getX() + i, tile.getY() + j);
//                        if (target != null) {
//                            target.setPlaceable(null);
//                        }
//                    }
//                }
//            }
//
//            case MegaBomb -> {
//                int range = 7;
//                for (int i = -range; i < range + 1; i++) {
//                    for (int j = -range; j < range + 1; j++) {
//
//                        Tile target = Tile.getTile(tile.getX() + i, tile.getY() + j);
//                        if (target != null) {
//                            target.setPlaceable(null);
//                        }
//                    }
//                }
//            }
//
//            case Sprinkler -> {
//                int[] dx = {0, 1, 0, -1};
//                int[] dy = {1, 0, -1, 0};
//                for (int i = 0; i < 4; i++) {
//                    Tile target = Tile.getTile(tile.getX() + dx[i], tile.getY() + dy[i]);
//                    if (target != null && target.getPlaceable() instanceof Plant plant) {
//                        plant.wateringPlant();
//                    }
//                }
//            }
//
//            case QualitySprinkler -> {
//                int range = 1;
//                for (int i = -range; i < range + 1; i++) {
//                    for (int j = -range; j < range + 1; j++) {
//
//                        Tile target = Tile.getTile(tile.getX() + i, tile.getY() + j);
//                        if (target != null && target.getPlaceable() instanceof Plant plant) {
//                            plant.wateringPlant();
//                        }
//                    }
//                }
//            }
//
//            case IridiumSprinkler -> {
//                int range = 2;
//                for (int i = -range; i < range + 1; i++) {
//                    for (int j = -range; j < range + 1; j++) {
//
//                        Tile target = Tile.getTile(tile.getX() + i, tile.getY() + j);
//                        if (target != null && target.getPlaceable() instanceof Plant plant) {
//                            plant.wateringPlant();
//                        }
//                    }
//                }
//            }
//
//            case Scarecrow -> {
//                int range = 8;
//                for (int i = -range; i < range + 1; i++) {
//                    for (int j = -range; j < range + 1; j++) {
//
//                        Tile target = Tile.getTile(tile.getX() + i, tile.getY() + j);
//                        if (target != null) {
//                            tile.setCrowImmunity(true);
//                        }
//                    }
//                }
//            }
//
//            case DeluxeScarecrow -> {
//                int range = 12;
//                for (int i = -range; i < range + 1; i++) {
//                    for (int j = -range; j < range + 1; j++) {
//
//                        Tile target = Tile.getTile(tile.getX() + i, tile.getY() + j);
//                        if (target != null) {
//                            tile.setCrowImmunity(true);
//                        }
//                    }
//                }
//            }
//
//            case BeeHouse -> {
//
//            }
//
//            case CheesePress -> {
//
//            }
//
//            case Keg -> {
//
//            }
//
//            case Loom -> {
//
//            }
//
//            case MayonnaiseMachine -> {
//
//            }
//
//            case OilMaker -> {
//
//            }
//
//            case PreservesJar -> {
//
//            }
//
//            case Dehydrator -> {
//
//            }
//
//            case FishSmoker -> {
//
//            }
//
//            case MysticTreeSeed -> {
//
//            }
//        }
//
//
//        return new Result(true, "Item placed Successfully.");
//    }

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
        Tile tile = Tile.getTile(player.getTileX(), player.getTileY());
        if (tile == null) {
            return new Result(false, "Tile not found");
        }
        if (!(tile.getPlaceable() instanceof Hut)) {
            return new Result(false, "you should be in Hut");
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
                if (food.getFoodtype().getName().trim().equals(itemName)) {
                    player.getPlayerMap().getHut().getRefrigerator().getFoods().remove(food);
                    player.getBackPack().addItemToInventory(food);
                }
            }
        }
        return new Result(false, "just put or pick");
    }

    public Result cookingShowRecipes() {
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        Tile tile = Tile.getTile(player.getTileX(), player.getTileY());
        if (tile == null) {
            return new Result(false, "Tile not found");
        }
        if (!(tile.getPlaceable() instanceof Hut)) {
            return new Result(false, "you should be in Hut");
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
        Tile tile = Tile.getTile(player.getTileX(), player.getTileY());
        if (tile == null) {
            return new Result(false, "Tile not found");
        }
        if (!(tile.getPlaceable() instanceof Hut)) {
            return new Result(false, "you should be in Hut");
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
        Tile tile = Tile.getTile(player.getTileX(), player.getTileY());
        if (tile == null) {
            return new Result(false, "Tile not found");
        }
        if (!(tile.getPlaceable() instanceof Hut)) {
            return new Result(false, "you should be in Hut");
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
            switch (food) {
                case TripleShotEspresso -> player.applyTemporaryMaxEnergyBoost(100, 5);
                case RedPlate -> player.applyTemporaryMaxEnergyBoost(50, 3);
                case HashBrowns, FarmersLunch -> {
                    player.setBuff(new Buff(BuffType.Farming, 5));
                    player.applyTemporaryMaxEnergyBoost(0, 0);
                }
                case Pancakes -> {
                    player.setBuff(new Buff(BuffType.Foraging, 11));
                    player.applyTemporaryMaxEnergyBoost(0, 0);
                }
                case SurvivalBurger -> {
                    player.setBuff(new Buff(BuffType.Foraging, 5));
                    player.applyTemporaryMaxEnergyBoost(0, 0);
                }
                case DishOTheSea -> {
                    player.setBuff(new Buff(BuffType.Fishing, 5));
                    player.applyTemporaryMaxEnergyBoost(0, 0);
                }
                case SeafoamPudding -> {
                    player.setBuff(new Buff(BuffType.Fishing, 10));
                    player.applyTemporaryMaxEnergyBoost(0, 0);
                }
                case MinersTreat -> {
                    player.setBuff(new Buff(BuffType.Mining, 5));
                    player.applyTemporaryMaxEnergyBoost(0, 0);
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
        App.getCurrentGame().getCurrentPlayingPlayer().getPlayerMap().getAnimalPlaces().add(animalPlace);
        return new Result(true, "build successfully");
    }

    public Result buyAnimal(String animal, String name) {
        AnimalType animalType;
        try {
            animalType = AnimalType.valueOf(animal);
        } catch (Exception e) {
            return new Result(false, "Invalid animal");
        }

        Animal newAnimal = new Animal(name, animalType,null);
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        double money = player.getBackPack().getCoin();
        if (money < animalType.getPrice()) {
            return new Result(false, "you dont have enough money");
        }
        player.getBackPack().addcoin(-animalType.getPrice());

        List<AnimalPlace> animalPlaces = player.getPlayerMap().getAnimalPlaces();
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
            player.getPlayerMap().getAnimals().add(newAnimal);

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
        Tile tile = Tile.getTile(player.getTileX(), player.getTileY());
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
        for (Animal animal : App.getCurrentGame().getCurrentPlayingPlayer().getPlayerMap().getAnimals()) {
            sb.append(animal.getName()).append(" (").append(animal.getAnimalType()).append(") ").append("\n")
                .append("friendship : ").append(animal.getFriendship()).append("\n")
                .append(animal.isPettedToday() ? "petted today" : "not petted today").append("\n")
                .append(animal.isFedToday() ? "feded today" : "not fed today").append("\n\n");
            if (animal.getTile() != null) {
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
        for (Animal animal : player.getPlayerMap().getAnimals()) {
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

//    public Result fishing(String fishingPole) {
//        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
//        if (!Animal.areWeNearWater(player.getTileX(), player.getTileY())) {
//            return new Result(false, "first go near water");
//        }
//        if (player.getBackPack().isBackPackFull()) {
//            return new Result(false, "your backpack is full");
//        }
//        FishingPoleType fishingPoleType;
//        try {
//            fishingPoleType = FishingPoleType.valueOf(fishingPole);
//        } catch (Exception e) {
//            return new Result(false, "invalid fishing pole");
//        }
//        if (!player.getBackPack().getBackPackItems().containsKey(fishingPoleType)) {
//            return new Result(false, "you dont have this fishing pole in your backpack");
//        }
//
//
//        double R = Math.random();
//        double M = 1;
//        TimeAndDate date = App.getCurrentGame().getDate();
//        switch (date.getTodayWeatherType()) {
//            case Sunny -> M = 1.5;
//            case Rainy -> M = 1.2;
//            case Storm -> M = 0.5;
//            default -> M = 1;
//        }
//        int level = player.getAbilities().getFishingLevel();
//        int count = (int) Math.ceil(R * M * (level + 2));
//        count = Math.min(6, count);
//        double pole = fishingPoleType.getPole();
//        double qualityInt = ((R * (level + 2) * pole) / (7 - M));
//        ItemQuality quality;
//        if (qualityInt < 0.5) {
//            quality = ItemQuality.Regular;
//        } else if (qualityInt < 0.7) {
//            quality = ItemQuality.Silver;
//        } else if (qualityInt < 0.9) {
//            quality = ItemQuality.Gold;
//        } else {
//            quality = ItemQuality.Iridium;
//        }
//        Fish fish = new Fish(null, null);
//        ArrayList<FishType> fishes = new ArrayList<>();
//        if (fishingPoleType.equals(FishingPoleType.TrainingFishingPole)) {
//            fishes.addAll(new ArrayList<>(Arrays.asList
//                (FishType.Sardine, FishType.Perch, FishType.Herring, FishType.SunFish)));
//        } else {
//            for (FishType fishType : FishType.values()) {
//                if (fishType.getSeason().equals(date.getSeason())) {
//                    fishes.add(fishType);
//                }
//            }
//        }
//        if (player.getAbilities().getFishingLevel() != 4) {
//            ArrayList<FishType> fishesToRemove = new ArrayList<>();
//            for (FishType fishType : fishes) {
//                if (fishType.isLegendary()) {
//                    fishesToRemove.add(fishType);
//                }
//            }
//            fishes.removeAll(fishesToRemove);
//        }
//        Random rand = new Random();
//        FishType randomElement = fishes.get(rand.nextInt(fishes.size()));
//        fish.setFishType(randomElement);
//        fish.setQuality(quality);
//        for (int i = 0; i < count; i++) {
//            player.getBackPack().addItemToInventory(fish);
//        }
//        player.getAbilities().increaseFishingAbility();
//        return new Result(true, count + " " + fish.getFishType().getName() + " got caught successfully");
//    }


    public String friendship(String player) {
        return GameClient.getGameStateApiClient().friendship(player);
    }

    public Result talk(String username, String massage) {
        return GameClient.gameStateApiClient.talk(username, massage);
    }

    public String talkHistory(String username) {
        return GameClient.getGameStateApiClient().talkHistory(username);
    }

    public Result gift(String username, String item, String amount) {
        return GameClient.gameStateApiClient.gift(username, item, amount);
    }

    public Result giftList() {
        return GameClient.gameStateApiClient.giftList();
    }

    public Result giftRate(String giftNumber, String rate) {
        return GameClient.gameStateApiClient.giftRate(giftNumber, rate);
    }

    public String giftHistory(String username) {
        return GameClient.getGameStateApiClient().giftHistory(username);
    }

    public Result hug(String username) {
        return GameClient.gameStateApiClient.hug(username);
    }

    public Result flower(String username) {
        return GameClient.gameStateApiClient.flower(username);
    }

    public Result askMarriage(String username, String ring) {
        return GameClient.gameStateApiClient.askMarriage(username, ring);
    }

    public Result respond(String accept, String username) {
        return GameClient.gameStateApiClient.respond(accept, username);
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
            + App.getCurrentGame().getPlayers().get(3).getUser().getUsername() + "\n"
            + App.getCurrentGame().getPlayers().get(0).getUser().getUsername() +
            "\nnew trade request or offer : \n"
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
        } else if (accept.equals("-accept")) {
            if (trade.getTradeType().equals("byMoney")) {
                tradeByMoney2(trade.getSender(), trade.getMatcher(), trade.getMatcher().group("type"));
                trade.getSender().addTradeHistory(trade);
                currentPlayer.addTradeHistory(trade);
                trade.getSender().getTrades().remove(trade);
                currentPlayer.getTrades().remove(trade);
                return new Result(true, "the operation was successful");
            } else {
                tradeByItem2(trade.getSender(), trade.getMatcher(), trade.getMatcher().group("type"));
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

    public Result tradeByMoney2(Player player, Matcher matcher, String type) {
        Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
        if (type.trim().equals("offer")) {
            String item = matcher.group("item");
            int amount = Integer.parseInt(matcher.group("amount"));
            double price = Float.parseFloat(matcher.group("price"));
            if (currentPlayer.getBackPack().getInventorySize(item) >= amount) {
                player.getBackPack().addcoin(-1 * price);
                currentPlayer.getBackPack().addcoin(price);
                for (int i = 0; i < amount; i++) {
                    BackPackable b = currentPlayer.getBackPack().useItem(item);
                    player.getBackPack().addItemToInventory(b);
                }
            } else {
                return new Result(false, "you have not enough items in your inventory");
            }
            return new Result(false, "this username does not exist in this game");
        } else {
            String item = matcher.group("item");
            int amount = Integer.parseInt(matcher.group("amount"));
            double price = Float.parseFloat(matcher.group("price"));
            if (currentPlayer.getBackPack().getCoin() < price) {
                return new Result(false, "you have not enough coins");
            }
            if (player.getBackPack().getInventorySize(item) >= amount) {
                player.getBackPack().addcoin(price);
                currentPlayer.getBackPack().addcoin(-1 * price);
                for (int i = 0; i < amount; i++) {
                    BackPackable b = player.getBackPack().useItem(item);
                    currentPlayer.getBackPack().addItemToInventory(b);
                }
            } else {
                return new Result(false, "this player have not enough items in her/his inventory");
            }
            return new Result(false, "this username does not exist in this game");
        }

    }

    public Result tradeByItem2(Player player, Matcher matcher, String type) {
        Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
        if (type.trim().equals("offer")) {
            String item = matcher.group("item");
            int amount = Integer.parseInt(matcher.group("amount"));
            String targetItem = matcher.group("targetItem");
            int targetAmount = Integer.parseInt(matcher.group("targetAmount"));
            if (player.getBackPack().getInventorySize(targetItem) >= targetAmount) {
                if (currentPlayer.getBackPack().getInventorySize(item) >= amount) {
                    for (int i = 0; i < amount; i++) {
                        BackPackable b = currentPlayer.getBackPack().useItem(item);
                        player.getBackPack().addItemToInventory(b);
                    }
                    for (int i = 0; i < targetAmount; i++) {
                        BackPackable b = player.getBackPack().useItem(targetItem);
                        currentPlayer.getBackPack().addItemToInventory(b);
                    }
                } else {
                    return new Result(false, "you have not enough item in your inventory");
                }
            } else {
                return new Result(false, "this player have not enough targetItems in her/his inventory");
            }

        } else {
            String item = matcher.group("item");
            int amount = Integer.parseInt(matcher.group("amount"));
            String targetItem = matcher.group("targetItem");
            int targetAmount = Integer.parseInt(matcher.group("targetAmount"));
            if (player.getBackPack().getInventorySize(item) >= amount) {
                if (currentPlayer.getBackPack().getInventorySize(targetItem) >= targetAmount) {
                    for (int i = 0; i < amount; i++) {
                        BackPackable b = player.getBackPack().useItem(item);
                        currentPlayer.getBackPack().addItemToInventory(b);
                    }
                    for (int i = 0; i < targetAmount; i++) {
                        BackPackable b = currentPlayer.getBackPack().useItem(targetItem);
                        player.getBackPack().addItemToInventory(b);
                    }
                } else {
                    return new Result(false, "this player have not enough item in your inventory");
                }
            } else {
                return new Result(false, "this player have not enough targetItems in her/his inventory");
            }
        }
        return new Result(false, "this username does not exist in this game");
    }

    public String tradeHistory() {
        return GameClient.getGameStateApiClient().tradeHistory();
    }

    public String tradeList() {
       return GameClient.getGameStateApiClient().tradeList();
    }


    public Result giftNPC(NPC npc, String item,String amount) {
        return GameClient.gameStateApiClient.giftNPC(npc.getName(), item, amount);
    }

    public String friendshipNPCList(String npc) {
        return GameClient.gameStateApiClient.friendshipNPCList(npc);
    }

    public Result questsList() {
        return GameClient.gameStateApiClient.questsList();
    }

    public Result questFinish(String index) {
        return GameClient.gameStateApiClient.questFinish(index);
    }

    public Result showMessage() {
        return GameClient.gameStateApiClient.showMessage();
    }

    public Result deleteMessage(int index) {
        return GameClient.gameStateApiClient.deleteMessage(index);
    }

}
