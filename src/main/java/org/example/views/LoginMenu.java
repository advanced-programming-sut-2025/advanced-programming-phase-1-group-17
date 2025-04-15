package org.example.views;


import org.example.controllers.LoginMenuController;
import org.example.models.App;
import org.example.models.enums.LoginMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu implements views.AppMenu {
    LoginMenuController loginMenuController = new LoginMenuController();
    MainMenu mainMenu = new MainMenu();
    public void run(Scanner scanner) {
        String command = scanner.nextLine();
        Matcher matcher;
        if ((matcher = LoginMenuCommands.login.getMatcher(command)) != null) {
            System.out.println(loginMenuController.loginUser(command));
        } else if ((matcher = LoginMenuCommands.forgetPassword.getMatcher(command)) != null) {
            System.out.println(loginMenuController.forgotPassword(command));
        } else {
            System.out.println("invalid command");
        }
    }
}
