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


}
