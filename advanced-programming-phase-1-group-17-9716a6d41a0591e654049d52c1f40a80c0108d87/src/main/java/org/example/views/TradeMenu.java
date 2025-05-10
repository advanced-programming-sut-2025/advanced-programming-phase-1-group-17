package org.example.views;

import org.example.controllers.GameMenuController;
import org.example.models.App;
import org.example.models.enums.Menu;
import org.example.models.enums.TradeMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeMenu implements AppMenu {
    @Override
    public void run(Scanner scanner) {
        GameMenuController controller = new GameMenuController();
        String command = scanner.nextLine();
        Matcher matcher;
        if ((matcher = TradeMenuCommands.tradeByMoney.getMatcher(command)) != null) {
            System.out.println(controller.tradeByMoney(matcher, matcher.group("type"),0));
        }
        else if ((matcher = TradeMenuCommands.tradeByItem.getMatcher(command)) != null) {
            System.out.println(controller.tradeByItem(matcher , matcher.group("type"),0));
        } else if (command.trim().equals("trade list")) {
            System.out.println(controller.tradeList());
        }else if ((matcher = TradeMenuCommands.tradeResponse.getMatcher(command)) != null) {
            System.out.println(controller.tradeResponse(matcher));
        }else if (command.trim().equals("trade history")) {
            System.out.println(controller.tradeHistory());
        }

        else if (command.trim().equals("exit")) {
            App.setCurrentMenu(Menu.GameMenu);
        }
        else {
            System.out.println("Invalid command");
        }
    }
}
