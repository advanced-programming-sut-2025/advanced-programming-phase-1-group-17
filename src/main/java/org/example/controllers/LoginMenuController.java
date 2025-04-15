package org.example.controllers;
import org.example.models.*;
import org.example.models.enums.*;
import org.example.views.LoginMenu;


public class LoginMenuController {
    public Result loginUser(String input) {
        if (input.trim().endsWith("--stay-logged-in")) {

        }
        String username = LoginMenuCommands.login.getMatcher(input).group("username").trim();
        String password = LoginMenuCommands.login.getMatcher(input).group("password").trim();
        if (App.getUserWithUsername(username) != null) {
            User user = App.getUserWithUsername(username);
            if (user.getPassword().equals(password)) {
                App.setLoggedInUser(user);
                App.setCurrentMenu(Menu.MainMenu);
                return new Result(true,"you are logged in successfully");
            } else {
                return new Result(false,"Invalid password");
            }
        } else {
            return new Result(false,"username not found");
        }
    }

    public Result forgotPassword(String input) {
        String username = LoginMenuCommands.login.getMatcher(input).group("username").trim();
        if (App.getUserWithUsername(username) != null) {
            //answer
        }
        else {
            return new Result(false,"username not found");
        }
    }
}
