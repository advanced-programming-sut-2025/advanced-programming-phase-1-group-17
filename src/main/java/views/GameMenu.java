package views;

import controllers.GameMenuController;
import models.enums.GameMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu implements AppMenu {
    private final GameMenuController controller = new GameMenuController();

    public void run(Scanner scanner) {
        String command = scanner.nextLine();
        Matcher matcher;
        if ((matcher = GameMenuCommands.startNewGame.getMatcher(command)) != null) {
            System.out.println(controller.newGame(matcher.group("username1"),
                    matcher.group("username2"),
                    matcher.group("username3")));
        }

        //For Energy
        else if ((matcher = GameMenuCommands.EnergyShow.getMatcher(command))!= null) {
            System.out.println(controller.energyShow());
        } else if ((matcher = GameMenuCommands.EnergySet.getMatcher(command))!= null) {
            System.out.println(controller.energySet(matcher.group("value")));
        } else if ((matcher = GameMenuCommands.EnergyUnlimited.getMatcher(command))!= null) {
            System.out.println(controller.energyUnlimited());
        }


        //For Inventory
        else if ((matcher = GameMenuCommands.InventoryShow.getMatcher(command))!= null) {
            System.out.println(controller.inventoryShow());
        }  else if ((matcher = GameMenuCommands.InventoryTrash.getMatcher(command))!= null) {
            System.out.println(controller.inventoryTrash(
                    matcher.group("itemName"),
                    matcher.group("number")
            ));
        }


        else {
            System.out.println("invalid command");
        }

    }

}
