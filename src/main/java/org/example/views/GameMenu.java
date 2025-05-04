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
                    matcher.group("username3"),
                    matcher.group("rest"),
                    scanner));
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


        //Tool
        else if ((matcher = GameMenuCommands.ToolsEquip.getMatcher(command))!= null) {
            System.out.println(controller.toolEquip(
                    matcher.group("toolName")
            ));
        } else if ((matcher = GameMenuCommands.ToolsShowCurrent.getMatcher(command))!= null) {
            System.out.println(controller.currentToolShow());
        } else if ((matcher = GameMenuCommands.ToolsShowAvailable.getMatcher(command))!= null) {
            System.out.println(controller.toolsShowAvailable());
        } else if ((matcher = GameMenuCommands.ToolsUpgrade.getMatcher(command))!= null) {
            System.out.println(controller.toolUpgrade(
                    matcher.group("toolName")
            ));
        } else if ((matcher = GameMenuCommands.ToolsUse.getMatcher(command))!= null) {
            System.out.println(controller.toolUse(
                    matcher.group("direction")
            ));
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

        //For Plants
        else if ((matcher = GameMenuCommands.CraftInfo.getMatcher(command)) != null) {
            System.out.println(controller.craftInfo(
                    matcher.group("craftName")
            ));
        } else if ((matcher = GameMenuCommands.Plant.getMatcher(command)) != null) {
            System.out.println(controller.plantSeed(
                    matcher.group("seed"),
                    matcher.group("direction")
            ));
        } else if ((matcher = GameMenuCommands.ShowPlant.getMatcher(command)) != null) {
            System.out.println(controller.showPlant(
                    matcher.group("x"),
                    matcher.group("y")
            ));
        } else if ((matcher = GameMenuCommands.Fertilize.getMatcher(command)) != null) {
            System.out.println(controller.fertilize(
                    matcher.group("fertilizer"),
                    matcher.group("direction")
            ));
        } else if ((matcher = GameMenuCommands.HowMuchWater.getMatcher(command)) != null) {
            System.out.println(controller.howMuchWater());
        }

        //For crafting
        else if ((matcher = GameMenuCommands.CraftingShowRecipes.getMatcher(command)) != null) {
            System.out.println(controller.craftingShowRecipes());
        } else if ((matcher = GameMenuCommands.CraftingCraft.getMatcher(command)) != null) {
            System.out.println(controller.craftingCraft(
                    matcher.group("itemName")
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
        }
        //cooking
        else if((matcher = GameMenuCommands.CookingRefrigerator.getMatcher(command)) != null ) {
            System.out.println(controller);
        } else if ((matcher = GameMenuCommands.CookingShowRecipes.getMatcher(command)) != null) {
            System.out.println(controller.cookingShowRecipes());
        } else if ((matcher = GameMenuCommands.CookingPrepare.getMatcher(command)) != null) {
            System.out.println(controller.cookingPrepare(
                    matcher.group("recipeName")
            ));
        } else if ((matcher = GameMenuCommands.Eat.getMatcher(command)) != null) {
            System.out.println(controller.eat(
                    matcher.group("foodName")
            ));
        }

        //For Artisan
        else if ((matcher = GameMenuCommands.ArtisanUse.getMatcher(command)) != null) {
            System.out.println(controller.artisanUse(
                    matcher.group("artisanUse"),
                    matcher.group("itemNames")
            ));
        } else if ((matcher = GameMenuCommands.ArtisanGet.getMatcher(command)) != null) {
            System.out.println(controller.artisanGet(
                    matcher.group("artisanName")
            ));
        }

        //For Trade
        else if ((matcher = GameMenuCommands.ShowAllProducts.getMatcher(command)) != null) {
            System.out.println(controller.showAllProducts());
        } else if ((matcher = GameMenuCommands.ShowAllAvailableProducts.getMatcher(command)) != null) {
            System.out.println(controller.showAllAvailableProducts());
        } else if ((matcher = GameMenuCommands.Purchase.getMatcher(command)) != null) {
            System.out.println(controller.purchase(
                    matcher.group("productName"),
                    matcher.group("count")
            ));
        } else if ((matcher = GameMenuCommands.CheatAddDollars.getMatcher(command)) != null) {
            System.out.println(controller.cheatAddDollars(
                    matcher.group("count")
            ));
        } else if ((matcher = GameMenuCommands.Sell.getMatcher(command)) != null) {
            System.out.println(controller.sellProduct(
                    matcher.group("productName"),
                    matcher.group("count")
            ));
        }

        // friendship
        else if (command.trim().equals("friendships")) {
            System.out.println(controller.friendship());
        } else if ((matcher = GameMenuCommands.talk.getMatcher(command)) != null) {
            System.out.println(controller.talk(matcher.group("username"), matcher.group("message").trim()));
        } else if ((matcher = GameMenuCommands.talkHistory.getMatcher(command)) != null) {
            System.out.println(controller.talkHistory(matcher.group("username").trim()));
        } else if ((matcher = GameMenuCommands.gift.getMatcher(command)) != null) {
            System.out.println(controller.gift(matcher.group("username").trim()
                    , matcher.group("item").trim()
                    , matcher.group("amount").trim()));
        }else if ((matcher = GameMenuCommands.hug.getMatcher(command))!=null) {
            System.out.println(controller.hug(matcher.group("username").trim()));
        }

        else if (command.trim().equals("show current menu")) {
            System.out.println(App.getCurrentMenu().name());
        } else if (command.trim().equals("menu exit")) {
            App.setCurrentMenu(Menu.MainMenu);
        } else {
            System.out.println("invalid command");
        }
    }
}
