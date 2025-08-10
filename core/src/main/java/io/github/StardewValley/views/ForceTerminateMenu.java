package io.github.StardewValley.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import io.github.StardewValley.controllers.ForceTerminateController;

public class ForceTerminateMenu implements Screen {
    private Stage stage;
    private final ForceTerminateController controller;
    private final Label titleLabel;
    private final TextButton yesButton;
    private final TextButton noButton;
    private final Label errorLabel;

    private boolean hasVoted = false;
    private boolean vote = false;

    private float refreshTimer = 0f;       // elapsed time counter
    private final float refreshInterval = 3f; // seconds between auto-refreshes

    public ForceTerminateMenu(ForceTerminateController controller, Skin skin) {
        this.controller = controller;
        this.controller.setView(this);

        this.titleLabel = new Label("Force Terminate Game", skin);

        this.yesButton = new TextButton("Yes", skin);
        this.yesButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!hasVoted) {
                    hasVoted = true;
                    vote = true;
                    controller.vote(true);
                    errorLabel.setText("Your vote has been placed.\nWaiting for others...");
                } else {
                    errorLabel.setText("You have already voted.\nPlease wait for others...");
                    errorLabel.setColor(255, 0, 0, 1);
                }
            }
        });

        this.noButton = new TextButton("No", skin);
        this.noButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!hasVoted) {
                    hasVoted = true;
                    vote = false;
                    controller.vote(false);
                    errorLabel.setText("Your vote has been placed.\nWaiting for others...");
                } else {
                    errorLabel.setText("You have already voted.\nPlease wait for others...");
                    errorLabel.setColor(255, 0, 0, 1);
                }
            }
        });

        this.errorLabel = new Label("", skin);
        this.errorLabel.setAlignment(Align.center);
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.add(titleLabel).pad(10).row();
        table.add(yesButton).pad(10).row();
        table.add(noButton).pad(10).row();
        table.add(errorLabel).padTop(30).fillX().row();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();

        // Auto-refresh logic
        if (hasVoted) {
            refreshTimer += delta;
            if (refreshTimer >= refreshInterval) {
                refreshTimer = 0f;
                controller.vote(vote); // re-check voting status
            }
        }
    }

    public Label getErrorLabel() {
        return errorLabel;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
