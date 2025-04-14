package org.example.views;


import org.example.controllers.LoginMenuController;

import java.util.Scanner;

public class LoginMenu implements views.AppMenu {
    LoginMenuController loginMenuController = new LoginMenuController();
    MainMenu mainMenu = new MainMenu();
    public void run(Scanner scanner) {
        while (true) {
            String command = scanner.nextLine();
            if (org.example.models.enums.LoginMenu.login.getMatcher(command).matches()) {
                String output = loginMenuController.loginUser(command);
                System.out.println(output);
                if (output.equals("you are logged in successfully")) {
                    mainMenu.run(scanner);
                }
            }
            else if (org.example.models.enums.LoginMenu.forgetPassword.getMatcher(command).matches()) {
                System.out.println(loginMenuController.forgetPassword(command));
            }
            else{
                System.out.println("invalid command");
            }
        }
    }
}
