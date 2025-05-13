package org.example.controllers;

import org.example.SaveUser;
import org.example.models.App;
import org.example.models.Result;
import org.example.models.User;
import org.example.models.enums.LoginMenuCommands;
import org.example.models.enums.Menu;

import java.util.Scanner;


public class LoginMenuController {
    public Result loginUser(String input) {
        String username = LoginMenuCommands.Login.getMatcher(input).group("username").trim();
        String password = LoginMenuCommands.Login.getMatcher(input).group("password").trim();
        if (App.getUserWithUsername(username) != null) {
            User user = App.getUserWithUsername(username);
            if (user.getPassword().equals(password)) {
                App.setLoggedInUser(user);
                if (input.trim().endsWith("--stay-logged-in")) {
                    SaveUser.saveLoggedInUser(user);
                }
                App.setCurrentMenu(Menu.MainMenu);
                return new Result(true, "you are logged in successfully");
            } else {
                return new Result(false, "Invalid password");
            }
        } else {
            return new Result(false, "username not found");
        }
    }

    public Result forgetPassword(String input, Scanner scanner) {
        String username = LoginMenuCommands.ForgetPassword.getMatcher(input).group("username").trim();
        User user;
        if ((user = App.getUserWithUsername(username)) != null) {
            System.out.println("question : " + user.getSecurityQuestion());
            System.out.println("please, enter your answer");
            String answer = scanner.nextLine();
            if (answer.equals(user.getSecurityAnswer())) {
                return new Result(true, "your password : " + user.getPassword());
            }
            else {
                return new Result(false, "wrong answer! try later");
            }
        } else {
            return new Result(false, "username not found");
        }
    }


    public void exit() {
        App.setCurrentMenu(Menu.SignUpMenu);
    }

    public void goToSignUpMenu() {
        App.setCurrentMenu(Menu.SignUpMenu);
    }
}
