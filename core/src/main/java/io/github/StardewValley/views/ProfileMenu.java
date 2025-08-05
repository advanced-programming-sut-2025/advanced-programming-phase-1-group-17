package io.github.StardewValley.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.Main;
import io.github.StardewValley.controllers.ProfileMenuController;

import java.util.Scanner;


public class ProfileMenu implements Screen {
    private final TextButton backButton;
    private Table buttonsTable;
    private Stage stage;
    private Skin skin;
    private ProfileMenuController controller;
    private final TextButton changeUserNameButton;
    private final TextButton changePasswordButton;
    private final TextButton changeEmailButton;
    private final TextButton changeAvatarButton;
    private final TextField newUserName;
    private final TextField newPassword;
    private final TextField oldPassword;
    private final TextButton MenuTitle;
    private final TextButton showUserInfo;
    private final TextField oldEmail;
    private final TextField newEmail;
    private final TextButton changeNickName;
    private final TextField NickName;
    private Label error;
    public Table table;
    public Table buttons;
    private TextButton changeAvatar;


    public ProfileMenu(ProfileMenuController profileMenuController, Skin skin) {
        this.changeNickName = new TextButton("Change Nickname", skin);
        this.changeAvatar = new TextButton("Change Avatar", skin);
        changeAvatar.setColor(0,0,1,1);
        changeNickName.setColor(0,0,1,1);
        this.NickName = new TextField("", skin);
        NickName.setMessageText("Enter new nickname");
        this.showUserInfo = new TextButton("user info", skin);
        showUserInfo.setColor(0,0,1,1);
        this.controller = profileMenuController;
        this.changeUserNameButton = new TextButton("ChangeUserName", skin);
        this.changeUserNameButton.setColor(0, 0, 1, 1);
        this.newUserName = new TextField("", skin);
        this.newUserName.setMessageText("enter new username");
        this.MenuTitle = new TextButton("ProfileMenu", skin);
        this.MenuTitle.setColor(0, 1, 0, 1);
        this.changePasswordButton = new TextButton("ChangePassword", skin);
        this.changePasswordButton.setColor(0, 0, 1, 1);
        this.newPassword = new TextField("", skin);
        this.oldPassword = new TextField("", skin);
        this.oldPassword.setMessageText("enter old password");
        this.newPassword.setMessageText("enter new password");
        this.changeEmailButton = new TextButton("changeEmail", skin);
        this.changeEmailButton.setColor(0, 0, 1, 1);
        this.oldEmail = new TextField("", skin);
        this.oldEmail.setMessageText("enter your old email");
        this.newEmail = new TextField("", skin);
        this.newEmail.setMessageText("enter new email");
        this.changeAvatarButton = new TextButton("ChangeAvatar", skin);
        this.changeAvatarButton.setColor(0, 0, 1, 1);
        this.backButton = new TextButton("Back", skin);
        this.error = new Label("", skin);
        error.setColor(1, 0, 0, 1);
        this.table = new Table();
        this.skin = skin;
        buttons = new Table().center();
        oldPassword.setVisible(false);
        newPassword.setVisible(false);
        newUserName.setVisible(false);
        oldEmail.setVisible(false);
        newEmail.setVisible(false);
        NickName.setVisible(false);
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
        buttons.add(changeUserNameButton).width(500);
        buttons.add(newUserName).width(500);
        buttons.row().pad(10, 0, 10, 0);
        buttons.add(changePasswordButton).width(500);
        buttons.add(oldPassword).width(500);
        buttons.add(newPassword).width(500);
        buttons.row().pad(10, 0, 10, 0);
        buttons.add(changeEmailButton).width(500);
        buttons.add(oldEmail).width(500);
        buttons.add(newEmail).width(500);
        buttons.row().pad(10, 0, 10, 0);
        buttons.add(showUserInfo).width(500);
        buttons.row().pad(10, 0, 10, 0);
        buttons.add(changeNickName).width(500);
        buttons.add(NickName).width(500);
        buttons.row().pad(10, 0, 10, 0);
        buttons.add(changeAvatar).width(500);
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

    public Table getButtonsTable() {
        return buttonsTable;
    }

    public void setButtonsTable(Table buttonsTable) {
        this.buttonsTable = buttonsTable;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    public void setSkin(Skin skin) {
        this.skin = skin;
    }

    public ProfileMenuController getController() {
        return controller;
    }

    public void setController(ProfileMenuController controller) {
        this.controller = controller;
    }

    public TextButton getChangeUserNameButton() {
        return changeUserNameButton;
    }

    public TextButton getChangePasswordButton() {
        return changePasswordButton;
    }

    public TextButton getChangeEmailButton() {
        return changeEmailButton;
    }

    public TextButton getChangeAvatarButton() {
        return changeAvatarButton;
    }

    public TextField getNewUserName() {
        return newUserName;
    }

    public TextField getNewPassword() {
        return newPassword;
    }

    public TextField getOldPassword() {
        return oldPassword;
    }

    public TextButton getMenuTitle() {
        return MenuTitle;
    }

    public TextField getOldEmail() {
        return oldEmail;
    }
    public TextField getNewEmail() {
        return newEmail;
    }

    public Label getError() {
        return error;
    }


    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Table getButtons() {
        return buttons;
    }

    public void setButtons(Table buttons) {
        this.buttons = buttons;
    }

    public Skin getSkin() {
        return skin;
    }

    public void setError(String error) {
        this.error.setText(error);

    }

    public TextButton getShowUserInfo() {
        return showUserInfo;
    }

    public TextButton getChangeNickName() {
        return changeNickName;
    }

    public TextField getNickName() {
        return NickName;
    }

    public TextButton getChangeAvatar() {
        return changeAvatar;
    }
}
