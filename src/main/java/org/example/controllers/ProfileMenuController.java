package org.example.controllers;

import org.example.models.App;
import org.example.models.Result;
import org.example.models.User;
import org.example.models.enums.ProfileMenuCommands;
import org.example.models.enums.SignUpMenuCommands;

public class ProfileMenuController {

    public Result showUserInfo() {
        User user = App.getLoggedInUser();
        return new Result(true, user.getUsername()
                + "\n" + user.getNickName()
                + "\n" + user.getTheMostMoneyInGame()
                + "\n" + user.getNumOfPlay());
    }

    public Result changeUserName(String input) {
        User user = App.getLoggedInUser();
        String newUsername = ProfileMenuCommands.changeUsername.getMatcher(input).group("username").trim();
        if (SignUpMenuCommands.Username.getMatcher(newUsername) == null) {
            return new Result(false, "new username format is invalid");
        }
        if (newUsername.equals(user.getUsername())) {
            return new Result(false, "new username is already taken");
        }
        user.setUsername(newUsername);
        return new Result(true, "set successfully");
    }

    public Result changeNickName(String input) {
        User user = App.getLoggedInUser();
        String newNickName = ProfileMenuCommands.changeNickName.getMatcher(input).group("nickname").trim();
        if (newNickName.equals(user.getNickName())) {
            return new Result(false, "new nickname is already taken");
        }
        user.setNickName(newNickName);
        return new Result(true, "set successfully");
    }

    public Result changeEmail(String input) {
        User user = App.getLoggedInUser();
        String newEmail = ProfileMenuCommands.changeEmail.getMatcher(input).group("email").trim();
        if (SignUpMenuCommands.Email.getMatcher(newEmail) == null) {
            return new Result(false, "new email format is invalid");
        }
        if (newEmail.equals(user.getEmail())) {
            return new Result(false, "new email is already taken");
        }
        user.setEmail(newEmail);
        return new Result(true, "set successfully");
    }

    public Result changePassword(String input) {
        User user = App.getLoggedInUser();
        String oldPassword = ProfileMenuCommands.changePassword.getMatcher(input).group("oldPassword").trim();
        if (!user.getPassword().equals(oldPassword)) {
            return new Result(false, "your old password is incorrect");
        }
        String newPassword = ProfileMenuCommands.changePassword.getMatcher(input).group("newPassword").trim();
        if (SignUpMenuCommands.ValidPassword.getMatcher(newPassword) == null) {
            return new Result(false, "new password format is invalid");
        }
        if (newPassword.equals(user.getPassword())) {
            return new Result(false, "your new password is already taken");
        }
        user.setPassword(newPassword);
        return new Result(true, "set successfully");
    }

}
