package controllers;

import org.example.models.*;
import org.example.models.enums.*;

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
                if (scanner.nextLine().equals("y")) {
                    username = suggestedUsername;
                    break;
                } else if (scanner.nextLine().equals("n")) {
                    return;
                }
            }
        } else if (SignUpMenuCommands.Username.getMatcher(username) == null) {
            System.out.println(new Result(false, "Username format is invalid."));
            System.out.println(new Result(false, "Password can only contain letters, digits, and -."));
            return;
        } else if (SignUpMenuCommands.Email.getMatcher(username) == null) {
            System.out.println(new Result(false, "Email format is invalid."));
            return;
        } else if (SignUpMenuCommands.ValidPassword.getMatcher(password) == null) {
            System.out.println(new Result(false, "Password format is invalid."));
            System.out.println(new Result(false, "Password can only contain letters, digits, and special characters."));
        } else if (!isPasswordStrong(password).message().isEmpty()) {
            System.out.println(isPasswordStrong(password));
        } else if (!password.equals(passwordConfirm)) {
            while (true) {
                System.out.println("Password Confirm Incorrect! Enter the password again or enter 'exit' to go to signup menu");
                if (scanner.nextLine().trim().equals(password)) {
                    break;
                } else if (scanner.nextLine().trim().equals("exit")) {
                    return;
                }
            }
        }

        //TODO: random generate password



        Gender gender1 = null;
        if (gender.trim().equalsIgnoreCase("female")) {
            gender1 = Gender.Female;
        } else { //default: male
            gender1 = Gender.Male;
        }


        App.getUsers().add(new User(username, password, email, nickname, gender1));
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


    public static String handlePasswordInput(Scanner scanner) {
        while (true) {
            System.out.println("آیا می‌خواهید از رمز عبور تصادفی استفاده کنید؟ (yes/no): ");
            String choice = scanner.nextLine().trim().toLowerCase();

            if (choice.equals("yes")) {
                while (true) {
                    String randomPassword = generateStrongPassword(12);
                    System.out.println("رمز عبور پیشنهادی: " + randomPassword);
                    System.out.println("آیا این رمز را تایید می‌کنید؟ (yes/no): ");
                    String confirm = scanner.nextLine().trim().toLowerCase();
                    if (confirm.equals("yes")) {
                        return randomPassword;
                    } else {
                        System.out.println("آیا می‌خواهید رمز دیگری تولید شود یا بازگردید؟ (generate/back): ");
                        String next = scanner.nextLine().trim().toLowerCase();
                        if (next.equals("back")) {
                            return null;
                        }
                    }
                }
            } else if (choice.equals("no")) {
                System.out.print("رمز عبور را وارد کنید: ");
                String password = scanner.nextLine();
                System.out.print("تکرار رمز عبور: ");
                String confirmPassword = scanner.nextLine();
                if (!password.equals(confirmPassword)) {
                    System.out.println("رمز عبور و تکرار آن یکسان نیستند.");
                } else {
                    return password;
                }
            } else {
                System.out.println("لطفاً فقط yes یا no وارد کنید.");
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


}