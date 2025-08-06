package io.github.StardewValley.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.controllers.UIControllers.CheatCodeTerminalController;

public class CheatCodeTerminal implements Screen {
    private Stage stage;
    private final CheatCodeTerminalController controller;
    private final Label titleLabel;

    private final TextArea outputArea;
    private final TextField inputField;
    private final ScrollPane scrollPane;
    private final TextButton exitButton;

    private final Table mainTable;

    public CheatCodeTerminal(CheatCodeTerminalController controller, Skin skin) {
        this.controller = controller;
        controller.setView(this);
        this.titleLabel = new Label("Cheat Code Terminal", skin);

        this.outputArea = new TextArea("", skin);
        outputArea.setDisabled(true); // Make it read-only
        this.scrollPane = new ScrollPane(outputArea, skin);

        this.inputField = new TextField("", skin);
        this.inputField.setMessageText("Command");

        inputField.setTextFieldListener((field, key) -> {
            if (key == '\n' || key == '\r') {
                String command = inputField.getText();
                controller.handleCommand(command);
                inputField.setText("");
            }
        });

        inputField.addListener(new FocusListener() {
            @Override
            public void keyboardFocusChanged(FocusListener.FocusEvent event, Actor actor, boolean focused) {
                if (!focused) {
                    stage.setKeyboardFocus(inputField);
                }
            }
        });

        this.exitButton = new TextButton("Exit", skin);
        this.exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.exit();
            }
        });
        this.mainTable = new Table();
    }

    @Override
    public void show() {
        //stage = new Stage(new ScreenViewport());
        stage = new Stage(new FitViewport(1920, 1080));
        Gdx.input.setInputProcessor(stage);
        mainTable.setFillParent(true);

        mainTable.add(titleLabel).colspan(2).padTop(20).padBottom(30).center().row();
        mainTable.row().expand().fill().colspan(2).center();
        mainTable.add(scrollPane).colspan(2);
        mainTable.row();
        mainTable.add(inputField).fillX();
        mainTable.add(exitButton).right();

        stage.addActor(mainTable);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(v);
        stage.draw();
        controller.handlePlayerInput();
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

    public TextArea getOutputArea() {
        return outputArea;
    }

    public TextField getInputField() {
        return inputField;
    }
}
