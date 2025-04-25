package org.example.views;

import org.example.controllers.GameMenuController;
import org.example.models.App;
import org.example.models.enums.GameMenuCommands;
import org.example.models.enums.Menu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu implements AppMenu {
    private final GameMenuController controller = new GameMenuController();

    public void run(Scanner scanner) {
        String command = scanner.nextLine();
        Matcher matcher;
        if ((matcher = GameMenuCommands.StartNewGame.getMatcher(command)) != null) {
            System.out.println(controller.newGame(matcher.group("username1"),
                    matcher.group("username2"),
                    matcher.group("username3")));
            controller.gameMap(scanner);
        } else if ((matcher = GameMenuCommands.ExitGame.getMatcher(command)) != null) {
            System.out.println(controller.exitGame());
        } else if ((matcher = GameMenuCommands.LoadGame.getMatcher(command)) != null) {
            System.out.println(controller.loadGame());
        } else if ((matcher = GameMenuCommands.NextTurn.getMatcher(command)) != null) {
            System.out.println(controller.nextTurn());
        } else if ((matcher = GameMenuCommands.Time.getMatcher(command)) != null) {
            System.out.println(controller.getTime());
        } else if ((matcher = GameMenuCommands.Date.getMatcher(command)) != null) {
            System.out.println(controller.getDate());
        } else if ((matcher = GameMenuCommands.DateTime.getMatcher(command)) != null) {
            System.out.println(controller.getDateTime());
        } else if ((matcher = GameMenuCommands.DayOfTheWeek.getMatcher(command)) != null) {
            System.out.println(controller.getDayOfTheWeek());
        } else if ((matcher = GameMenuCommands.CheatAdvanceTime.getMatcher(command)) != null) {
            System.out.println(controller.changeTime(
                    matcher.group("hour")
            ));
        } else if ((matcher = GameMenuCommands.CheatAdvanceDate.getMatcher(command)) != null) {
            System.out.println(controller.changeDate(
                    matcher.group("day")
            ));
        } else if ((matcher = GameMenuCommands.Season.getMatcher(command)) != null) {
            System.out.println(controller.getSeason());
        } else if ((matcher = GameMenuCommands.CheatThor.getMatcher(command)) != null) {
            System.out.println(controller.cheatThor(
                    Integer.parseInt(matcher.group("x")),
                    Integer.parseInt(matcher.group("y"))
            ));
        } else if ((matcher = GameMenuCommands.Weather.getMatcher(command)) != null) {
            System.out.println(controller.getWeather());
        } else if ((matcher = GameMenuCommands.WeatherForecast.getMatcher(command)) != null) {
            System.out.println(controller.weatherForeCast());
        } else if ((matcher = GameMenuCommands.CheatWeatherSet.getMatcher(command)) != null) {
            System.out.println(controller.changeWeather(
                    matcher.group("type")
            ));
        }

        //For Energy
        else if ((matcher = GameMenuCommands.EnergyShow.getMatcher(command)) != null) {
            System.out.println(controller.energyShow());
        } else if ((matcher = GameMenuCommands.EnergySet.getMatcher(command)) != null) {
            System.out.println(controller.energySet(matcher.group("value")));
        } else if ((matcher = GameMenuCommands.EnergyUnlimited.getMatcher(command)) != null) {
            System.out.println(controller.energyUnlimited());
        }


        //For Inventory
        else if ((matcher = GameMenuCommands.InventoryShow.getMatcher(command)) != null) {
            System.out.println(controller.inventoryShow());
        } else if ((matcher = GameMenuCommands.InventoryTrash.getMatcher(command)) != null) {
            System.out.println(controller.inventoryTrash(
                    matcher.group("itemName"),
                    matcher.group("number")
            ));
        }
        // build greenhouse
        else if ((matcher = GameMenuCommands.GreenhouseBuild.getMatcher(command)) != null) {
            System.out.println(controller.buildGreenHouse());
        }
        // move player
        else if ((matcher = GameMenuCommands.Walk.getMatcher(command)) != null) {
            System.out.println(controller.walk(Integer.parseInt(matcher.group("x"))
                    , Integer.parseInt(matcher.group("y")), scanner));
        }
        // print map
        else if ((matcher = GameMenuCommands.PrintMap.getMatcher(command)) != null) {
            controller.printMap(Integer.parseInt(matcher.group("x"))
                    , Integer.parseInt(matcher.group("y"))
                    , Integer.parseInt(matcher.group("size")));
        } else if (command.trim().equals("help reading map")) {
            controller.helpReadingMap();
        } else if (command.trim().equals("show current menu")) {
            System.out.println(App.getCurrentMenu().name());
        } else if (command.trim().equals("menu exit")) {
            App.setCurrentMenu(Menu.MainMenu);
        } else {
            System.out.println("invalid command");
        }

    }

}
