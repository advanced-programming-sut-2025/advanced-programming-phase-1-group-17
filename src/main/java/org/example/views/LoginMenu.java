package org.example.views;


import org.example.controllers.LoginMenuController;
import org.example.models.App;
import org.example.models.enums.LoginMenuCommands;

import java.util.Scanner;

public class LoginMenu implements views.AppMenu {
    LoginMenuController loginMenuController = new LoginMenuController();
    MainMenu mainMenu = new MainMenu();
    public void run(Scanner scanner) {
            String command = scanner.nextLine();
            if (LoginMenuCommands.login.getMatcher(command).matches()) {
                String output = loginMenuController.loginUser(command);
                System.out.println(output);
                if (output.equals("you are logged in successfully")) {
                    App.setCurrentMenu();
                }
            }
            else if (LoginMenuCommands.forgetPassword.getMatcher(command).matches()) {
                System.out.println(loginMenuController.forgetPassword(command));
            }
            else{
                System.out.println("invalid command");
            }
    }
}
