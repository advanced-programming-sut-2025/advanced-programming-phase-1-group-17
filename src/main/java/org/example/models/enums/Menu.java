package org.example.models.enums;


import org.example.views.*;

import java.util.Scanner;

public enum Menu {
    LoginMenu(new LoginMenu()),
    SignUpMenu(new SignUpMenu()),
    ExitMenu(new ExitMenu()),
    GameMenu(new GameMenu()),
    ProfileMenu(new ProfileMenu()),
    AvatarMenu(new AvatarMenu()),
    MainMenu(new MainMenu()),
    TradeMenu(new TradeMenu());

    private final AppMenu menu;

    Menu(AppMenu menu) {
        this.menu = menu;
    }

    public void checkCommand(Scanner scanner) {
        this.menu.run(scanner);
    }
}