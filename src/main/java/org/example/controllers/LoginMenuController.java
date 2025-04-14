package org.example.controllers;
import org.example.models.Result;
import org.example.models.*;
import org.example.models.enums.*;


public class LoginMenuController {
    public String loginUser(String input) {
        if (input.trim().endsWith("--stay-logged-in")) {

        }
        String username = LoginMenu.login.getMatcher(input).group("username").trim();
        String password = LoginMenu.login.getMatcher(input).group("password").trim();
        if (User.getUserWithUsername(username) != null) {
            User user = User.getUserWithUsername(username);
            if (user.getPassword().equals(password)) {
                User.userOfLogin = user;
                return "you are logged in successfully";
            } else {
                return "Invalid password";
            }
        } else {
            return "username not found";
        }
    }

    public String forgotPassword(String input) {
        String username =LoginMenu.login.getMatcher(input).group("username").trim();
        if (User.getUserWithUsername(username) != null) {
            //answer
        }
        else {
            return "username not found";
        }
    }
}
