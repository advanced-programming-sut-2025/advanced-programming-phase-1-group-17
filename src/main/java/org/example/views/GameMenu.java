package org.example.views;

import org.example.controllers.GameMenuController;
import org.example.models.enums.GameMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu implements views.AppMenu {

    public void run(Scanner scanner) {
        String command = scanner.nextLine();
        GameMenuController gameMenuController = new GameMenuController();
        Matcher matcher;
        if ((matcher = GameMenuCommands.startNewGame.getMatcher(command)) != null) {
            System.out.println(gameMenuController.newGame(matcher.group("username1"),
                    matcher.group("username2"),
                    matcher.group("username3")));
        } else if ((matcher =) != null) {

        } else {
            System.out.println("invalid command");
        }

    }

}
