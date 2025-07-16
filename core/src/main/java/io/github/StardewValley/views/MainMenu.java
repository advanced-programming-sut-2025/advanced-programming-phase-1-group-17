package io.github.StardewValley.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.Main;
import io.github.StardewValley.controllers.MainMenuController;
import io.github.StardewValley.controllers.ProfileMenuController;
import io.github.StardewValley.models.App;
import io.github.StardewValley.models.User;
import io.github.StardewValley.models.enums.MainMenuCommands;
import io.github.StardewValley.models.enums.Menu;

import java.util.Scanner;

public class MainMenu implements AppMenu, Screen {
    private TextButton backButton;
    private Table buttonsTable;
    private Stage stage;
    private Skin skin;
    private MainMenuController controller;
    private TextButton profileMenuButton;
    private TextButton gameMenuButton;
    private TextButton logoutAndGotoLoginMenuButton;
    private TextField newUserName;
    private TextField newPassword;
    private TextField oldPassword;
    private TextButton menuTitle;
    private TextButton showUserInfo;
    private TextField oldEmail;
    private TextField newEmail;
    private TextButton changeNickName;
    private TextField NickName;
    private Label error;
    public Table table;
    public Table buttons;
    private Image avatar;
    private Label nickName;

    public MainMenu(MainMenuController mainMenuController, Skin skin) {

        this.controller = mainMenuController;
        this.nickName = new Label(App.getLoggedInUser().getNickName(), skin);
        nickName.setColor(0, 1, 0, 1);
        nickName.setFontScale(2);
        nickName.setPosition(1750, 800);
        this.avatar = new Image(new Texture(App.getLoggedInUser().getAvatar()));
        avatar.setSize(200, 200);
        avatar.setPosition(1700, 850);
        this.gameMenuButton = new TextButton("Game menu", skin);
        this.profileMenuButton = new TextButton("Profile menu", skin);
        this.menuTitle = new TextButton("Main menu", skin);
        menuTitle.setColor(0, 0, 1, 1);
        this.logoutAndGotoLoginMenuButton = new TextButton("log out", skin);
        this.table = new Table().top().left();
        this.skin = skin;
        this.buttons = new Table().center();
        controller.setView(this);


    }


    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        buttons.setFillParent(true);
        table.add(menuTitle).left();
        buttons.row().pad(10, 0, 10, 0);
        buttons.setFillParent(true);
        buttons.add(gameMenuButton).width(500);
        buttons.row().pad(10, 0, 10, 0);
        buttons.add(profileMenuButton).width(500);
        buttons.row().pad(10, 0, 10, 0);
        buttons.add(logoutAndGotoLoginMenuButton).width(500);
        buttons.row().pad(10, 0, 10, 0);
        buttons.row().pad(10, 0, 10, 0);
        stage.addActor(nickName);
        stage.addActor(avatar);
        stage.addActor(table);
        stage.addActor(buttons);


    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
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

    public void run(Scanner scanner) {

    }

    public TextButton getBackButton() {
        return backButton;
    }

    public void setBackButton(TextButton backButton) {
        this.backButton = backButton;
    }

    public TextButton getProfileMenuButton() {
        return profileMenuButton;
    }

    public void setProfileMenuButton(TextButton profileMenuButton) {
        this.profileMenuButton = profileMenuButton;
    }

    public TextButton getGameMenuButton() {
        return gameMenuButton;
    }

    public void setGameMenuButton(TextButton gameMenuButton) {
        this.gameMenuButton = gameMenuButton;
    }

    public TextButton getLogoutAndGotoLoginMenuButton() {
        return logoutAndGotoLoginMenuButton;
    }

    public void setLogoutAndGotoLoginMenuButton(TextButton logoutAndGotoLoginMenuButton) {
        this.logoutAndGotoLoginMenuButton = logoutAndGotoLoginMenuButton;
    }


}
