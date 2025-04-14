package org.example.controllers;

import org.example.models.Result;
import org.example.models.enums.MainMenu;

public class MainMenuController {
    public String changeMenu(String input) {
        String menu = MainMenu.changeMenu.getMatcher(input).group("menu").trim();
        if (menu.equals("game")) {
            return "successfully changeMenu. you are now in gameMenu";
        }
        else if (menu.equals("profile")) {
            return "successfully changeMenu. you are now in profileMenu";
        }
        else if (menu.equals("avatar")) {
            return "successfully changeMenu. you are now in avatarMenu";
        }
        else{
            return "invalid menu";
        }
    }
}
