package org.example.views;


import org.example.controllers.LoginMenuController;
import org.example.models.App;
import org.example.models.enums.LoginMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu implements AppMenu {
    LoginMenuController loginMenuController = new LoginMenuController();
    MainMenu mainMenu = new MainMenu();
    public void run(Scanner scanner) {
        String command = scanner.nextLine();
        Matcher matcher;
        if ((matcher = LoginMenuCommands.Login.getMatcher(command)) != null) {
            System.out.println(loginMenuController.loginUser(command));
        } else if ((matcher = LoginMenuCommands.ForgetPassword.getMatcher(command)) != null) {
            System.out.println(loginMenuController.forgetPassword(command,scanner));
        } else if ((matcher = LoginMenuCommands.Exit.getMatcher(command)) != null){
            loginMenuController.exit();
        } else if (command.trim().equals("show current menu")) {
            System.out.println(App.getCurrentMenu().name());
        }
        else {
            System.out.println("invalid command");
        }
    }
}
