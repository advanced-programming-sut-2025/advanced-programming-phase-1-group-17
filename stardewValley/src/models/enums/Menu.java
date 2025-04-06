package models.enums;

import views.*;
import views.GameMenu;
import views.LoginMenu;
import views.ProfileMenu;
import views.SignUpMenu;

import java.util.Scanner;

public enum Menu {
    LoginMenu(new LoginMenu()),
    SignUpMenu(new SignUpMenu()),
    ExitMenu(new ExitMenu()),
    GameMenu(new GameMenu()),
    ProfileMenu(new ProfileMenu());

    private final AppMenu menu;

    Menu(AppMenu menu) {
        this.menu = menu;
    }

    public void checkCommand(Scanner scanner) {
        this.menu.run(scanner);
    }
}