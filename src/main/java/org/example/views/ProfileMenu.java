package org.example.views;

import org.example.controllers.ProfileMenuController;
import org.example.models.enums.ProfileMenuCommands;
import views.AppMenu;
import java.util.Scanner;
public class ProfileMenu implements AppMenu {
    public void run(Scanner scanner) {
        ProfileMenuController profileMenuController = new ProfileMenuController();
        while (true) {
            String command = scanner.nextLine();
            if (command.trim().equals("user info")) {
                profileMenuController.showUserInfo();
            }
            else if (ProfileMenuCommands.changeUsername.getMatcher(command).matches()) {
                System.out.println(profileMenuController.changeUserName(command));
            }
            else if (ProfileMenuCommands.changeNickName.getMatcher(command).matches()) {
                System.out.println(profileMenuController.changeNickName(command));
            }
            else if (ProfileMenuCommands.changeEmail.getMatcher(command).matches()) {
                System.out.println(profileMenuController.changeEmail(command));
            }
            else if (ProfileMenuCommands.changePassword.getMatcher(command).matches()) {
                System.out.println(profileMenuController.changePassword(command));
            }
            else {
                System.out.println("invalid command");
            }
        }
    }
}
