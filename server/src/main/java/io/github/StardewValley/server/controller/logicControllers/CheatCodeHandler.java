package io.github.StardewValley.server.controller.logicControllers;

import io.github.StardewValley.server.repository.AnimalDataService;
import io.github.StardewValley.shared.dto.AnimalDTO;
import io.github.StardewValley.shared.models.game.Game;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.Result;
import io.github.StardewValley.shared.models.backpack.BackPackable;
import io.github.StardewValley.shared.models.backpack.BackPackableType;
import io.github.StardewValley.shared.models.crafting.CraftingItem;
import io.github.StardewValley.shared.models.enums.WeatherType;
import io.github.StardewValley.shared.models.map.Tile;
import io.github.StardewValley.shared.models.market.MarketsController;

import java.util.ArrayList;

public class CheatCodeHandler {
    public static String changeTime(String hour, Game game) {
        int amount = Integer.parseInt(hour);
        for (int i = 0; i < amount; i++)
            game.getDate().increaseHour(game);
        return getDateTime(game).message();
    }

    private static Result getDateTime(Game game) {
        StringBuilder sb = new StringBuilder();
        sb.append(getTime(game)).append("\n")
            .append(getDate(game));
        return new Result(true, sb.toString());
    }

    private static Result getTime(Game game) {
        return new Result(true, game.getDate().getHour() + " : " +
            game.getDate().getMinute());
    }

    private static Result getDate(Game game) {
        return new Result(true, game.getDate().getDay() + "/" +
            game.getDate().getMonth() + "/" + game.getDate().getYear());
    }

    public static String changeDate(String day, Game game) {
        int amount = Integer.parseInt(day);
        for (int i = 0; i < amount; i++) {
            game.getDate().goToNextDay(game);
            for (CraftingItem craftingItem : game.getAllCraftingItems()) {
                if (craftingItem.getArtisanProductInProgress() == null)
                    continue;
                craftingItem.getArtisanProductInProgress().goToNextDay(24);
            }
        }
        return amount + " days added successfully";
    }

    public static String cheatThor(int x, int y, Game game) {
        Tile tile = game.getTile(x, y);
        if (tile == null)
            return "tile not found";
        game.getLightningLogicController().triggerLightning();
        tile.lightningStrike();
        return "Successfully lightninged.";
    }

    public static String changeWeather(String input, Game game) {
        try {
            game.getDate().setTomorrowWeather(WeatherType.valueOf(input));
            return "tomorrow weather changed to "
                + game.getDate().getTomorrowWeather().name() + " successfully";
        } catch (Exception e) {
            return "valid options : Sunny,Rainy,Storm,Snow";
        }
    }

    public static String energyUnlimited(Player player) {
        player.setMaxEnergy(Double.POSITIVE_INFINITY);
        player.setEnergy(Double.POSITIVE_INFINITY);
        player.setEnergyUnlimited(true);
        return "Energy successfully set to infinity";
    }

    public static String addItem(String itemName, String countStr, Player player, Game game) {
        int count;
        try {
            count = Integer.parseInt(countStr);
        } catch (NumberFormatException e) {
            return "Invalid number format for count.";
        }

        ArrayList<Object> result = MarketsController.addItem(itemName, player, game);

        BackPackableType type = (BackPackableType) result.get(0);
        BackPackable sampleItem = (BackPackable) result.get(1);

        if (type == null && sampleItem == null)
            return "Invalid item name";

        if (player.getBackPack().getBackPackItems().get(type) == null &&
            player.getBackPack().isBackPackFull())
            return "Backpack Full";

        for (int i = 0; i < count; i++) {
            player.getBackPack().addItemToInventory(sampleItem);
        }

        return count + " x " + itemName + " added to backpack.";
    }

    public static String setFriendship(String animalName, String amount) {
        AnimalDTO animal = AnimalDataService.findAnimalByName(animalName);
        if (animal == null) {
            return "animal not found";
        }

        int amountInt = Integer.parseInt(amount);
        animal.setFriendship(amountInt);
        return "friendship is now " + animal.getFriendship();
    }

    public static String cheatAddDollars(String count, Player player) {
        double amount;
        try {
            amount = Double.parseDouble(count);
        } catch (Exception e) {
            return "Amount must be number.";
        }

        player.addCoin(amount);
        return "Your new Balance: %.1f".formatted(
            player.getCoin()
        );
    }
}
