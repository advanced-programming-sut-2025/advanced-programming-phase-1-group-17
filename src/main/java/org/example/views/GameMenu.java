package org.example.views;

import org.example.controllers.GameMenuController;
import org.example.display;
import org.example.models.App;
import org.example.models.enums.GameMenuCommands;
import org.example.models.enums.Menu;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.zip.CheckedOutputStream;

public class GameMenu implements AppMenu {
    private final GameMenuController controller = new GameMenuController();

    public void run(Scanner scanner) {
        String command = scanner.nextLine();
        Matcher matcher;
        if ((matcher = GameMenuCommands.startNewGame.getMatcher(command)) != null) {
            System.out.println(controller.newGame(matcher.group("username1"),
                    matcher.group("username2"),
                    matcher.group("username3")));
            controller.gameMap(scanner);
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
        else if (command.trim().equals("greenhouse build")) {
            System.out.println(controller.buildGreenHouse());
        }
        // move player
        else if ((matcher = GameMenuCommands.Walk.getMatcher(command)) != null) {
            System.out.println(controller.walk(Integer.parseInt(matcher.group("x"))
                    , Integer.parseInt(matcher.group("y")), scanner));
        }
        // print map
        else if ((matcher = GameMenuCommands.printMap.getMatcher(command)) != null) {
            controller.printMap(Integer.parseInt(matcher.group("x"))
                    , Integer.parseInt(matcher.group("y"))
                    , Integer.parseInt(matcher.group("size")));
        } else if (command.trim().equals("show current menu")) {
            System.out.println(App.getCurrentMenu().name());
        } else if (command.trim().equals("menu exit")) {
            App.setCurrentMenu(Menu.MainMenu);
        } else {
            System.out.println("invalid command");
        }

    }

}
