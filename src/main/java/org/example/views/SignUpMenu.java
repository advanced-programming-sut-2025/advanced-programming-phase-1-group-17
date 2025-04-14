package org.example.views;

import java.util.Scanner;

import controllers.SignUpMenuController;
import org.example.models.enums.*;
import org.example.models.enums.SignUpMenuCommands;

import java.util.regex.Matcher;

public class SignUpMenu implements views.AppMenu {
    private final SignUpMenuController controller = new SignUpMenuController();

    public void run(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher;

        if ((matcher = SignUpMenuCommands.GoToLoginMenu.getMatcher(input)) != null) {
            System.out.println(controller.enterLoginMenu());
        } else if ((matcher = SignUpMenuCommands.GoToMainMenu.getMatcher(input)) != null) {
            System.out.println(controller.enterMainMenu());
        } else if ((matcher = SignUpMenuCommands.ShowCurrentMenu.getMatcher(input)) != null) {
            System.out.println(controller.showCurrentMenu());
        } else if ((matcher = SignUpMenuCommands.Register.getMatcher(input)) != null) {
            controller.register(
                    matcher.group("username"),
                    matcher.group("password"),
                    matcher.group("passwordConfirm"),
                    matcher.group("nickname"),
                    matcher.group("email"),
                    matcher.group("gender"),
                    scanner
            );
        } else {
            System.out.println("Invalid Command");
        }
    }
}
