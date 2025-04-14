package org.example.controllers;

import org.example.models.Result;

public class ProfileMenuController {

    public String showUserInfo(){
        //User user = useri ke logine
        return use.getUsername() + "\n" + user.getNikcName() + "\n" + user.getTheMostMoneyIngame()+"\n"+user.getNumOfPlay();
    }

    public String changeUserName(String input){
        //User user = useri ke logine
        String newUsername = models.enums.ProfileMenu.changeUserName(input).group("username").trim();
        // check
        if (!check) {
            return "invalid username";
        }
        user.setUsename(newUsername);
        return "set successfully";
    }

    public String changeNickName(String input){
        //User user = useri ke logine
        String newNickName = models.enums.ProfileMenu.changeNickName(input).group("nickname").trim();
        // check
        if (!check) {
            return "invalid nickname";
        }
        user.setNickName(newNickName);
        return "set successfully";
    }

    public String changeEmail(String input){
        //User user = useri ke logine
        String newEmail = models.enums.ProfileMenu.changeNickName(input).group("email").trim();
        // check
        if (!check) {
            return "invalid email";
        }
        user.setNickName(newEmail);
        return "set successfully";
    }

    public String changePassword(String input){
        //User user = useri ke logine
        String oldPassword =  models.enums.ProfileMenu.changeNickName(input).group("oldPassword").trim();
        if (!user.getPasswor().equals(oldPassword)){
            return "your old password is incorrect";
        }
        String newPassword = models.enums.ProfileMenu.changeNickName(input).group("newPassword").trim();
        // check
        if (!check) {
            return "invalid password";
        }
        user.setPassword(newPassword);
        return "set successfully";
    }

}
