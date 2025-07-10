package io.github.StardewValley.controllers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import io.github.StardewValley.GameAssetManager;
import io.github.StardewValley.Main;
import io.github.StardewValley.SaveUser;
import io.github.StardewValley.models.*;
import io.github.StardewValley.models.NPCS.NPC;
import io.github.StardewValley.models.enums.MainMenuCommands;
import io.github.StardewValley.models.enums.Menu;
import io.github.StardewValley.models.map.GreenHouse;
import io.github.StardewValley.models.map.PlayerMap;
import io.github.StardewValley.models.map.Tile;
import io.github.StardewValley.views.GameMenu;
import io.github.StardewValley.views.LoginMenu;
import io.github.StardewValley.views.MainMenu;
import io.github.StardewValley.views.ProfileMenu;

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
                Main.getMain().setScreen(new ProfileMenu(new ProfileMenuController(),GameAssetManager.getGameAssetManager().getSkin()));
            }
        });
        view.getGameMenuButton().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new GameMenu(new GameMenuController(),GameAssetManager.getGameAssetManager().getSkin()));
            }
        });
        view.getLogoutAndGotoLoginMenuButton().addListener(
            new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    App.setLoggedInUser(null);
                    SaveUser.clearStayLoggedInFile();
                    Main.getMain().getScreen().dispose();
                    Main.getMain().setScreen(new LoginMenu(new LoginMenuController(),GameAssetManager.getGameAssetManager().getSkin()));
                }
            }
        );

    }

}
