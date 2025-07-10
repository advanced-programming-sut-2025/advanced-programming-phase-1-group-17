package io.github.StardewValley.controllers;

import io.github.StardewValley.GameAssetManager;
import io.github.StardewValley.Main;
import io.github.StardewValley.PasswordUtil;
import io.github.StardewValley.SaveUser;
import io.github.StardewValley.models.App;
import io.github.StardewValley.models.Result;
import io.github.StardewValley.models.User;
import io.github.StardewValley.models.enums.Gender;
import io.github.StardewValley.models.enums.Menu;
import io.github.StardewValley.models.enums.SignUpMenuCommands;
import io.github.StardewValley.views.LoginMenu;
import io.github.StardewValley.views.MainMenu;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class SignUpMenuController {
//    public Result enterLoginMenu() {
//        App.setCurrentMenu(Menu.LoginMenu);
//
//        return new Result(true, "Redirecting to Login Menu...");
//    }


    public void enterLoginMenu() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new LoginMenu(new LoginMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }


//    public void exit() {
//        App.setCurrentMenu(Menu.ExitMenu);
//    }
//
//    public Result showCurrentMenu() {
//        return new Result(true, App.getCurrentMenu().name());
//    }

    public Result register(String username,
                           String password,
                           String passwordConfirm,
                           String nickname,
                           String email,
                           Gender gender) {
        User user = App.getUserWithUsername(username);
        if (user != null) {
            return new Result(false, "Username Already Taken;");

//            String suggestedUsername = giveSimilarUsername(username);
//            System.out.printf("Suggested Username: %s\n", suggestedUsername);
//            while (true) {
//                System.out.println("Press [y] to confirm this username, or press [n] to exit.");
//                String input = scanner.nextLine();
//                if (input.equals("y")) {
//                    username = suggestedUsername;
//                    break;
//                } else if (input.equals("n")) {
//                    return;
//                }
//            }
        } else if (SignUpMenuCommands.Username.getMatcher(username) == null) {
            return new Result(false, "Username format is invalid.\n" +
                "Username can only contain letters, digits, and -.");

        } else if (SignUpMenuCommands.Email.getMatcher(email) == null) {
            return new Result(false, "Email format is invalid.");

        } else if (SignUpMenuCommands.ValidPassword.getMatcher(password) == null) {
            return new Result(false, "Password format is invalid. " +
                "Password can only contain letters, digits, and special characters.");

        } else if (!isPasswordStrong(password).getMessage().isEmpty()) {
            return isPasswordStrong(password);

        } else if (!password.equals(passwordConfirm)) {
            return new Result(false, "Password Confirm Incorrect!");
        }

        String hashedPassword = PasswordUtil.hashPassword(password);
        User newUser = new User(username, password, hashedPassword, email, nickname, gender);


        //chooseSecurityQuestion(newUser, scanner);
        App.getUsers().add(newUser);
        SaveUser.saveUser(App.getUsers());
        return new Result(true, "User successfully added!");
    }


//    public String giveSimilarUsername(String username) {
//        String similarUsername = null;
//
//        while (true) {
//            int randomNumber = (int) (Math.random() * 10000);
//            similarUsername = username + randomNumber;
//            if (App.getUserWithUsername(similarUsername) == null)
//                return similarUsername;
//        }
//    }

    public Result isPasswordStrong(String password) {
        if (password.length() < 8) {
            return new Result(false, "Password must have at least 8 characters");
        } else if (!password.matches(".*[a-z].*")) {
            return new Result(false, "Password must have a lower case alphabet letter");
        } else if (!password.matches(".*[A-Z].*")) {
            return new Result(false, "Password must have an upper case alphabet letter");
        } else if (!password.matches(".*\\d.*")) {
            return new Result(false, "Password must have a digit");
        } else if (!password.matches(".*[!#$%\\^&*()=+{}\\[\\]|\\\\/:;'\\" + "\",<>?].*")) {
            return new Result(false, "Password must contain at least one special character");
        }
        return new Result(true, ""); //password is strong
    }


//    public static String handleRandomPasswordInput(Scanner scanner) {
//        while (true) {
//            String randomPassword = generateStrongPassword(12);
//            System.out.println("Suggested password: " + randomPassword);
//            System.out.println("Do you want to set this as your password?(yes/no)");
//            String confirm = scanner.nextLine().trim().toLowerCase();
//            if (confirm.equals("yes")) {
//                return randomPassword;
//            } else {
//                System.out.println("Enter 'generate' to generate a new random password or enter 'back'" +
//                    "to go back to signup menu");
//                String next = scanner.nextLine().trim().toLowerCase();
//                if (next.equals("back")) {
//                    return null;
//                }
//            }
//        }
//    }


    public String generateStrongPassword(int length) {
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String special = "!#$%^&*()=+{}[]|/:;'\"<>,?";

        String all = upper + lower + digits + special;
        SecureRandom rand = new SecureRandom();
        List<Character> password = new ArrayList<>();

        password.add(upper.charAt(rand.nextInt(upper.length())));
        password.add(lower.charAt(rand.nextInt(lower.length())));
        password.add(digits.charAt(rand.nextInt(digits.length())));
        password.add(special.charAt(rand.nextInt(special.length())));

        for (int i = 4; i < length; i++) {
            password.add(all.charAt(rand.nextInt(all.length())));
        }

        Collections.shuffle(password);

        StringBuilder result = new StringBuilder();
        for (char c : password) {
            result.append(c);
        }

        return result.toString();
    }
}
