package io.github.StardewValley.views;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.github.StardewValley.controllers.MainMenuController;
import io.github.StardewValley.models.App;
import io.github.StardewValley.models.User;
import io.github.StardewValley.models.enums.MainMenuCommands;
import io.github.StardewValley.models.enums.Menu;

import java.util.Scanner;

public class MainMenu implements AppMenu , Screen {
    MainMenuController mainMenuController = new MainMenuController();

    public MainMenu(MainMenuController mainMenuController, Skin skin) {

    }

    public void run(Scanner scanner) {
        String command = scanner.nextLine();
        if (command.trim().equals("user logout")) {
            System.out.println(mainMenuController.exitMenu());
        } else if (MainMenuCommands.changeMenu.getMatcher(command).matches()) {
            System.out.println(mainMenuController.changeMenu(command));
        } else if (command.trim().equals("show current menu")) {
            System.out.println(App.getCurrentMenu().name());
        }
        else if (command.trim().equals("load game")) {
            System.out.println(mainMenuController.loadGame());
        }else if (command.trim().equals("menu exit")) {
            App.setLoggedInUser(null);
            App.setCurrentMenu(Menu.LoginMenu);
            System.out.println("You are in Login menu now");
        }
        else {
            System.out.println("invalid command");
        }

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {

    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
