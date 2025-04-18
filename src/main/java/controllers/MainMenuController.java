package controllers;

import models.enums.MainMenuCommands;

public class MainMenuController {
    public String changeMenu(String input) {
        String menu = MainMenuCommands.changeMenu.getMatcher(input).group("menu").trim();
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
