package org.example.controllers;
import org.example.models.*;
import org.example.models.enums.*;


public class LoginMenuController {
    public String loginUser(String input) {
        if (input.trim().endsWith("--stay-logged-in")) {

        }
        String username = LoginMenuCommands.login.getMatcher(input).group("username").trim();
        String password = LoginMenuCommands.login.getMatcher(input).group("password").trim();
        if (App.getUserWithUsername(username) != null) {
            User user = App.getUserWithUsername(username);
            if (user.getPassword().equals(password)) {
                App.setLoggedInUser(user);
                return "you are logged in successfully";
            } else {
                return "Invalid password";
            }
        } else {
            return "username not found";
        }
    }

    public String forgotPassword(String input) {
        String username = LoginMenuCommands.login.getMatcher(input).group("username").trim();
        if (App.getUserWithUsername(username) != null) {
            //answer
        }
        else {
            return "username not found";
        }
    }
}
