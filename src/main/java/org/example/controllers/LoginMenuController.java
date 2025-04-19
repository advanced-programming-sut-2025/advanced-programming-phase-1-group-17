package org.example.controllers;
import org.example.models.App;
import org.example.models.Result;
import org.example.models.User;
import org.example.models.enums.LoginMenuCommands;
import org.example.models.enums.Menu;


public class LoginMenuController {
    public Result loginUser(String input) {
        if (input.trim().endsWith("--stay-logged-in")) {

        }
        String username = LoginMenuCommands.Login.getMatcher(input).group("username").trim();
        String password = LoginMenuCommands.Login.getMatcher(input).group("password").trim();
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
        String username = LoginMenuCommands.Login.getMatcher(input).group("username").trim();
        if (App.getUserWithUsername(username) != null) {
            //answer
        }
        else {
            return new Result(false,"username not found");
        }
        return new Result(true, ""); //added to be able to run
    }


    public void exit() {
        App.setCurrentMenu(Menu.ExitMenu);
    }

}
