package org.example.views;

import java.util.Scanner;

import org.example.controllers.MainMenuController;
import org.example.models.App;
import org.example.models.enums.MainMenuCommands;
import org.example.models.enums.Menu;

public class MainMenu implements AppMenu {
    MainMenuController mainMenuController = new MainMenuController();

    public void run(Scanner scanner) {
        String command = scanner.nextLine();
        if (command.trim().equals("user logout")) {
            System.out.println(mainMenuController.exitMenu());
        } else if (MainMenuCommands.changeMenu.getMatcher(command).matches()) {
            System.out.println(mainMenuController.changeMenu(command));
        } else if (command.trim().equals("show current menu")) {
            System.out.println(App.getCurrentMenu().name());
        } else {
            System.out.println("invalid command");
        }

    }
}
