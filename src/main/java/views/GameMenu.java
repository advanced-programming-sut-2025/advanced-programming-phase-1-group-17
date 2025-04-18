package views;

import controllers.GameMenuController;
import models.enums.GameMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu implements AppMenu {
    private final GameMenuController controller = new GameMenuController();

    public void run(Scanner scanner) {
        String command = scanner.nextLine();
        Matcher matcher;
        if ((matcher = GameMenuCommands.startNewGame.getMatcher(command)) != null) {
            System.out.println(controller.newGame(matcher.group("username1"),
                    matcher.group("username2"),
                    matcher.group("username3")));
        } else {
            System.out.println("invalid command");
        }

    }

}
