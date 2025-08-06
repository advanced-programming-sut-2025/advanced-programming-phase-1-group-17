package io.github.StardewValley.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.Main;
import io.github.StardewValley.controllers.LoginMenuController;


import java.util.Scanner;


public class LoginMenu implements Screen {
    LoginMenuController controller ;
    private  TextButton backButton;
    private Table buttonsTable;
    private Stage stage;
    private Skin skin;
    private  TextButton userNameButton;
    private  TextButton passwordButton;
    private  TextButton forgotPasswordButton;
    private  TextField userName;
    private TextField answer;
    private  TextButton loginButton;
    private  TextField password;
    private  TextButton MenuTitle;
    private  TextButton showUserInfo;
    private Label error;
    private Table table;
    private Table buttons;
    private CheckBox checkBox;




    public LoginMenu(LoginMenuController controller, Skin skin) {
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
        this.checkBox = new CheckBox("stay loggedIn", skin);
        this.checkBox.setChecked(false);
        checkBox.setColor(0, 0, 1, 1);
        this.answer = new TextField("", skin);
        this.answer.setMessageText("enter your answer");
        this.backButton = new TextButton("Back", skin);
        backButton.setColor(0, 1, 0, 1);
        this.error = new Label("", skin);
        error.setColor(1, 0, 0, 1);
        this.table = new Table();
        this.skin = skin;
        buttons = new Table().center();
        password.setVisible(false);
        userName.setVisible(false);
        answer.setVisible(false);


        controller.setView(this);
    }

    @Override
    public void show() {
        //stage = new Stage(new ScreenViewport());
        this.stage = new Stage(new FitViewport(1920, 1080));
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        table.top().left();
        table.add(MenuTitle).left();
        table.add(backButton).right().padLeft(1550);
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
        buttons.add(answer).width(500);
        buttons.row().pad(10, 0, 10, 0);
        buttons.add(loginButton).width(500);
        buttons.row().pad(10, 0, 10, 0);
        buttons.add(checkBox).width(500);
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
    public void setError(String error) {
        this.error.setText(error);
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public TextField getAnswer() {
        return answer;
    }

    public void setAnswer(TextField answer) {
        this.answer = answer;
    }

    public Label getError() {
        return error;
    }
}
