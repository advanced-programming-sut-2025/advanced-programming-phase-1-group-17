package io.github.StardewValley.controllers;

import io.github.StardewValley.Main;
import io.github.StardewValley.controllers.helperControllers.MarketsController;
import io.github.StardewValley.models.*;
import io.github.StardewValley.models.animal.Animal;
import io.github.StardewValley.models.artisan.ArtisanProduct;
import io.github.StardewValley.models.crafting.CraftingItem;
import io.github.StardewValley.models.enums.GameMenuCommands;
import io.github.StardewValley.models.enums.WeatherType;
import io.github.StardewValley.models.map.Tile;
import io.github.StardewValley.views.CheatCodeTerminal;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class CheatCodeTerminalController {
    private CheatCodeTerminal view;
    private final MarketsController marketsController = new MarketsController();

    public void setView(CheatCodeTerminal cheatCodeTerminal) {
        this.view = cheatCodeTerminal;
    }

    public void handleCommand(String command) {
        Matcher matcher;
        String result = "Invalid Command";

        if ((matcher = GameMenuCommands.CheatAdvanceTime.getMatcher(command)) != null) {
            result = changeTime(
                matcher.group("hour")
            );
        } else if ((matcher = GameMenuCommands.CheatAdvanceDate.getMatcher(command)) != null) {
            result = changeDate(
                matcher.group("day")
            );
        } else if ((matcher = GameMenuCommands.CheatThor.getMatcher(command)) != null) {
            result = cheatThor(
                Integer.parseInt(matcher.group("x")),
                Integer.parseInt(matcher.group("y"))
            );
        } else if ((matcher = GameMenuCommands.CheatWeatherSet.getMatcher(command)) != null) {
            result = changeWeather(
                matcher.group("type")
            );
        } else if ((matcher = GameMenuCommands.EnergyUnlimited.getMatcher(command)) != null) {
            result = energyUnlimited();
        } else if ((matcher = GameMenuCommands.CheatAddItem.getMatcher(command)) != null) {
            result = addItem(matcher.group("itemName"), matcher.group("count"));
        } else if ((matcher = GameMenuCommands.CheatSetFriendshipWithAnimal.getMatcher(command)) != null) {
            result = setFriendship(matcher.group("animalName"),
                matcher.group("amount"));
        } else if ((matcher = GameMenuCommands.CheatAddDollars.getMatcher(command)) != null) {
            result = cheatAddDollars(
                matcher.group("count")
            );
        }
        view.getOutputArea().setText("%s\n>%s\n%s"
            .formatted(view.getOutputArea().getText().trim(), view.getInputField().getText(), result));
    }

    private String changeTime(String hour) {
        int amount = Integer.parseInt(hour);
        for (int i = 0; i < amount; i++)
            App.getCurrentGame().getDate().increaseHour();
        return getDateTime().getMessage();
    }

    private Result getDateTime() {
        StringBuilder sb = new StringBuilder();
        sb.append(getTime()).append("\n")
            .append(getDate());
        return new Result(true, sb.toString());
    }

    private Result getTime() {
        return new Result(true, App.getCurrentGame().getDate().getHour() + " : " +
            App.getCurrentGame().getDate().getMinute());
    }


    private Result getDate() {
        return new Result(true, App.getCurrentGame().getDate().getDay() + "/" +
            App.getCurrentGame().getDate().getMonth() + "/" + App.getCurrentGame().getDate().getYear());
    }

    public String changeDate(String day) {
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

    private String cheatThor(int x, int y) {
        Tile tile = Tile.getTile(x, y);
        if (tile == null)
            return "tile not found";
        tile.lightningStrike();
        return "Successfully lightninged.";
    }

    private String changeWeather(String input) {
        try {
            App.getCurrentGame().getDate().setTomorrowWeather(WeatherType.valueOf(input));
            return "tomorrow weather changed to "
                + App.getCurrentGame().getDate().getTomorrowWeather().name() + " successfully";
        } catch (Exception e) {
            return "valid options : Sunny,Rainy,Storm,Snow";
        }
    }

    private String energyUnlimited() {
        App.getCurrentGame().getCurrentPlayingPlayer().setEnergy(Double.POSITIVE_INFINITY);
        return "Energy successfully set to infinity";
    }

    private String addItem(String itemName, String countStr) {
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        int count;
        try {
            count = Integer.parseInt(countStr);
        } catch (NumberFormatException e) {
            return "Invalid number format for count.";
        }

        ArrayList<Object> result = marketsController.addItem(itemName);

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

    private String setFriendship(String animalName, String amount) {
        Animal animal = Animal.findAnimalByName(animalName);
        if (animal == null) {
            return "animal not found";
        }

        int amountInt = Integer.parseInt(amount);
        animal.cheatSetFriendship(amountInt);
        return "friendship is now " + animal.getFriendship();
    }

    public String cheatAddDollars(String count) {
        double amount;
        try {
            amount = Double.parseDouble(count);
        } catch (Exception e) {
            return "Amount must be number.";
        }

        App.getCurrentGame().getCurrentPlayingPlayer().getBackPack().addCoin(amount);
        return "Your new Balance: %.1f".formatted(
            App.getCurrentGame().getCurrentPlayingPlayer().getBackPack().getCoin()
        );
    }

    public void exit() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(Main.getGameView());
    }
}
