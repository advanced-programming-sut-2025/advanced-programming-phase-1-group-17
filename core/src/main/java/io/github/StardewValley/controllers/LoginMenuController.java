package io.github.StardewValley.controllers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import com.google.gson.Gson;
import io.github.StardewValley.GameAssetManagerClient;
import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.Main;

import io.github.StardewValley.shared.dto.*;
import io.github.StardewValley.shared.models.App;
import io.github.StardewValley.shared.models.enums.NetworkRequests;
import io.github.StardewValley.views.LoginMenu;
import io.github.StardewValley.views.MainMenu;
import io.github.StardewValley.views.SignUpMenu;
import okhttp3.*;

import java.io.IOException;


public class LoginMenuController {
    private LoginMenu view;

    public void setView(LoginMenu view) {
        this.view = view;
        setupButtonListener();
    }

    private void setupButtonListener() {
        view.getBackButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new SignUpMenu(new SignUpMenuController(),   GameAssetManagerClient.getGameAssetManager().getSkin()));
            }
        });
        view.getUserNameButton().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.getUserName().setVisible(true);
            }
        });
        view.getPasswordButton().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.getPassword().setVisible(true);
            }
        });
        view.getForgotPasswordButton().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (!view.getAnswer().isVisible()) {
                    view.getAnswer().setVisible(true);
                    return;
                }
                if (view.getUserName().getText().isEmpty()) {
                    view.setError("Please enter a username");
                    return;
                }

                DoesUserExistResponse doesUserExistResponse = doesUserExistRequest(view.getUserName().getText());
                if (!doesUserExistResponse.getResult()) {
                    view.setError(doesUserExistResponse.getMessage());
                    return;
                }

                forgotPassword(view.getUserName().getText(), view.getAnswer().getText());
            }
        });
        view.getLoginButton().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                String username = view.getUserName().getText();
                String password = view.getPassword().getText();
                if (username.isEmpty() || password.isEmpty()) {
                    view.setError("Username and Password are required");
                    return;
                }
                DoesUserExistResponse doesUserExistResponse = doesUserExistRequest(username);
                if (!doesUserExistResponse.getResult()) {
                    view.setError(doesUserExistResponse.getMessage());
                    return;
                }

                login(username, password);
            }
        });
    }

    private void forgotPassword(String username, String securityAnswer) {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        ForgotPasswordRequest requestObj = new ForgotPasswordRequest(username, securityAnswer);
        String json = gson.toJson(requestObj);

        RequestBody body = RequestBody.create(json, MediaType.get("application/json"));
        Request request = new Request.Builder()
            .url("http://%s:%d/api/auth/%s".formatted(
                Main.getServerIP(),
                Main.getServerPort(),
                NetworkRequests.ForgotPasswordRequest.getMessage()
            ))
            .post(body)
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                view.setError("Server error: " + response.code());
                return;
            }

            String responseJson = response.body().string();
            ForgotPasswordResponse forgotPasswordResponse = gson.fromJson(responseJson, ForgotPasswordResponse.class);
            if (!forgotPasswordResponse.isMatch()) {
                view.setError(forgotPasswordResponse.getMessage());
                return;
            }
            view.setError("Your new password is %s".formatted(forgotPasswordResponse.getMessage()));
        } catch (IOException e) {
            e.printStackTrace();
            view.setError("Could not connect to the server");
        }
    }


    private void login(String username, String password) {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        LoginRequest requestObj = new LoginRequest(username, password);
        String json = gson.toJson(requestObj);

        RequestBody body = RequestBody.create(json, MediaType.get("application/json"));
        Request request = new Request.Builder()
            .url("http://%s:%d/api/auth/%s".formatted(
                Main.getServerIP(),
                Main.getServerPort(),
                NetworkRequests.LoginRequest.getMessage()
            ))
            .post(body)
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                view.setError("Server error: " + response.code());
                return;
            }

            String responseJson = response.body().string();
            LoginResponse loginResponse = gson.fromJson(responseJson, LoginResponse.class);

            App.setLoggedInUser(loginResponse.getUserDTO());
            Main.setJwt(loginResponse.getToken());

            Main.getMain().getScreen().dispose();
            view.getCheckBox().setChecked(false);
            Main.getMain().setScreen(new MainMenu(new MainMenuController(),   GameAssetManagerClient.getGameAssetManager().getSkin()));
        } catch (IOException e) {
            e.printStackTrace();
            view.setError("Could not connect to the server.");
        }
    }

    private DoesUserExistResponse doesUserExistRequest(String username) {
        OkHttpClient client = new OkHttpClient();
        DoesUserExistRequest userExistRequest = new DoesUserExistRequest(username);
        Gson gson = new Gson();
        String json = gson.toJson(userExistRequest);

        RequestBody body = RequestBody.create(json, MediaType.get("application/json"));
        Request request = new Request.Builder()
            .url("http://%s:%d/api/auth/%s".formatted(
                Main.getServerIP(),
                Main.getServerPort(),
                NetworkRequests.DoesUserExist.getMessage()
            ))
            .post(body)
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                return new DoesUserExistResponse(false, "Server error: " + response.code());
            }
            String responseJson = response.body().string();
            return gson.fromJson(responseJson, DoesUserExistResponse.class);

        } catch (IOException e) {
            e.printStackTrace();
            return new DoesUserExistResponse(false, "Could not connect to server.");
        }
    }
}
