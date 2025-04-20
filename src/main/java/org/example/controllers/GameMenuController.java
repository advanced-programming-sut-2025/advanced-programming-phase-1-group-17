package org.example.controllers;

import org.example.models.*;
import org.example.models.map.GreenHouse;
import org.example.models.map.Tile;
import org.example.models.plant.Seed;
import org.example.models.tools.BackPack;
import org.example.models.tools.Tool;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


public class GameMenuController {
    //for walk
    static int numOfTiles = 0;

    public Result newGame(String username1, String username2, String username3) {
        //TODO handel errors
        User user1, user2, user3;
        if (username1 == null) {
            user1 = new User();
            user1.setUsername("guest0");
        } else {
            user1 = App.getUserWithUsername(username1);
        }
        if (username2 == null) {
            user2 = new User();
            user2.setUsername("guest1");
        } else {
            user2 = App.getUserWithUsername(username2);
        }
        if (username3 == null) {
            user3 = new User();
            user3.setUsername("guest2");
        } else {
            user3 = App.getUserWithUsername(username3);
        }
        Game game = new Game(user1, user2, user3);
        App.setCurrentGame(game);
        App.getGames().add(game);
        return new Result(true, "new game created Successfully");
    }

    public Result gameMap(String mapNumber) {
        return new Result(true, "t");
    }


    public Result loadGame() {
        return new Result(false, "t");
    }


    public Result exitGame() {
        return new Result(false, "t");

    }

    public Result removeCurrentGame() {
        return new Result(false, "t");
    }

    public Result nextTurn() {
        return new Result(true, "Switched to %s".formatted(App.getCurrentGame().
                getCurrentPlayingPlayer().getUser().getUsername()));
    }

    public Result getTime() {
        return new Result(false, "t");
    }

    public Result getDate() {
        return new Result(false, "t");
    }

    public Result getDateTime() {
        return new Result(false, "t");
    }

    public Result getDayOfTheWeek() {
        return new Result(false, "t");
    }

    public Result getSeason() {
        return new Result(false, "t");
    }

    public Result changeTime(String input) {
        return new Result(false, "t");
    }

    public Result changeDate(String input) {
        return new Result(false, "t");
    }

    public Result getWeather() {
        return new Result(false, "t");
    }

    public Result WeatherForeCast(String input) {
        return new Result(false, "t");
    }

    public Result changeWeather(String input) {
        return new Result(false, "t");
    }

    public Result buildGreenHouse() {
        Player player = App.getCurrentPlayer();
        if (player.getCoin() >= 1000 && player.getWood() >= 500) {
            player.addCoin(-1000);
            player.addWood(-500);
            player.getPlayerMap().setGreenHouse(new GreenHouse(player));
            return new Result(true, "GreenHouse created Successfully");
        } else {
            return new Result(false, "you don't have enough coin and wood");
        }
    }

    public boolean isValid(int x, int y, int rows, int cols, boolean[][] visited, Tile tile, Player player) {
        return x >= 0 && y >= 0 && x < rows && y < cols &&
                tile.isWalkAble() && !visited[x][y]
                && tile.getOwner().equals(player);
    }

    public boolean bfs(int startX, int startY, int endX, int endY, boolean[][] visited, int rows, int cols) {
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startX, startY});
        int numOfTiles2 = 0;
        while (!queue.isEmpty()) {
            numOfTiles2++;
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];
            if (x == endX && y == endY) {
                numOfTiles = numOfTiles2;
                return true;
            }
            for (int i = 0; i < 8; i++) {
                int nextX = x + dx[i];
                int nextY = y + dy[i];
                Tile tile = Tile.getTile(nextX, nextY);
                if (isValid(nextX, nextY, rows, cols, visited, tile, App.getCurrentPlayer())) {
                    visited[nextX][nextY] = true;
                    queue.add(new int[]{nextX, nextY});
                }
            }
        }
        return false;
    }

    public Result walk(int x, int y, Scanner scanner) {
        Player player = App.getCurrentPlayer();
        for (Tile tile : App.getCurrentGame().getTiles()) {
            if (tile.getX() == x && tile.getY() == y) {
                if (tile.isWalkAble()) {
                    //TODO BFS
                    int rows = 0;
                    int cols = 0;
                    boolean[][] visited = new boolean[rows][cols];
                    visited[x][y] = true;
                    if (!bfs(player.getX(), player.getY(), x, y, visited, rows, cols)) {
                        return new Result(false, "there is no way");
                    } else {
                        System.out.println("your energy : " + player.getEnergy());
                        System.out.println("energy needed : " + numOfTiles / 20);
                        System.out.println("would you still go to your destination? (press y or n)");
                        String answer = scanner.nextLine();
                            if (answer.equals("y")) {
                                if (player.getEnergy() >= numOfTiles / 20) {
                                    player.setEnergy(player.getEnergy() - numOfTiles / 20);
                                    //TODO
                                    return new Result(true, "you have reached your destination");
                                } else {
                                    //TODO
                                }
                            }
                            else {
                                return new Result(false, "walk cancelled");
                            }

                    }
                } else {
                    return new Result(false, "this tile is not walkable");
                }
                break;
            }
        }

        return new Result(false, "t");
    }

    public Result printMap(int x, int y, int size) {
        return new Result(false, "t");
    }

    public Result helpReadingMap() {
        return new Result(false, "t");
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

        result.append("Seeds: \n");
        for (Seed seed : backPack.getSeeds().keySet()) {
            result.append("%s: %d\n".formatted(seed.getType().name(), backPack.getSeeds().get(seed)));
        }

        result.append("\nTools: \n");
        for (Tool tool : backPack.getTools().keySet()) {
            result.append("%s: %d\n".formatted(tool, backPack.getTools().get(tool)));
        }

        result.append("Products: \n");
        for (Product product : backPack.getProducts().keySet()) {
            result.append("%s: %d\n".formatted(product.getName(), backPack.getProducts().get(product)));
        }
        return new Result(true, result.toString().trim());
    }

    public Result inventoryTrash(String itemName, String number) {
        itemName = itemName.trim().toLowerCase();

        BackPack backPack = App.getCurrentGame().getCurrentPlayingPlayer().getBackPack();
        for (Product product : backPack.getProducts().keySet()) {
            if (product.getName().equals(itemName)) {
                if (number == null) {
                    backPack.getProducts().remove(product);
                    return new Result(true, "Completely deleted %s from your inventory"
                            .formatted(product.getName()));
                } else {
                    backPack.getProducts().compute(product, (k, oldQuantity) -> oldQuantity - Integer.parseInt(number));
                    return new Result(true, "Deleted %d of %s from your inventory"
                            .formatted(Integer.parseInt(number), product.getName()));
                }
            }
        }

        for (Seed seed : backPack.getSeeds().keySet()) {
            if (seed.getType().name().equals(itemName)) {
                if (number == null) {
                    backPack.getSeeds().remove(seed);
                    return new Result(true, "Completely deleted %s from your inventory"
                            .formatted(seed.getType().name()));
                } else {
                    backPack.getSeeds().compute(seed, (k, oldQuantity) -> oldQuantity - Integer.parseInt(number));
                    return new Result(true, "Deleted %d of %s from your inventory"
                            .formatted(Integer.parseInt(number), seed.getType().name()));
                }
            }
        }

        for (Tool tool : backPack.getTools().keySet()) {
            if (tool.getName().name().equals(itemName)) {
                if (number == null) {
                    backPack.getTools().remove(tool);
                    return new Result(true, "Completely deleted %s from your inventory"
                            .formatted(tool.getName().name()));
                } else {
                    backPack.getTools().compute(tool, (k, oldQuantity) -> oldQuantity - Integer.parseInt(number));
                    return new Result(true, "Deleted %d of %s from your inventory"
                            .formatted(Integer.parseInt(number), tool.getName().name()));
                }
            }
        }
        return new Result(false, "Item with this name doesn't exist in your backpack.");
    }

    public Result toolEquip(String toolName) {
        return new Result(false, "t");
    }

    public Result currentToolShow(String toolName) {
        return new Result(false, "t");
    }

    public Result toolsShow() {
        return new Result(false, "t");
    }

    public Result toolUpgrade(String toolName) {
        return new Result(false, "t");
    }

    public Result toolUse(String direction) {
        return new Result(false, "t");
    }

    public Result craftInfo(String name) {
        return new Result(false, "t");
    }

    public Result plantSeed(String seed, String direction) {
        return new Result(false, "t");
    }

    public Result showPlant(String x, String y) {
        return new Result(false, "t");
    }

    public Result fertilize(String fertilizer, String direction) {
        return new Result(false, "t");
    }

    public Result howMuchWater() {
        return new Result(false, "t");
    }

    public Result craftingShowRecipes() {
        return new Result(false, "t");
    }

    public Result craftingCraft(String itemName) {
        return new Result(false, "t");
    }

    public Result placeItem(String itemName, String direction) {
        return new Result(false, "t");
    }

    public Result addItem(String itemName, String number) {
        return new Result(false, "t");
    }

    public Result cookingPrepare(String recipeName) {
        return new Result(false, "t");
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

    public Result artisianUse(String artisianName, String itemName) {
        return new Result(false, "t");
    }

    public Result artisianGet(String artisianName) {
        return new Result(false, "t");
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
        return new Result(false, "t");
    }

    public Result talk(String username, String massage) {
        return new Result(false, "t");
    }

    public Result talkHistory(String username) {
        return new Result(false, "t");
    }

    public Result gift(String username, String item, String amount) {
        return new Result(false, "t");
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
