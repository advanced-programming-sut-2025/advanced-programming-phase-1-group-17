package io.github.StardewValley.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.GameClient;
import io.github.StardewValley.Main;
import io.github.StardewValley.controllers.MainMenuController;

import java.awt.datatransfer.FlavorEvent;

public class MainMenu implements Screen {
    private TextButton backButton;
    private Stage stage;
    private MainMenuController controller;
    private TextButton profileMenuButton;
    private TextButton gameMenuButton;
    private TextButton logoutAndGotoLoginMenuButton;
    private TextButton loadSavedGamesButton;
    private TextButton resumeOngoingGameButton;
    private TextButton menuTitle;
    private Label errorLabel;
    public Table table;
    public Table buttons;
    private Image avatar;
    private Label nickName;

    public MainMenu(MainMenuController mainMenuController, Skin skin) {

        this.controller = mainMenuController;
        try {
            this.nickName = new Label(GameClient.getGameStateApiClient().getUserWithUserDTO().getNickname(), skin);
        } catch (Exception e) {
            this.nickName = new Label("", skin);
        }
        nickName.setColor(0, 1, 0, 1);
        nickName.setFontScale(2);
        nickName.setPosition(1750, 800);
        try {
        this.avatar = new Image(new Texture(GameClient.getGameStateApiClient().getUserWithUserDTO().getAvatar()));
        }catch (Exception e){
            this.avatar = new Image(new Texture(Gdx.files.internal("avatar/avatar1.jpg")));
        }
        avatar.setSize(200, 200);
        avatar.setPosition(1700, 850);
        this.gameMenuButton = new TextButton("Lobby", skin);
        this.profileMenuButton = new TextButton("Profile menu", skin);
        this.loadSavedGamesButton = new TextButton("Load Saved Games", skin);
        this.resumeOngoingGameButton = new TextButton("Resume Ongoing Game", skin);
        this.menuTitle = new TextButton("Main menu", skin);
        this.errorLabel = new Label("", skin);
        this.errorLabel.setColor(255, 0, 0, 1);


        menuTitle.setColor(0, 0, 1, 1);
        this.logoutAndGotoLoginMenuButton = new TextButton("log out", skin);
        this.table = new Table().top().left();
        this.buttons = new Table().center();
        controller.setView(this);


    }


    @Override
    public void show() {
        //stage = new Stage(new ScreenViewport());
        this.stage = new Stage(new FitViewport(1920, 1080));
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        buttons.setFillParent(true);
        table.add(menuTitle).left();
        buttons.row().pad(10, 0, 10, 0);
        buttons.setFillParent(true);
        buttons.add(gameMenuButton).width(500);
        buttons.row().pad(10, 0, 10, 0);
        buttons.add(loadSavedGamesButton).width(500);
        buttons.row().pad(10, 0, 10, 0);
        buttons.add(resumeOngoingGameButton).width(500);
        buttons.row().pad(10, 0, 10, 0);
        buttons.add(profileMenuButton).width(500);
        buttons.row().pad(10, 0, 10, 0);
        buttons.add(logoutAndGotoLoginMenuButton).width(500);
        buttons.row().pad(10, 0, 10, 0);
        buttons.add(errorLabel).width(500);
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

    public TextButton getLoadSavedGamesButton() {
        return loadSavedGamesButton;
    }

    public TextButton getResumeOngoingGameButton() {
        return resumeOngoingGameButton;
    }

    public Label getErrorLabel() {
        return errorLabel;
    }
}
