package io.github.StardewValley.controllers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.StardewValley.GameAssetManagerClient;
import io.github.StardewValley.TokenStorage;
import io.github.StardewValley.Main;
import io.github.StardewValley.views.*;

public class MainMenuController {

    private MainMenu view;
    public void setView(MainMenu view) {
        this.view = view;
        setupButtonListener();
    }
    public void setupButtonListener(){
        view.getProfileMenuButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new ProfileMenu(new ProfileMenuController(),  GameAssetManagerClient.getGameAssetManager().getSkin()));
            }
        });
        view.getGameMenuButton().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new LobbyScreen(Main.getJwtToken()));
            }
        });
        view.getLogoutAndGotoLoginMenuButton().addListener(
            new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    TokenStorage.clearToken();
                    Main.getMain().getScreen().dispose();
                    Main.getMain().setScreen(new LoginMenu(new LoginMenuController(),  GameAssetManagerClient.getGameAssetManager().getSkin()));
                }
            }
        );

    }

}
