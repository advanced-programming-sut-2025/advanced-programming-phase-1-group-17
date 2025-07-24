package io.github.StardewValley.controllers;

import com.google.gson.Gson;
import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.models.App;
import io.github.StardewValley.shared.models.Result;
import io.github.StardewValley.shared.models.UserDTO;
import io.github.StardewValley.shared.dto.RegisterRequest;
import io.github.StardewValley.shared.dto.RegisterResponse;
import io.github.StardewValley.shared.enums.Gender;
import io.github.StardewValley.shared.models.enums.SignUpMenuCommands;
import io.github.StardewValley.views.LoginMenu;
import okhttp3.*;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SignUpMenuController {
    public void enterLoginMenu() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new LoginMenu(new LoginMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    public Result register(String username,
                           String password,
                           String passwordConfirm,
                           String nickname,
                           String email,
                           Gender gender) {
        if (SignUpMenuCommands.Username.getMatcher(username) == null) {
            return new Result(false, "Username format is invalid.\n" +
                "Username can only contain letters, digits, and -.");

        } else if (SignUpMenuCommands.Email.getMatcher(email) == null) {
            return new Result(false, "Email format is invalid.");

        } else if (SignUpMenuCommands.ValidPassword.getMatcher(password) == null) {
            return new Result(false, "Password format is invalid. " +
                "Password can only contain letters, digits, and special characters.");

        }
        Result passwordCheck = isPasswordStrong(password);
        if (!passwordCheck.isSuccessful()) {
            return passwordCheck;
        }
        else if (!password.equals(passwordConfirm)) {
            return new Result(false, "Password Confirm Incorrect!");
        }
        return sendRegisterRequest(username, password, nickname, email, gender);
    }

    private Result sendRegisterRequest(String username, String password,
                                       String nickname, String email, Gender gender) {

        OkHttpClient client = new OkHttpClient();
        RegisterRequest registerRequest = new RegisterRequest(username, password, nickname, email, gender);
        Gson gson = new Gson();
        String json = gson.toJson(registerRequest);

        RequestBody body = RequestBody.create(json, MediaType.get("application/json"));
        Request request = new Request.Builder()
            .url("http://localhost:8080/api/auth/register")
            .post(body)
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                return new Result(false, "Server Error: " + response.code());
            }

            String responseJson = response.body().string();
            RegisterResponse registerResponse = gson.fromJson(responseJson, RegisterResponse.class);

            if (registerResponse.isSuccess()) {
                App.setLoggedInUser(new UserDTO(username, nickname, gender));
                return new Result(true, "User registered successfully!");
            } else {
                return new Result(false, registerResponse.getMessage());
            }

        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, "Network error: " + e.getMessage());
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
