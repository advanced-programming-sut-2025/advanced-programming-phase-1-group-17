package io.github.StardewValley.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.Main;
import io.github.StardewValley.controllers.LoginMenuController;
import io.github.StardewValley.models.App;
import io.github.StardewValley.models.enums.LoginMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu implements AppMenu, Screen {
    LoginMenuController controller ;
    private  TextButton backButton;
    private Table buttonsTable;
    private Stage stage;
    private Skin skin;
    private  TextButton userNameButton;
    private  TextButton passwordButton;
    private  TextButton forgotPasswordButton;
    private  TextField userName;
    private  TextButton loginButton;
    private  TextField password;
    private  TextButton MenuTitle;
    private  TextButton showUserInfo;
    private Label error;
    public Table table;
    public Table buttons;



    public LoginMenu(LoginMenuController controller, Skin skin) {
//        Pixmap pixmap = new Pixmap(Gdx.files.internal("cursor2.png"));
//        Cursor customCursor = Gdx.graphics.newCursor(pixmap, 0, 0);
//        Gdx.graphics.setCursor(customCursor);
//        pixmap.dispose();
        this.controller = controller;
        this.userNameButton = new TextButton("UserName", skin);
        this.userNameButton.setColor(0, 0, 1, 1);
        this.userName = new TextField("", skin);
        this.userName.setMessageText("enter your username");
        this.MenuTitle = new TextButton("LoginMenu", skin);
        this.MenuTitle.setColor(0, 1, 0, 1);
        this.passwordButton = new TextButton("Password", skin);
        this.passwordButton.setColor(0, 0, 1, 1);
        this.password = new TextField("", skin);
        this.password.setMessageText("enter your password");
        this.forgotPasswordButton = new TextButton("forgot password", skin);
        this.forgotPasswordButton.setColor(0, 0, 1, 1);
        this.loginButton = new TextButton("Login", skin);
        this.loginButton.setColor(0, 0, 1, 1);

        this.backButton = new TextButton("Back", skin);
        this.error = new Label("", skin);
        error.setColor(1, 0, 0, 1);
        this.table = new Table();
        this.skin = skin;
        buttons = new Table().center();
        password.setVisible(false);
        userName.setVisible(false);

        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        table.top().left();
        table.add(MenuTitle).left();
        table.add(backButton).right().padLeft(1430);
        buttons.add(error);
        buttons.row().pad(10, 0, 10, 0);
        buttons.setFillParent(true);
        buttons.add(userNameButton).width(500);
        buttons.add(userName).width(500);
        buttons.row().pad(10, 0, 10, 0);
        buttons.add(passwordButton).width(500);
        buttons.add(password).width(500);
        buttons.row().pad(10, 0, 10, 0);
        buttons.add(forgotPasswordButton).width(500);
        buttons.row().pad(10, 0, 10, 0);
        buttons.add(loginButton).width(500);
        buttons.row().pad(10, 0, 10, 0);
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
        String command = scanner.nextLine();
        Matcher matcher;
        if ((matcher = LoginMenuCommands.Login.getMatcher(command)) != null) {
            System.out.println(controller.loginUser(command));
        } else if ((matcher = LoginMenuCommands.GoToSignUpMenu.getMatcher(command)) != null) {
            controller.goToSignUpMenu();
        } else if ((matcher = LoginMenuCommands.ForgetPassword.getMatcher(command)) != null) {
            System.out.println(controller.forgetPassword(command,scanner));
        } else if ((matcher = LoginMenuCommands.Exit.getMatcher(command)) != null){
            controller.exit();
        } else if (command.trim().equals("show current menu")) {
            System.out.println(App.getCurrentMenu().name());
        }
        else {
            System.out.println("invalid command");
        }
    }

    public TextButton getBackButton() {
        return backButton;
    }

    public void setBackButton(TextButton backButton) {
        this.backButton = backButton;
    }

    public TextButton getUserNameButton() {
        return userNameButton;
    }

    public void setUserNameButton(TextButton userNameButton) {
        this.userNameButton = userNameButton;
    }

    public TextButton getPasswordButton() {
        return passwordButton;
    }

    public void setPasswordButton(TextButton passwordButton) {
        this.passwordButton = passwordButton;
    }

    public TextField getUserName() {
        return userName;
    }

    public void setUserName(TextField userName) {
        this.userName = userName;
    }

    public TextField getPassword() {
        return password;
    }

    public void setPassword(TextField password) {
        this.password = password;
    }

    public TextButton getForgotPasswordButton() {
        return forgotPasswordButton;
    }

    public void setForgotPasswordButton(TextButton forgotPasswordButton) {
        this.forgotPasswordButton = forgotPasswordButton;
    }

    public TextButton getLoginButton() {
        return loginButton;
    }

    public void setLoginButton(TextButton loginButton) {
        this.loginButton = loginButton;
    }
}
