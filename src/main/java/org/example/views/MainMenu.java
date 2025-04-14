package org.example.views;

import java.util.Scanner;
import org.example.controllers.MainMenuController;
import org.example.models.enums.*;
public class MainMenu implements views.AppMenu {
    MainMenuController mainMenuController = new MainMenuController();
    public void run(Scanner scanner) {
        while (true) {
            String command = scanner.nextLine();
            if (command.trim().equals("user logout")) {
                System.out.println("logged out _ you are in login menu");
                break;
            }
            else if (org.example.models.enums.MainMenu.changeMenu.getMatcher(command).matches()) {
                String output = mainMenuController.changeMenu(command);
                System.out.println(output);
                if (output.equals("successfully changeMenu. you are now in gameMenu")) {
                    GameMenu gameMenu = new GameMenu();
                    gameMenu.run(scanner);
                }
                else if (output.equals("successfully changeMenu. you are now in profileMenu")) {
                   ProfileMenu profileMenu = new ProfileMenu();
                    profileMenu.run(scanner);
                }
                else if (output.equals("successfully changeMenu. you are now in avatarMenu")) {
                    AvatarMenu avatarMenu = new AvatarMenu();
                    avatarMenu.run(scanner);
                }
            }
            else {
                System.out.println("invalid command");
            }
        }
    }
}
