package io.github.StardewValley.server.controller.logicControllers;

import io.github.StardewValley.shared.controller.LightningController;
import io.github.StardewValley.shared.models.App;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.Result;
import io.github.StardewValley.shared.models.animal.Animal;
import io.github.StardewValley.shared.models.backpack.BackPackable;
import io.github.StardewValley.shared.models.backpack.BackPackableType;
import io.github.StardewValley.shared.models.crafting.CraftingItem;
import io.github.StardewValley.shared.models.enums.WeatherType;
import io.github.StardewValley.shared.models.map.Tile;
import io.github.StardewValley.shared.models.market.MarketsController;

import java.util.ArrayList;

public class CheatCodeHandler {
    public static String changeTime(String hour) {
        int amount = Integer.parseInt(hour);
        for (int i = 0; i < amount; i++)
            App.getCurrentGame().getDate().increaseHour();
        return getDateTime().getMessage();
    }

    private static Result getDateTime() {
        StringBuilder sb = new StringBuilder();
        sb.append(getTime()).append("\n")
            .append(getDate());
        return new Result(true, sb.toString());
    }

    private static Result getTime() {
        return new Result(true, App.getCurrentGame().getDate().getHour() + " : " +
            App.getCurrentGame().getDate().getMinute());
    }


    private static Result getDate() {
        return new Result(true, App.getCurrentGame().getDate().getDay() + "/" +
            App.getCurrentGame().getDate().getMonth() + "/" + App.getCurrentGame().getDate().getYear());
    }

    public static String changeDate(String day) {
        int amount = Integer.parseInt(day);
        for (int i = 0; i < amount; i++) {
            App.getCurrentGame().getDate().goToNextDay();
            for (CraftingItem craftingItem : CraftingItem.getAllCraftingItems()) {
                if (craftingItem.getArtisanProductInProgress() == null)
                    continue;
                craftingItem.getArtisanProductInProgress().goToNextDay(24);
                craftingItem.updateProgressBar();
            }
        }
        return amount + " days added successfully";
    }

    public static String cheatThor(int x, int y) {
        Tile tile = Tile.getTile(x, y);
        if (tile == null)
            return "tile not found";
        LightningController.getLightningController().triggerLightning();
        tile.lightningStrike();
        return "Successfully lightninged.";
    }

    public static String changeWeather(String input) {
        try {
            App.getCurrentGame().getDate().setTomorrowWeather(WeatherType.valueOf(input));
            return "tomorrow weather changed to "
                + App.getCurrentGame().getDate().getTomorrowWeather().name() + " successfully";
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

    public static String addItem(String itemName, String countStr, Player player) {
        int count;
        try {
            count = Integer.parseInt(countStr);
        } catch (NumberFormatException e) {
            return "Invalid number format for count.";
        }

        ArrayList<Object> result = MarketsController.addItem(itemName, player);

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
        Animal animal = Animal.findAnimalByName(animalName);
        if (animal == null) {
            return "animal not found";
        }

        int amountInt = Integer.parseInt(amount);
        animal.cheatSetFriendship(amountInt);
        return "friendship is now " + animal.getFriendship();
    }

    public static String cheatAddDollars(String count, Player player) {
        double amount;
        try {
            amount = Double.parseDouble(count);
        } catch (Exception e) {
            return "Amount must be number.";
        }

        player.getBackPack().addCoin(amount);
        return "Your new Balance: %.1f".formatted(
            player.getBackPack().getCoin()
        );
    }
}
