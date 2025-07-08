package io.github.StardewValley.controllers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import io.github.StardewValley.GameAssetManager;
import io.github.StardewValley.Main;
import io.github.StardewValley.PasswordUtil;
import io.github.StardewValley.SaveUser;
import io.github.StardewValley.models.App;
import io.github.StardewValley.models.Result;
import io.github.StardewValley.models.User;
import io.github.StardewValley.models.enums.LoginMenuCommands;
import io.github.StardewValley.models.enums.Menu;
import io.github.StardewValley.views.LoginMenu;
import io.github.StardewValley.views.MainMenu;

import java.util.Scanner;


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
//                Sfx_Controller.getInstance().playClick();
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new MainMenu(new MainMenuController(),GameAssetManager.getGameAssetManager().getSkin()));
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
        view.getLoginButton().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                String username = view.getUserName().getText();
                String password = view.getPassword().getText();
                //todo check these strings
                Main.getMain().getScreen().dispose();
                //todo going to mainmenu just for testing , rewrite it later
                Main.getMain().setScreen(new MainMenu(new MainMenuController(),GameAssetManager.getGameAssetManager().getSkin()));
            }
        });
    }

    public Result loginUser(String input) {
        String username = LoginMenuCommands.Login.getMatcher(input).group("username").trim();
        String password = LoginMenuCommands.Login.getMatcher(input).group("password").trim();
        if (App.getUserWithUsername(username) != null) {
            User user = App.getUserWithUsername(username);
            String hashedInput = PasswordUtil.hashPassword(password);
            if (user.getPasswordHash().equals(hashedInput)) {
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
                return new Result(true, "your password : " + user.getRawPassword());
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
