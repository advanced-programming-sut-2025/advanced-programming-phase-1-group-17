package io.github.StardewValley.models.enums;


import io.github.StardewValley.GameAssetManager;
import io.github.StardewValley.controllers.GameMenuController;
import io.github.StardewValley.controllers.MainMenuController;
import io.github.StardewValley.controllers.ProfileMenuController;
import io.github.StardewValley.views.*;

import java.util.Scanner;

public enum Menu {
    LoginMenu(new LoginMenu()),
    SignUpMenu(new SignUpMenu()),
    ExitMenu(new ExitMenu()),
    GameMenu(new GameMenu(new GameMenuController(),GameAssetManager.getGameAssetManager().getSkin())),
    ProfileMenu(new ProfileMenu(new ProfileMenuController(), GameAssetManager.getGameAssetManager().getSkin())),
    AvatarMenu(new AvatarMenu()),
    MainMenu(new MainMenu(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin())),
    TradeMenu(new TradeMenu());

    private final AppMenu menu;

    Menu(AppMenu menu) {
        this.menu = menu;
    }

    public void checkCommand(Scanner scanner) {
        this.menu.run(scanner);
    }
}
