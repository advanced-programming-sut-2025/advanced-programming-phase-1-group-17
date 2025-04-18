package controllers;

import models.*;
import models.plant.Seed;
import models.tools.BackPack;
import models.tools.Tool;


public class GameMenuController {
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

    public Result gameMap(String mapNumber){
        return new Result(true, "t");
    }


    public Result loadGame() {return new Result(false,"t");

    }


    public Result exitGame() {return new Result(false,"t");

    }

    public Result removeCurrentGame() {return new Result(false,"t");
    }

    public Result nextTurn(){return new Result(false,"t");
    }

    public Result getTime() {return new Result(false,"t");
    }

    public Result getDate() {return new Result(false,"t");
    }

    public Result getDateTime() {return new Result(false,"t");
    }

    public Result getDayOfTheWeek() {return new Result(false,"t");
    }

    public Result getSeason() {return new Result(false,"t");
    }

    public Result changeTime(String input) {return new Result(false,"t");
    }

    public Result changeDate(String input) {return new Result(false,"t");
    }

    public Result getWeather() {return new Result(false,"t");
    }

    public Result WeatherForeCast(String input) {return new Result(false,"t");
    }

    public Result changeWeather(String input) {return new Result(false,"t");
    }

    public Result buildGreenHouse() {return new Result(false,"t");
    }

    public Result walk(int x, int y) {return new Result(false,"t");
    }

    public Result printMap(int x, int y, int size) {return new Result(false,"t");
    }

    public Result helpReadingMap() {return new Result(false,"t");
    }

    public Result energyShow() {
        return new Result(true, "%f".formatted(
                App.getCurrentGame().getCurrentPlayingPlayer().getEnergy()));
    }

    public Result energySet(String value) {
        double energy;
        try{
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
                }
                else {
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
                }
                else {
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
                }
                else {
                    backPack.getTools().compute(tool, (k, oldQuantity) -> oldQuantity - Integer.parseInt(number));
                    return new Result(true, "Deleted %d of %s from your inventory"
                            .formatted(Integer.parseInt(number), tool.getName().name()));
                }
            }
        }
        return new Result(false, "Item with this name doesn't exist in your backpack.");
    }

    public Result toolEquip(String toolName) {return new Result(false,"t");
    }

    public Result currentToolShow(String toolName) {return new Result(false,"t");
    }

    public Result toolsShow() {return new Result(false,"t");
    }

    public Result toolUpgrade(String toolName) {return new Result(false,"t");
    }

    public Result toolUse(String direction) {return new Result(false,"t");
    }

    public Result craftInfo(String name) {return new Result(false,"t");
    }

    public Result plantSeed(String seed, String direction) {return new Result(false,"t");
    }

    public Result showPlant(String x, String y) {return new Result(false,"t");
    }

    public Result fertilize(String fertilizer, String direction) {return new Result(false,"t");
    }

    public Result howMuchWater() {return new Result(false,"t");
    }

    public Result craftingShowRecipes() {return new Result(false,"t");
    }

    public Result craftingCraft(String itemName) {return new Result(false,"t");
    }

    public Result placeItem(String itemName, String direction) {return new Result(false,"t");
    }

    public Result addItem(String itemName, String number) {return new Result(false,"t");
    }

    public Result cookingPrepare(String recipeName) {return new Result(false,"t");
    }

    public Result eat(String foodName) {return new Result(false,"t");
    }

    public Result build(String name, String x, String y) {return new Result(false,"t");
    }

    public Result buyAnimal(String animal, String name) {return new Result(false,"t");
    }

    public Result pet(String name) {return new Result(false,"t");
    }

    public Result setFriendship(String animalName, String amount) {return new Result(false,"t");
    }

    public Result animals() {return new Result(false,"t");
    }

    public Result shepherdAnimal(String animalName, String x, String y) {return new Result(false,"t");
    }

    public Result feedHay(String animalName) {return new Result(false,"t");
    }

    public Result produce() {return new Result(false,"t");
    }

    public Result collectProduct(String name) {return new Result(false,"t");
    }

    public Result sellAnimal(String name) {return new Result(false,"t");
    }

    public Result fishing(String fishingPole) {return new Result(false,"t");
    }

    public Result artisianUse(String artisianName, String itemName) {return new Result(false,"t");
    }

    public Result artisianGet(String artisianName) {return new Result(false,"t");
    }

    public Result showAllProducts() {return new Result(false,"t");
    }

    public Result showAllAvailableProducts() {return new Result(false,"t");
    }

    public Result purchase(String productName, String count) {return new Result(false,"t");
    }

    public Result addDollars(String count) {return new Result(false,"t");
    }

    public Result sellProduct(String productName, String count) {return new Result(false,"t");
    }

    public Result friendship() {return new Result(false,"t");
    }

    public Result talk(String username, String massage) {return new Result(false,"t");
    }

    public Result talkHistory(String username) {return new Result(false,"t");
    }

    public Result gift(String username, String item, String amount) {return new Result(false,"t");
    }

    public Result giftList() {return new Result(false,"t");
    }

    public Result giftRate(String giftNumber, String rate) {return new Result(false,"t");
    }

    public Result giftHistory(String username) {return new Result(false,"t");
    }

    public Result hug(String username) {return new Result(false,"t");
    }

    public Result flower(String username) {return new Result(false,"t");
    }

    public Result askMarriage(String username, String marriage) {return new Result(false,"t");
    }

    public Result respond(String username) {return new Result(false,"t");
    }

    public Result startTrade() {return new Result(false,"t");
    }

    public Result trade(String username, String type, String amount, String price, String targetItem) {return new Result(false,"t");
    }

    public Result tradeResponse(String acceptance, String id) {return new Result(false,"t");
    }

    public Result tradeHistory() {return new Result(false,"t");
    }

    public Result tradeList() {return new Result(false,"t");
    }

    public Result meetNPC(String npcName) {return new Result(false,"t");
    }

    public Result giftNPC(String NPCName, String item) {return new Result(false,"t");
    }

    public Result friendshipNPCList() {return new Result(false,"t");
    }

    public Result questsList() {return new Result(false,"t");
    }

    public Result questFinish(String index) {return new Result(false,"t");
    }


}
