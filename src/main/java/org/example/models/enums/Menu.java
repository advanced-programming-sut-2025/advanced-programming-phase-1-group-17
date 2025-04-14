package org.example.models.enums;


import org.example.views.*;
import org.example.views.GameMenu;
import org.example.views.LoginMenu;
import org.example.views.ProfileMenu;
import org.example.views.SignUpMenu;

import java.util.Scanner;

public enum Menu {
    LoginMenu(new LoginMenu()),
    SignUpMenu(new SignUpMenu()),
    ExitMenu(new ExitMenu()),
    GameMenu(new GameMenu()),
    ProfileMenu(new ProfileMenu());

    private final views.AppMenu menu;

    Menu(views.AppMenu menu) {
        this.menu = menu;
    }

    public void checkCommand(Scanner scanner) {
        this.menu.run(scanner);
    }
}