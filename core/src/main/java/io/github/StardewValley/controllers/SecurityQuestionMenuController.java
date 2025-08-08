package io.github.StardewValley.controllers;

import com.google.gson.Gson;
import io.github.StardewValley.GameAssetManagerClient;
import io.github.StardewValley.GameClient;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.dto.SecurityQuestionRequest;
import io.github.StardewValley.shared.dto.SecurityQuestionResponse;
import io.github.StardewValley.shared.models.Result;
import io.github.StardewValley.shared.models.UserDTO;
import io.github.StardewValley.shared.models.enums.NetworkRequests;
import io.github.StardewValley.views.SecurityQuestionMenu;
import io.github.StardewValley.views.SignUpMenu;
import okhttp3.*;

import java.io.IOException;

public class SecurityQuestionMenuController {
    private UserDTO user;
    private String question = "";
    private SecurityQuestionMenu view;

    public void setQuestion(String question) {
        this.question = question;
        this.user = GameClient.getLoggedInUser();
    }

    public void checkAnswer() {
        if (question.equals("")) {
            view.getErrorLabel().setText("You need to pick a question First.");
        } else if (view.getSecurityAnswer().getText().equals(view.getSecurityAnswerConfirm().getText()) &&
            !view.getSecurityAnswer().getText().isEmpty()) {

            user.setSecurityQuestion(question);
            user.setSecurityAnswer(view.getSecurityAnswer().getText());


            Result serverResponse = sendSecurityQuestion();
            if (!serverResponse.successful()) {
                view.getErrorLabel().setText(serverResponse.message());
                return;
            }

            Main.getMain().getScreen().dispose();
            SignUpMenu signupMenu = new SignUpMenu(new SignUpMenuController(),   GameAssetManagerClient.getGameAssetManager().getSkin());
            Main.getMain().setScreen(signupMenu);
            signupMenu.getErrorLabel().setText("User %s registered successfully.".formatted(user.getUsername()));
        } else {
            view.getErrorLabel().setText("Answer and Answer Confirm are not the same.");
        }
    }

    private Result sendSecurityQuestion() {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        SecurityQuestionRequest registerRequest = new SecurityQuestionRequest(user.getUsername(), user.getSecurityQuestion(), user.getSecurityAnswer());
        String json = gson.toJson(registerRequest);

        RequestBody body = RequestBody.create(json, MediaType.get("application/json"));
        Request request = new Request.Builder()
            .url("http://%s:%d/api/auth/%s".formatted(
                Main.getServerIP(),
                Main.getServerPort(),
                NetworkRequests.SecurityQuestion.getMessage()
            ))
            .post(body)
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                return new Result(false, "Server Error: " + response.code());
            }

            String responseJson = response.body().string();
            SecurityQuestionResponse securityQuestionResponse = gson.fromJson(responseJson, SecurityQuestionResponse.class);

            if (securityQuestionResponse.isSuccess()) {
                return new Result(true, "User registered successfully!");
            } else {
                return new Result(false, securityQuestionResponse.getMessage());
            }

        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, "Network error: " + e.getMessage());
        }
    }

    public void setView(SecurityQuestionMenu view) {
        this.view = view;
    }
}
