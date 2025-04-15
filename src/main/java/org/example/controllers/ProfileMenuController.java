package org.example.controllers;

import org.example.models.App;
import org.example.models.Result;
import org.example.models.User;
import org.example.models.enums.ProfileMenuCommands;

public class ProfileMenuController {

    public Result showUserInfo() {
        //User user = useri ke logine
        User user = App.getLoggedInUser();
        return new Result(true, user.getUsername()
                + "\n" + user.getNickName()
                + "\n" + user.getTheMostMoneyIngame()
                + "\n" + user.getNumOfPlay());
    }

    public Result changeUserName(String input) {
        //User user = useri ke logine
        User user = App.getLoggedInUser();
        String newUsername = ProfileMenuCommands.changeUsername.getMatcher(input).group("username").trim();
        // check
        if (!check) {
            return new Result(false, "invalid username");
        }
        user.setUsername(newUsername);
        return new Result(true, "set successfully");
    }

    public Result changeNickName(String input) {
        //User user = useri ke logine
        User user = App.getLoggedInUser();
        String newNickName = ProfileMenuCommands.changeNickName.getMatcher(input).group("nickname").trim();
        // check
        if (!check) {
            return new Result(false, "invalid nickname");
        }
        user.setNickName(newNickName);
        return new Result(true, "set successfully");
    }

    public Result changeEmail(String input) {
        //User user = useri ke logine
        User user = App.getLoggedInUser();
        String newEmail = ProfileMenuCommands.changeNickName.getMatcher(input).group("email").trim();
        // check
        if (!check) {
            return new Result(false, "invalid email");
        }
        user.setNickName(newEmail);
        return new Result(true, "set successfully");
    }

    public Result changePassword(String input) {
        //User user = useri ke logine
        User user = App.getLoggedInUser();
        String oldPassword = ProfileMenuCommands.changeNickName.getMatcher(input).group("oldPassword").trim();
        if (!user.getPassword().equals(oldPassword)) {
            return new Result(false, "your old password is incorrect");
        }
        String newPassword = ProfileMenuCommands.changeNickName.getMatcher(input).group("newPassword").trim();
        // check
        if (!check) {
            return new Result(false, "invalid password");
        }
        user.setPassword(newPassword);
        return new Result(true, "set successfully");
    }

}
