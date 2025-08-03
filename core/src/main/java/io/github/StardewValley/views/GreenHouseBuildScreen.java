package io.github.StardewValley.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.controllers.GreenHouseBuildController;

public class GreenHouseBuildScreen implements Screen {
    private final Stage stage;
    private final GreenHouseBuildController controller;
    private final Label titleLabel;
    private final Label messageLabel;
    private final TextButton buildButton;
    private final TextButton backButton;

    public GreenHouseBuildScreen(GreenHouseBuildController controller, Skin skin) {
        this.controller = controller;
        this.controller.setView(this);

        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        stage.addActor(mainTable);

        titleLabel = new Label("Build Greenhouse", skin, "title");
        messageLabel = new Label("", skin);

        buildButton = new TextButton("Build (1000 coin + 500 wood)", skin);
        backButton = new TextButton("Back", skin);

        buildButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.onBuildClicked();
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.onBackClicked();
            }
        });

        mainTable.add(titleLabel).colspan(2).padBottom(20).row();
        mainTable.add(buildButton).pad(10);
        mainTable.add(backButton).pad(10).row();
        mainTable.add(messageLabel).colspan(2).padTop(20);
    }

    public void showMessage(String msg) {
        messageLabel.setText(msg);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        controller.handlePlayerInput();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { stage.dispose(); }


    public Label getMessageLabel() {
        return messageLabel;
    }
}
