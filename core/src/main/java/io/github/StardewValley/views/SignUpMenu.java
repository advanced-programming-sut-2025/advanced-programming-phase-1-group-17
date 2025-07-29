package io.github.StardewValley.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.GameAssetManagerClient;
import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.Main;
import io.github.StardewValley.controllers.SecurityQuestionMenuController;
import io.github.StardewValley.controllers.SignUpMenuController;
import io.github.StardewValley.shared.models.Result;
import io.github.StardewValley.shared.models.enums.Gender;


public class SignUpMenu implements Screen {
    private final SignUpMenuController controller;
    private Stage stage;

    private final Label titleLabel;
    private final TextField usernameField;
    private final TextField passwordField;
    private final TextField passwordConfirmField;
    private final TextField nickNameField;
    private final TextField emailField;


    private final Table genderTable;
    private final ScrollPane genderPane;
    private Gender selectedGender;

    private final Label errorLabel;
    private final TextButton randomPasswordGenerationButton;
    private final TextButton registerButton;
    private final TextButton mainMenuButton;

    private final Table mainTable;

    public SignUpMenu(SignUpMenuController controller, Skin skin) {
        this.controller = controller;

        this.titleLabel = new Label("Signup Menu", skin);

        this.usernameField = new TextField("", skin);
        this.usernameField.setMessageText("Username");

        this.passwordField = new TextField("", skin);
        this.passwordField.setMessageText("Password");
        this.passwordField.setPasswordMode(true);
        this.passwordField.setPasswordCharacter('*');

        this.passwordConfirmField = new TextField("", skin);
        this.passwordConfirmField.setMessageText("Confirm Password");
        this.passwordConfirmField.setPasswordMode(true);
        this.passwordConfirmField.setPasswordCharacter('*');

        this.nickNameField = new TextField("", skin);
        this.nickNameField.setMessageText("Nickname");

        this.emailField = new TextField("", skin);
        this.emailField.setMessageText("Email");




        this.genderTable = new Table();
        for (Gender gender : Gender.values()) {
            TextButton genderButton = new TextButton(gender.name(), skin);
            genderButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selectedGender = gender;
                }
            });
            genderTable.add(genderButton).pad(4).fillX().row();
        }
        this.genderPane = new ScrollPane(genderTable, skin);

        this.errorLabel = new Label("", skin);
        this.errorLabel.setAlignment(Align.center);

        this.randomPasswordGenerationButton = new TextButton("Random Password", skin);
        randomPasswordGenerationButton.setColor(0,0,1,1);
        this.randomPasswordGenerationButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String strongRandomPassword = controller.generateStrongPassword(12);
                passwordField.setText(strongRandomPassword);
                passwordConfirmField.setText(strongRandomPassword);
                errorLabel.setText("New Password: %s".formatted(strongRandomPassword));
                //errorLabel.setColor();
            }
        });

        this.registerButton = new TextButton("Register", skin);
        registerButton.setColor(0,0,1,1);
        this.registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Result result = controller.register(
                    usernameField.getText(),
                    passwordField.getText(),
                    passwordConfirmField.getText(),
                    nickNameField.getText(),
                    emailField.getText(),
                    selectedGender
                );
                if (result.isSuccessful()) {
                    Main.getMain().getScreen().dispose();
                    SecurityQuestionMenuController securityQuestionMenuController = new SecurityQuestionMenuController();
                    //TODO
                    //securityQuestionMenuController.setUser(App.getUserWithUsername(usernameField.getText()));
                    Main.getMain().setScreen(new SecurityQuestionMenu(
                        securityQuestionMenuController,
                          GameAssetManagerClient.getGameAssetManager().getSkin()));
                }
                errorLabel.setText(result.getMessage());
            }
        });

        this.mainMenuButton = new TextButton("Login Menu", skin);
        mainMenuButton.setColor(0,0,1,1);
        this.mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.enterLoginMenu();
            }
        });

        this.mainTable = new Table();
    }


    @Override
    public void show() {
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        mainTable.setFillParent(true);
        mainTable.center().padTop(20);
        mainTable.defaults().pad(5).fillX().width(400);

        // Title
        mainTable.add(titleLabel).center().padBottom(10).row();

        // Fields
        mainTable.add(usernameField).row();
        mainTable.add(nickNameField).row();
        mainTable.add(emailField).row();
        mainTable.add(passwordField).row();
        mainTable.add(passwordConfirmField).row();


        // Gender section as horizontal row
        Table genderRow = new Table();
        genderRow.defaults().pad(4).fillX().expandX();
        for (Gender gender : Gender.values()) {
            TextButton genderButton = new TextButton(gender.name(),   GameAssetManagerClient.getGameAssetManager().getSkin());
            genderButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selectedGender = gender;
                }
            });
            genderRow.add(genderButton);
        }

        mainTable.add(new Label("Gender:", titleLabel.getStyle())).left().padTop(5).row();
        mainTable.add(genderRow).row();

        // Random password button
        mainTable.add(randomPasswordGenerationButton).padTop(5).row();

        // Register and Main Menu buttons
        Table buttonRow = new Table();
        buttonRow.defaults().pad(5).expandX().fillX();
        buttonRow.add(registerButton);
        buttonRow.add(mainMenuButton);
        mainTable.add(buttonRow).padTop(10).row();

        // Error label
        mainTable.add(errorLabel).center().padTop(5).row();

        // Add to stage directly (no scroll)
        stage.addActor(mainTable);
    }




    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
    }

    public Label getErrorLabel() {
        return errorLabel;
    }
}
