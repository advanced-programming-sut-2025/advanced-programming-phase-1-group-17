package views;

import java.util.Scanner;

import controllers.MainMenuController;
import models.App;
import models.enums.MainMenuCommands;
import models.enums.Menu;

public class MainMenu implements AppMenu {
    MainMenuController mainMenuController = new MainMenuController();

    public void run(Scanner scanner) {
        String command = scanner.nextLine();
        if (command.trim().equals("user logout")) {
            System.out.println("logged out _ you are in login menu");
            App.setLoggedInUser(null);
            App.setCurrentMenu(Menu.LoginMenu);
        } else if (MainMenuCommands.changeMenu.getMatcher(command).matches()) {
            String output = mainMenuController.changeMenu(command);
            System.out.println(output);
            if (output.equals("successfully changeMenu. you are now in gameMenu")) {
                App.setCurrentMenu(Menu.GameMenu);
            } else if (output.equals("successfully changeMenu. you are now in profileMenu")) {
                App.setCurrentMenu(Menu.ProfileMenu);
            } else if (output.equals("successfully changeMenu. you are now in avatarMenu")) {
                App.setCurrentMenu(Menu.AvatarMenu);
            }
        } else {
            System.out.println("invalid command");
        }

    }
}
