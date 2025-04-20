package org.example.controllers;

import org.example.models.App;
import org.example.models.Result;
import org.example.models.enums.MainMenuCommands;
import org.example.models.enums.Menu;

public class MainMenuController {
    public Result exitMenu() {
        App.setCurrentMenu(Menu.LoginMenu);
        App.setLoggedInUser(null);
        return new Result(true, "you are logged out");
    }
    public Result changeMenu(String input) {
        String menu = MainMenuCommands.changeMenu.getMatcher(input).group("menu").trim();
        if (menu.equals("game menu")) {
            App.setCurrentMenu(Menu.GameMenu);
            return new Result(true,"successfully changeMenu. you are now in gameMenu");
        }
        else if (menu.equals("profile menu")) {
            App.setCurrentMenu(Menu.ProfileMenu);
            return new Result(true,"successfully changeMenu. you are now in profileMenu");
        }
        else if (menu.equals("avatar menu")) {
            App.setCurrentMenu(Menu.AvatarMenu);
            return new Result(true,"successfully changeMenu. you are now in avatarMenu");
        }
        else{
            return new Result(true,"invalid menu");
        }
    }
}
