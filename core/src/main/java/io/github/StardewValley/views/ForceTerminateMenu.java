package io.github.StardewValley.views;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.StardewValley.controllers.ForceTerminateController;

public class ForceTerminateMenu implements Screen {
    private Stage stage;
    private final ForceTerminateController controller;
    private final Label titleLabel;
    private final TextButton yesButton;
    private final TextButton noButton;
    private final Label errorLabel;

    private boolean hasVoted = false;

    public ForceTerminateMenu(ForceTerminateController controller, Skin skin) {
        this.controller = controller;

        this.titleLabel = new Label("Force Terminate Game", skin);

        this.yesButton = new TextButton("Yes", skin);
        this.yesButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!hasVoted) {
                    hasVoted = true;
                    controller.vote(true);
                    errorLabel.setText("Your vote has been placed.\nWait for other users to vote.");
                } else {
                    errorLabel.setText("You have already voted.\nPlease wait for other users to vote.");
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
                    controller.vote(false);
                    errorLabel.setText("Your vote has been placed.\nWait for other users to vote.");
                } else {
                    errorLabel.setText("You have already voted.\nPlease wait for other users to vote.");
                    errorLabel.setColor(255, 0, 0, 1);
                }
            }
        });

        this.errorLabel = new Label("", skin);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

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
}
