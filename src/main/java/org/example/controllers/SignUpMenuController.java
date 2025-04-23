package org.example.controllers;

import org.example.models.App;
import org.example.models.Result;
import org.example.models.User;
import org.example.models.enums.Gender;
import org.example.models.enums.Menu;
import org.example.models.enums.SignUpMenuCommands;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class SignUpMenuController {
    public Result enterLoginMenu() {
        App.setCurrentMenu(Menu.LoginMenu);

        return new Result(true, "Redirecting to Login Menu...");
    }


    public Result enterMainMenu() {
        App.setCurrentMenu(Menu.MainMenu);
        return new Result(true, "Redirecting to Main Menu...");
    }


    public void exit() {
        App.setCurrentMenu(Menu.ExitMenu);
    }

    public Result showCurrentMenu() {
        return new Result(true, App.getCurrentMenu().name());
    }

    public void register(String username,
                         String password,
                         String passwordConfirm,
                         String nickname,
                         String email,
                         String gender,
                         Scanner scanner) {
        User user = App.getUserWithUsername(username);
        if (user != null) {
            System.out.println(new Result(false, "Username Already Taken;"));

            String suggestedUsername = giveSimilarUsername(username);
            System.out.printf("Suggested Username: %s\n", suggestedUsername);
            while (true) {
                System.out.println("Press [y] to confirm this username, or press [n] to exit.");
                String input = scanner.nextLine();
                if (input.equals("y")) {
                    username = suggestedUsername;
                    break;
                } else if (input.equals("n")) {
                    return;
                }
            }
        } else if (SignUpMenuCommands.Username.getMatcher(username) == null) {
            System.out.println(new Result(false, "Username format is invalid."));
            System.out.println(new Result(false, "Username can only contain letters, digits, and -."));
            return;
        } else if (SignUpMenuCommands.Email.getMatcher(email) == null) {
            System.out.println(new Result(false, "Email format is invalid."));
            return;
        } else if (password.equalsIgnoreCase("random")) {
            String randomPassword = handleRandomPasswordInput(scanner);
            if (randomPassword == null) {
                return;
            } else {
                password = randomPassword;
            }
        } else if (SignUpMenuCommands.ValidPassword.getMatcher(password) == null) {
            System.out.println(new Result(false, "Password format is invalid. " +
                    "Password can only contain letters, digits, and special characters."));
            return;
        } else if (!isPasswordStrong(password).getMessage().isEmpty()) {
            System.out.println(isPasswordStrong(password));
            return;
        } else if (!password.equals(passwordConfirm)) {
            while (true) {
                System.out.println("Password Confirm Incorrect! Enter the password again or enter 'exit' to go to signup menu");
                String input = scanner.nextLine().trim();
                if (input.equals(password)) {
                    break;
                } else if (input.equals("exit")) {
                    return;
                }
            }
        }

        Gender gender1 = null;
        if (gender.trim().equalsIgnoreCase("female")) {
            gender1 = Gender.Female;
        } else { //default: male
            gender1 = Gender.Male;
        }

        //TODO: SAVING
        User newUser = new User(username, password, email, nickname, gender1);

        System.out.println("Just one more step left to add user!\n");
        chooseSecurityQuestion(newUser, scanner);
        App.getUsers().add(newUser);
        System.out.println("User successfully added!");
    }


    public String giveSimilarUsername(String username) {
        String similarUsername = null;

        while (true) {
            int randomNumber = (int) (Math.random() * 10000);
            similarUsername = username + randomNumber;
            if (App.getUserWithUsername(similarUsername) == null)
                return similarUsername;
        }
    }

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


    public static String handleRandomPasswordInput(Scanner scanner) {
        while (true) {
            String randomPassword = generateStrongPassword(12);
            System.out.println("Suggested password: " + randomPassword);
            System.out.println("Do you want to set this as your password?(yes/no)");
            String confirm = scanner.nextLine().trim().toLowerCase();
            if (confirm.equals("yes")) {
                return randomPassword;
            } else {
                System.out.println("Enter 'generate' to generate a new random password or enter 'back'" +
                        "to go back to signup menu");
                String next = scanner.nextLine().trim().toLowerCase();
                if (next.equals("back")) {
                    return null;
                }
            }
        }
    }



    public static String generateStrongPassword(int length) {
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

    public static void chooseSecurityQuestion(User user, Scanner scanner) {
        List<String> securityQuestions = List.of(
                "What is the name of your first pet?",
                "What is your mother's maiden name?",
                "What was the name of your elementary school?",
                "What is your favorite book?",
                "What city were you born in?",
                "What is your favorite food?",
                "What was the make of your first car?",
                "What is your father's middle name?",
                "In what city did your parents meet?",
                "What was the name of your childhood best friend?"
        );

        System.out.println("Please choose one of the following security questions:");
        for (int i = 0; i < securityQuestions.size(); i++) {
            System.out.println((i + 1) + ". " + securityQuestions.get(i));
        }

        int choice = -1;
        while (choice < 1 || choice > securityQuestions.size()) {
            System.out.print("Enter the number of your chosen question: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        String question = securityQuestions.get(choice - 1);
        System.out.println("Your chosen question: " + question);
        System.out.print("Enter your answer: ");
        String answer = scanner.nextLine();

        user.setSecurityQuestion(question);
        user.setSecurityAnswer(answer); // Optional: hash before saving

        System.out.println("Security question and answer saved!");
    }

}