package io.github.StardewValley.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.Main;
import io.github.StardewValley.controllers.GameMenuController;
import io.github.StardewValley.shared.models.App;

import java.util.Scanner;

public class GameMenu implements Screen {
    private final GameMenuController controller;
    private Stage stage;
    private Skin skin;
    private final Table table;
    private final Table buttons;
    private final Table Users;
    private final Label error;
    private final TextButton MenuTitle;
    private final TextButton startGame;
    private final TextButton backButton;
    private final TextButton addUser;
    private final TextField UserName;
    private TextButton User1;
    private TextButton User2;
    private TextButton User3;
    private TextButton User4;
    private  TextButton deleteUser1;
    private TextButton deleteUser2;
    private TextButton deleteUser3;
    private TextButton deleteUser4;
    private TextButton loadGame;
    private Scanner scanner = new Scanner(System.in);




    public GameMenu(GameMenuController controller, Skin skin) {
        this.skin = skin;
        this.controller = controller;
        this.table = new Table();
        this.Users =new Table();
        this.buttons = new Table();
        this.error = new Label("", skin);
        error.setColor(1,0,0,1);
        MenuTitle = new TextButton("GameMenu", skin);
        MenuTitle.setColor(0,1,0,1);
        startGame = new TextButton("StartGame", skin);
        startGame.setColor(0,0,1,1);
        backButton = new TextButton("Back", skin);
        addUser = new TextButton("addUser", skin);
        addUser.setColor(0,0,1,1);
        UserName = new TextField("", skin);
        UserName.setMessageText("Enter Username of your friend");
        loadGame = new TextButton("Load last Game", skin);
        loadGame.setColor(0,0,1,1);
        User1 = new TextButton(App.getLoggedInUser().getUsername(), skin);
        User1.setColor(0,1,0,1);
        User2 = new TextButton("-", skin);
        User2.setColor(0,1,0,1);
        User3 = new TextButton("-", skin);
        User3.setColor(0,1,0,1);
        User4 = new TextButton("-", skin);
        User4.setColor(0,1,0,1);
        deleteUser1 = new TextButton("><", skin );
        deleteUser1.setColor(1,0,0,1);
        deleteUser2 = new TextButton("><", skin);
        deleteUser2.setColor(1,0,0,1);
        deleteUser3 = new TextButton("><", skin);
        deleteUser3.setColor(1,0,0,1);
        deleteUser4 = new TextButton("><", skin);
        deleteUser4.setColor(1,0,0,1);
        controller.setView(this);




    }
    @Override
    public void show() {
        //stage = new Stage(new ScreenViewport());
        stage = new Stage(new FitViewport(1920, 1080));
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        buttons.setFillParent(true);
        Users.setFillParent(true);
        table.left().top();
        table.add(MenuTitle);
        Users.center().top();
        Users.row().pad(10, 0, 10, 0);
        Users.add(User1).width(200);
        Users.add(User2).width(200);
        Users.add(User3).width(200);
        Users.add(User4).width(200);
        Users.row().pad(10, 0, 10, 0);
        Users.add(deleteUser1).width(100);
        Users.add(deleteUser2).width(100);
        Users.add(deleteUser3).width(100);
        Users.add(deleteUser4).width(100);
        buttons.center();
        error.setPosition(750, 650);
        buttons.row().pad(10, 0, 10, 0);
        buttons.add(startGame).width(300);
        buttons.add(addUser).width(300);
        buttons.add(UserName).width(300);
        buttons.row().pad(10, 0, 10, 0);
        buttons.add(loadGame).width(300);
        stage.addActor(error);
        stage.addActor(backButton);
        stage.addActor(Users);
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

    public GameMenuController getController() {
        return controller;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Skin getSkin() {
        return skin;
    }

    public void setSkin(Skin skin) {
        this.skin = skin;
    }

    public Table getTable() {
        return table;
    }

    public Table getButtons() {
        return buttons;
    }

    public Label getError() {
        return error;
    }

    public TextButton getMenuTitle() {
        return MenuTitle;
    }

    public TextButton getStartGame() {
        return startGame;
    }

    public TextButton getBackButton() {
        return backButton;
    }

    public TextButton getAddUser() {
        return addUser;
    }

    public TextField getUserName() {
        return UserName;
    }

    public TextButton getUser1() {
        return User1;
    }

    public void setUser1(TextButton user1) {
        User1 = user1;
    }

    public TextButton getUser2() {
        return User2;
    }

    public void setUser2(TextButton user2) {
        User2 = user2;
    }

    public TextButton getUser3() {
        return User3;
    }

    public void setUser3(TextButton user3) {
        User3 = user3;
    }

    public TextButton getUser4() {
        return User4;
    }

    public void setUser4(TextButton user4) {
        User4 = user4;
    }

    public TextButton getDeleteUser1() {
        return deleteUser1;
    }

    public void setDeleteUser1(TextButton deleteUser1) {
        this.deleteUser1 = deleteUser1;
    }

    public TextButton getDeleteUser2() {
        return deleteUser2;
    }

    public void setDeleteUser2(TextButton deleteUser2) {
        this.deleteUser2 = deleteUser2;
    }

    public TextButton getDeleteUser3() {
        return deleteUser3;
    }

    public void setDeleteUser3(TextButton deleteUser3) {
        this.deleteUser3 = deleteUser3;
    }

    public TextButton getDeleteUser4() {
        return deleteUser4;
    }

    public void setDeleteUser4(TextButton deleteUser4) {
        this.deleteUser4 = deleteUser4;
    }

    public Table getUsers() {
        return Users;
    }
    public void setError(String error) {
        this.error.setText(error);
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public TextButton getLoadGame() {
        return loadGame;
    }
}
